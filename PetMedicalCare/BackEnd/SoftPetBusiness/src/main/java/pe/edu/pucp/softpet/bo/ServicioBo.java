package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

public class ServicioBo {

    private final ServicioDaoImpl servicioDao;

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
    public ArrayList<ServicioDto> ListaBusqeudaAvanzada2ConPaginado(String nombre, String rango, Boolean activo, Integer tipo) {
        
        // 1. Limpieza de datos: Convertir vacíos a NULL
        // Para nombre y rango seguimos validando cadenas vacías
        String nombreFiltro = (nombre != null && !nombre.trim().isEmpty()) ? nombre.trim() : null;
        String rangoFiltro = (rango != null && !rango.trim().isEmpty()) ? rango.trim() : null;

        // 2. Manejo de ACTIVO
        // Como ya recibimos un Boolean (true, false o null), lo pasamos directamente.
        // El DAO ya sabe que 'null' significa "traer todos".
        
        // 3. Validación de PAGINACIÓN (tipo -> pagina)
        // Si es nulo o <= 0, forzamos página 1
        int numeroPagina = (tipo != null && tipo > 0) ? tipo : 1;

        // 4. Llamada al DAO
        // Pasamos el booleano 'activo' directamente
        List<ServicioDto> listaResultado = this.servicioDao.buscarServiciosPaginados(
            nombreFiltro, 
            rangoFiltro, 
            activo, 
            numeroPagina
        );

        // 5. Conversión de retorno (List -> ArrayList)
        if (listaResultado instanceof ArrayList) {
            return (ArrayList<ServicioDto>) listaResultado;
        } else {
            return new ArrayList<>(listaResultado);
        }
    }

}
