package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

public interface RecetaMedicaDao extends DaoBase<RecetaMedicaDto> {

    RecetaMedicaDto obtenerPorIdCita(Integer citaId);
    
    ArrayList<RecetaMedicaDto> listarPorMascotaYFecha(int mascotaId, String fecha);
}
