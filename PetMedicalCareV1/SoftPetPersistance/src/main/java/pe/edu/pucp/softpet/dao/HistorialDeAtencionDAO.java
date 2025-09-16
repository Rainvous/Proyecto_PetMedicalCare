
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.HistorialDeAtencionesDTO;

/**
 *
 * @author User
 */
public interface HistorialDeAtencionDAO {
    
    public Integer insertar(HistorialDeAtencionesDTO historialDeAtencion);

    public HistorialDeAtencionesDTO obtenerPorId(Integer historiaAtencion_id);

    public ArrayList<HistorialDeAtencionesDTO> listarTodos();

    public Integer modificar(HistorialDeAtencionesDTO historialDeAtencion);

}
