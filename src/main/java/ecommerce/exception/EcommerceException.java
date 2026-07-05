package ecommerce.exception;

/**
 * Excepcion base del dominio del e-commerce.
 * Permite capturar errores propios del negocio sin depender de excepciones genericas.
 */
public class EcommerceException extends RuntimeException {

    public EcommerceException(String mensaje) {
        super(mensaje);
    }

    public EcommerceException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
