package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

public interface UsuarioDAO extends DaoBase<UsuarioDto> {

    ArrayList<UsuarioDto> ObtenerPorCorreoYContra(String correo, String contra);
}
