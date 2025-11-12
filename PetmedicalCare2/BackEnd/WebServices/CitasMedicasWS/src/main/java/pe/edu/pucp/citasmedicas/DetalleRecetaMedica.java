package pe.edu.pucp.citasmedicas;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;

import pe.edu.pucp.softpet.bo.DetalleRecetaBo;
import pe.edu.pucp.softpet.dto.recetas.DetalleRecetaDto;

@Path("DetalleReceta")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DetalleRecetaMedica {

    private final DetalleRecetaBo bo;

    public DetalleRecetaMedica() {
        this.bo = new DetalleRecetaBo();
    }

    // =========================
    //          GET
    // =========================
    @GET
    public ArrayList<DetalleRecetaDto> listarTodos() {
        return this.bo.listarTodos();
    }

    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") int detalleRecetaId) {
        DetalleRecetaDto dto = this.bo.obtenerPorId(detalleRecetaId);
        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(dto).build();
    }

    // =========================
    //          POST
    // =========================
    /**
     * Inserta un nuevo detalle de receta recibiendo el DTO completo.
     * El BO utiliza insertar(DetalleRecetaDto), por lo que pasamos el DTO directamente.
     */
    @POST
    public Response insertar(DetalleRecetaDto dto, @Context UriInfo uriInfo) {
        // Validación de entrada
        validarParaInsertar(dto);

        Integer nuevoId = this.bo.insertar(dto);
        if (nuevoId == null || nuevoId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Establece el ID generado para el nuevo recurso
        dto.setDetalleRecetaId(nuevoId);
        URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(nuevoId)).build();
        return Response.created(location).entity(dto).build(); // 201 Created
    }

    // =========================
    //           PUT
    // =========================
    /**
     * Modifica un detalle de receta existente recibiendo el DTO completo.
     * Utiliza el método modificar(DetalleRecetaDto) del BO.
     */
    @PUT
    public Response modificar(DetalleRecetaDto dto) {
        // Validación de entrada
        validarParaModificar(dto);

        Integer filas = this.bo.modificar(dto);
        if (filas == null || filas == 0) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(dto).build(); // 200 OK
    }

    // =========================
    //          DELETE
    // =========================
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int detalleRecetaId) {
        Integer filas = this.bo.eliminar(detalleRecetaId);
        if (filas != null && filas > 0) {
            return Response.noContent().build(); // 204 No Content
        }
        return Response.status(Response.Status.NOT_FOUND).build(); // 404 Not Found
    }

    // =========================
    //     Validaciones mínimas
    // =========================
    private void validarParaInsertar(DetalleRecetaDto d) {
        if (d == null) badRequest("Body vacío");
        if (d.getReceta() == null || d.getReceta().getRecetaMedicaId() == null)
            badRequest("Se requiere 'receta.recetaMedicaId'");
        if (d.getDescripcionMedicamento() == null || d.getDescripcionMedicamento().isBlank())
            badRequest("Se requiere 'descripcionMedicamento'");
        if (d.getPresentacion() == null || d.getPresentacion().isBlank())
            badRequest("Se requiere 'presentacion'");
        if (d.getCantidad() == null || d.getCantidad().isBlank())
            badRequest("Se requiere 'cantidad'");
        if (d.getActivo() == null)
            badRequest("Se requiere 'activo'");
    }

    private void validarParaModificar(DetalleRecetaDto d) {
        if (d == null) badRequest("Body vacío");
        if (d.getDetalleRecetaId() == null || d.getDetalleRecetaId() <= 0)
            badRequest("Se requiere 'detalleRecetaId' válido");
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
