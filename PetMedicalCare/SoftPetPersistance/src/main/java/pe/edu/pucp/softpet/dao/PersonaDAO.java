/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

/**
 *
 * @author marti
 */
public interface PersonaDAO {
    
    public Integer insertar(PersonaDto persona);

    public PersonaDto obtenerPorId(Integer personaId);

    public ArrayList<PersonaDto> listarTodos();

    public Integer modificar(PersonaDto persona);

    public Integer eliminar(PersonaDto persona);
}
