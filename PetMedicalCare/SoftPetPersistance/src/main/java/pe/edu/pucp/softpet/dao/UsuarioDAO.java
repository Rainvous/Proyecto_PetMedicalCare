package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

/**
 *
 * @author marti
 */
public interface UsuarioDAO extends DaoBase<UsuarioDto> {
    ArrayList<UsuarioDto> ObtenerPorCorreoYContra(String correo, String contra  );
}
