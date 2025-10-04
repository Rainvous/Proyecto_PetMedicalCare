package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;

public interface MascotaDao {

    public Integer insertar(MascotaDto mascota);

    public MascotaDto obtenerPorId(Integer mascotaId);

    public ArrayList<MascotaDto> listarTodos();

    public Integer modificar(MascotaDto mascota);

    public Integer eliminar(MascotaDto mascota);
}
