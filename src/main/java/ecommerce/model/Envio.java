package ecommerce.model;

import ecommerce.enums.EstadoEnvio;
import ecommerce.enums.TipoEnvio;
import ecommerce.interfaces.Enviable;

import java.util.Objects;

public class Envio implements Enviable {

    private String codigoSeguimiento;
    private String direccion;
    private String provincia;
    private String ciudad;
    private String codigoPostal;
    private TipoEnvio tipoEnvio;
    private EstadoEnvio estado;
    private double costo;

    public Envio(String codigoSeguimiento, String direccion, String provincia, String ciudad,
            String codigoPostal, TipoEnvio tipoEnvio, EstadoEnvio estado, double costo) {
        setCodigoSeguimiento(codigoSeguimiento);
        setDireccion(direccion);
        setProvincia(provincia);
        setCiudad(ciudad);
        setCodigoPostal(codigoPostal);
        this.tipoEnvio = Objects.requireNonNull(tipoEnvio, "El tipo de envío es obligatorio.");
        this.estado = Objects.requireNonNull(estado, "El estado del envío es obligatorio.");
        setCosto(costo);
    }

    @Override
    public double calcularCostoEnvio() {
        return costo;
    }

    public void actualizarEstado(EstadoEnvio nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "El nuevo estado del envío es obligatorio.");
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        validarTextoObligatorio(codigoSeguimiento, "El código de seguimiento es obligatorio.");
        this.codigoSeguimiento = codigoSeguimiento.trim().toUpperCase();
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        validarTextoObligatorio(direccion, "La dirección es obligatoria.");
        this.direccion = direccion.trim();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        validarTextoObligatorio(provincia, "La provincia es obligatoria.");
        this.provincia = provincia.trim();
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        validarTextoObligatorio(ciudad, "La ciudad es obligatoria.");
        this.ciudad = ciudad.trim();
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        validarTextoObligatorio(codigoPostal, "El código postal es obligatorio.");
        this.codigoPostal = codigoPostal.trim();
    }

    public TipoEnvio getTipoEnvio() {
        return tipoEnvio;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        if (costo < 0) {
            throw new IllegalArgumentException("El costo del envío no puede ser negativo.");
        }
        this.costo = costo;
    }

    private static void validarTextoObligatorio(String valor, String mensaje) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(mensaje);
        }
    }
}
