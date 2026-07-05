package ecommerce.model;

import ecommerce.enums.RolUsuario;
import ecommerce.interfaces.Calculable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Carrito implements Calculable {

    private int id;
    private Usuario cliente;
    private List<ItemCarrito> items;
    private LocalDateTime fechaCreacion;

    public Carrito(int id, Usuario cliente) {
        setId(id);
        this.cliente = Objects.requireNonNull(cliente, "El cliente es obligatorio.");
        if (!cliente.tieneRol(RolUsuario.CLIENTE)) {
            throw new IllegalArgumentException("El carrito solo puede pertenecer a un usuario cliente.");
        }
        this.items = new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now();
    }

    public void agregarProducto(Producto producto, int cantidad) {
        Objects.requireNonNull(producto, "El producto es obligatorio.");
        if (!producto.validarDisponibilidad(cantidad)) {
            throw new IllegalArgumentException("El producto no tiene disponibilidad suficiente.");
        }

        for (ItemCarrito item : items) {
            if (item.getProducto().getCodigo().equals(producto.getCodigo())) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }

        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(String codigoProducto) {
        items.removeIf(item -> item.getProducto().getCodigo().equalsIgnoreCase(codigoProducto));
    }

    public void vaciar() {
        items.clear();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    @Override
    public double calcularTotal() {
        return items.stream()
                .mapToDouble(ItemCarrito::calcularSubtotal)
                .sum();
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

    public Usuario getCliente() {
        return cliente;
    }

    public List<ItemCarrito> getItems() {
        return Collections.unmodifiableList(items);
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}
