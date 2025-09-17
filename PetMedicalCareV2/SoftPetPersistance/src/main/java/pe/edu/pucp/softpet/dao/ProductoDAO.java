package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.productosDTO.ProductosDTO;

/**
 *
 * @author ferro
 */
public interface ProductoDAO {

    public Integer insertar(ProductosDTO producto);

    public ProductosDTO obtenerPorId(Integer productoId);

    public ArrayList<ProductosDTO> listarTodos();

    public Integer modificar(ProductosDTO producto);

    public Integer eliminar(ProductosDTO producto);
}
