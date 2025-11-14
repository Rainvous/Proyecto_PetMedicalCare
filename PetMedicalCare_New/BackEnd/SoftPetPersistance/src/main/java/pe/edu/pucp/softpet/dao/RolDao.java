package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

public interface RolDao extends DaoBase<RolDto> {

    ArrayList<RolDto> ObtenerRolesDelUsuario(Integer idUser);
}
