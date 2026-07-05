package ecommerce.model;

import ecommerce.enums.EstadoEnvio;
import ecommerce.enums.TipoEnvio;
import ecommerce.interfaces.Enviable;
import ecommerce.util.ValidadorDominio;

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
        this.tipoEnvio = ValidadorDominio.validarObjetoObligatorio(tipoEnvio,
                "El tipo de envio es obligatorio.");
        this.estado = ValidadorDominio.validarObjetoObligatorio(estado,
                "El estado del envio es obligatorio.");
        setCosto(costo);
    }

    @Override
    public double calcularCostoEnvio() {
        return costo;
    }

    public void actualizarEstado(EstadoEnvio nuevoEstado) {
        this.estado = ValidadorDominio.validarObjetoObligatorio(nuevoEstado,
                "El nuevo estado del envio es obligatorio.");
    }

    public String getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(String codigoSeguimiento) {
        ValidadorDominio.validarTextoObligatorio(codigoSeguimiento,
                "El codigo de seguimiento es obligatorio.");
        this.codigoSeguimiento = codigoSeguimiento.trim().toUpperCase();
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        ValidadorDominio.validarTextoObligatorio(direccion, "La direccion es obligatoria.");
        this.direccion = direccion.trim();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        ValidadorDominio.validarTextoObligatorio(provincia, "La provincia es obligatoria.");
        this.provincia = provincia.trim();
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        ValidadorDominio.validarTextoObligatorio(ciudad, "La ciudad es obligatoria.");
        this.ciudad = ciudad.trim();
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        ValidadorDominio.validarTextoObligatorio(codigoPostal,
                "El codigo postal es obligatorio.");
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
        ValidadorDominio.validarDecimalNoNegativo(costo,
                "El costo del envio no puede ser negativo.");
        this.costo = costo;
    }
}
