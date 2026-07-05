package ecommerce.ui;

import ecommerce.enums.EstadoProducto;

public class EstadoProductoSelector {

    private final EntradaConsola entrada;

    public EstadoProductoSelector(EntradaConsola entrada) {
        this.entrada = entrada;
    }

    public EstadoProducto seleccionarEstado() {
        imprimirEstados(false);
        int opcion = entrada.leerOpcion("Seleccione estado: ", 1, EstadoProducto.values().length);
        return EstadoProducto.values()[opcion - 1];
    }

    public EstadoProducto seleccionarEstadoOpcional(EstadoProducto estadoActual) {
        System.out.println("Estado actual: " + estadoActual);
        imprimirEstados(true);
        int opcion = entrada.leerOpcion("Seleccione estado: ", 0, EstadoProducto.values().length);
        return opcion == 0 ? estadoActual : EstadoProducto.values()[opcion - 1];
    }

    private void imprimirEstados(boolean incluirMantener) {
        if (incluirMantener) {
            System.out.println("0. Mantener estado actual");
        }

        EstadoProducto[] estados = EstadoProducto.values();
        for (int i = 0; i < estados.length; i++) {
            System.out.println((i + 1) + ". " + estados[i]);
        }
    }
}
