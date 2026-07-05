package ecommerce.model;

import ecommerce.enums.EstadoProducto;

public class ProductoFisico extends Producto {

    public ProductoFisico(int id, String codigo, String nombre, String descripcion, double precio,
            Categoria categoria, int stock, double peso, EstadoProducto estado) {
        super(id, codigo, nombre, descripcion, precio, categoria, stock, peso, estado);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio();
    }
}
