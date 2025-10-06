package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

/**
 *
 * @author marti
 */
public interface VeterinarioDao {

    public Integer insertar(VeterinarioDto veterinario);

    public VeterinarioDto obtenerPorId(Integer veterinarioId);

    public ArrayList<VeterinarioDto> listarTodos();

    public Integer modificar(VeterinarioDto veterinario);

    public Integer eliminar(VeterinarioDto veterinario);
}
