package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;

public interface MascotaDao extends DaoBase<MascotaDto> {
    
    ArrayList<MascotaDto> listarPorIdPersona(Integer personaId);
    
    ArrayList<MascotaDto> listarMascotasActivas();
}
