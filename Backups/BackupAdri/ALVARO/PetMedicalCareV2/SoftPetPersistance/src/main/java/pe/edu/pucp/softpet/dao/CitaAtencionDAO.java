package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;

/**
 *
 * @author ferro
 */
public interface CitaAtencionDAO {
    
    public Integer insertar(CitaAtencionDTO citaAtencion);

    public CitaAtencionDTO obtenerPorId(Integer citaAtencionId);

    public ArrayList<CitaAtencionDTO> listarTodos();

    public Integer modificar(CitaAtencionDTO citaAtencion);
}
