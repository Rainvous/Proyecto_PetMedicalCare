package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.RolDao;
import pe.edu.pucp.softpet.daoImp.RolDaoImpl;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

public class RolBO {

    private final RolDao rolDao;

    public RolBO() {
        this.rolDao = new RolDaoImpl();
    }

    public Integer insertar(String nombre, boolean activo) {

        RolDto rolDto = new RolDto();
        rolDto.setNombre(nombre);
        rolDto.setActivo(activo);

        return this.rolDao.insertar(rolDto);
    }

    public Integer modificar(int rolId, String nombre, boolean activo) {
        RolDto rolDto = new RolDto();

        rolDto.setRolId(rolId);
        rolDto.setNombre(nombre);
        rolDto.setActivo(activo);

        return this.rolDao.modificar(rolDto);
    }

    public Integer eliminar(int rolId) {
        RolDto rolDto = new RolDto();
        rolDto.setRolId(rolId);
        return this.rolDao.eliminar(rolDto);
    }

    public RolDto obtenerPorID(int rolId) {
        RolDto rolDto = new RolDto();
        rolDto.setRolId(rolId);
        return this.rolDao.obtenerPorId(rolId);
    }

    public ArrayList<RolDto> listarTodos() {
        return this.rolDao.listarTodos();
    }
}
