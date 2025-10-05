package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

public interface PersonaDao {

    public Integer insertar(PersonaDto persona);

    public PersonaDto obtenerPorId(Integer personaId);

    public ArrayList<PersonaDto> listarTodos();

    public Integer modificar(PersonaDto persona);

    public Integer eliminar(PersonaDto persona);
}
