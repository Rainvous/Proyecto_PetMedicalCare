/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;


/**
 *
 * @author ferro
 */
public interface RolDAO {
    
    public Integer insertar(RolDto rol);
    
    public RolDto obtenerPorId(Integer rolId);
    
    public ArrayList<RolDto> listarTodos();
    
    public Integer modificar(RolDto rol);
    
    public Integer eliminar(RolDto rol);
    
    
}
