/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.usuarios.RolUsuarioDto;

/**
 *
 * @author marti
 */
public interface RolUsuarioDao {
    public Integer insertar(RolUsuarioDto rolUsuario);

    public RolUsuarioDto obtenerPorId(Integer rolUsuarioId);

    public ArrayList<RolUsuarioDto> listarTodos();

    public Integer modificar(RolUsuarioDto rolUsuario);

    public Integer eliminar(RolUsuarioDto rolUsuario);
}
