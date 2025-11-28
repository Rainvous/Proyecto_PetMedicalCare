package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

public interface RecetaMedicaDao extends DaoBase<RecetaMedicaDto> {

    RecetaMedicaDto obtenerPorIdCita(Integer citaId);
    
    ArrayList<RecetaMedicaDto> listarPorMascotaYFecha(int mascotaId, String fecha);
    
    //ArrayList<RecetaMedicaDto> listarBusquedaAvanzada(String mascota, String duenio, java.sql.Date fecha, String activo);

    ArrayList<RecetaMedicaDto> listarBusquedaAvanzada(RecetaMedicaDto dto, String fechaStr, String activo);
}
