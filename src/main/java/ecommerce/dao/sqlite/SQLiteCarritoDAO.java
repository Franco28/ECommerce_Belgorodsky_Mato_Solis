package ecommerce.dao.sqlite;

import ecommerce.dao.interfaces.CarritoDAO;
import ecommerce.exception.DatabaseException;
import ecommerce.model.Carrito;
import ecommerce.model.ItemCarrito;
import ecommerce.model.Producto;
import ecommerce.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class SQLiteCarritoDAO extends SQLiteBaseDAO implements CarritoDAO {

    private final SQLiteUsuarioDAO usuarioDAO;
    private final SQLiteProductoDAO productoDAO;

    public SQLiteCarritoDAO() {
        this.usuarioDAO = new SQLiteUsuarioDAO();
        this.productoDAO = new SQLiteProductoDAO();
    }

    @Override
    public Carrito buscarActivoPorCliente(int clienteId) {
        String sql = "SELECT id, usuario_id, fecha_creacion FROM carritos WHERE usuario_id = ? AND activo = 1 ORDER BY id DESC LIMIT 1";

        try (Connection connection = obtenerConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clienteId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return null;
                }

                Carrito carrito = mapearCarrito(resultSet);
                cargarItems(carrito);
                return carrito;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("No se pudo buscar el carrito activo del cliente.", ex);
        }
    }

    @Override
    public Carrito guardar(Carrito carrito) {
        if (carrito.getId() <= 0) {
            insertar(carrito);
        } else {
            actualizar(carrito);
        }
        reemplazarItems(carrito);
        return carrito;
    }

    @Override
    public void reemplazarItems(Carrito carrito) {
        if (carrito.getId() <= 0) {
            return;
        }

        String deleteSql = "DELETE FROM carrito_items WHERE carrito_id = ?";
        String insertSql = "INSERT INTO carrito_items (carrito_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (Connection connection = obtenerConexion()) {
            connection.setAutoCommit(false);

            try (PreparedStatement deleteStatement = connection.prepareStatement(deleteSql)) {
                deleteStatement.setInt(1, carrito.getId());
                deleteStatement.executeUpdate();
            }

            try (PreparedStatement insertStatement = connection.prepareStatement(insertSql)) {
                for (ItemCarrito item : carrito.getItems()) {
                    insertStatement.setInt(1, carrito.getId());
                    insertStatement.setInt(2, item.getProducto().getId());
                    insertStatement.setInt(3, item.getCantidad());
                    insertStatement.setDouble(4, item.getPrecioUnitario());
                    insertStatement.addBatch();
                }
                insertStatement.executeBatch();
            }

            connection.commit();
        } catch (SQLException ex) {
            throw new DatabaseException("No se pudieron guardar los items del carrito.", ex);
        }
    }

    @Override
    public void desactivar(int carritoId) {
        String sql = "UPDATE carritos SET activo = 0 WHERE id = ?";

        try (Connection connection = obtenerConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carritoId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException("No se pudo desactivar el carrito.", ex);
        }
    }

    private void insertar(Carrito carrito) {
        String sql = "INSERT INTO carritos (usuario_id, fecha_creacion, activo) VALUES (?, ?, 1)";

        try (Connection connection = obtenerConexion();
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, carrito.getCliente().getId());
            statement.setString(2, carrito.getFechaCreacion().toString());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    carrito.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("No se pudo guardar el carrito.", ex);
        }
    }

    private void actualizar(Carrito carrito) {
        String sql = "UPDATE carritos SET usuario_id = ?, fecha_creacion = ?, activo = 1 WHERE id = ?";

        try (Connection connection = obtenerConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carrito.getCliente().getId());
            statement.setString(2, carrito.getFechaCreacion().toString());
            statement.setInt(3, carrito.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DatabaseException("No se pudo actualizar el carrito.", ex);
        }
    }

    private Carrito mapearCarrito(ResultSet resultSet) throws SQLException {
        Usuario cliente = usuarioDAO.buscarPorId(resultSet.getInt("usuario_id"));
        LocalDateTime fechaCreacion = LocalDateTime.parse(resultSet.getString("fecha_creacion"));
        return new Carrito(resultSet.getInt("id"), cliente, fechaCreacion);
    }

    private void cargarItems(Carrito carrito) {
        String sql = "SELECT producto_id, cantidad, precio_unitario FROM carrito_items WHERE carrito_id = ?";

        try (Connection connection = obtenerConexion();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, carrito.getId());

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Producto producto = productoDAO.buscarPorId(resultSet.getInt("producto_id"));
                    carrito.agregarItemPersistido(producto,
                            resultSet.getInt("cantidad"),
                            resultSet.getDouble("precio_unitario"));
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("No se pudieron cargar los items del carrito.", ex);
        }
    }
}
