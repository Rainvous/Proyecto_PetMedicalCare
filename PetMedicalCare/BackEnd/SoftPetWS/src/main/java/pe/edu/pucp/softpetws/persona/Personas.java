package pe.edu.pucp.softpetws.persona;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.io.IOException;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.PersonaBo;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

@WebService(serviceName = "Personas")
public class Personas {

    private final PersonaBo personaBo;

    public Personas() {
        this.personaBo = new PersonaBo();
    }

    @WebMethod(operationName = "insertar_persona")
    public Integer insertar(@WebParam(name = "usuarioId") Integer usuarioId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "nroDocumento") Integer nroDocumento,
            @WebParam(name = "ruc") Integer ruc,
            @WebParam(name = "tipoDocumento") String tipoDocumento,
            @WebParam(name = "activo") Boolean activo) {

        return this.personaBo.insertar(usuarioId, nombre, direccion,
                telefono, sexo, nroDocumento, ruc, tipoDocumento, activo);
    }

    @WebMethod(operationName = "modificar_persona")
    public Integer modificar(@WebParam(name = "personaId") Integer personaId,
            @WebParam(name = "usuarioId") Integer usuarioId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "nroDocumento") Integer nroDocumento,
            @WebParam(name = "ruc") Integer ruc,
            @WebParam(name = "tipoDocumento") String tipoDocumento,
            @WebParam(name = "activo") Boolean activo) {

        return this.personaBo.modificar(personaId, usuarioId, nombre, direccion,
                telefono, sexo, nroDocumento, ruc, tipoDocumento, activo);
    }

    @WebMethod(operationName = "modificarPersonaCompleta")
    public Integer modificarPersonaCompleta(
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
            @WebParam(name = "tipoDocumento") String tipoDocumento) {
        return this.personaBo.modificarPersonaCompleta(idPersona, idUsuario, username, password, correo, activo,
                nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento);
    }

    @WebMethod(operationName = "eliminarPersonaCompleta")
    public Integer eliminarPersonaCompleta(@WebParam(name = "idPersona") Integer idPersona) {
        return this.personaBo.eliminarPersonaCompleta(idPersona);
    }

    @WebMethod(operationName = "eliminar_persona")
    public Integer eliminar(@WebParam(name = "personaId") int personaId) {
        return this.personaBo.eliminar(personaId);
    }

    @WebMethod(operationName = "obtenerPorId_persona")
    public PersonaDto obtenerPorId(@WebParam(name = "personaId") int personaId) {
        return this.personaBo.obtenerPorId(personaId);
    }

    @WebMethod(operationName = "insertarPersonaCompleta")
    public Integer insertarPersonaCompleta(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "direccion") String direccion,
            @WebParam(name = "telefono") String telefono,
            @WebParam(name = "sexo") String sexo,
            @WebParam(name = "nroDocumento") Integer nroDocumento,
            @WebParam(name = "ruc") Integer ruc,
            @WebParam(name = "tipoDocumento") String tipoDocumento) {
        return this.personaBo.insertarPersonaCompleta(username, password, correo, true,
                nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento);
    }

    @WebMethod(operationName = "listar_personas")
    public ArrayList<PersonaDto> listarTodos() throws IOException, InterruptedException {
        return this.personaBo.listarTodos();
    }

    @WebMethod(operationName = "ListasBusquedaAvanzada")
    public ArrayList<PersonaDto> ListasBusquedaAvanzada(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "NroDocumento") String NroDocumento,
            @WebParam(name = "Ruc") String Ruc,
            @WebParam(name = "Activo") Integer Activo) {
        return this.personaBo.ListasBusquedaAvanzada(nombre, NroDocumento, Ruc, Activo);
    }

    @WebMethod(operationName = "ListasBusquedaAvanzadaParaCliente")
    public ArrayList<PersonaDto> ListasBusquedaAvanzadaParaCliente() {
        return (ArrayList<PersonaDto>) this.personaBo.ListasBusquedaAvanzadaParaCliente();
    }

    @WebMethod(operationName = "listar_personas_activas")
    public ArrayList<PersonaDto> listarPersonasActivas() throws IOException, InterruptedException {
        return this.personaBo.listarPersonasActivas();
    }

    @WebMethod(operationName = "VerificarSiLaPersonaTieneInformacion")
    public int VerificarSiLaPersonaTieneInformacion(
            @WebParam(name = "nombreTipo") int idServicio) {
        return this.personaBo.VerificarSiLaPersonaTieneInformacion(idServicio);
    }
    // ================== MÉTODOS PARA PUNTO DE VENTA (GUEST) ==================

    @WebMethod(operationName = "obtenerIdDePersonaGuest")
    public int obtenerIdDePersonaGuest() {
        return this.personaBo.obtenerIdDePersonaGuest();
    }

    @WebMethod(operationName = "ObtenerDatosPersonaGuest")
    public PersonaDto ObtenerDatosPersonaGuest() {
        // Delegamos directamente al BO
        return this.personaBo.ObtenerDatosPersonaGuest();
    }

    @WebMethod(operationName = "insertarOModificarUsuarioGest")
    public int insertarOModificarUsuarioGest(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "ruc") Integer ruc,
            @WebParam(name = "nroDocumento") Integer nroDocumento) {

        return this.personaBo.insertarOModificarUsuarioGest(nombre, ruc, nroDocumento);
    }
     @WebMethod(operationName = "buscar_clientes_paginados")
    public ArrayList<PersonaDto> buscarClientesPaginados(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "nroDoc") String nroDoc,
            @WebParam(name = "ruc") String ruc,
            @WebParam(name = "activo") Boolean activo,
            @WebParam(name = "pagina") int pagina) {
        
        return this.personaBo.buscarClientesPaginados(nombre, nroDoc, ruc, activo, pagina);
    }

}
