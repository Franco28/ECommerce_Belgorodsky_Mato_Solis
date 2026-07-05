package ecommerce.model;

import java.util.Objects;

public class ItemOrden {

    private Producto producto;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    public ItemOrden(Producto producto, int cantidad, double precioUnitario) {
        this.producto = Objects.requireNonNull(producto, "El producto es obligatorio.");
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        this.subtotal = this.precioUnitario * this.cantidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario <= 0) {
            throw new IllegalArgumentException("El precio unitario debe ser mayor a cero.");
        }
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }
}
