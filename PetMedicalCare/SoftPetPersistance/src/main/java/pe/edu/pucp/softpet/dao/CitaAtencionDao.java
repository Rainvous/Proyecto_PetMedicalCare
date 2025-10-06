package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

/**
 *
 * @author marti
 */
public interface CitaAtencionDao {

    public Integer insertar(CitaAtencionDto citas);

    public CitaAtencionDto obtenerPorId(Integer citasId);

    public ArrayList<CitaAtencionDto> listarTodos();

    public Integer modificar(CitaAtencionDto citas);

    public Integer eliminar(CitaAtencionDto citas);
}
