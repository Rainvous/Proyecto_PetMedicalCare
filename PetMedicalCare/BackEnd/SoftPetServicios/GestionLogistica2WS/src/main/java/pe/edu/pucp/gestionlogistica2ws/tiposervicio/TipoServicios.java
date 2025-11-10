package pe.edu.pucp.gestionlogistica2ws.tiposervicio;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;

import pe.edu.pucp.softpet.bo.TipoServicioBo;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

@WebService(serviceName = "tiposervicio")
public class TipoServicios {

    private final TipoServicioBo tipoServicioBo;

    public TipoServicios() {
        this.tipoServicioBo = new TipoServicioBo();
    }

    @WebMethod(operationName = "insertar_tiposervicios")
    public Integer insertar(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "activo") boolean activo) {

        return this.tipoServicioBo.insertar(nombre, descripcion, activo);
    }

    @WebMethod(operationName = "modificar_tiposervicios")
    public Integer modificar(
            @WebParam(name = "tipoServicioId") int tipoServicioId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "activo") boolean activo) {

        return this.tipoServicioBo.modificar(tipoServicioId, nombre, descripcion, activo);
    }

    @WebMethod(operationName = "eliminar_tiposervicios")
    public int eliminar(@WebParam(name = "tipoServicioId") int tipoServicioId) {
        return this.tipoServicioBo.eliminar(tipoServicioId);
    }

    @WebMethod(operationName = "obtener_por_id")
    public TipoServicioDto obtenerPorId(@WebParam(name = "id") int tipoServicioId) {
        return this.tipoServicioBo.obtenerPorId(tipoServicioId);
    }

    @WebMethod(operationName = "listar_todos")
    public ArrayList<TipoServicioDto> listarTodos() {
        return this.tipoServicioBo.listarTodos();
    }
}
