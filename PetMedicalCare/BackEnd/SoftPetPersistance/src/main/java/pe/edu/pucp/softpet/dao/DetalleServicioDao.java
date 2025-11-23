package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.servicios.DetalleServicioDto;

public interface DetalleServicioDao extends DaoBase<DetalleServicioDto> {
     public ArrayList<DetalleServicioDto> listarPorIdCita(int idcita) ;
    
}
