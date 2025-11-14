package pe.edu.pucp.softpetws.servicio;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.ServicioBo;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

@WebService(serviceName = "Servicios")
public class Servicios {

    private final ServicioBo servicioBo;

    public Servicios() {
        this.servicioBo = new ServicioBo();
    }

    @WebMethod(operationName = "insertar_servicios")
    public Integer insertar(@WebParam(name = "tipoServicioId") int tipoServicioId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "costo") double costo,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "activo") boolean activo) {

        return this.servicioBo.insertar(tipoServicioId, nombre, descripcion,
                costo, estado, activo);
    }

    @WebMethod(operationName = "modificar_servicios")
    public Integer modificar(@WebParam(name = "servicioId") int servicioId,
            @WebParam(name = "tipoServicioId") int tipoServicioId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "costo") double costo,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "activo") boolean activo) {

        return this.servicioBo.modificar(servicioId, tipoServicioId, nombre,
                descripcion, costo, estado, activo);
    }

    @WebMethod(operationName = "eliminar_servicios")
    public Integer eliminar(int servicioId) {
        return this.servicioBo.eliminar(servicioId);
    }

    @WebMethod(operationName = "obtener_por_id")
    public ServicioDto obtenerPorId(@WebParam(name = "id") int servicioId) {
        return this.servicioBo.obtenerPorId(servicioId);
    }

    @WebMethod(operationName = "listar_todos")
    public ArrayList<ServicioDto> listarTodos() {
        return this.servicioBo.listarTodos();
    }

    @WebMethod(operationName = "ListasBusquedaAvanzada")
    public ArrayList<ServicioDto> ListasBusquedaAvanzada(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "rango") String rango,
            @WebParam(name = "activo") String activo) {

        return servicioBo.ListasBusquedaAvanzada(nombre, rango, activo);
    }
}
