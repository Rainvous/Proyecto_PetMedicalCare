package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.personas.HorarioLaboralDto;

public interface HorarioLaboralDao extends DaoBase<HorarioLaboralDto> {

    int guardarHorario(HorarioLaboralDto horarioDto);
    
    ArrayList<HorarioLaboralDto> listarPorVeterinario(Integer veterinarioId);
}