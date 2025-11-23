package pe.edu.pucp.softpetws.veterinario;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.io.IOException;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.VeterinarioBo;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

@WebService(serviceName = "veterinarios")
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

    @WebMethod(operationName = "listar_veterinarios_activos")
    public ArrayList<VeterinarioDto> listarVeterinariosActivos() throws IOException, InterruptedException {
        return this.veterinarioBo.listarVeterinariosActivos();
    }

    @WebMethod(operationName = "VerificarSiExisteHorarioLaboral")
    public int VerificarSiExisteHorarioLaboral(
            @WebParam(name = "fecha") String fecha,
            @WebParam(name = "idVeterinario") Integer idVeterinario
    ) {
        return this.veterinarioBo.VerificarSiExisteHorarioLaboral(fecha, idVeterinario);

    }

    @WebMethod(operationName = "ListasBusquedaAvanzadaVeterinario")
    public ArrayList<VeterinarioDto> ListasBusquedaAvanzadaVeterinario(
            @WebParam(name = "Especialidad") String Especialidad,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nroDocumento") String nroDocumento,
            @WebParam(name = "estadoActivo") Integer estadoActivo) { // Parametro INT
        return (ArrayList<VeterinarioDto>) this.veterinarioBo.ListasBusquedaAvanzadaVeterinario(
                Especialidad, nombre, nroDocumento, estadoActivo);
    }

    @WebMethod(operationName = "insertarVeterinarioCompleto")
    public Integer insertarVeterinarioCompleto(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "nroDocumento") Integer nroDocumento,
            @WebParam(name = "ruc") Integer ruc,
            @WebParam(name = "tipoDocumento") String tipoDocumento,
            @WebParam(name = "fechaContratacion") String fechaContratacion,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "especialidad") String especialidad) {
        return this.veterinarioBo.insertarVeterinarioCompleto(
                username, password, correo, true, // Activo por defecto
                nombre, direccion, telefono, sexo,
                nroDocumento, ruc, tipoDocumento,
                fechaContratacion, estado, especialidad);
    }

    // ... (Métodos existentes) ...
    @WebMethod(operationName = "modificarVeterinarioCompleto")
    public Integer modificarVeterinarioCompleto(
            @WebParam(name = "idVeterinario") Integer idVeterinario,
            @WebParam(name = "idPersona") Integer idPersona,
            @WebParam(name = "idUsuario") Integer idUsuario,
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "activo") boolean activo,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "nroDocumento") Integer nroDocumento,
            @WebParam(name = "ruc") Integer ruc,
            @WebParam(name = "tipoDocumento") String tipoDocumento,
            @WebParam(name = "fechaContratacion") String fechaContratacion,
            @WebParam(name = "estado") String estado,
            @WebParam(name = "especialidad") String especialidad) {

        return this.veterinarioBo.modificarVeterinarioCompleto(
                idVeterinario, idPersona, idUsuario,
                username, password, correo, activo,
                nombre, direccion, telefono, sexo,
                nroDocumento, ruc, tipoDocumento,
                fechaContratacion, estado, especialidad);
    }

    @WebMethod(operationName = "eliminarVeterinarioCompleto")
    public Integer eliminarVeterinarioCompleto(@WebParam(name = "idVeterinario") Integer idVeterinario) {
        return this.veterinarioBo.eliminarVeterinarioCompleto(idVeterinario);
    }
}
