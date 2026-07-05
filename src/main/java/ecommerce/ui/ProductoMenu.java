package ecommerce.ui;

import ecommerce.enums.EstadoProducto;
import ecommerce.exception.DatosInvalidosException;
import ecommerce.exception.EcommerceException;
import ecommerce.model.Categoria;
import ecommerce.model.Producto;
import ecommerce.model.ProductoDigital;
import ecommerce.model.ProductoFisico;
import ecommerce.model.ProductoImportado;
import ecommerce.service.CategoriaService;
import ecommerce.service.ProductoService;

public class ProductoMenu {

    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final EntradaConsola entrada;
    private final CategoriaSelector categoriaSelector;
    private final EstadoProductoSelector estadoProductoSelector;

    public ProductoMenu(ProductoService productoService, CategoriaService categoriaService, EntradaConsola entrada) {
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.entrada = entrada;
        this.categoriaSelector = new CategoriaSelector(categoriaService, entrada);
        this.estadoProductoSelector = new EstadoProductoSelector(entrada);
    }

    public void mostrar() {
        int opcion;

        do {
            ConsolaUtils.imprimirTitulo("GESTION DE PRODUCTOS");
            System.out.println("1. Registrar producto");
            System.out.println("2. Modificar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Buscar producto por ID");
            System.out.println("5. Buscar producto por codigo");
            System.out.println("6. Listar productos");
            System.out.println("7. Listar productos por categoria");
            System.out.println("8. Listar productos sin stock");
            System.out.println("9. Activar producto");
            System.out.println("10. Inactivar producto");
            System.out.println("11. Suspender producto");
            System.out.println("0. Volver");

            opcion = entrada.leerEntero("Opcion: ");
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    private void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> registrarProducto();
                case 2 -> modificarProducto();
                case 3 -> eliminarProducto();
                case 4 -> buscarPorId();
                case 5 -> buscarPorCodigo();
                case 6 -> listarProductos();
                case 7 -> listarPorCategoria();
                case 8 -> listarSinStock();
                case 9 -> activarProducto();
                case 10 -> inactivarProducto();
                case 11 -> suspenderProducto();
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

    private void registrarProducto() {
        validarCategoriasDisponibles();
        ConsolaUtils.imprimirTitulo("REGISTRAR PRODUCTO");

        Producto producto = leerNuevoProducto();
        productoService.registrarProducto(producto);

        System.out.println("Producto registrado correctamente.");
        ConsolaUtils.imprimirProducto(producto);
    }

    private Producto leerNuevoProducto() {
        int tipo = seleccionarTipoProducto();
        String codigo = entrada.leerTexto("Codigo unico: ");
        String nombre = entrada.leerTexto("Nombre: ");
        String descripcion = entrada.leerTexto("Descripcion: ");
        double precio = entrada.leerDecimal("Precio: ");
        Categoria categoria = categoriaSelector.seleccionarCategoria();
        int stock = entrada.leerEntero("Stock inicial: ");
        EstadoProducto estado = estadoProductoSelector.seleccionarEstado();

        return switch (tipo) {
            case 1 -> crearProductoFisico(codigo, nombre, descripcion, precio, categoria, stock, estado);
            case 2 -> crearProductoDigital(codigo, nombre, descripcion, precio, categoria, stock, estado);
            case 3 -> crearProductoImportado(codigo, nombre, descripcion, precio, categoria, stock, estado);
            default -> throw new DatosInvalidosException("Tipo de producto invalido.");
        };
    }

    private ProductoFisico crearProductoFisico(String codigo, String nombre, String descripcion, double precio,
            Categoria categoria, int stock, EstadoProducto estado) {
        double peso = entrada.leerDecimal("Peso: ");
        return new ProductoFisico(0, codigo, nombre, descripcion, precio, categoria, stock, peso, estado);
    }

    private ProductoDigital crearProductoDigital(String codigo, String nombre, String descripcion, double precio,
            Categoria categoria, int stock, EstadoProducto estado) {
        String urlDescarga = entrada.leerTexto("URL de descarga: ");
        return new ProductoDigital(0, codigo, nombre, descripcion, precio, categoria, stock, estado, urlDescarga);
    }

    private ProductoImportado crearProductoImportado(String codigo, String nombre, String descripcion, double precio,
            Categoria categoria, int stock, EstadoProducto estado) {
        double peso = entrada.leerDecimal("Peso: ");
        double impuesto = entrada.leerDecimal("Porcentaje de impuesto de importacion: ");
        return new ProductoImportado(0, codigo, nombre, descripcion, precio, categoria, stock, peso, estado, impuesto);
    }

    private int seleccionarTipoProducto() {
        System.out.println("1. Producto fisico");
        System.out.println("2. Producto digital");
        System.out.println("3. Producto importado");
        return entrada.leerOpcion("Tipo de producto: ", 1, 3);
    }

    private void modificarProducto() {
        validarCategoriasDisponibles();
        ConsolaUtils.imprimirTitulo("MODIFICAR PRODUCTO");

        int id = entrada.leerEntero("ID del producto: ");
        Producto producto = productoService.buscarPorId(id);

        ConsolaUtils.imprimirProducto(producto);
        System.out.println();

        producto.setCodigo(entrada.leerTextoOpcional("Codigo", producto.getCodigo()));
        producto.setNombre(entrada.leerTextoOpcional("Nombre", producto.getNombre()));
        producto.setDescripcion(entrada.leerTextoOpcional("Descripcion", producto.getDescripcion()));
        producto.setPrecio(entrada.leerDecimalOpcional("Precio", producto.getPrecio()));
        producto.setCategoria(categoriaSelector.seleccionarCategoriaOpcional(producto.getCategoria()));
        producto.setStock(entrada.leerEnteroOpcional("Stock", producto.getStock()));
        producto.setPeso(entrada.leerDecimalOpcional("Peso", producto.getPeso()));
        producto.setEstado(estadoProductoSelector.seleccionarEstadoOpcional(producto.getEstado()));

        if (producto instanceof ProductoDigital productoDigital) {
            productoDigital.setUrlDescarga(entrada.leerTextoOpcional("URL de descarga", productoDigital.getUrlDescarga()));
        } else if (producto instanceof ProductoImportado productoImportado) {
            productoImportado.setPorcentajeImpuestoImportacion(
                    entrada.leerDecimalOpcional("Porcentaje de impuesto de importacion",
                            productoImportado.getPorcentajeImpuestoImportacion()));
        }

        productoService.modificarProducto(producto);
        System.out.println("Producto modificado correctamente.");
    }

    private void eliminarProducto() {
        ConsolaUtils.imprimirTitulo("ELIMINAR PRODUCTO");
        int id = entrada.leerEntero("ID del producto: ");
        Producto producto = productoService.buscarPorId(id);
        ConsolaUtils.imprimirProducto(producto);

        if (entrada.confirmar("El producto se eliminara definitivamente.")) {
            productoService.eliminarProducto(id);
            System.out.println("Producto eliminado correctamente.");
        } else {
            System.out.println("Eliminacion cancelada.");
        }
    }

    private void buscarPorId() {
        ConsolaUtils.imprimirTitulo("BUSCAR PRODUCTO POR ID");
        int id = entrada.leerEntero("ID del producto: ");
        ConsolaUtils.imprimirProducto(productoService.buscarPorId(id));
    }

    private void buscarPorCodigo() {
        ConsolaUtils.imprimirTitulo("BUSCAR PRODUCTO POR CODIGO");
        String codigo = entrada.leerTexto("Codigo: ");
        ConsolaUtils.imprimirProducto(productoService.buscarPorCodigo(codigo));
    }

    private void listarProductos() {
        ConsolaUtils.imprimirTitulo("LISTADO DE PRODUCTOS");
        ConsolaUtils.imprimirProductos(productoService.listarProductos());
    }

    private void listarPorCategoria() {
        validarCategoriasDisponibles();
        ConsolaUtils.imprimirTitulo("PRODUCTOS POR CATEGORIA");
        Categoria categoria = categoriaSelector.seleccionarCategoria();
        ConsolaUtils.imprimirProductos(productoService.listarPorCategoria(categoria.getId()));
    }

    private void listarSinStock() {
        ConsolaUtils.imprimirTitulo("PRODUCTOS SIN STOCK");
        ConsolaUtils.imprimirProductos(productoService.listarSinStock());
    }

    private void activarProducto() {
        ConsolaUtils.imprimirTitulo("ACTIVAR PRODUCTO");
        int id = entrada.leerEntero("ID del producto: ");
        productoService.activarProducto(id);
        System.out.println("Producto activado correctamente.");
    }

    private void inactivarProducto() {
        ConsolaUtils.imprimirTitulo("INACTIVAR PRODUCTO");
        int id = entrada.leerEntero("ID del producto: ");
        productoService.inactivarProducto(id);
        System.out.println("Producto inactivado correctamente.");
    }

    private void suspenderProducto() {
        ConsolaUtils.imprimirTitulo("SUSPENDER PRODUCTO");
        int id = entrada.leerEntero("ID del producto: ");
        productoService.suspenderProducto(id);
        System.out.println("Producto suspendido correctamente.");
    }

    private void validarCategoriasDisponibles() {
        if (categoriaService.listarCategorias().isEmpty()) {
            throw new DatosInvalidosException("Debe registrar al menos una categoria antes de operar productos.");
        }
    }
}
