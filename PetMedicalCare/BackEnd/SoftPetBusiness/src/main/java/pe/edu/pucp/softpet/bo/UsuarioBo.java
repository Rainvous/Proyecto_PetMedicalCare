package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.UsuarioDAO;
import pe.edu.pucp.softpet.daoImp.UsuarioDaoImpl;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

public class UsuarioBo {

    private final UsuarioDAO usuarioDao;

    public UsuarioBo() {
        this.usuarioDao = new UsuarioDaoImpl();
    }

    public Integer insertar(String username, String password,
            String correo, boolean activo) {

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setUsername(username);
        usuarioDto.setPassword(password);
        usuarioDto.setCorreo(correo);
        usuarioDto.setActivo(activo);

        return this.usuarioDao.insertar(usuarioDto);
    }

    public Integer modificar(int usuarioId, String username, String password,
            String correo, boolean activo) {

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setUsuarioId(usuarioId);
        usuarioDto.setUsername(username);
        usuarioDto.setPassword(password);
        usuarioDto.setCorreo(correo);
        usuarioDto.setActivo(activo);

        return this.usuarioDao.modificar(usuarioDto);
    }

    public Integer eliminar(Integer usuarioId) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuarioId(usuarioId);
        return this.usuarioDao.eliminar(usuarioDto);
    }

    public UsuarioDto obtenerPorId(Integer usuarioId) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuarioId(usuarioId);
        return this.usuarioDao.obtenerPorId(usuarioId);
    }

    public ArrayList<UsuarioDto> listarTodos() {
        return this.usuarioDao.listarTodos();
    }
    public ArrayList<UsuarioDto> ObtenerPorCorreoYContra(String correo, String contra) {
        // Simplemente pasamos la llamada al DAO
        return this.usuarioDao.ObtenerPorCorreoYContra(correo, contra);
    }
}
