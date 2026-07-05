package ecommerce.model;

import ecommerce.enums.RolUsuario;
import ecommerce.exception.CarritoVacioException;
import ecommerce.exception.PermisoDenegadoException;
import ecommerce.exception.ProductoNoDisponibleException;
import ecommerce.interfaces.Calculable;
import ecommerce.util.ValidadorDominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Carrito implements Calculable {

    private int id;
    private Usuario cliente;
    private List<ItemCarrito> items;
    private LocalDateTime fechaCreacion;

    public Carrito(int id, Usuario cliente) {
        setId(id);
        this.cliente = ValidadorDominio.validarObjetoObligatorio(cliente,
                "El cliente es obligatorio.");
        if (!cliente.tieneRol(RolUsuario.CLIENTE)) {
            throw new PermisoDenegadoException(
                    "El carrito solo puede pertenecer a un usuario cliente.");
        }
        this.items = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        ValidadorDominio.validarObjetoObligatorio(producto, "El producto es obligatorio.");
        ValidadorDominio.validarEnteroMayorACero(cantidad, "La cantidad debe ser mayor a cero.");

        int cantidadTotalSolicitada = cantidad + obtenerCantidadActual(producto.getCodigo());
        if (!producto.validarDisponibilidad(cantidadTotalSolicitada)) {
            throw new ProductoNoDisponibleException(
                    "El producto no tiene disponibilidad suficiente para la cantidad solicitada.");
        }

        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo().equals(producto.getCodigo())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }

        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(String codigoProducto) {
        ValidadorDominio.validarTextoObligatorio(codigoProducto,
                "El código del producto es obligatorio.");
        items.removeIf(item -> item.getProducto().getCodigo().equalsIgnoreCase(codigoProducto.trim()));
    }

    public void modificarCantidad(String codigoProducto, int nuevaCantidad) {
        ValidadorDominio.validarTextoObligatorio(codigoProducto,
                "El código del producto es obligatorio.");
        ValidadorDominio.validarEnteroMayorACero(nuevaCantidad,
                "La cantidad debe ser mayor a cero.");

        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo().equalsIgnoreCase(codigoProducto.trim())) {
                if (!item.getProducto().validarDisponibilidad(nuevaCantidad)) {
                    throw new ProductoNoDisponibleException(
                            "El producto no tiene stock suficiente para la nueva cantidad.");
                }
                item.setCantidad(nuevaCantidad);
                return;
            }
        }

        throw new ProductoNoDisponibleException("El producto indicado no existe en el carrito.");
    }

    public void vaciar() {
        items.clear();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public void validarNoVacio() {
        if (estaVacio()) {
            throw new CarritoVacioException("El carrito está vacío.");
        }
    }

    @Override
    public double calcularTotal() {
        return items.stream()
                .mapToDouble(ItemCarrito::calcularSubtotal)
                .sum();
    }

    public double calcularSubtotal() {
        return calcularTotal();
    }

    private int obtenerCantidadActual(String codigoProducto) {
        return items.stream()
                .filter(item -> item.getProducto().getCodigo().equals(codigoProducto))
                .mapToInt(ItemCarrito::getCantidad)
                .sum();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        ValidadorDominio.validarIdNoNegativo(id, "El ID no puede ser negativo.");
        this.id = id;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public List<ItemCarrito> getItems() {
        return Collections.unmodifiableList(items);
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}
