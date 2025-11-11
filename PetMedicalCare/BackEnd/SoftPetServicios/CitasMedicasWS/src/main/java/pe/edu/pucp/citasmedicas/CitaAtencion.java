/*
 * Servicio RESTful para Cita de Atención
 */
package pe.edu.pucp.citasmedicas;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;

import pe.edu.pucp.softpet.bo.CitaAtencionBo;
import pe.edu.pucp.softpet.dto.citas.CitaAtencionDto;

@Path("CitaAtencion")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CitaAtencion {

    private final CitaAtencionBo citaBo;

    @Context
    private UriInfo uriInfo;

    public CitaAtencion() {
        this.citaBo = new CitaAtencionBo();
    }

    // =========================
    // GET /CitaAtencion
    // =========================
    @GET
    public Response listarTodos() {
        ArrayList<CitaAtencionDto> lista = this.citaBo.listarTodos();
        return Response.ok(lista).build();
    }

    // =========================
    // GET /CitaAtencion/{id}
    // =========================
    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") int citaId) {
        CitaAtencionDto cita = this.citaBo.obtenerPorId(citaId);
        if (cita == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(cita).build();
    }

    // =========================
    // POST /CitaAtencion
    // Crea recibiendo el DTO
    // =========================
    @POST
    public Response insertar(CitaAtencionDto cita) {
        if (cita == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El cuerpo (JSON) es requerido.").build();
        }

        // Inserta usando el BO que recibe DTO
        Integer nuevoId = this.citaBo.insertar(cita);

        if (nuevoId == null || nuevoId <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No se pudo crear la cita. Verifica campos requeridos.")
                    .build();
        }

        cita.setCitaId(nuevoId);
        URI location = uriInfo.getAbsolutePathBuilder()
                              .path(String.valueOf(nuevoId))
                              .build();
        return Response.created(location).entity(cita).build(); // 201 + Location
    }

    // =========================
    // PUT /CitaAtencion
    // Modifica recibiendo el DTO
    // =========================
    @PUT
    public Response modificar(CitaAtencionDto cita) {
        if (cita == null || cita.getCitaId() == 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("citaId es requerido para modificar.").build();
        }

        Integer filas = this.citaBo.modificar(cita);
        if (filas == null || filas == 0) {
            // Podría ser BAD_REQUEST si valida campos, o NOT_FOUND si no existe
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No se actualizó la cita. Verifica que exista y los datos sean válidos.")
                    .build();
        }

        return Response.ok(cita).build(); // 200 OK con el DTO actualizado
    }

    // =========================
    // DELETE /CitaAtencion/{id}
    // =========================
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int citaId) {
        Integer filas = this.citaBo.eliminar(citaId);
        if (filas != null && filas > 0) {
            return Response.noContent().build(); // 204
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
        // --- MÉTODO 1: El que ya tienes ---
    // Responde a: .../busqueda/2025-11-10
    @GET
    @Path("busqueda/{fecha}")    
    public Response ListasBusquedaAvanzada(@PathParam("fecha") String fecha) {
        // Llama al BO (y al SP) pasando la fecha que llegó
        ArrayList<CitaAtencionDto> lista = citaBo.ListasBusquedaAvanzada(fecha);
        return Response.ok(lista).build();
    }

    // --- MÉTODO 2: El que debes AGREGAR ---
    // Responde a: .../busqueda/
    @GET
    @Path("busqueda") // <-- Nota: El @Path no tiene "/{fecha}"
    public Response ListasBusquedaAvanzadaHoy() { // <-- Le puedes poner un nombre diferente

        // Llama a la MISMA lógica de negocio, pero pasando NULL.
        // Tu SP sp_listar_citas_por_fecha(NULL) ya sabe que 
        // si recibe NULL, debe buscar las citas de HOY (CURDATE()).
        ArrayList<CitaAtencionDto> lista = citaBo.ListasBusquedaAvanzada(null);

        return Response.ok(lista).build();
    }
}
