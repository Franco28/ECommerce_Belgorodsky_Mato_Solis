package ecommerce.model;

import ecommerce.enums.EstadoProducto;

public class ProductoDigital extends Producto {

    private String urlDescarga;

    public ProductoDigital(int id, String codigo, String nombre, String descripcion, double precio,
            Categoria categoria, int stock, EstadoProducto estado, String urlDescarga) {
        super(id, codigo, nombre, descripcion, precio, categoria, stock, 0, estado);
        setUrlDescarga(urlDescarga);
    }

    @Override
    public double calcularPrecioFinal() {
        return getPrecio() * 0.90;
    }

    public String getUrlDescarga() {
        return urlDescarga;
    }

    public void setUrlDescarga(String urlDescarga) {
        if (urlDescarga == null || urlDescarga.trim().isEmpty()) {
            throw new IllegalArgumentException("La URL de descarga es obligatoria.");
        }
        this.urlDescarga = urlDescarga.trim();
    }
}
