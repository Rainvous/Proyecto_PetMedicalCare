package pe.edu.pucp.citasmedicas;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.core.Context;

import java.net.URI;
import java.util.ArrayList;

import pe.edu.pucp.softpet.bo.DetalleServicioBo;
import pe.edu.pucp.softpet.dto.servicios.DetalleServicioDto;

@Path("DetalleServicio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DetalleServicio {

    private final DetalleServicioBo bo;

    public DetalleServicio() {
        this.bo = new DetalleServicioBo();
    }

    // =========================
    //          GET
    // =========================
    @GET
    public ArrayList<DetalleServicioDto> listarTodos() {
        return this.bo.listarTodos();
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") int detalleServicioId) {
        DetalleServicioDto dto = this.bo.obtenerPorId(detalleServicioId);
        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dto).build();
    }

    // =========================
    //          POST
    // =========================
    /**
     * Inserta recibiendo directamente el DTO. El BO expone insertar(paramétrico),
     * por lo que aquí se extraen los campos necesarios del DTO.
     */
    @POST
    public Response insertar(DetalleServicioDto dto, @Context UriInfo uriInfo) {
        validarParaInsertar(dto);

        int citaId = dto.getCita().getCitaId();
        int servicioId = dto.getServicio().getServicioId();
        String descripcion = dto.getDescripcion();
        double costo = dto.getCosto();
        boolean activo = dto.getActivo();

        Integer nuevoId = this.bo.insertar(citaId, servicioId, descripcion, costo, activo);
        if (nuevoId == null || nuevoId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        dto.setDetalleServicioId(nuevoId);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(nuevoId)).build();
        return Response.created(location).entity(dto).build();
    }

    // =========================
    //           PUT
    // =========================
    /**
     * Modifica recibiendo directamente el DTO, manteniendo el estilo de tu plantilla.
     * Requiere que el DTO traiga detalleServicioId y las claves foráneas en 'cita' y 'servicio'.
     */
    @PUT
    public Response modificar(DetalleServicioDto dto) {
        validarParaModificar(dto);

        int id = dto.getDetalleServicioId();
        int citaId = dto.getCita().getCitaId();
        int servicioId = dto.getServicio().getServicioId();
        String descripcion = dto.getDescripcion();
        double costo = dto.getCosto();
        
        boolean activo = dto.getActivo();

        Integer filas = this.bo.modificar(id, citaId, servicioId, descripcion, costo, activo);
        if (filas == null || filas == 0) {
            // Manteniendo tu patrón: BAD_REQUEST cuando no se logró modificar (p.ej., reglas BO/DAO)
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(dto).build();
    }

    // =========================
    //          DELETE
    // =========================
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int detalleServicioId) {
        Integer filas = this.bo.eliminar(detalleServicioId);
        if (filas != null && filas > 0) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // =========================
    //     Validaciones mínimas
    // =========================
    private void validarParaInsertar(DetalleServicioDto d) {
        if (d == null) badRequest("Body vacío");
        if (d.getCita() == null || d.getCita().getCitaId() == null)
            badRequest("Se requiere 'cita.citaId'");
        if (d.getServicio() == null || d.getServicio().getServicioId() == null)
            badRequest("Se requiere 'servicio.servicioId'");
        if (d.getCosto() == null)
            badRequest("Se requiere 'costo'");
        if (d.getActivo() == null)
            badRequest("Se requiere 'activo'");
        // descripcion puede ser opcional según tu dominio; si es obligatoria, descomenta:
        // if (d.getDescripcion() == null || d.getDescripcion().isBlank()) badRequest("Se requiere 'descripcion'");
    }

    private void validarParaModificar(DetalleServicioDto d) {
        if (d == null) badRequest("Body vacío");
        if (d.getDetalleServicioId() == null || d.getDetalleServicioId() <= 0)
            badRequest("Se requiere 'detalleServicioId' válido");
        validarParaInsertar(d); // mismas reglas de FK/otros campos
    }

    private void badRequest(String msg) {
        throw new WebApplicationException(
            Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ApiError("bad_request", msg))
                    .build()
        );
    }

    // DTO simple de error para respuestas 4xx desde validaciones
    public static class ApiError {
        public String error;
        public String message;
        public ApiError(String e, String m) { this.error = e; this.message = m; }
    }
}
