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
public interface UsuariosDAO {
    public Integer insertar(UsuarioDto usuario);

    public UsuarioDto obtenerPorId(Integer usuarioId);

    public ArrayList<UsuarioDto> listarTodos();

    public Integer modificar(UsuarioDto usuario);

    public Integer eliminar(UsuarioDto usuario);
}
