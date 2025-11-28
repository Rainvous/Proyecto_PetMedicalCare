package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.citas.CitaProgramadaDto;

public interface CitaAtencionDao extends DaoBase<CitaAtencionDto> {

    // --- Consultas Personalizadas (SELECTS) que implementaste en el DAO ---
    
    // Listar citas por ID de Mascota
    ArrayList<CitaAtencionDto> listarPorIdMascota(Integer mascotaId);

    // Búsqueda avanzada por fecha y veterinario (Versión 1)
    ArrayList<CitaAtencionDto> ListasBusquedaAvanzada(String fecha, String idVeterinario);

    // Búsqueda avanzada por fecha y veterinario (Versión 2 - Procedure optimizado/nuevo)
    ArrayList<CitaAtencionDto> ListasBusquedaAvanzada_2(String fecha, String idVeterinario);

    // Listar citas para verificar horarios disponibles (Retorna DTO de CitaProgramada)
    ArrayList<CitaProgramadaDto> ListarProgramadas(int idVeterinario, Date fechaDeCitas);

    // Listar citas filtradas por Mascota y Fecha específica
    ArrayList<CitaAtencionDto> ListasCitasPorMascotasYFechas(Integer idMascota, String fecha);

   
}