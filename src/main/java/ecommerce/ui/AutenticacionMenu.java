package ecommerce.ui;

import ecommerce.exception.EcommerceException;
import ecommerce.model.Usuario;
import ecommerce.service.AutenticacionService;

public class AutenticacionMenu {

    private final AutenticacionService autenticacionService;
    private final EntradaConsola entrada;

    public AutenticacionMenu(AutenticacionService autenticacionService, EntradaConsola entrada) {
        this.autenticacionService = autenticacionService;
        this.entrada = entrada;
    }

    public boolean mostrar() {
        if (autenticacionService.requiereAdministradorInicial()) {
            registrarAdministradorInicial();
            return true;
        }

        int opcion;
        do {
            imprimirMenu();
            opcion = entrada.leerOpcion("Opción: ", 1, 2);

            switch (opcion) {
                case 1 -> {
                    iniciarSesion();
                    return true;
                }
                case 2 -> {
                    System.out.println("Saliendo del sistema.");
                    return false;
                }
                default -> System.out.println("Opción incorrecta.");
            }
        } while (opcion != 2);

        return false;
    }

    private void imprimirMenu() {
        ConsolaUtils.imprimirTitulo("ACCESO AL SISTEMA");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Salir");
    }

    private void iniciarSesion() {
        boolean sesionIniciada = false;

        while (!sesionIniciada) {
            try {
                String email = entrada.leerTexto("Email: ");
                String contrasenia = entrada.leerTexto("Contraseña: ");
                Usuario usuario = autenticacionService.iniciarSesion(email, contrasenia);
                System.out.println("Sesión iniciada: " + usuario.getNombre() + " "
                        + usuario.getApellido() + " - " + usuario.getRol());
                sesionIniciada = true;
            } catch (EcommerceException ex) {
                System.out.println("Error: " + ex.getMessage());
                entrada.pausar();
            }
        }
    }

    private void registrarAdministradorInicial() {
        boolean registrado = false;

        while (!registrado) {
            try {
                ConsolaUtils.imprimirTitulo("CREACIÓN DEL ADMINISTRADOR INICIAL");
                String nombre = entrada.leerTexto("Nombre: ");
                String apellido = entrada.leerTexto("Apellido: ");
                String email = entrada.leerTexto("Email: ");
                String contrasenia = entrada.leerTexto("Contraseña: ");

                Usuario usuario = autenticacionService.registrarPrimerAdministrador(nombre, apellido, email, contrasenia);
                System.out.println("Administrador inicial creado: " + usuario.getEmail());
                registrado = true;
            } catch (EcommerceException ex) {
                System.out.println("Error: " + ex.getMessage());
                entrada.pausar();
            }
        }
    }
}
