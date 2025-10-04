package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

public interface RolDao {

    public Integer insertar(RolDto rol);

    public RolDto obtenerPorId(Integer rolId);

    public ArrayList<RolDto> listarTodos();

    public Integer modificar(RolDto rol);

    public Integer eliminar(RolDto rol);
}
