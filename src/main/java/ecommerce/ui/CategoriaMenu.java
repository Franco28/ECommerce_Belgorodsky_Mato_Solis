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
            ConsolaUtils.imprimirTitulo("GESTION DE CATEGORIAS");
            System.out.println("1. Alta de categoria");
            System.out.println("2. Modificar categoria");
            System.out.println("3. Eliminar categoria");
            System.out.println("4. Buscar categoria por ID");
            System.out.println("5. Buscar categoria por nombre");
            System.out.println("6. Listar categorias");
            System.out.println("7. Activar categoria");
            System.out.println("8. Desactivar categoria");
            System.out.println("0. Volver");

            opcion = entrada.leerEntero("Opcion: ");
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
                default -> System.out.println("Opcion incorrecta.");
            }
        } catch (EcommerceException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        if (opcion != 0) {
            entrada.pausar();
        }
    }

    private void crearCategoria() {
        ConsolaUtils.imprimirTitulo("ALTA DE CATEGORIA");
        String nombre = entrada.leerTexto("Nombre: ");
        String descripcion = entrada.leerTexto("Descripcion: ");

        Categoria categoria = categoriaService.crearCategoria(nombre, descripcion);

        System.out.println("Categoria creada correctamente.");
        ConsolaUtils.imprimirCategoria(categoria);
    }

    private void modificarCategoria() {
        ConsolaUtils.imprimirTitulo("MODIFICAR CATEGORIA");
        int id = entrada.leerEntero("ID de categoria: ");
        Categoria categoria = categoriaService.buscarPorId(id);

        ConsolaUtils.imprimirCategoria(categoria);
        System.out.println();

        categoria.setNombre(entrada.leerTextoOpcional("Nombre", categoria.getNombre()));
        categoria.setDescripcion(entrada.leerTextoOpcional("Descripcion", categoria.getDescripcion()));

        categoriaService.modificarCategoria(categoria);
        System.out.println("Categoria modificada correctamente.");
    }

    private void eliminarCategoria() {
        ConsolaUtils.imprimirTitulo("ELIMINAR CATEGORIA");
        int id = entrada.leerEntero("ID de categoria: ");
        Categoria categoria = categoriaService.buscarPorId(id);
        ConsolaUtils.imprimirCategoria(categoria);

        if (entrada.confirmar("La categoria se eliminara definitivamente.")) {
            categoriaService.eliminarCategoria(id);
            System.out.println("Categoria eliminada correctamente.");
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }

    private void buscarPorId() {
        ConsolaUtils.imprimirTitulo("BUSCAR CATEGORIA POR ID");
        int id = entrada.leerEntero("ID de categoria: ");
        ConsolaUtils.imprimirCategoria(categoriaService.buscarPorId(id));
    }

    private void buscarPorNombre() {
        ConsolaUtils.imprimirTitulo("BUSCAR CATEGORIA POR NOMBRE");
        String nombre = entrada.leerTexto("Nombre: ");
        ConsolaUtils.imprimirCategoria(categoriaService.buscarPorNombre(nombre));
    }

    private void listarCategorias() {
        ConsolaUtils.imprimirTitulo("LISTADO DE CATEGORIAS");
        ConsolaUtils.imprimirCategorias(categoriaService.listarCategorias());
    }

    private void activarCategoria() {
        ConsolaUtils.imprimirTitulo("ACTIVAR CATEGORIA");
        int id = entrada.leerEntero("ID de categoria: ");
        categoriaService.activarCategoria(id);
        System.out.println("Categoria activada correctamente.");
    }

    private void desactivarCategoria() {
        ConsolaUtils.imprimirTitulo("DESACTIVAR CATEGORIA");
        int id = entrada.leerEntero("ID de categoria: ");
        categoriaService.desactivarCategoria(id);
        System.out.println("Categoria desactivada correctamente.");
    }
}
