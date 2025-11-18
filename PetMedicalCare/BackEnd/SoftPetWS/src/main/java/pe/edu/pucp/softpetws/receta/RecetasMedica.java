package pe.edu.pucp.softpetws.receta;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;

import pe.edu.pucp.softpet.bo.RecetaMedicaBo;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

@WebService(serviceName = "RecetasMedica")
public class RecetasMedica {

    private final RecetaMedicaBo recetaBo;
//    private static final Date FECHA_MIN = Date.valueOf("1900-01-01");

    public RecetasMedica() {
        this.recetaBo = new RecetaMedicaBo();
    }

    // =========================
    //        INSERTAR
    // =========================
    @WebMethod(operationName = "insertar_receta")
    public Integer insertar(
            @WebParam(name = "citaId") int citaId,
            @WebParam(name = "fechaEmision") String fechaEmision,
            @WebParam(name = "vigenciaHasta") String vigenciaHasta,
            @WebParam(name = "diagnostico") String diagnostico,
            @WebParam(name = "observaciones") String observaciones,
            @WebParam(name = "activo") boolean activo) {

//        Date fechaE = (fechaEmision != null && !fechaEmision.isEmpty())
//                ? Date.valueOf(fechaEmision)
//                : FECHA_MIN;
//
//        Date fechaV = (vigenciaHasta != null && !vigenciaHasta.isEmpty())
//                ? Date.valueOf(vigenciaHasta)
//                : FECHA_MIN;

        return this.recetaBo.insertar(citaId, fechaEmision, vigenciaHasta, diagnostico, observaciones, activo);
    }

    // =========================
    //        MODIFICAR
    // =========================
    @WebMethod(operationName = "modificar_receta")
    public Integer modificar(
            @WebParam(name = "recetaId") int recetaId,
            @WebParam(name = "citaId") int citaId,
            @WebParam(name = "fechaEmision") String fechaEmision,
            @WebParam(name = "vigenciaHasta") String vigenciaHasta,
            @WebParam(name = "diagnostico") String diagnostico,
            @WebParam(name = "observaciones") String observaciones,
            @WebParam(name = "activo") boolean activo) {

//        Date fechaE = (fechaEmision != null && !fechaEmision.isEmpty())
//                ? Date.valueOf(fechaEmision)
//                : FECHA_MIN;
//
//        Date fechaV = (vigenciaHasta != null && !vigenciaHasta.isEmpty())
//                ? Date.valueOf(vigenciaHasta)
//                : FECHA_MIN;

        return this.recetaBo.modificar(recetaId, citaId, fechaEmision, vigenciaHasta, diagnostico, observaciones, activo);
    }

    // =========================
    //        ELIMINAR
    // =========================
    @WebMethod(operationName = "eliminar_receta")
    public Integer eliminar(@WebParam(name = "recetaId") int recetaId) {
        return this.recetaBo.eliminar(recetaId);
    }

    // =========================
    //      OBTENER POR ID
    // =========================
    @WebMethod(operationName = "obtener_por_id")
    public RecetaMedicaDto obtenerPorId(@WebParam(name = "recetaId") int recetaId) {
        return this.recetaBo.obtenerPorId(recetaId);
    }

    // =========================
    //       LISTAR TODOS
    // =========================
    @WebMethod(operationName = "listar_todos")
    public ArrayList<RecetaMedicaDto> listarTodos() {
        return this.recetaBo.listarTodos();
    }
    
    @WebMethod(operationName = "obtener_receta_por_cita")
    public RecetaMedicaDto obtenerPorIdCita(
            @WebParam(name = "citaId") int citaId) {
        
        return this.recetaBo.obtenerPorIdCita(citaId);
    }
}
