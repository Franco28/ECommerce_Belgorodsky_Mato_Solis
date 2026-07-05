package ecommerce.ui;

import ecommerce.model.Categoria;
import ecommerce.service.CategoriaService;

import java.util.List;

public class CategoriaSelector {

    private final CategoriaService categoriaService;
    private final EntradaConsola entrada;

    public CategoriaSelector(CategoriaService categoriaService, EntradaConsola entrada) {
        this.categoriaService = categoriaService;
        this.entrada = entrada;
    }

    public Categoria seleccionarCategoria() {
        List<Categoria> categorias = categoriaService.listarCategorias();
        ConsolaUtils.imprimirCategorias(categorias);
        int id = entrada.leerEntero("ID de categoría: ");
        return categoriaService.buscarPorId(id);
    }

    public Categoria seleccionarCategoriaOpcional(Categoria categoriaActual) {
        System.out.println("Categoría actual: " + categoriaActual.getNombre() + " (ID " + categoriaActual.getId() + ")");
        System.out.println("0. Mantener categoría actual");
        ConsolaUtils.imprimirCategorias(categoriaService.listarCategorias());

        int id = entrada.leerEntero("ID de categoría: ");
        if (id == 0) {
            return categoriaActual;
        }
        return categoriaService.buscarPorId(id);
    }
}
