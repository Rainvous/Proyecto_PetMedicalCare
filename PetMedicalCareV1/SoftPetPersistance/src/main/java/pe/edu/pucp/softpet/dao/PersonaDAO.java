/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.actoresdto.PersonasDTO;

/**
 *
 * @author User
 */
public interface PersonaDAO {
     public Integer insertar(PersonasDTO persona);
    
    public PersonasDTO obtenerPorId(Integer personaId);
    
    public ArrayList<PersonasDTO> listarTodos();
    
    public Integer modificar(PersonasDTO persona);
    
    public Integer eliminar(PersonasDTO persona);
    
}
