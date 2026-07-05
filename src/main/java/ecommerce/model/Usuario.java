package ecommerce.model;

import ecommerce.enums.EstadoUsuario;
import ecommerce.enums.RolUsuario;

import java.time.LocalDate;
import java.util.Objects;

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
        this.fechaAlta = Objects.requireNonNull(fechaAlta, "La fecha de alta es obligatoria.");
        this.estado = Objects.requireNonNull(estado, "El estado del usuario es obligatorio.");
        this.rol = Objects.requireNonNull(rol, "El rol del usuario es obligatorio.");
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
        if (id < 0) {
            throw new IllegalArgumentException("El ID no puede ser negativo.");
        }
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarTextoObligatorio(nombre, "El nombre es obligatorio.");
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        validarTextoObligatorio(apellido, "El apellido es obligatorio.");
        this.apellido = apellido.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validarTextoObligatorio(email, "El email es obligatorio.");
        if (!email.contains("@")) {
            throw new IllegalArgumentException("El email no tiene un formato válido.");
        }
        this.email = email.trim().toLowerCase();
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        validarTextoObligatorio(contrasenia, "La contraseña es obligatoria.");
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
        this.rol = Objects.requireNonNull(rol, "El rol es obligatorio.");
    }

    private static void validarTextoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
