package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.ProductoDao;
import pe.edu.pucp.softpet.daoImp.ProductoDaoImpl;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.daoImp.TipoProductoDaoImpl;

public class ProductoBo {

    private ProductoDao productoDao;

    public ProductoBo() {
        this.productoDao = new ProductoDaoImpl();
    }

    // Inserta un producto asegurando que el nombre y presentación estén en mayúsculas
    public Integer insertar(String nombre, String presentacion,
            double precioUnitario, boolean activo,
            int tipoProductoId, int stock) {
        ProductoDto producto = new ProductoDto();

        // Convertimos nombre y presentación a mayúsculas y eliminamos espacios extra
        producto.setNombre(nombre.trim().toUpperCase());
        producto.setPresentacion(presentacion.trim().toUpperCase());
        producto.setPrecioUnitario(precioUnitario);
        producto.setActivo(activo);

        // Asignamos el tipo de producto
        producto.setTipoProducto(new TipoProductoDaoImpl().obtenerPorId(tipoProductoId));
        
        producto.setStock(stock);

        return this.productoDao.insertar(producto);
    }

    // Modifica un producto
    public Integer modificar(int productoId, String nombre, String presentacion,
            double precioUnitario, boolean activo,
            int tipoProductoId, int stock) {
        ProductoDto producto = new ProductoDto();

        producto.setProductoId(productoId);
        producto.setNombre(nombre.trim().toUpperCase());
        producto.setPresentacion(presentacion.trim().toUpperCase());
        producto.setPrecioUnitario(precioUnitario);
        producto.setActivo(activo);
        
        producto.setTipoProducto(new TipoProductoDaoImpl().obtenerPorId(tipoProductoId));
        
        producto.setStock(stock);

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
