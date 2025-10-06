package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

/**
 *
 * @author marti
 */
public interface TipoServicioDao {

    public Integer insertar(TipoServicioDto tipoSservicio);

    public TipoServicioDto obtenerPorId(Integer tipoSservicioId);

    public ArrayList<TipoServicioDto> listarTodos();

    public Integer modificar(TipoServicioDto tipoSservicio);

    public Integer eliminar(TipoServicioDto tipoSservicio);
}
