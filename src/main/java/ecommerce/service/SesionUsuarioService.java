package ecommerce.service;

import ecommerce.enums.RolUsuario;
import ecommerce.exception.PermisoDenegadoException;
import ecommerce.model.Usuario;
import ecommerce.util.ValidadorDominio;

import java.util.Optional;

public class SesionUsuarioService {

    private final SeguridadService seguridadService;
    private Usuario usuarioActual;

    public SesionUsuarioService(SeguridadService seguridadService) {
        this.seguridadService = ValidadorDominio.validarObjetoObligatorio(seguridadService,
                "El servicio de seguridad es obligatorio.");
    }

    public void iniciarSesion(Usuario usuario) {
        seguridadService.validarUsuarioActivo(usuario);
        this.usuarioActual = usuario;
    }

    public void cerrarSesion() {
        this.usuarioActual = null;
    }

    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    public Optional<Usuario> obtenerUsuarioActual() {
        return Optional.ofNullable(usuarioActual);
    }

    public Usuario requerirUsuarioActual() {
        if (usuarioActual == null) {
            throw new PermisoDenegadoException("Debe iniciar sesion para utilizar el sistema.");
        }
        return usuarioActual;
    }

    public void validarRol(RolUsuario rolRequerido) {
        seguridadService.validarRol(requerirUsuarioActual(), rolRequerido);
    }

    public void validarAlgunRol(RolUsuario... rolesPermitidos) {
        seguridadService.validarAlgunRol(requerirUsuarioActual(), rolesPermitidos);
    }
}
