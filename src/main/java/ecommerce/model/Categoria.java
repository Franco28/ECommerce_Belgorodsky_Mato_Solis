package ecommerce.model;

import ecommerce.enums.EstadoCategoria;
import ecommerce.util.ValidadorDominio;

public class Categoria {

    private int id;
    private String nombre;
    private String descripcion;
    private EstadoCategoria estado;

    public Categoria(int id, String nombre, String descripcion, EstadoCategoria estado) {
        setId(id);
        setNombre(nombre);
        setDescripcion(descripcion);
        this.estado = ValidadorDominio.validarObjetoObligatorio(estado,
                "El estado de la categoria es obligatorio.");
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
        ValidadorDominio.validarIdNoNegativo(id, "El ID no puede ser negativo.");
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        ValidadorDominio.validarTextoObligatorio(nombre, "El nombre de la categoria es obligatorio.");
        this.nombre = nombre.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        ValidadorDominio.validarTextoObligatorio(descripcion, "La descripcion de la categoria es obligatoria.");
        this.descripcion = descripcion.trim();
    }

    public EstadoCategoria getEstado() {
        return estado;
    }
}
