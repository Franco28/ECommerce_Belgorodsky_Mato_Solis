package ecommerce;

import ecommerce.enums.EstadoCategoria;
import ecommerce.enums.EstadoProducto;
import ecommerce.enums.EstadoUsuario;
import ecommerce.enums.RolUsuario;
import ecommerce.model.Carrito;
import ecommerce.model.Categoria;
import ecommerce.model.Producto;
import ecommerce.model.ProductoFisico;
import ecommerce.model.Usuario;

import java.time.LocalDate;

public class Main {

        public static void main(String[] args) {
                Categoria tecnologia = new Categoria(
                                1,
                                "Tecnología",
                                "Productos electrónicos y accesorios",
                                EstadoCategoria.ACTIVA);

                Producto teclado = new ProductoFisico(
                                1,
                                "TEC-001",
                                "Teclado mecánico",
                                "Teclado mecánico retroiluminado",
                                75000,
                                tecnologia,
                                10,
                                0.8,
                                EstadoProducto.ACTIVO);

                Usuario cliente = new Usuario(
                                1,
                                "Franco",
                                "Mato",
                                "franco@email.com",
                                "123456",
                                LocalDate.now(),
                                EstadoUsuario.ACTIVO,
                                RolUsuario.CLIENTE);

                Carrito carrito = new Carrito(1, cliente);
                carrito.agregarProducto(teclado, 2);

                teclado.mostrarInformacion();
                System.out.printf("Total del carrito: $%.2f%n", carrito.calcularTotal());
        }
}
