package ecommerce.service;

import ecommerce.dao.interfaces.CarritoDAO;
import ecommerce.dao.interfaces.ProductoDAO;
import ecommerce.enums.RolUsuario;
import ecommerce.exception.PermisoDenegadoException;
import ecommerce.model.Carrito;
import ecommerce.model.Producto;
import ecommerce.model.Usuario;
import ecommerce.util.ValidadorDominio;

/**
 * Servicio de carrito. Mantiene el carrito como objeto de dominio activo
 * y consulta productos persistidos para evitar operar con datos desactualizados.
 */
public class CarritoService {

    private final ProductoDAO productoDAO;
    private final SeguridadService seguridadService;
    private final CarritoDAO carritoDAO;

    public CarritoService(ProductoDAO productoDAO) {
        this(productoDAO, new SeguridadService(), null);
    }

    public CarritoService(ProductoDAO productoDAO, SeguridadService seguridadService) {
        this(productoDAO, seguridadService, null);
    }

    public CarritoService(ProductoDAO productoDAO, SeguridadService seguridadService, CarritoDAO carritoDAO) {
        this.productoDAO = ValidadorDominio.validarObjetoObligatorio(productoDAO,
                "El DAO de productos es obligatorio.");
        this.seguridadService = ValidadorDominio.validarObjetoObligatorio(seguridadService,
                "El servicio de seguridad es obligatorio.");
        this.carritoDAO = carritoDAO;
    }

    public Carrito crearCarrito(Usuario cliente) {
        seguridadService.validarRol(cliente, RolUsuario.CLIENTE);
        return new Carrito(0, cliente);
    }

    public void agregarProducto(Carrito carrito, String codigoProducto, int cantidad) {
        validarCarrito(carrito);
        ValidadorDominio.validarTextoObligatorio(codigoProducto,
                "El codigo del producto es obligatorio.");
        ValidadorDominio.validarEnteroMayorACero(cantidad,
                "La cantidad debe ser mayor a cero.");

        Producto producto = productoDAO.buscarPorCodigo(codigoProducto);
        carrito.agregarProducto(producto, cantidad);
        persistirCarrito(carrito);
    }

    public void modificarCantidad(Carrito carrito, String codigoProducto, int nuevaCantidad) {
        validarCarrito(carrito);
        Producto productoActualizado = productoDAO.buscarPorCodigo(codigoProducto);
        carrito.modificarCantidad(productoActualizado.getCodigo(), nuevaCantidad);
        persistirCarrito(carrito);
    }

    public void eliminarProducto(Carrito carrito, String codigoProducto) {
        validarCarrito(carrito);
        carrito.eliminarProducto(codigoProducto);
        persistirCarrito(carrito);
    }

    public void vaciarCarrito(Carrito carrito) {
        validarCarrito(carrito);
        carrito.vaciar();
        persistirCarrito(carrito);
    }

    public double calcularSubtotal(Carrito carrito) {
        validarCarrito(carrito);
        return carrito.calcularSubtotal();
    }

    public double calcularTotal(Carrito carrito) {
        validarCarrito(carrito);
        return carrito.calcularTotal();
    }

    public void validarCarritoParaCompra(Carrito carrito) {
        validarCarrito(carrito);
        carrito.validarNoVacio();
        carrito.getItems().forEach(item -> {
            Producto productoActualizado = productoDAO.buscarPorId(item.getProducto().getId());
            if (!productoActualizado.validarDisponibilidad(item.getCantidad())) {
                throw new ecommerce.exception.StockInsuficienteException(
                        "No hay stock suficiente para el producto " + productoActualizado.getCodigo() + ".");
            }
        });
    }

    public Carrito guardarCarrito(Carrito carrito) {
        validarCarrito(carrito);
        if (carritoDAO == null) {
            return carrito;
        }
        return carritoDAO.guardar(carrito);
    }

    private void persistirCarrito(Carrito carrito) {
        if (carritoDAO != null && carrito.getId() > 0) {
            carritoDAO.guardar(carrito);
        }
    }

    private void validarCarrito(Carrito carrito) {
        ValidadorDominio.validarObjetoObligatorio(carrito, "El carrito es obligatorio.");
        if (!carrito.getCliente().tieneRol(RolUsuario.CLIENTE)) {
            throw new PermisoDenegadoException("El carrito debe pertenecer a un cliente.");
        }
    }
}
