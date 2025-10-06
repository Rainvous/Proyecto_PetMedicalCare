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
