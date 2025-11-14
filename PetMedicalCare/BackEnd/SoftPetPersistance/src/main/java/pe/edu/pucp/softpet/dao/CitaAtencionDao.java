package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

public interface CitaAtencionDao extends DaoBase<CitaAtencionDto> {
    
    ArrayList<CitaAtencionDto> listarPorIdMascota(Integer mascotaId);
}
