package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.DetalleDTO.DetalleProductosDTO;

/**
 *
 * @author ferro
 */
public interface DetalleProductoDAO {
    
    public Integer insertar(DetalleProductosDTO detalleProducto);

    public DetalleProductosDTO obtenerPorId(Integer detalleProductoId);

    public ArrayList<DetalleProductosDTO> listarTodos();

    public Integer modificar(DetalleProductosDTO detalleProducto);

    public Integer eliminar(DetalleProductosDTO detalleProducto);
}
