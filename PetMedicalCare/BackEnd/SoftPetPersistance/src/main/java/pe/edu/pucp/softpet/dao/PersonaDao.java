package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

public interface PersonaDao extends DaoBase<PersonaDto> {

    ArrayList<PersonaDto> listarPersonasActivas();
}
