package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;

import pe.edu.pucp.softpet.dao.TipoServicioDao;
import pe.edu.pucp.softpet.daoImp.TipoServicioDaoImpl;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

public class TipoServicioBo {

    private final TipoServicioDao dao;

    public TipoServicioBo() {
        this.dao = new TipoServicioDaoImpl();
    }

    public Integer insertar(String nombre, String descripcion, boolean activo) {
        TipoServicioDto dto = new TipoServicioDto();
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setActivo(activo);
        return this.dao.insertar(dto);
    }

    public Integer modificar(int tipoServicioId, String nombre, String descripcion, boolean activo) {
        TipoServicioDto dto = new TipoServicioDto();
        dto.setTipoServicioId(tipoServicioId);
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setActivo(activo);
        return this.dao.modificar(dto);
    }

    public Integer eliminar(int tipoServicioId) {
        TipoServicioDto dto = new TipoServicioDto();
        dto.setTipoServicioId(tipoServicioId);
        return this.dao.eliminar(dto);
    }

    public TipoServicioDto obtenerPorId(int tipoServicioId) {
        return this.dao.obtenerPorId(tipoServicioId);
    }

    public ArrayList<TipoServicioDto> listarTodos() {
        return this.dao.listarTodos();
    }
}
