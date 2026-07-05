package ecommerce.model;

import ecommerce.enums.EstadoCategoria;

import java.util.Objects;

public class Categoria {

    private int id;
    private String nombre;
    private String descripcion;
    private EstadoCategoria estado;

    public Categoria(int id, String nombre, String descripcion, EstadoCategoria estado) {
        setId(id);
        setNombre(nombre);
        setDescripcion(descripcion);
        this.estado = Objects.requireNonNull(estado, "El estado de la categoría es obligatorio.");
    }

    public boolean estaActiva() {
        return estado == EstadoCategoria.ACTIVA;
    }

    public void activar() {
        this.estado = EstadoCategoria.ACTIVA;
    }

    public void desactivar() {
        this.estado = EstadoCategoria.INACTIVA;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarTextoObligatorio(nombre, "El nombre de la categoría es obligatorio.");
        this.nombre = nombre.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        validarTextoObligatorio(descripcion, "La descripción de la categoría es obligatoria.");
        this.descripcion = descripcion.trim();
    }

    public EstadoCategoria getEstado() {
        return estado;
    }

    private static void validarTextoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
