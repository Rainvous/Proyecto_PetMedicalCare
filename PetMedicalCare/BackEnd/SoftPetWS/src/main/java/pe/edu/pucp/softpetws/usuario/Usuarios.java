package pe.edu.pucp.softpetws.usuario;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.UsuarioBo;

import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

@WebService(serviceName = "Usuarios")
public class Usuarios {

    private final UsuarioBo usuarioBo;

    public Usuarios() {
        this.usuarioBo = new UsuarioBo();
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<UsuarioDto> listarTodos() {
        return this.usuarioBo.listarTodos();
    }

    @WebMethod(operationName = "obtenerPorId")
    public UsuarioDto obtenerPorId(@WebParam(name = "usuarioId") int usuarioId) {
        return this.usuarioBo.obtenerPorId(usuarioId);
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "activo") boolean activo) {

        return this.usuarioBo.insertar(
                username,
                password,
                correo,
                activo
        );
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "usuarioId") int usuarioId,
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "activo") boolean activo) {

        return this.usuarioBo.modificar(
                usuarioId,
                username,
                password,
                correo,
                activo
        );
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "usuarioId") int usuarioId) {
        return this.usuarioBo.eliminar(usuarioId);
    }

    @WebMethod(operationName = "ObtenerPorCorreoYContra")
    public ArrayList<UsuarioDto> ObtenerPorCorreoYContra(
            @WebParam(name = "correo") String correo,
            @WebParam(name = "password") String contra) {
        
        return this.usuarioBo.ObtenerPorCorreoYContra(correo, contra);
    }
}
