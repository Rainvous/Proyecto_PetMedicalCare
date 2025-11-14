package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.actoresdto.VeterinariosDTO;

/**
 *
 * @author ferro
 */
public interface VeterinarioDAO {
    
    public Integer insertar(VeterinariosDTO veterinario);

    public VeterinariosDTO obtenerPorId(Integer veterinarioId);

    public ArrayList<VeterinariosDTO> listarTodos();

    public Integer modificar(VeterinariosDTO veterinario);

    public Integer eliminar(VeterinariosDTO veterinario);
}
