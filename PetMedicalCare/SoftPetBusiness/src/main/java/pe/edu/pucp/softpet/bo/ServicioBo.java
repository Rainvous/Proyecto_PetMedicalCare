package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.ServicioDao;
import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
import pe.edu.pucp.softpet.daoImp.TipoServicioDaoImpl;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

public class ServicioBo {

    private ServicioDao servicioDao;

    public ServicioBo() {
        this.servicioDao = new ServicioDaoImpl();
    }

    // Inserta un servicio asegurando que el nombre y descripción estén en mayúsculas
    public Integer insertar(String nombre, double costo, String estado,
            String descripcion, boolean activo, int tipoServicioId) {
        ServicioDto servicio = new ServicioDto();

        // Normalizamos los textos (trim + upper)
        servicio.setNombre(nombre.trim().toUpperCase());
        servicio.setCosto(costo);
        servicio.setEstado(estado.trim().toUpperCase());
        servicio.setDescripcion(descripcion.trim().toUpperCase());
        servicio.setActivo(activo);

        // Asignamos el tipo de servicio
        servicio.setTipoServicio(new TipoServicioDaoImpl().obtenerPorId(tipoServicioId));

        return this.servicioDao.insertar(servicio);
    }

    // Modifica un servicio existente
    public Integer modificar(int servicioId, String nombre, double costo, String estado,
            String descripcion, boolean activo, int tipoServicioId) {
        ServicioDto servicio = new ServicioDto();

        servicio.setServicioId(servicioId);
        servicio.setNombre(nombre.trim().toUpperCase());
        servicio.setCosto(costo);
        servicio.setEstado(estado.trim().toUpperCase());
        servicio.setDescripcion(descripcion.trim().toUpperCase());
        servicio.setActivo(activo);
        servicio.setTipoServicio(new TipoServicioDaoImpl().obtenerPorId(tipoServicioId));

        return this.servicioDao.modificar(servicio);
    }

    // Elimina un servicio por su ID
    public Integer eliminar(int servicioId) {
        ServicioDto servicio = new ServicioDto();
        servicio.setServicioId(servicioId);
        return this.servicioDao.eliminar(servicio);
    }

    // Obtiene un servicio por su ID
    public ServicioDto obtenerPorId(int servicioId) {
        return this.servicioDao.obtenerPorId(servicioId);
    }

    // Lista todos los servicios registrados
    public ArrayList<ServicioDto> listarTodos() {
        return this.servicioDao.listarTodos();
    }
}
