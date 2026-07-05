package ecommerce.exception;

/**
 * Excepción base del dominio del e-commerce.
 * Permite capturar errores propios del negocio sin depender de excepciones genéricas.
 */
public class EcommerceException extends RuntimeException {

    public EcommerceException(String mensaje) {
        super(mensaje);
    }

    public EcommerceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
