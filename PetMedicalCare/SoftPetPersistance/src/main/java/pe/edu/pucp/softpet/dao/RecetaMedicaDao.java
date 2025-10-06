package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

/**
 *
 * @author marti
 */
public interface RecetaMedicaDao {

    public Integer insertar(RecetaMedicaDto recetas);

    public RecetaMedicaDto obtenerPorId(Integer recetasId);

    public ArrayList<RecetaMedicaDto> listarTodos();

    public Integer modificar(RecetaMedicaDto recetas);

    public Integer eliminar(RecetaMedicaDto recetas);
}
