package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;

import pe.edu.pucp.softpet.dao.TipoProductoDao;
import pe.edu.pucp.softpet.daoImp.TipoProductoDaoImpl;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

public class TipoProductoBo {

    private final TipoProductoDao dao;

    public TipoProductoBo() {
        this.dao = new TipoProductoDaoImpl();
    }

    // INSERTAR con parámetros (retorna PK autogenerada)
    public Integer insertar(String nombre, String descripcion, boolean activo) {
        TipoProductoDto dto = new TipoProductoDto();
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setActivo(activo);
        return this.dao.insertar(dto);
    }

    // MODIFICAR con parámetros (retorna filas afectadas)
    public Integer modificar(int tipoProductoId, String nombre, String descripcion, boolean activo) {
        TipoProductoDto dto = new TipoProductoDto();
        dto.setTipoProductoId(tipoProductoId);
        dto.setNombre(nombre);
        dto.setDescripcion(descripcion);
        dto.setActivo(activo);
        return this.dao.modificar(dto);
    }

    public Integer eliminar(int tipoProductoId) {
        TipoProductoDto dto = new TipoProductoDto();
        dto.setTipoProductoId(tipoProductoId);
        return this.dao.eliminar(dto);
    }

    public TipoProductoDto obtenerPorId(int tipoProductoId) {
        return this.dao.obtenerPorId(tipoProductoId);
    }

    public ArrayList<TipoProductoDto> listarTodos() {
        return this.dao.listarTodos();
    }
}
