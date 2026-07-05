package ecommerce.ui;

import java.util.Scanner;

public class EntradaConsola {

    private final Scanner scanner;

    public EntradaConsola(Scanner scanner) {
        this.scanner = scanner;
    }

    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();

            if (!valor.isEmpty()) {
                return valor;
            }

            System.out.println("El valor ingresado no puede estar vacío.");
        }
    }

    public String leerTextoOpcional(String mensaje, String valorActual) {
        System.out.print(mensaje + " [actual: " + valorActual + "]: ");
        String valor = scanner.nextLine().trim();
        return valor.isEmpty() ? valorActual : valor;
    }

    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim();

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un número entero válido.");
            }
        }
    }

    public int leerEnteroOpcional(String mensaje, int valorActual) {
        while (true) {
            System.out.print(mensaje + " [actual: " + valorActual + "]: ");
            String valor = scanner.nextLine().trim();

            if (valor.isEmpty()) {
                return valorActual;
            }

            try {
                return Integer.parseInt(valor);
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un número entero válido.");
            }
        }
    }

    public double leerDecimal(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim().replace(',', '.');

            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un número decimal válido.");
            }
        }
    }

    public double leerDecimalOpcional(String mensaje, double valorActual) {
        while (true) {
            System.out.print(mensaje + " [actual: " + valorActual + "]: ");
            String valor = scanner.nextLine().trim().replace(',', '.');

            if (valor.isEmpty()) {
                return valorActual;
            }

            try {
                return Double.parseDouble(valor);
            } catch (NumberFormatException ex) {
                System.out.println("Debe ingresar un número decimal válido.");
            }
        }
    }

    public int leerOpcion(String mensaje, int minimo, int maximo) {
        while (true) {
            int opcion = leerEntero(mensaje);

            if (opcion >= minimo && opcion <= maximo) {
                return opcion;
            }

            System.out.println("La opción ingresada no existe.");
        }
    }

    public boolean confirmar(String mensaje) {
        String confirmacion = leerTexto(mensaje + " Escriba SI para confirmar: ");
        return "SI".equalsIgnoreCase(confirmacion);
    }

    public void pausar() {
        System.out.println();
        System.out.print("Presione Enter para continuar...");
        scanner.nextLine();
    }
}
