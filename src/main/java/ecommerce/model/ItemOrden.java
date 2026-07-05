package ecommerce.model;

import ecommerce.util.ValidadorDominio;

public class ItemOrden {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public ItemOrden(Producto producto, int cantidad, double precioUnitario) {
        this.producto = ValidadorDominio.validarObjetoObligatorio(producto,
                "El producto es obligatorio.");
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        recalcularSubtotal();
    }

    private void recalcularSubtotal() {
        this.subtotal = this.precioUnitario * this.cantidad;
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
        recalcularSubtotal();
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        ValidadorDominio.validarDecimalMayorACero(precioUnitario,
                "El precio unitario debe ser mayor a cero.");
        this.precioUnitario = precioUnitario;
        recalcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }
}
