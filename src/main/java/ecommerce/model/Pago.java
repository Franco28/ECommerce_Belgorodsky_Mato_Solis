package ecommerce.model;

import ecommerce.enums.EstadoPago;
import ecommerce.enums.MetodoPago;

import java.time.LocalDateTime;
import java.util.Objects;

public class Pago {

    private int id;
    private MetodoPago metodoPago;
    private double monto;
    private EstadoPago estado;
    private LocalDateTime fecha;

    public Pago(int id, MetodoPago metodoPago, double monto, EstadoPago estado, LocalDateTime fecha) {
        setId(id);
        this.metodoPago = Objects.requireNonNull(metodoPago, "El método de pago es obligatorio.");
        setMonto(monto);
        this.estado = Objects.requireNonNull(estado, "El estado del pago es obligatorio.");
        this.fecha = Objects.requireNonNull(fecha, "La fecha del pago es obligatoria.");
    }

    public void aprobar() {
        this.estado = EstadoPago.APROBADO;
    }

    public void rechazar() {
        this.estado = EstadoPago.RECHAZADO;
    }

    public void cancelar() {
        this.estado = EstadoPago.CANCELADO;
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

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        }
        this.monto = monto;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
