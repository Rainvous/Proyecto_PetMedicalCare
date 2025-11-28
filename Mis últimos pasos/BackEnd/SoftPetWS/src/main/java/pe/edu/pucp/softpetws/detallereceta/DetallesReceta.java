package pe.edu.pucp.softpetws.detallereceta;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;

import pe.edu.pucp.softpet.bo.DetalleRecetaBo;
import pe.edu.pucp.softpet.dto.recetas.DetalleRecetaDto;

@WebService(serviceName = "DetallesReceta")
public class DetallesReceta {

    private final DetalleRecetaBo detalleRecetaBo;

    public DetallesReceta() {
        this.detalleRecetaBo = new DetalleRecetaBo();
    }

    // =========================
    //        INSERTAR
    // =========================
    @WebMethod(operationName = "insertar_detallereceta")
    public Integer insertar(
            @WebParam(name = "recetaMedicaId") int recetaMedicaId,
            @WebParam(name = "descripcionMedicamento") String descripcionMedicamento,
            @WebParam(name = "presentacion") String presentacion,
            @WebParam(name = "viaAdministracion") String viaAdministracion,
            @WebParam(name = "dosis") String dosis,
            @WebParam(name = "frecuencia") String frecuencia,
            @WebParam(name = "duracion") String duracion,
            @WebParam(name = "indicacion") String indicacion,
            @WebParam(name = "cantidad") String cantidad,
            @WebParam(name = "activo") boolean activo) {

        return this.detalleRecetaBo.insertar(
                recetaMedicaId,
                descripcionMedicamento,
                presentacion,
                viaAdministracion,
                dosis,
                frecuencia,
                duracion,
                indicacion,
                cantidad,
                activo
        );
    }

    // =========================
    //        MODIFICAR
    // =========================
    @WebMethod(operationName = "modificar_detallereceta")
    public Integer modificar(
            @WebParam(name = "detalleRecetaId") int detalleRecetaId,
            @WebParam(name = "recetaMedicaId") int recetaMedicaId,
            @WebParam(name = "descripcionMedicamento") String descripcionMedicamento,
            @WebParam(name = "presentacion") String presentacion,
            @WebParam(name = "viaAdministracion") String viaAdministracion,
            @WebParam(name = "dosis") String dosis,
            @WebParam(name = "frecuencia") String frecuencia,
            @WebParam(name = "duracion") String duracion,
            @WebParam(name = "indicacion") String indicacion,
            @WebParam(name = "cantidad") String cantidad,
            @WebParam(name = "activo") boolean activo) {

        return this.detalleRecetaBo.modificar(
                detalleRecetaId,
                recetaMedicaId,
                descripcionMedicamento,
                presentacion,
                viaAdministracion,
                dosis,
                frecuencia,
                duracion,
                indicacion,
                cantidad,
                activo
        );
    }

    // =========================
    //        ELIMINAR
    // =========================
    @WebMethod(operationName = "eliminar_detallereceta")
    public Integer eliminar(@WebParam(name = "detalleRecetaId") int detalleRecetaId) {
        return this.detalleRecetaBo.eliminar(detalleRecetaId);
    }

    // =========================
    //      OBTENER POR ID
    // =========================
    @WebMethod(operationName = "obtener_por_id")
    public DetalleRecetaDto obtenerPorId(@WebParam(name = "detalleRecetaId") int detalleRecetaId) {
        return this.detalleRecetaBo.obtenerPorId(detalleRecetaId);
    }

    // =========================
    //       LISTAR TODOS
    // =========================
    @WebMethod(operationName = "listar_todos")
    public ArrayList<DetalleRecetaDto> listarTodos() {
        return this.detalleRecetaBo.listarTodos();
    }

    @WebMethod(operationName = "listar_detalles_por_receta")
    public ArrayList<DetalleRecetaDto> listarPorIdReceta(
            @WebParam(name = "recetaId") int recetaId) {

        return this.detalleRecetaBo.listarPorIdReceta(recetaId);
    }
}
