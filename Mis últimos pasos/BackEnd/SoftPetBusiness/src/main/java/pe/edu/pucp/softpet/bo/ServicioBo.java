package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.ServicioDao;
import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

public class ServicioBo {

    private final ServicioDao servicioDao;

    public ServicioBo() {
        this.servicioDao = new ServicioDaoImpl();
    }

    public Integer insertar(int tipoServicioId, String nombre,
            String descripcion, double costo, String estado, boolean activo) {

        ServicioDto servicio = new ServicioDto();

        TipoServicioDto tipoServicio = new TipoServicioDto();
        tipoServicio.setTipoServicioId(tipoServicioId);

        servicio.setTipoServicio(tipoServicio);
        servicio.setNombre(nombre);
        servicio.setDescripcion(descripcion);
        servicio.setCosto(costo);
        servicio.setEstado(estado);
        servicio.setActivo(activo);

        return this.servicioDao.insertar(servicio);
    }

    public Integer modificar(int servicioId, int tipoServicioId, String nombre,
            String descripcion, double costo, String estado, boolean activo) {

        ServicioDto servicio = new ServicioDto();

        TipoServicioDto tipoServicio = new TipoServicioDto();
        tipoServicio.setTipoServicioId(tipoServicioId);

        servicio.setServicioId(servicioId);
        servicio.setTipoServicio(tipoServicio);
        servicio.setNombre(nombre);
        servicio.setDescripcion(descripcion);
        servicio.setCosto(costo);
        servicio.setEstado(estado);
        servicio.setActivo(activo);

        return this.servicioDao.modificar(servicio);
    }

    public Integer eliminar(int servicioId) {
        ServicioDto servicio = new ServicioDto();
        servicio.setServicioId(servicioId);
        return this.servicioDao.eliminar(servicio);
    }

    public ServicioDto obtenerPorId(int servicioId) {
        return this.servicioDao.obtenerPorId(servicioId);
    }

    public ArrayList<ServicioDto> listarTodos() {
        return this.servicioDao.listarTodos();
    }

    public ArrayList<ServicioDto> ListasBusquedaAvanzada(String nombre, String rango, String activo) {

        ServicioDto servicio = new ServicioDto();
        servicio.setNombre(nombre == null ? "" : nombre);

        return (ArrayList<ServicioDto>) servicioDao.ListasBusquedaAvanzada(servicio, rango == null ? "" : rango, activo == null ? "" : activo);
    }

    public ArrayList<ServicioDto> listarServiciosActivos() {
        return this.servicioDao.listarServiciosActivos();
    }

    public ArrayList<ServicioDto> ListarPorTipoServicio(String nombreTipo) {
        return this.servicioDao.ListarPorTipoServicio(nombreTipo);
    }

    public int VerificarSiElServicioTieneInformacion(int idServicio) {
        return this.servicioDao.VerificarSiElServicioTieneInformacion(idServicio);
    }
    
    public ArrayList<ServicioDto> ListasBusquedaAvanzada2(String nombre, String rango, String activo, Integer tipoId) {

        ServicioDto servicio = new ServicioDto();
        servicio.setNombre(nombre == null ? "" : nombre);

        return (ArrayList<ServicioDto>) servicioDao.ListasBusquedaAvanzada2(servicio, rango == null ? "" : rango, activo == null ? "" : activo, tipoId);
    }
        // -----------------------------------------------------------------------------------
    //  FUNCIÓN ´PAGINACION: Búsqueda Paginada de SERVICIOS
    // -----------------------------------------------------------------------------------
    public ArrayList<ServicioDto> buscarServiciosPaginados(String nombre, String rango, Boolean activo, int pagina) {
        
        // 1. Limpieza de datos: Convertir vacíos a NULL para optimizar el 'IS NULL' del SQL
        String nombreFiltro = (nombre != null && !nombre.trim().isEmpty()) ? nombre.trim() : null;
        String rangoFiltro = (rango != null && !rango.trim().isEmpty()) ? rango.trim() : null;

        // 2. Validación de Página: Si es <= 0, forzamos página 1
        int numeroPagina = pagina > 0 ? pagina : 1;

        // 3. Llamada al DAO (El DAO ya maneja la lógica de MySQL vs MSSQL)
        List<ServicioDto> listaResultado = this.servicioDao.buscarServiciosPaginados(
            nombreFiltro, 
            rangoFiltro, 
            activo, 
            numeroPagina
        );

        // 4. Conversión segura a ArrayList
        if (listaResultado instanceof ArrayList) {
            return (ArrayList<ServicioDto>) listaResultado;
        } else {
            return new ArrayList<>(listaResultado);
        }
    }
    
    

}
