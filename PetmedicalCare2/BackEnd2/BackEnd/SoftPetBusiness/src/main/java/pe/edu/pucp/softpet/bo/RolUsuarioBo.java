package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.RolUsuarioDao;
import pe.edu.pucp.softpet.daoImp.RolDaoImpl;
import pe.edu.pucp.softpet.daoImp.RolUsuarioDaoImpl;
import pe.edu.pucp.softpet.daoImp.UsuarioDaoImpl;
import pe.edu.pucp.softpet.dto.usuarios.RolUsuarioDto;

public class RolUsuarioBo {

    private final RolUsuarioDao rolUsuarioDao;

    public RolUsuarioBo() {
        this.rolUsuarioDao = new RolUsuarioDaoImpl();
    }

    public Integer insertar(int rolId, int usuarioId, boolean activo) {

        RolUsuarioDto rolUsuario = new RolUsuarioDto();

        rolUsuario.setRol(new RolDaoImpl().obtenerPorId(rolId));
        rolUsuario.setUsuario(new UsuarioDaoImpl().obtenerPorId(usuarioId));
        rolUsuario.setActivo(activo);

        return this.rolUsuarioDao.insertar(rolUsuario);
    }

    public Integer modificar(int rolUsuarioId, int rolId, int usuarioId, boolean activo) {
        RolUsuarioDto rolUsuario = new RolUsuarioDto();

        rolUsuario.setRolUsuarioId(rolUsuarioId);
        rolUsuario.setRol(new RolDaoImpl().obtenerPorId(rolId));
        rolUsuario.setUsuario(new UsuarioDaoImpl().obtenerPorId(usuarioId));
        rolUsuario.setActivo(activo);

        return this.rolUsuarioDao.modificar(rolUsuario);
    }

    public Integer eliminar(Integer rolUsuarioId) {
        RolUsuarioDto rolUsuarioDto = new RolUsuarioDto();
        rolUsuarioDto.setRolUsuarioId(rolUsuarioId);
        return this.rolUsuarioDao.eliminar(rolUsuarioDto);
    }

    public RolUsuarioDto obtenerPorID(int rolUsuarioId) {
        RolUsuarioDto rolUsuarioDto = new RolUsuarioDto();
        rolUsuarioDto.setRolUsuarioId(rolUsuarioId);
        return this.rolUsuarioDao.obtenerPorId(rolUsuarioId);
    }

    public ArrayList<RolUsuarioDto> listarTodos() {
        return this.rolUsuarioDao.listarTodos();
    }
}
