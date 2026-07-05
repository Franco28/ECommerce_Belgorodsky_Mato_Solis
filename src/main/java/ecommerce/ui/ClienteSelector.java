package ecommerce.ui;

import ecommerce.enums.RolUsuario;
import ecommerce.exception.DatosInvalidosException;
import ecommerce.model.Usuario;
import ecommerce.service.SesionUsuarioService;
import ecommerce.service.UsuarioService;

import java.util.List;
import java.util.Optional;

public class ClienteSelector {

    private final UsuarioService usuarioService;
    private final EntradaConsola entrada;
    private final SesionUsuarioService sesionUsuarioService;

    public ClienteSelector(UsuarioService usuarioService, EntradaConsola entrada) {
        this(usuarioService, entrada, null);
    }

    public ClienteSelector(UsuarioService usuarioService, EntradaConsola entrada,
            SesionUsuarioService sesionUsuarioService) {
        this.usuarioService = usuarioService;
        this.entrada = entrada;
        this.sesionUsuarioService = sesionUsuarioService;
    }

    public Usuario seleccionarClienteActivo() {
        Optional<Usuario> clienteSesion = obtenerClienteDeSesion();
        if (clienteSesion.isPresent()) {
            return clienteSesion.get();
        }

        List<Usuario> clientes = usuarioService.listarUsuarios().stream()
                .filter(usuario -> usuario.tieneRol(RolUsuario.CLIENTE))
                .filter(Usuario::estaActivo)
                .toList();

        if (clientes.isEmpty()) {
            throw new DatosInvalidosException("Debe existir al menos un cliente activo para operar el carrito.");
        }

        ConsolaUtils.imprimirUsuarios(clientes);
        int id = entrada.leerEntero("ID del cliente: ");
        Usuario cliente = usuarioService.buscarPorId(id);

        if (!cliente.tieneRol(RolUsuario.CLIENTE)) {
            throw new DatosInvalidosException("El usuario seleccionado no tiene rol de cliente.");
        }

        if (!cliente.estaActivo()) {
            throw new DatosInvalidosException("El cliente seleccionado no está activo.");
        }

        return cliente;
    }

    private Optional<Usuario> obtenerClienteDeSesion() {
        if (sesionUsuarioService == null || !sesionUsuarioService.haySesionActiva()) {
            return Optional.empty();
        }

        Usuario usuario = sesionUsuarioService.requerirUsuarioActual();
        if (usuario.tieneRol(RolUsuario.CLIENTE)) {
            return Optional.of(usuario);
        }

        return Optional.empty();
    }
}
