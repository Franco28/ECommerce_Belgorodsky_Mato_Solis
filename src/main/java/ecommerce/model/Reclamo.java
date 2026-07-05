package ecommerce.model;

import ecommerce.enums.EstadoReclamo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reclamo {

    private String numeroReclamo;
    private Usuario cliente;
    private OrdenCompra pedidoAsociado;
    private String motivo;
    private LocalDateTime fecha;
    private EstadoReclamo estado;

    public Reclamo(String numeroReclamo, Usuario cliente, OrdenCompra pedidoAsociado,
            String motivo, LocalDateTime fecha, EstadoReclamo estado) {
        setNumeroReclamo(numeroReclamo);
        this.cliente = Objects.requireNonNull(cliente, "El cliente es obligatorio.");
        this.pedidoAsociado = Objects.requireNonNull(pedidoAsociado, "El pedido asociado es obligatorio.");
        setMotivo(motivo);
        this.fecha = Objects.requireNonNull(fecha, "La fecha del reclamo es obligatoria.");
        this.estado = Objects.requireNonNull(estado, "El estado del reclamo es obligatorio.");
    }

    public void cambiarEstado(EstadoReclamo nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "El nuevo estado del reclamo es obligatorio.");
    }

    public String getNumeroReclamo() {
        return numeroReclamo;
    }

    public void setNumeroReclamo(String numeroReclamo) {
        if (numeroReclamo == null || numeroReclamo.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de reclamo es obligatorio.");
        }
        this.numeroReclamo = numeroReclamo.trim().toUpperCase();
    }

    public Usuario getCliente() {
        return cliente;
    }

    public OrdenCompra getPedidoAsociado() {
        return pedidoAsociado;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        if (motivo == null || motivo.trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo del reclamo es obligatorio.");
        }
        this.motivo = motivo.trim();
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoReclamo getEstado() {
        return estado;
    }
}
