package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.ServicioDao;
import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
import pe.edu.pucp.softpet.daoImp.TipoServicioDaoImpl;
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
    
    public ArrayList<ServicioDto> ListasBusquedaAvanzada(String nombre,String rango,String activo){
        
        ServicioDto servicio = new ServicioDto();
        servicio.setNombre(nombre == null ? "" : nombre);

        return (ArrayList<ServicioDto>)servicioDao.ListasBusquedaAvanzada(servicio, rango == null ? "" : rango, activo == null ? "" : activo);
    }
    
    public ArrayList<ServicioDto> listarServiciosActivos() {
        return this.servicioDao.listarServiciosActivos();
    }
    
    public ArrayList<ServicioDto> ListarPorTipoServicio(String nombreTipo) {
        return this.servicioDao.ListarPorTipoServicio(nombreTipo);
    }
    
    public int VerificarSiElServicioTieneInformacion(int idServicio){
        return this.servicioDao.VerificarSiElServicioTieneInformacion(idServicio);
    }
}
