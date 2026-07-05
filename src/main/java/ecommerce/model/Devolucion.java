package ecommerce.model;

import ecommerce.enums.EstadoDevolucion;
import ecommerce.util.ValidadorDominio;

import java.time.LocalDateTime;

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
        this.cliente = ValidadorDominio.validarObjetoObligatorio(cliente,
                "El cliente es obligatorio.");
        this.producto = ValidadorDominio.validarObjetoObligatorio(producto,
                "El producto es obligatorio.");
        setMotivo(motivo);
        this.fecha = ValidadorDominio.validarObjetoObligatorio(fecha,
                "La fecha de la devolucion es obligatoria.");
        this.estado = ValidadorDominio.validarObjetoObligatorio(estado,
                "El estado de la devolucion es obligatorio.");
    }

    public void cambiarEstado(EstadoDevolucion nuevoEstado) {
        this.estado = ValidadorDominio.validarObjetoObligatorio(nuevoEstado,
                "El nuevo estado de la devolucion es obligatorio.");
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

    public Producto getProducto() {
        return producto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        ValidadorDominio.validarTextoObligatorio(motivo,
                "El motivo de la devolucion es obligatorio.");
        this.motivo = motivo.trim();
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoDevolucion getEstado() {
        return estado;
    }
}
