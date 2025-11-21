package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;

import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
import pe.edu.pucp.softpet.dto.util.enums.Sexo;

public class PersonaBo {

    private final PersonaDaoImpl dao;

    public PersonaBo() {
        this.dao = new PersonaDaoImpl();
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

    public PersonaDto obtenerPorId(int personaId) {
        return this.dao.obtenerPorId(personaId);
    }

    public ArrayList<PersonaDto> listarTodos() {
        return this.dao.listarTodos();
    }

    public ArrayList<PersonaDto> ListasBusquedaAvanzada(
            String nombre,
            String NroDocumento,
            String Ruc,
            String Telefono,
            Boolean Activo) {

        String ActivoString;

        if (null == Activo) {
            ActivoString = "";
        } else if (Activo == true) {
            ActivoString = "1";
        } else {
            ActivoString = "0";
        }

        return (ArrayList<PersonaDto>) this.dao.ListasBusquedaAvanzada(
                nombre == null ? "" : nombre,
                NroDocumento == null ? "" : NroDocumento,
                Ruc == null ? "" : Ruc,
                Telefono == null ? "" : Telefono,
                ActivoString);
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
}
