package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;

import pe.edu.pucp.softpet.dao.DetalleServicioDao;
import pe.edu.pucp.softpet.daoImp.DetalleServicioDaoImpl;
import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;
import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;
import pe.edu.pucp.softpet.dto.servicios.DetalleServicioDto;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

public class DetalleServicioBo {

    private final DetalleServicioDao dao;

    public DetalleServicioBo() {
        this.dao = new DetalleServicioDaoImpl();
    }

    // INSERTAR con parámetros (retorna PK autogenerada)
    public Integer insertar(int citaId, int servicioId, String descripcion, double costo, boolean activo) {
        DetalleServicioDto dto = new DetalleServicioDto();

        // Resolver FKs como objetos (el DaoImpl espera DTOs anidados)
        CitaAtencionDto cita = new CitaAtencionDaoImpl().obtenerPorId(citaId);
        ServicioDto servicio = new ServicioDaoImpl().obtenerPorId(servicioId);

        dto.setCita(cita);
        dto.setServicio(servicio);
        dto.setDescripcion(descripcion);
        dto.setCosto(costo);
        dto.setActivo(activo);

        return this.dao.insertar(dto);
    }

    // MODIFICAR con parámetros (retorna filas afectadas)
    public Integer modificar(int detalleServicioId, int citaId, int servicioId, String descripcion, double costo, boolean activo) {
        DetalleServicioDto dto = new DetalleServicioDto();

        CitaAtencionDto cita = new CitaAtencionDaoImpl().obtenerPorId(citaId);
        ServicioDto servicio = new ServicioDaoImpl().obtenerPorId(servicioId);

        dto.setDetalleServicioId(detalleServicioId);
        dto.setCita(cita);
        dto.setServicio(servicio);
        dto.setDescripcion(descripcion);
        dto.setCosto(costo);
        dto.setActivo(activo);

        return this.dao.modificar(dto);
    }

    public Integer eliminar(int detalleServicioId) {
        DetalleServicioDto dto = new DetalleServicioDto();
        dto.setDetalleServicioId(detalleServicioId);
        return this.dao.eliminar(dto);
    }

    public DetalleServicioDto obtenerPorId(int detalleServicioId) {
        return this.dao.obtenerPorId(detalleServicioId);
    }

    public ArrayList<DetalleServicioDto> listarTodos() {
        return this.dao.listarTodos();
    }
}
