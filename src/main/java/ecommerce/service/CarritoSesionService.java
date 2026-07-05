package ecommerce.service;

import ecommerce.dao.interfaces.CarritoDAO;

import ecommerce.exception.CarritoVacioException;
import ecommerce.model.Carrito;
import ecommerce.model.Usuario;
import ecommerce.util.ValidadorDominio;

import java.util.HashMap;
import java.util.Map;

public class CarritoSesionService {

    private final CarritoService carritoService;
    private final CarritoDAO carritoDAO;
    private final Map<Integer, Carrito> carritosActivos;

    public CarritoSesionService(CarritoService carritoService) {
        this(carritoService, null);
    }

    public CarritoSesionService(CarritoService carritoService, CarritoDAO carritoDAO) {
        this.carritoService = ValidadorDominio.validarObjetoObligatorio(carritoService,
                "El servicio de carrito es obligatorio.");
        this.carritoDAO = carritoDAO;
        this.carritosActivos = new HashMap<>();
    }

    public Carrito obtenerOCrearCarrito(Usuario cliente) {
        ValidadorDominio.validarObjetoObligatorio(cliente, "El cliente es obligatorio.");
        Carrito carrito = carritosActivos.get(cliente.getId());
        if (carrito != null) {
            return carrito;
        }

        if (carritoDAO != null) {
            carrito = carritoDAO.buscarActivoPorCliente(cliente.getId());
            if (carrito != null) {
                carritosActivos.put(cliente.getId(), carrito);
                return carrito;
            }
        }

        carrito = carritoService.guardarCarrito(carritoService.crearCarrito(cliente));
        carritosActivos.put(cliente.getId(), carrito);
        return carrito;
    }

    public Carrito obtenerCarritoExistente(Usuario cliente) {
        ValidadorDominio.validarObjetoObligatorio(cliente, "El cliente es obligatorio.");
        Carrito carrito = carritosActivos.get(cliente.getId());
        if (carrito == null) {
            throw new CarritoVacioException("El cliente no tiene un carrito activo cargado.");
        }
        return carrito;
    }

    public void quitarCarrito(Usuario cliente) {
        ValidadorDominio.validarObjetoObligatorio(cliente, "El cliente es obligatorio.");
        Carrito carrito = carritosActivos.remove(cliente.getId());
        if (carritoDAO != null && carrito != null && carrito.getId() > 0) {
            carritoDAO.desactivar(carrito.getId());
        }
    }
}
