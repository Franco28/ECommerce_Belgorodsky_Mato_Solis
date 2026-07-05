package ecommerce.model;

import ecommerce.enums.TipoMovimientoInventario;
import ecommerce.util.ValidadorDominio;

import java.time.LocalDateTime;

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
        this.producto = ValidadorDominio.validarObjetoObligatorio(producto,
                "El producto es obligatorio.");
        setCantidad(cantidad);
        this.tipo = ValidadorDominio.validarObjetoObligatorio(tipo,
                "El tipo de movimiento es obligatorio.");
        this.fecha = ValidadorDominio.validarObjetoObligatorio(fecha,
                "La fecha del movimiento es obligatoria.");
        setMotivo(motivo);
        setStockResultante(stockResultante);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        ValidadorDominio.validarIdNoNegativo(id, "El ID no puede ser negativo.");
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        ValidadorDominio.validarEnteroMayorACero(cantidad,
                "La cantidad del movimiento debe ser mayor a cero.");
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
        ValidadorDominio.validarTextoObligatorio(motivo,
                "El motivo del movimiento es obligatorio.");
        this.motivo = motivo.trim();
    }

    public int getStockResultante() {
        return stockResultante;
    }

    public void setStockResultante(int stockResultante) {
        ValidadorDominio.validarEnteroNoNegativo(stockResultante,
                "El stock resultante no puede ser negativo.");
        this.stockResultante = stockResultante;
    }
}
