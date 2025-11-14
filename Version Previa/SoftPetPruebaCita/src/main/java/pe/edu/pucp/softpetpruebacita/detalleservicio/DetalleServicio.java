package pe.edu.pucp.softpetpruebacita.detalleservicio;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;

import pe.edu.pucp.softpet.bo.DetalleServicioBo;
import pe.edu.pucp.softpet.dto.servicios.DetalleServicioDto;

@WebService(serviceName = "DetalleServicio")
public class DetalleServicio {

    private final DetalleServicioBo detalleServicioBo;

    public DetalleServicio() {
        this.detalleServicioBo = new DetalleServicioBo();
    }

    // =========================
    //        INSERTAR
    // =========================
    @WebMethod(operationName = "insertar_detalleservicio")
    public Integer insertar(
            @WebParam(name = "citaId") int citaId,
            @WebParam(name = "servicioId") int servicioId,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "costo") double costo,
            @WebParam(name = "activo") boolean activo) {

        return this.detalleServicioBo.insertar(citaId, servicioId, descripcion, costo, activo);
    }

    // =========================
    //        MODIFICAR
    // =========================
    @WebMethod(operationName = "modificar_detalleservicio")
    public Integer modificar(
            @WebParam(name = "detalleServicioId") int detalleServicioId,
            @WebParam(name = "citaId") int citaId,
            @WebParam(name = "servicioId") int servicioId,
            @WebParam(name = "descripcion") String descripcion,
            @WebParam(name = "costo") double costo,
            @WebParam(name = "activo") boolean activo) {

        return this.detalleServicioBo.modificar(detalleServicioId, citaId, servicioId, descripcion, costo, activo);
    }

    // =========================
    //        ELIMINAR
    // =========================
    @WebMethod(operationName = "eliminar_detalleservicio")
    public Integer eliminar(@WebParam(name = "detalleServicioId") int detalleServicioId) {
        return this.detalleServicioBo.eliminar(detalleServicioId);
    }

    // =========================
    //      OBTENER POR ID
    // =========================
    @WebMethod(operationName = "obtener_por_id")
    public DetalleServicioDto obtenerPorId(@WebParam(name = "detalleServicioId") int detalleServicioId) {
        return this.detalleServicioBo.obtenerPorId(detalleServicioId);
    }

    // =========================
    //       LISTAR TODOS
    // =========================
    @WebMethod(operationName = "listar_todos")
    public ArrayList<DetalleServicioDto> listarTodos() {
        return this.detalleServicioBo.listarTodos();
    }
}
