package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.RolDao;
import pe.edu.pucp.softpet.daoImp.RolDaoImpl;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

public class RolBO {

    private RolDao rolDao;

    public RolBO() {
        this.rolDao = new RolDaoImpl();
    }

    public Integer insertar(String nombre, Boolean activo) {
        RolDto rolDto = new RolDto();
        rolDto.setNombre(nombre);
        rolDto.setActivo(activo);
        return this.rolDao.insertar(rolDto);
    }

    public RolDto obtenerPorID(Integer rolId) {
        RolDto rolDto = new RolDto();
        rolDto.setRolId(rolId);
        return this.rolDao.obtenerPorId(rolId);
    }

    public ArrayList<RolDto> listarTodos() {
        return this.rolDao.listarTodos();
    }

    public Integer modificar(Integer rolId, String nombre, Boolean activo) {
        RolDto rolDto = new RolDto();
        rolDto.setRolId(rolId);
        rolDto.setNombre(nombre);
        rolDto.setActivo(activo);
        return this.rolDao.modificar(rolDto);
    }

    public Integer eliminar(Integer rolId) {
        RolDto rolDto = new RolDto();
        rolDto.setRolId(rolId);
        return this.rolDao.eliminar(rolDto);
    }

}
