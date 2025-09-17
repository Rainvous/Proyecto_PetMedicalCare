package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.productosDTO.MovimientosInventarioDTO;

/**
 *
 * @author ferro
 */
public interface MovimientoInventarioDAO {
    
    public Integer insertar(MovimientosInventarioDTO movInventario);

    public MovimientosInventarioDTO obtenerPorId(Integer movInventarioId);

    public ArrayList<MovimientosInventarioDTO> listarTodos();

    public Integer modificar(MovimientosInventarioDTO movInventario);
}
