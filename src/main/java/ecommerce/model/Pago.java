package ecommerce.model;

import ecommerce.enums.EstadoPago;
import ecommerce.enums.MetodoPago;
import ecommerce.util.ValidadorDominio;

import java.time.LocalDateTime;

public class Pago {

    private int id;
    private MetodoPago metodoPago;
    private double monto;
    private EstadoPago estado;
    private LocalDateTime fecha;

    public Pago(int id, MetodoPago metodoPago, double monto, EstadoPago estado, LocalDateTime fecha) {
        setId(id);
        this.metodoPago = ValidadorDominio.validarObjetoObligatorio(metodoPago,
                "El método de pago es obligatorio.");
        setMonto(monto);
        this.estado = ValidadorDominio.validarObjetoObligatorio(estado,
                "El estado del pago es obligatorio.");
        this.fecha = ValidadorDominio.validarObjetoObligatorio(fecha,
                "La fecha del pago es obligatoria.");
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
        ValidadorDominio.validarIdNoNegativo(id, "El ID no puede ser negativo.");
        this.id = id;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        ValidadorDominio.validarDecimalMayorACero(monto,
                "El monto del pago debe ser mayor a cero.");
        this.monto = monto;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
