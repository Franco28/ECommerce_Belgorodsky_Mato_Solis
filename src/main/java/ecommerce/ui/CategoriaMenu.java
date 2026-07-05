package ecommerce.ui;

import ecommerce.exception.EcommerceException;
import ecommerce.model.Categoria;
import ecommerce.service.CategoriaService;

public class CategoriaMenu {

    private final CategoriaService categoriaService;
    private final EntradaConsola entrada;

    public CategoriaMenu(CategoriaService categoriaService, EntradaConsola entrada) {
        this.categoriaService = categoriaService;
        this.entrada = entrada;
    }

    public void mostrar() {
        int opcion;

        do {
            ConsolaUtils.imprimirTitulo("GESTIÓN DE CATEGORÍAS");
            System.out.println("1. Alta de categoría");
            System.out.println("2. Modificar categoría");
            System.out.println("3. Eliminar categoría");
            System.out.println("4. Buscar categoría por ID");
            System.out.println("5. Buscar categoría por nombre");
            System.out.println("6. Listar categorías");
            System.out.println("7. Activar categoría");
            System.out.println("8. Desactivar categoría");
            System.out.println("0. Volver");

            opcion = entrada.leerEntero("Opción: ");
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    private void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> crearCategoria();
                case 2 -> modificarCategoria();
                case 3 -> eliminarCategoria();
                case 4 -> buscarPorId();
                case 5 -> buscarPorNombre();
                case 6 -> listarCategorias();
                case 7 -> activarCategoria();
                case 8 -> desactivarCategoria();
                case 0 -> { }
                default -> System.out.println("Opción incorrecta.");
            }
        } catch (EcommerceException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        if (opcion != 0) {
            entrada.pausar();
        }
    }

    private void crearCategoria() {
        ConsolaUtils.imprimirTitulo("ALTA DE CATEGORÍA");
        String nombre = entrada.leerTexto("Nombre: ");
        String descripcion = entrada.leerTexto("Descripción: ");

        Categoria categoria = categoriaService.crearCategoria(nombre, descripcion);

        System.out.println("Categoría creada correctamente.");
        ConsolaUtils.imprimirCategoria(categoria);
    }

    private void modificarCategoria() {
        ConsolaUtils.imprimirTitulo("MODIFICAR CATEGORÍA");
        int id = entrada.leerEntero("ID de categoría: ");
        Categoria categoria = categoriaService.buscarPorId(id);

        ConsolaUtils.imprimirCategoria(categoria);
        System.out.println();

        categoria.setNombre(entrada.leerTextoOpcional("Nombre", categoria.getNombre()));
        categoria.setDescripcion(entrada.leerTextoOpcional("Descripción", categoria.getDescripcion()));

        categoriaService.modificarCategoria(categoria);
        System.out.println("Categoría modificada correctamente.");
    }

    private void eliminarCategoria() {
        ConsolaUtils.imprimirTitulo("ELIMINAR CATEGORÍA");
        int id = entrada.leerEntero("ID de categoría: ");
        Categoria categoria = categoriaService.buscarPorId(id);
        ConsolaUtils.imprimirCategoria(categoria);

        if (entrada.confirmar("La categoría se eliminará definitivamente.")) {
            categoriaService.eliminarCategoria(id);
            System.out.println("Categoría eliminada correctamente.");
        } else {
            System.out.println("Eliminación cancelada.");
        }
    }

    private void buscarPorId() {
        ConsolaUtils.imprimirTitulo("BUSCAR CATEGORÍA POR ID");
        int id = entrada.leerEntero("ID de categoría: ");
        ConsolaUtils.imprimirCategoria(categoriaService.buscarPorId(id));
    }

    private void buscarPorNombre() {
        ConsolaUtils.imprimirTitulo("BUSCAR CATEGORÍA POR NOMBRE");
        String nombre = entrada.leerTexto("Nombre: ");
        ConsolaUtils.imprimirCategoria(categoriaService.buscarPorNombre(nombre));
    }

    private void listarCategorias() {
        ConsolaUtils.imprimirTitulo("LISTADO DE CATEGORÍAS");
        ConsolaUtils.imprimirCategorias(categoriaService.listarCategorias());
    }

    private void activarCategoria() {
        ConsolaUtils.imprimirTitulo("ACTIVAR CATEGORÍA");
        int id = entrada.leerEntero("ID de categoría: ");
        categoriaService.activarCategoria(id);
        System.out.println("Categoría activada correctamente.");
    }

    private void desactivarCategoria() {
        ConsolaUtils.imprimirTitulo("DESACTIVAR CATEGORÍA");
        int id = entrada.leerEntero("ID de categoría: ");
        categoriaService.desactivarCategoria(id);
        System.out.println("Categoría desactivada correctamente.");
    }
}
