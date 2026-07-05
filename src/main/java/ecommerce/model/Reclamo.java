package ecommerce.model;

import ecommerce.enums.EstadoReclamo;
import ecommerce.util.ValidadorDominio;

import java.time.LocalDateTime;

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
        this.cliente = ValidadorDominio.validarObjetoObligatorio(cliente,
                "El cliente es obligatorio.");
        this.pedidoAsociado = ValidadorDominio.validarObjetoObligatorio(pedidoAsociado,
                "El pedido asociado es obligatorio.");
        setMotivo(motivo);
        this.fecha = ValidadorDominio.validarObjetoObligatorio(fecha,
                "La fecha del reclamo es obligatoria.");
        this.estado = ValidadorDominio.validarObjetoObligatorio(estado,
                "El estado del reclamo es obligatorio.");
    }

    public void cambiarEstado(EstadoReclamo nuevoEstado) {
        this.estado = ValidadorDominio.validarObjetoObligatorio(nuevoEstado,
                "El nuevo estado del reclamo es obligatorio.");
    }

    public String getNumeroReclamo() {
        return numeroReclamo;
    }

    public void setNumeroReclamo(String numeroReclamo) {
        ValidadorDominio.validarTextoObligatorio(numeroReclamo,
                "El número de reclamo es obligatorio.");
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
        ValidadorDominio.validarTextoObligatorio(motivo,
                "El motivo del reclamo es obligatorio.");
        this.motivo = motivo.trim();
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoReclamo getEstado() {
        return estado;
    }
}
