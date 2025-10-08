package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

public interface ServicioDao extends DaoBase<ServicioDto> {

    ArrayList<ServicioDto> ListarPorTipoServicio(String NombreTipo);

    ArrayList<ServicioDto> ListarPorNombre(String Nombre);
}
