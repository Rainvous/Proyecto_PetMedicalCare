package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

public interface UsuarioDao {

    public Integer insertar(UsuarioDto usuario);

    public UsuarioDto obtenerPorId(Integer usuarioId);

    public ArrayList<UsuarioDto> listarTodos();

    public Integer modificar(UsuarioDto usuario);

    public Integer eliminar(UsuarioDto usuario);
}
