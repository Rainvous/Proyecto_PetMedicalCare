package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

public interface ServicioDao extends DaoBase<ServicioDto> {

    // Consultas específicas existentes
    ArrayList<ServicioDto> ListarPorTipoServicio(String NombreTipo);

    ArrayList<ServicioDto> ListarPorNombre(String Nombre);
    
    ArrayList<ServicioDto> listarServiciosActivos();

    // Búsqueda avanzada antigua (Por Procedure)
    ArrayList<ServicioDto> ListasBusquedaAvanzada(ServicioDto servicio, String rango, String activo);

    // -------------------------------------------------------------------------
    // NUEVOS MÉTODOS AGREGADOS (Según tu Impl)
    // -------------------------------------------------------------------------
    public ArrayList<ServicioDto> ListasBusquedaAvanzada2(ServicioDto servicio, String rango, String activo, Integer tipoId) ;
    // Búsqueda Paginada (MySQL / SQL Server)
    List<ServicioDto> buscarServiciosPaginados(String nombre, String rangoId, Boolean activo, int pagina);

    // Verificación de integridad
    int VerificarSiElServicioTieneInformacion(int idServicio);
}