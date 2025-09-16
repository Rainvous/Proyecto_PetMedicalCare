
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public interface HistorialDeAtencionDAO {
    
    public Integer insertar(HistorialDeAtencionDAO historialDeAtencion);

    public HistorialDeAtencionDAO obtenerPorId(Integer historiaAtencion_id);

    public ArrayList<HistorialDeAtencionDAO> listarTodos();

    public Integer modificar(HistorialDeAtencionDAO historialDeAtencion);

}
