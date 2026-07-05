package ecommerce.model;

import ecommerce.enums.EstadoOrden;
import ecommerce.interfaces.Calculable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class OrdenCompra implements Calculable {

    private String numero;
    private Usuario cliente;
    private LocalDateTime fecha;
    private List<ItemOrden> productos;
    private double total;
    private EstadoOrden estado;
    private Pago pago;
    private Envio envio;

    public OrdenCompra(String numero, Usuario cliente, LocalDateTime fecha, EstadoOrden estado) {
        setNumero(numero);
        this.cliente = Objects.requireNonNull(cliente, "El cliente es obligatorio.");
        this.fecha = Objects.requireNonNull(fecha, "La fecha de la orden es obligatoria.");
        this.estado = Objects.requireNonNull(estado, "El estado de la orden es obligatorio.");
        this.productos = new ArrayList<>();
        this.total = 0;
    }

    public void agregarItem(ItemOrden item) {
        productos.add(Objects.requireNonNull(item, "El item de orden es obligatorio."));
        recalcularTotal();
    }

    public void asociarPago(Pago pago) {
        this.pago = Objects.requireNonNull(pago, "El pago es obligatorio.");
    }

    public void asociarEnvio(Envio envio) {
        this.envio = Objects.requireNonNull(envio, "El envío es obligatorio.");
    }

    public void cambiarEstado(EstadoOrden nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "El nuevo estado de la orden es obligatorio.");
    }

    @Override
    public double calcularTotal() {
        return productos.stream()
                .mapToDouble(ItemOrden::getSubtotal)
                .sum();
    }

    private void recalcularTotal() {
        this.total = calcularTotal();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de orden es obligatorio.");
        }
        this.numero = numero.trim().toUpperCase();
    }

    public Usuario getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<ItemOrden> getProductos() {
        return Collections.unmodifiableList(productos);
    }

    public double getTotal() {
        return total;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public Pago getPago() {
        return pago;
    }

    public Envio getEnvio() {
        return envio;
    }
}
