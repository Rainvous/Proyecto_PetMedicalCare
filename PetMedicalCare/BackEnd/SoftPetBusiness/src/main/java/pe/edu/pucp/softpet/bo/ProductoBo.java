package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.ProductoDao;
import pe.edu.pucp.softpet.daoImp.ProductoDaoImpl;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.daoImp.TipoProductoDaoImpl;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

public class ProductoBo {

    private final ProductoDaoImpl productoDao;

    public ProductoBo() {
        this.productoDao = new ProductoDaoImpl();
    }

    public Integer insertar(int tipoProductoId, String nombre, String presentacion,
            double precioUnitario, int stock, boolean activo) {

        ProductoDto producto = new ProductoDto();
        
        TipoProductoDto tipoProd = new TipoProductoDto();
        tipoProd.setTipoProductoId(tipoProductoId);

        producto.setTipoProducto(tipoProd);
        producto.setNombre(nombre);
        producto.setPresentacion(presentacion);
        producto.setPrecioUnitario(precioUnitario);
        producto.setStock(stock);
        producto.setActivo(activo);

        return this.productoDao.insertar(producto);
    }

    public Integer modificar(int productoId, int tipoProductoId, String nombre,
            String presentacion, double precioUnitario, int stock, boolean activo) {

        ProductoDto producto = new ProductoDto();
        
        TipoProductoDto tipoProd = new TipoProductoDto();
        tipoProd.setTipoProductoId(tipoProductoId);

        producto.setProductoId(productoId);
        producto.setTipoProducto(tipoProd);
        producto.setNombre(nombre);
        producto.setPresentacion(presentacion);
        producto.setPrecioUnitario(precioUnitario);
        producto.setStock(stock);
        producto.setActivo(activo);

        return this.productoDao.modificar(producto);
    }

    public Integer eliminar(int productoId) {
        ProductoDto producto = new ProductoDto();
        producto.setProductoId(productoId);
        return this.productoDao.eliminar(producto);
    }

    public ProductoDto obtenerPorId(int productoId) {
        return this.productoDao.obtenerPorId(productoId);
    }

    public ArrayList<ProductoDto> listarTodos() {
        return this.productoDao.listarTodos();
    }
    
    public ArrayList<ProductoDto> listarProductosActivos() {
        return this.productoDao.listarProductosActivos();
    }
    
    public ArrayList<ProductoDto> ListarPorTipo(String nombreTipo) {
        return this.productoDao.ListarPorTipo(nombreTipo);
    }
    
    public int VerificarSiElProductoTieneInformacion(int idServicio){
        return this.productoDao.VerificarSiElProductoTieneInformacion(idServicio);
    }
}
