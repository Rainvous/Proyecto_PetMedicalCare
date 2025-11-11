package pe.edu.pucp.softpet.services.softpetws.vet;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.io.IOException;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.VeterinarioBo;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

@WebService(serviceName = "Veterinarios")
public class Veterinarios {

    private final VeterinarioBo veterinarioBo;

    public Veterinarios() {
        this.veterinarioBo = new VeterinarioBo();
    }

    @WebMethod(operationName = "insertar_veterinario")
    public Integer insertar(@WebParam(name = "personaId") int personaId,
            @WebParam(name = "fechaContratacion") String fechaContratacion,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "especialidad") String especialidad,
            @WebParam(name = "activo") boolean activo) {
        
        return this.veterinarioBo.insertar(personaId, fechaContratacion,
                estado, especialidad, activo);
    }

    @WebMethod(operationName = "modificar_veterinario")
    public Integer modificar(@WebParam(name = "veterinarioId") int veterinarioId,
            @WebParam(name = "personaId") int personaId,
            @WebParam(name = "fechaContratacion") String fechaContratacion,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "especialidad") String especialidad,
            @WebParam(name = "activo") boolean activo) {

        return this.veterinarioBo.modificar(veterinarioId, personaId, fechaContratacion,
                estado, especialidad, activo);
    }

    @WebMethod(operationName = "eliminar_veterinario")
    public Integer eliminar(@WebParam(name = "veterinarioId") int veterinarioId) {
        return this.veterinarioBo.eliminar(veterinarioId);
    }

    @WebMethod(operationName = "obtenerPorId_veterinario")
    public VeterinarioDto obtenerPorId(@WebParam(name = "veterinarioId") int veterinarioId) {
        return this.veterinarioBo.obtenerPorId(veterinarioId);
    }

    @WebMethod(operationName = "listar_veterinarios")
    public ArrayList<VeterinarioDto> listarTodos() throws IOException, InterruptedException {
        return this.veterinarioBo.listarTodos();
    }
}
