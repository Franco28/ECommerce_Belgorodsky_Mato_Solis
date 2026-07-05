package ecommerce.model;

import ecommerce.enums.EstadoOrden;
import ecommerce.interfaces.Calculable;
import ecommerce.util.ValidadorDominio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        this.cliente = ValidadorDominio.validarObjetoObligatorio(cliente,
                "El cliente es obligatorio.");
        this.fecha = ValidadorDominio.validarObjetoObligatorio(fecha,
                "La fecha de la orden es obligatoria.");
        this.estado = ValidadorDominio.validarObjetoObligatorio(estado,
                "El estado de la orden es obligatorio.");
        this.productos = new ArrayList<>();
        this.total = 0;
    }

    public void agregarItem(ItemOrden item) {
        productos.add(ValidadorDominio.validarObjetoObligatorio(item,
                "El item de orden es obligatorio."));
        recalcularTotal();
    }

    public void asociarPago(Pago pago) {
        this.pago = ValidadorDominio.validarObjetoObligatorio(pago,
                "El pago es obligatorio.");
    }

    public void asociarEnvio(Envio envio) {
        this.envio = ValidadorDominio.validarObjetoObligatorio(envio,
                "El envio es obligatorio.");
    }

    public void cambiarEstado(EstadoOrden nuevoEstado) {
        this.estado = ValidadorDominio.validarObjetoObligatorio(nuevoEstado,
                "El nuevo estado de la orden es obligatorio.");
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
        ValidadorDominio.validarTextoObligatorio(numero,
                "El numero de orden es obligatorio.");
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
