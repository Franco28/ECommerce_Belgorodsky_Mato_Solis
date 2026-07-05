package ecommerce.model;

import ecommerce.util.ValidadorDominio;

public class ItemCarrito {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;

    public ItemCarrito(Producto producto, int cantidad) {
        this.producto = ValidadorDominio.validarObjetoObligatorio(producto,
                "El producto es obligatorio.");
        setCantidad(cantidad);
        this.precioUnitario = producto.calcularPrecioFinal();
    }

    public double calcularSubtotal() {
        return precioUnitario * cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        ValidadorDominio.validarEnteroMayorACero(cantidad,
                "La cantidad debe ser mayor a cero.");
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }
}
