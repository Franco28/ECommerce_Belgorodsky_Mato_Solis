package ecommerce.model;

import ecommerce.enums.EstadoUsuario;
import ecommerce.enums.RolUsuario;
import ecommerce.util.ValidadorDominio;

import java.time.LocalDate;

public class Usuario {

    private int id;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenia;
    private LocalDate fechaAlta;
    private EstadoUsuario estado;
    private RolUsuario rol;

    public Usuario(int id, String nombre, String apellido, String email, String contrasenia,
            LocalDate fechaAlta, EstadoUsuario estado, RolUsuario rol) {
        setId(id);
        setNombre(nombre);
        setApellido(apellido);
        setEmail(email);
        setContrasenia(contrasenia);
        this.fechaAlta = ValidadorDominio.validarObjetoObligatorio(fechaAlta,
                "La fecha de alta es obligatoria.");
        this.estado = ValidadorDominio.validarObjetoObligatorio(estado,
                "El estado del usuario es obligatorio.");
        this.rol = ValidadorDominio.validarObjetoObligatorio(rol,
                "El rol del usuario es obligatorio.");
    }

    public void activar() {
        this.estado = EstadoUsuario.ACTIVO;
    }

    public void desactivar() {
        this.estado = EstadoUsuario.INACTIVO;
    }

    public boolean estaActivo() {
        return estado == EstadoUsuario.ACTIVO;
    }

    public boolean tieneRol(RolUsuario rol) {
        return this.rol == rol;
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
        ValidadorDominio.validarTextoObligatorio(nombre, "El nombre es obligatorio.");
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        ValidadorDominio.validarTextoObligatorio(apellido, "El apellido es obligatorio.");
        this.apellido = apellido.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        ValidadorDominio.validarEmail(email);
        this.email = email.trim().toLowerCase();
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        ValidadorDominio.validarTextoObligatorio(contrasenia, "La contrasena es obligatoria.");
        this.contrasenia = contrasenia;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = ValidadorDominio.validarObjetoObligatorio(rol, "El rol es obligatorio.");
    }
}
