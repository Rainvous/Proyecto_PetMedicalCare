/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
/**
 *
 * @author marti
 */
public interface UsuariosDAO extends DaoBase<UsuarioDto> {
     public UsuarioDto obtenerPorId(Integer idDto);

}
