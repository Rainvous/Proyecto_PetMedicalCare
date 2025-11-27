package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.utils.GmailService;
import pe.edu.pucp.softpet.dao.PersonaDao;

import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.bo.utils.SecurityUtil;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;
import pe.edu.pucp.softpet.dto.usuarios.RolUsuarioDto;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
import pe.edu.pucp.softpet.dto.util.enums.Sexo;
import pe.edu.pucp.softpet.dto.util.enums.TipoRol;

public class PersonaBo {

    private final PersonaDao dao;
    private final RolBO rol;
    private final RolUsuarioBo rolusuario;
    
    private final GmailService gmailService;

    public PersonaBo() {
        this.dao = new PersonaDaoImpl();
        this.rol= new RolBO();
        this.rolusuario=new RolUsuarioBo();
        
        this.gmailService = new GmailService(); 
    }

    // INSERTAR con parámetros (retorna PK autogenerada)
    public Integer insertar(
            Integer usuarioId,
            String nombre,
            String direccion,
            String telefono,
            String sexo,
            Integer nroDocumento,
            Integer ruc,
            String tipoDocumento,
            Boolean activo) {

        PersonaDto dto = new PersonaDto();

        UsuarioDto usuario = new UsuarioDto();
        usuario.setUsuarioId(usuarioId);

        dto.setUsuario(usuario);
        dto.setNombre(nombre);
        dto.setDireccion(direccion);
        dto.setTelefono(telefono);
        dto.setSexo(Sexo.valueOf(sexo));
        dto.setNroDocumento(nroDocumento);
        if (ruc != null) {
            dto.setRuc(ruc);
        }
        dto.setTipoDocumento(tipoDocumento);
        dto.setActivo(activo);

        return this.dao.insertar(dto);
    }
    
    // MODIFICAR con parámetros (retorna filas afectadas)
    public Integer modificar(
            Integer personaId,
            Integer usuarioId,
            String nombre,
            String direccion,
            String telefono,
            String sexo,
            Integer nroDocumento,
            Integer ruc,
            String tipoDocumento,
            Boolean activo) {

        PersonaDto dto = new PersonaDto();

        UsuarioDto usuario = new UsuarioDto();
        usuario.setUsuarioId(usuarioId);

        dto.setUsuario(usuario);
        dto.setNombre(nombre);
        dto.setDireccion(direccion);
        dto.setTelefono(telefono);
        dto.setSexo(Sexo.valueOf(sexo));
        dto.setNroDocumento(nroDocumento);
        if (ruc != null) {
            dto.setRuc(ruc);
        }
        dto.setTipoDocumento(tipoDocumento);
        dto.setActivo(activo);

        return this.dao.modificar(dto);
    }

    public Integer eliminar(int personaId) {
        PersonaDto dto = new PersonaDto();
        dto.setPersonaId(personaId);
        return this.dao.eliminar(dto);
    }
    
    // MODIFICAR con parámetros (retorna filas afectadas)
    // ...
    public Integer modificarPersonaCompleta(Integer idPersona, Integer idUsuario, String username, String password, String correo, boolean activo, String nombre, String direccion, String telefono, String sexo, Integer nroDocumento, Integer ruc, String tipoDocumento) {
        return this.dao.modificarPersonaCompleta(idPersona, idUsuario, username, password, correo, activo, nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento);
    }

    public Integer eliminarPersonaCompleta(Integer idPersona) {
        return this.dao.eliminarPersonaCompleta(idPersona);
    }

//    public Integer insertarPersonaCompleta(
//            String username, String password, String correo, boolean activoUsuario,
//            String nombre, String direccion, String telefono, String sexo,
//            Integer nroDocumento, Integer ruc, String tipoDocumento) {
//        return this.dao.insertarPersonaCompleta(username, password, correo, activoUsuario,
//                nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento);
//    }
    
    // INSERTAR CLIENTE COMPLETO SEGURO
    public Integer insertarPersonaCompleta(
            String username, String passwordIgnorada, String correo, boolean activoUsuario,
            String nombre, String direccion, String telefono, String sexo,
            Integer nroDocumento, Integer ruc, String tipoDocumento) {
        
        // 1. Seguridad
        String passwordRaw = SecurityUtil.generarPasswordAleatoria();
        String passwordHashed = SecurityUtil.sha256(passwordRaw);
        
        // 2. Correo
        new Thread(() -> {
            this.gmailService.enviarCorreo_Credenciales(correo, passwordRaw);
        }).start();

        // 3. BD (Guardamos el Hash)
        return this.dao.insertarPersonaCompleta(username, passwordHashed, correo, activoUsuario,
                nombre, direccion, telefono, sexo, nroDocumento, ruc, tipoDocumento);
    }

    public PersonaDto obtenerPorId(int personaId) {
        return this.dao.obtenerPorId(personaId);
    }

    public ArrayList<PersonaDto> listarTodos() {
        return this.dao.listarTodos();
    }

    // Búsqueda de Clientes (Sin Teléfono, Con RUC y Estado INT)
    public ArrayList<PersonaDto> ListasBusquedaAvanzada(String nombre, String NroDocumento, String Ruc, Integer Activo) {
        return (ArrayList<PersonaDto>) this.dao.ListasBusquedaAvanzada(
                nombre == null ? "" : nombre,
                NroDocumento == null ? "" : NroDocumento,
                Ruc == null ? "" : Ruc,
                Activo);
    }

    public ArrayList<PersonaDto> ListasBusquedaAvanzadaParaCliente() {
        return (ArrayList<PersonaDto>) this.dao.ListasBusquedaAvanzadaParaCliente();
    }

    public ArrayList<PersonaDto> listarPersonasActivas() {
        return this.dao.listarPersonasActivas();
    }

    public int VerificarSiLaPersonaTieneInformacion(int idServicio) {
        return this.dao.VerificarSiLaPersonaTieneInformacion(idServicio);
    }
    //Funciones para PUNTO DE VENTA
    public int obtenerIdDePersonaGuest(){
        return this.dao.obtenerPersonaGuestOCero();
    }
    public PersonaDto ObtenerDatosPersonaGuest(){
        int id=this.dao.obtenerPersonaGuestOCero();
        return this.dao.obtenerPersonaPorIdCompleto(id);
    }
    public int  insertarOModificarUsuarioGest(String nombre, Integer RUC, Integer nroDocumento){
           RolDto rol= new RolDto();
           int idrol= TipoRol.GUEST.toString().equals("GUEST")? 5:0;
           rol.setRolId(idrol);
           RolUsuarioDto rolusuario=new RolUsuarioDto();
           rolusuario.setRol(rol);
           String username="guest";
           String password="XXXXXXXXXXXX";
           String correo="guest@gmail.com";
           boolean activo=true;
           String direccion="";
           String telefono="";
           String sexo="F";
           String tipoDoc="DNI";
           int idnuevo = this.dao.insertarPersonaCompleta(username, password, correo, activo, nombre, direccion, telefono, sexo, nroDocumento, RUC, tipoDoc,idrol);
           return idnuevo;

//           
           
           
           
    }
    // -----------------------------------------------------------------------------------
    //  FUNCIÓN ´PAGINACION: Búsqueda Paginada de Clientes
    // -----------------------------------------------------------------------------------
    public ArrayList<PersonaDto> buscarClientesPaginados(String nombre, String nroDoc, String ruc, Boolean activo, int pagina) {
        return (ArrayList<PersonaDto>) this.dao.buscarClientesPaginados(nombre, nroDoc, ruc, activo, pagina);
    }
    

        
}
