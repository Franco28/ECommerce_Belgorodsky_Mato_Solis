package ecommerce.service;

import ecommerce.enums.RolUsuario;
import ecommerce.exception.DatosInvalidosException;
import ecommerce.exception.PermisoDenegadoException;
import ecommerce.model.Usuario;
import ecommerce.util.ValidadorDominio;

public class AutenticacionService {

    private final UsuarioService usuarioService;
    private final SesionUsuarioService sesionUsuarioService;

    public AutenticacionService(UsuarioService usuarioService, SesionUsuarioService sesionUsuarioService) {
        this.usuarioService = ValidadorDominio.validarObjetoObligatorio(usuarioService,
                "El servicio de usuarios es obligatorio.");
        this.sesionUsuarioService = ValidadorDominio.validarObjetoObligatorio(sesionUsuarioService,
                "El servicio de sesión es obligatorio.");
    }

    public Usuario iniciarSesion(String email, String contrasenia) {
        ValidadorDominio.validarEmail(email);
        ValidadorDominio.validarTextoObligatorio(contrasenia, "La contraseña es obligatoria.");

        Usuario usuario = usuarioService.buscarPorEmail(email);

        if (!usuario.getContrasenia().equals(contrasenia)) {
            throw new DatosInvalidosException("Email o contraseña incorrectos.");
        }

        sesionUsuarioService.iniciarSesion(usuario);
        return usuario;
    }

    public Usuario registrarPrimerAdministrador(String nombre, String apellido, String email, String contrasenia) {
        if (!usuarioService.listarUsuarios().isEmpty()) {
            throw new PermisoDenegadoException("El administrador inicial solo puede crearse cuando no existen usuarios.");
        }

        Usuario usuario = usuarioService.registrarUsuario(nombre, apellido, email, contrasenia, RolUsuario.ADMINISTRADOR);
        sesionUsuarioService.iniciarSesion(usuario);
        return usuario;
    }

    public boolean requiereAdministradorInicial() {
        return usuarioService.listarUsuarios().isEmpty();
    }

    public void cerrarSesion() {
        sesionUsuarioService.cerrarSesion();
    }
}
