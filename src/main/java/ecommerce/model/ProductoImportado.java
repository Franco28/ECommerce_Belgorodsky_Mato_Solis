package ecommerce.model;

import ecommerce.enums.EstadoProducto;
import ecommerce.util.ValidadorDominio;

public class ProductoImportado extends Producto {

    private double porcentajeImpuestoImportacion;

    public ProductoImportado(int id, String codigo, String nombre, String descripcion, double precio,
            Categoria categoria, int stock, double peso, EstadoProducto estado,
            double porcentajeImpuestoImportacion) {
        super(id, codigo, nombre, descripcion, precio, categoria, stock, peso, estado);
        setPorcentajeImpuestoImportacion(porcentajeImpuestoImportacion);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio() + (getPrecio() * porcentajeImpuestoImportacion / 100);
    }

    public double getPorcentajeImpuestoImportacion() {
        return porcentajeImpuestoImportacion;
    }

    public void setPorcentajeImpuestoImportacion(double porcentajeImpuestoImportacion) {
        ValidadorDominio.validarDecimalNoNegativo(porcentajeImpuestoImportacion,
                "El impuesto de importación no puede ser negativo.");
        this.porcentajeImpuestoImportacion = porcentajeImpuestoImportacion;
    }
}
