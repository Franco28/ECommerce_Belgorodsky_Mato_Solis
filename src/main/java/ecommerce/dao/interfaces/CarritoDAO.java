package ecommerce.dao.interfaces;

import ecommerce.model.Carrito;

public interface CarritoDAO {

    Carrito buscarActivoPorCliente(int clienteId);

    Carrito guardar(Carrito carrito);

    void reemplazarItems(Carrito carrito);

    void desactivar(int carritoId);
}
