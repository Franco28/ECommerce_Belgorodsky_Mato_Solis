package ecommerce.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Calificacion {

    private int id;
    private Usuario cliente;
    private Producto producto;
    private int puntuacion;
    private String comentario;
    private LocalDateTime fecha;

    public Calificacion(int id, Usuario cliente, Producto producto, int puntuacion,
            String comentario, LocalDateTime fecha) {
        setId(id);
        this.cliente = Objects.requireNonNull(cliente, "El cliente es obligatorio.");
        this.producto = Objects.requireNonNull(producto, "El producto es obligatorio.");
        setPuntuacion(puntuacion);
        setComentario(comentario);
        this.fecha = Objects.requireNonNull(fecha, "La fecha de la calificación es obligatoria.");
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

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        if (comentario == null || comentario.trim().isEmpty()) {
            throw new IllegalArgumentException("El comentario es obligatorio.");
        }
        this.comentario = comentario.trim();
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
