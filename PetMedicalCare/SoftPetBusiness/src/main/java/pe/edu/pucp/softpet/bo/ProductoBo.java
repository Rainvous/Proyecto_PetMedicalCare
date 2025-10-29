package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.ProductoDao;
import pe.edu.pucp.softpet.daoImp.ProductoDaoImpl;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.daoImp.TipoProductoDaoImpl;

public class ProductoBo {

    private final ProductoDao productoDao;

    public ProductoBo() {
        this.productoDao = new ProductoDaoImpl();
    }

    // Inserta un producto asegurando que el nombre y presentación estén en mayúsculas
    public Integer insertar(int tipoProductoId, String nombre,
            String presentacion, double precioUnitario, int stock, boolean activo) {
        ProductoDto producto = new ProductoDto();

        producto.setTipoProducto(new TipoProductoDaoImpl().obtenerPorId(tipoProductoId));
        producto.setNombre(nombre);
        producto.setPresentacion(presentacion);
        producto.setPrecioUnitario(precioUnitario);
        producto.setStock(stock);
        producto.setActivo(activo);

        return this.productoDao.insertar(producto);
    }

    // Modifica un producto
    public Integer modificar(int productoId, int tipoProductoId, String nombre,
            String presentacion, double precioUnitario, int stock, boolean activo) {
        ProductoDto producto = new ProductoDto();

        producto.setProductoId(productoId);
        producto.setTipoProducto(new TipoProductoDaoImpl().obtenerPorId(tipoProductoId));
        producto.setNombre(nombre);
        producto.setPresentacion(presentacion);
        producto.setPrecioUnitario(precioUnitario);
        producto.setStock(stock);
        producto.setActivo(activo);

        return this.productoDao.modificar(producto);
    }

    // Elimina un producto por ID
    public Integer eliminar(int productoId) {
        ProductoDto producto = new ProductoDto();
        producto.setProductoId(productoId);
        return this.productoDao.eliminar(producto);
    }

    // Obtiene un producto por su ID
    public ProductoDto obtenerPorId(int productoId) {
        return this.productoDao.obtenerPorId(productoId);
    }

    // Lista todos los productos
    public ArrayList<ProductoDto> listarTodos() {
        return this.productoDao.listarTodos();
    }
}
