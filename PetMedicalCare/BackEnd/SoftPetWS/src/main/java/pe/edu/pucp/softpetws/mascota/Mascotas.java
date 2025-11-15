package pe.edu.pucp.softpetws.mascota;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.io.IOException;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.MascotaBo;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;

@WebService(serviceName = "Mascotas")
public class Mascotas {

    private final MascotaBo mascotaBo;

    public Mascotas() {
        this.mascotaBo = new MascotaBo();
    }

    @WebMethod(operationName = "insertar_mascota")
    public Integer insertar(@WebParam(name = "personaId") Integer personaId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "especie") String especie,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "raza") String raza,
            @WebParam(name = "color") String color,
            @WebParam(name = "fechaDefuncion") String fechaDefuncion,
            @WebParam(name = "activo") Boolean activo) {

        return this.mascotaBo.insertar(personaId, nombre, especie,
                sexo, raza, color, fechaDefuncion, activo);
    }

    @WebMethod(operationName = "modificar_mascota")
    public Integer modificar(@WebParam(name = "mascotaId") Integer mascotaId,
            @WebParam(name = "personaId") Integer personaId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "especie") String especie,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "raza") String raza,
            @WebParam(name = "color") String color,
            @WebParam(name = "fechaDefuncion") String fechaDefuncion,
            @WebParam(name = "activo") Boolean activo) {

        return this.mascotaBo.insertar(personaId, nombre, especie,
                sexo, raza, color, fechaDefuncion, activo);
    }

    @WebMethod(operationName = "eliminar_mascota")
    public Integer eliminar(@WebParam(name = "mascotaId") int mascotaId) {
        return this.mascotaBo.eliminar(mascotaId);
    }

    @WebMethod(operationName = "obtenerPorId_mascota")
    public MascotaDto obtenerPorId(@WebParam(name = "mascotaId") int mascotaId) {
        return this.mascotaBo.obtenerPorId(mascotaId);
    }

    @WebMethod(operationName = "listar_mascotas")
    public ArrayList<MascotaDto> listarTodos() throws IOException, InterruptedException {
        return this.mascotaBo.listarTodos();
    }

    @WebMethod(operationName = "ListasBusquedaAvanzada")
    public ArrayList<MascotaDto> ListasBusquedaAvanzada(
            @WebParam(name = "nombreMascota") String nombreMascota,
            @WebParam(name = "raza") String raza,
            @WebParam(name = "especie") String especie,
            @WebParam(name = "nombreDeLaPersona") String nombreDeLaPersona) {
        return this.mascotaBo.ListasBusquedaAvanzada(nombreMascota, raza, especie, nombreDeLaPersona);
    }
    
    @WebMethod(operationName = "listar_mascotas_por_persona")
    public ArrayList<MascotaDto> listarPorIdPersona(
            @WebParam(name = "personaId") int personaId) throws IOException, InterruptedException {
        
        return this.mascotaBo.listarPorIdPersona(personaId);
    }

    @WebMethod(operationName = "listar_mascotas_activas")
    public ArrayList<MascotaDto> listarMascotasActivas() throws IOException, InterruptedException {
        return this.mascotaBo.listarMascotasActivas();
    }
    
    @WebMethod(operationName = "VerificarSiLaMascotaTieneInformacion")
    public int VerificarSiLaMascotaTieneInformacion(
            @WebParam(name = "nombreTipo") int idServicio){
        return this.mascotaBo.VerificarSiLaMascotaTieneInformacion(idServicio);
    }
}
