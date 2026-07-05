package ecommerce.ui;

import ecommerce.exception.DatosInvalidosException;
import ecommerce.exception.EcommerceException;
import ecommerce.model.InventarioMovimiento;
import ecommerce.model.Producto;
import ecommerce.service.InventarioService;
import ecommerce.service.ProductoService;

public class InventarioMenu {

    private final InventarioService inventarioService;
    private final ProductoService productoService;
    private final EntradaConsola entrada;

    public InventarioMenu(InventarioService inventarioService, ProductoService productoService, EntradaConsola entrada) {
        this.inventarioService = inventarioService;
        this.productoService = productoService;
        this.entrada = entrada;
    }

    public void mostrar() {
        int opcion;

        do {
            ConsolaUtils.imprimirTitulo("GESTION DE INVENTARIO");
            System.out.println("1. Ingreso de stock");
            System.out.println("2. Egreso de stock");
            System.out.println("3. Ajuste de stock");
            System.out.println("4. Consultar stock");
            System.out.println("5. Historial por producto");
            System.out.println("6. Listar movimientos");
            System.out.println("0. Volver");

            opcion = entrada.leerEntero("Opcion: ");
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    private void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1 -> ingresarStock();
                case 2 -> egresarStock();
                case 3 -> ajustarStock();
                case 4 -> consultarStock();
                case 5 -> historialPorProducto();
                case 6 -> listarMovimientos();
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

    private void ingresarStock() {
        ConsolaUtils.imprimirTitulo("INGRESO DE STOCK");
        Producto producto = seleccionarProducto();
        int cantidad = entrada.leerEntero("Cantidad a ingresar: ");
        String motivo = entrada.leerTexto("Motivo: ");

        InventarioMovimiento movimiento = inventarioService.ingresarStock(producto.getId(), cantidad, motivo);

        System.out.println("Stock ingresado correctamente.");
        ConsolaUtils.imprimirMovimientoInventario(movimiento);
    }

    private void egresarStock() {
        ConsolaUtils.imprimirTitulo("EGRESO DE STOCK");
        Producto producto = seleccionarProducto();
        int cantidad = entrada.leerEntero("Cantidad a egresar: ");
        String motivo = entrada.leerTexto("Motivo: ");

        InventarioMovimiento movimiento = inventarioService.egresarStock(producto.getId(), cantidad, motivo);

        System.out.println("Stock egresado correctamente.");
        ConsolaUtils.imprimirMovimientoInventario(movimiento);
    }

    private void ajustarStock() {
        ConsolaUtils.imprimirTitulo("AJUSTE DE STOCK");
        Producto producto = seleccionarProducto();
        int nuevoStock = entrada.leerEntero("Nuevo stock: ");
        String motivo = entrada.leerTexto("Motivo: ");

        InventarioMovimiento movimiento = inventarioService.ajustarStock(producto.getId(), nuevoStock, motivo);

        System.out.println("Stock ajustado correctamente.");
        ConsolaUtils.imprimirMovimientoInventario(movimiento);
    }

    private void consultarStock() {
        ConsolaUtils.imprimirTitulo("CONSULTAR STOCK");
        Producto producto = seleccionarProducto();
        int stock = inventarioService.consultarStock(producto.getId());

        System.out.println("Producto: " + producto.getCodigo() + " - " + producto.getNombre());
        System.out.println("Stock disponible: " + stock);
    }

    private void historialPorProducto() {
        ConsolaUtils.imprimirTitulo("HISTORIAL DE INVENTARIO POR PRODUCTO");
        Producto producto = seleccionarProducto();
        ConsolaUtils.imprimirMovimientosInventario(
                inventarioService.obtenerHistorialPorProducto(producto.getId()));
    }

    private void listarMovimientos() {
        ConsolaUtils.imprimirTitulo("LISTADO DE MOVIMIENTOS DE INVENTARIO");
        ConsolaUtils.imprimirMovimientosInventario(inventarioService.listarMovimientos());
    }

    private Producto seleccionarProducto() {
        var productos = productoService.listarProductos();
        if (productos.isEmpty()) {
            throw new DatosInvalidosException("Debe registrar al menos un producto antes de operar inventario.");
        }

        ConsolaUtils.imprimirProductos(productos);
        int id = entrada.leerEntero("ID del producto: ");
        return productoService.buscarPorId(id);
    }
}
