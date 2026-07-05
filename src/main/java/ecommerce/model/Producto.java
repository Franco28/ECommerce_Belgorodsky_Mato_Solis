package ecommerce.model;

import ecommerce.enums.EstadoProducto;
import ecommerce.interfaces.Descontable;
import ecommerce.interfaces.Mostrable;

import java.util.Objects;

public abstract class Producto implements Mostrable, Descontable {

    private int id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private Categoria categoria;
    private int stock;
    private double peso;
    private EstadoProducto estado;

    protected Producto(int id, String codigo, String nombre, String descripcion, double precio,
            Categoria categoria, int stock, double peso, EstadoProducto estado) {
        setId(id);
        setCodigo(codigo);
        setNombre(nombre);
        setDescripcion(descripcion);
        setPrecio(precio);
        setCategoria(categoria);
        setStock(stock);
        setPeso(peso);
        this.estado = Objects.requireNonNull(estado, "El estado del producto es obligatorio.");
        actualizarEstadoPorStock();
    }

    public abstract double calcularPrecioFinal();

    public boolean validarDisponibilidad(int cantidad) {
        return estado == EstadoProducto.ACTIVO && cantidad > 0 && stock >= cantidad;
    }

    @Override
    public double aplicarDescuento(double porcentaje) {
        if (porcentaje <= 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El porcentaje de descuento debe estar entre 1 y 100.");
        }
        return calcularPrecioFinal() - (calcularPrecioFinal() * porcentaje / 100);
    }

    @Override
    public void mostrarInformacion() {
        System.out.printf("[%s] %s - %s - Precio final: $%.2f - Stock: %d - Estado: %s%n",
                codigo, nombre, descripcion, calcularPrecioFinal(), stock, estado);
    }

    public void ingresarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a ingresar debe ser mayor a cero.");
        }
        this.stock += cantidad;
        actualizarEstadoPorStock();
    }

    public void egresarStock(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a egresar debe ser mayor a cero.");
        }
        if (cantidad > stock) {
            throw new IllegalArgumentException("No hay stock suficiente para realizar el egreso.");
        }
        this.stock -= cantidad;
        actualizarEstadoPorStock();
    }

    public void activar() {
        this.estado = stock > 0 ? EstadoProducto.ACTIVO : EstadoProducto.SIN_STOCK;
    }

    public void inactivar() {
        this.estado = EstadoProducto.INACTIVO;
    }

    public void suspender() {
        this.estado = EstadoProducto.SUSPENDIDO;
    }

    private void actualizarEstadoPorStock() {
        if (stock == 0 && estado == EstadoProducto.ACTIVO) {
            this.estado = EstadoProducto.SIN_STOCK;
        }
        if (stock > 0 && estado == EstadoProducto.SIN_STOCK) {
            this.estado = EstadoProducto.ACTIVO;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("El ID no puede ser negativo.");
        }
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        validarTextoObligatorio(codigo, "El código del producto es obligatorio.");
        this.codigo = codigo.trim().toUpperCase();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        validarTextoObligatorio(nombre, "El nombre del producto es obligatorio.");
        this.nombre = nombre.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        validarTextoObligatorio(descripcion, "La descripción del producto es obligatoria.");
        this.descripcion = descripcion.trim();
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero.");
        }
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = Objects.requireNonNull(categoria, "La categoría es obligatoria.");
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }
        this.stock = stock;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo.");
        }
        this.peso = peso;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = Objects.requireNonNull(estado, "El estado del producto es obligatorio.");
    }

    private static void validarTextoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
