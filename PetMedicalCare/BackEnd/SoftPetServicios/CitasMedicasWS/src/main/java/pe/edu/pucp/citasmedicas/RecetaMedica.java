/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.RecetaMedicaBo;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

/**
 *
 * @author User
 */
@Path("RecetaMedica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecetaMedica {
    private RecetaMedicaBo recetaBo;
    
    public RecetaMedica(){
        this.recetaBo= new RecetaMedicaBo();
    }
    @GET
    public ArrayList<RecetaMedicaDto> listarTodos() {
        return this.recetaBo.listarTodos();
    }
    @GET
    @Path("{id}")
    public Response obtenerPorId(@PathParam("id") int recetaMedicaId) {
         RecetaMedicaDto receta= this.recetaBo.obtenerPorId(recetaMedicaId);
         if(receta==null){
            return  Response.status(Response.Status.NOT_FOUND).build();
         }
         
         return Response.ok().entity(receta).build();
    }
    
            /** Inserta una receta recibiendo directamente el DTO.
     * @param receta
     * @return  */
    @POST
    public Response insertar(RecetaMedicaDto receta) {

       int respuesta= this.recetaBo.insertar(receta);
       if(respuesta==0){
           return Response.status(Response.Status.BAD_REQUEST).build();
       }
       receta.setRecetaMedicaId(respuesta);
       return Response.status(Response.Status.CREATED).entity(receta).build();
    }

    /** Modifica una receta recibiendo directamente el DTO.
     * @param receta
     * @return  */
     @PUT
    public Response modificar(RecetaMedicaDto receta) {
        int respuesta=this.recetaBo.modificar(receta);
        if(respuesta==0){
            return    Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(receta).build();
        
        
    }
   @DELETE
   @Path("{id}")
    public Response eliminar(@PathParam("id")int recetaMedicaId){
        int respuesta=this.recetaBo.eliminar(recetaMedicaId);
        if(respuesta>0){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
