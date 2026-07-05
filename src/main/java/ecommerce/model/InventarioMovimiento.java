package ecommerce.model;

import ecommerce.enums.TipoMovimientoInventario;

import java.time.LocalDateTime;
import java.util.Objects;

public class InventarioMovimiento {

    private int id;
    private Producto producto;
    private int cantidad;
    private TipoMovimientoInventario tipo;
    private LocalDateTime fecha;
    private String motivo;
    private int stockResultante;

    public InventarioMovimiento(int id, Producto producto, int cantidad, TipoMovimientoInventario tipo,
            LocalDateTime fecha, String motivo, int stockResultante) {
        setId(id);
        this.producto = Objects.requireNonNull(producto, "El producto es obligatorio.");
        setCantidad(cantidad);
        this.tipo = Objects.requireNonNull(tipo, "El tipo de movimiento es obligatorio.");
        this.fecha = Objects.requireNonNull(fecha, "La fecha del movimiento es obligatoria.");
        setMotivo(motivo);
        setStockResultante(stockResultante);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID no puede ser negativo.");
        }
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad del movimiento debe ser mayor a cero.");
        }
        this.cantidad = cantidad;
    }

    public TipoMovimientoInventario getTipo() {
        return tipo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo del movimiento es obligatorio.");
        }
        this.motivo = motivo.trim();
    }

    public int getStockResultante() {
        return stockResultante;
    }

    public void setStockResultante(int stockResultante) {
        if (stockResultante < 0) {
            throw new IllegalArgumentException("El stock resultante no puede ser negativo.");
        }
        this.stockResultante = stockResultante;
    }
}
