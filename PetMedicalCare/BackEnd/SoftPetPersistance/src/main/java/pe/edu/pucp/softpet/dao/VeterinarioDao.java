package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

public interface VeterinarioDao extends DaoBase<VeterinarioDto> {

    ArrayList<VeterinarioDto> listarVeterinariosActivos();
}
