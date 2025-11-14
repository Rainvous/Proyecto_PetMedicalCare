package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.recetas.DetalleRecetaDto;

public interface DetalleRecetaDao extends DaoBase<DetalleRecetaDto> {

    ArrayList<DetalleRecetaDto> listarPorIdReceta(Integer recetaMedicaId);
}
