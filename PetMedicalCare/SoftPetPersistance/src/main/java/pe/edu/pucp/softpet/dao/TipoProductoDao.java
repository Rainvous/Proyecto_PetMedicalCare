package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

/**
 *
 * @author marti
 */
public interface TipoProductoDao {

    public Integer insertar(TipoProductoDto tipoProducto);

    public TipoProductoDto obtenerPorId(Integer tipoProductoId);

    public ArrayList<TipoProductoDto> listarTodos();

    public Integer modificar(TipoProductoDto tipoProducto);

    public Integer eliminar(TipoProductoDto tipoProducto);
}
