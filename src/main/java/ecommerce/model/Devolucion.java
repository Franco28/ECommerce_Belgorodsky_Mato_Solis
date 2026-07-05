package ecommerce.model;

import ecommerce.enums.EstadoDevolucion;

import java.time.LocalDateTime;
import java.util.Objects;

public class Devolucion {

    private int id;
    private Usuario cliente;
    private Producto producto;
    private String motivo;
    private LocalDateTime fecha;
    private EstadoDevolucion estado;

    public Devolucion(int id, Usuario cliente, Producto producto, String motivo,
            LocalDateTime fecha, EstadoDevolucion estado) {
        setId(id);
        this.cliente = Objects.requireNonNull(cliente, "El cliente es obligatorio.");
        this.producto = Objects.requireNonNull(producto, "El producto es obligatorio.");
        setMotivo(motivo);
        this.fecha = Objects.requireNonNull(fecha, "La fecha de la devolución es obligatoria.");
        this.estado = Objects.requireNonNull(estado, "El estado de la devolución es obligatorio.");
    }

    public void cambiarEstado(EstadoDevolucion nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "El nuevo estado de la devolución es obligatorio.");
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

    public Usuario getCliente() {
        return cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de la devolución es obligatorio.");
        }
        this.motivo = motivo.trim();
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoDevolucion getEstado() {
        return estado;
    }
}
