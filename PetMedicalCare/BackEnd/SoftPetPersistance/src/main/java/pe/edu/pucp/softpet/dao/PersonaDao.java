package pe.edu.pucp.softpet.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

public interface PersonaDao extends DaoBase<PersonaDto> {

    // -------------------------------------------------------------------------
    // MÉTODOS DE LECTURA / LISTADO
    // -------------------------------------------------------------------------
    ArrayList<PersonaDto> listarPersonasActivas();
    
    PersonaDto obtenerPersonaPorIdCompleto(int personaId);
    
    int obtenerPersonaGuestOCero();
    
    ArrayList<PersonaDto> ListasBusquedaAvanzada(String nombre, String NroDocumento, String Ruc, Integer Activo);
    
    ArrayList<PersonaDto> ListasBusquedaAvanzadaParaCliente();
    
    // Método nuevo con paginación
    List<PersonaDto> buscarClientesPaginados(String nombre, String nroDoc, String ruc, Boolean activo, int pagina);

    // -------------------------------------------------------------------------
    // MÉTODOS TRANSACCIONALES COMPLEJOS (Usuario + Persona + Rol)
    // -------------------------------------------------------------------------
    Integer insertarPersonaCompleta(String username, String password, String correo, boolean activoUsuario,
                                    String nombre, String direccion, String telefono, String sexo,
                                    Integer nroDocumento, Integer ruc, String tipoDocumento);

    Integer insertarPersonaCompleta(String username, String password, String correo, boolean activoUsuario,
                                    String nombre, String direccion, String telefono, String sexo,
                                    Integer nroDocumento, Integer ruc, String tipoDocumento, int idrol);

    Integer modificarPersonaCompleta(Integer idPersona, Integer idUsuario,
                                     String username, String password, String correo, boolean activo,
                                     String nombre, String direccion, String telefono, String sexo,
                                     Integer nroDocumento, Integer ruc, String tipoDocumento);

    Integer eliminarPersonaCompleta(Integer idPersona);

    // -------------------------------------------------------------------------
    // MÉTODOS AUXILIARES TRANSACCIONALES (Reciben conexión externa)
    // -------------------------------------------------------------------------
    Integer insertarTransaccional(PersonaDto persona, Connection con) throws SQLException;
    
    Integer modificarTransaccional(PersonaDto persona, Connection con) throws SQLException;

    // -------------------------------------------------------------------------
    // VERIFICACIONES
    // -------------------------------------------------------------------------
    int VerificarSiLaPersonaTieneInformacion(int idServicio);
    public RolDto obtenerRolPorPersonaId(int personaId);
     public String obtenerCorreoPorPersonaId(int personaId) ;
}