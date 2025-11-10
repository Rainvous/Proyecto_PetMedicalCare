package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;

import pe.edu.pucp.softpet.dao.MetodoDePagoDao;
import pe.edu.pucp.softpet.daoImp.MetodoDePagoDaoImpl;
import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;
import pe.edu.pucp.softpet.dto.util.enums.TipoMetodoPago;

public class MetodoDePagoBo {

    private final MetodoDePagoDao dao;

    public MetodoDePagoBo() {
        this.dao = new MetodoDePagoDaoImpl();
    }

    // INSERTAR con parámetros (retorna PK autogenerada)
    public Integer insertar(TipoMetodoPago nombre, boolean activo) {
        MetodoDePagoDto dto = new MetodoDePagoDto();
        dto.setNombre(nombre);      // se persiste como String con toString() en el DaoImpl
        dto.setActivo(activo);
        return this.dao.insertar(dto);
    }

    // MODIFICAR con parámetros (retorna filas afectadas)
    public Integer modificar(int metodoDePagoId, TipoMetodoPago nombre, boolean activo) {
        MetodoDePagoDto dto = new MetodoDePagoDto();
        dto.setMetodoDePagoId(metodoDePagoId);
        dto.setNombre(nombre);
        dto.setActivo(activo);
        return this.dao.modificar(dto);
    }

    public Integer eliminar(int metodoDePagoId) {
        MetodoDePagoDto dto = new MetodoDePagoDto();
        dto.setMetodoDePagoId(metodoDePagoId);
        return this.dao.eliminar(dto);
    }

    public MetodoDePagoDto obtenerPorId(int metodoDePagoId) {
        return this.dao.obtenerPorId(metodoDePagoId);
    }

    public ArrayList<MetodoDePagoDto> listarTodos() {
        return this.dao.listarTodos();
    }
}
