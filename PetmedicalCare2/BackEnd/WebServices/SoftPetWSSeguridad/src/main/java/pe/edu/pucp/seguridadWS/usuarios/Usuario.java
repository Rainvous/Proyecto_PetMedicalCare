/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.seguridadWS.usuarios;

/**
 *
 * @author marti
 */
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam; // IMPORTANTE: Añadir este import
import jakarta.jws.WebService;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.UsuarioBo;

// Importamos las clases de Usuario
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

@WebService(serviceName = "Usuarios") // Solo queda la anotación SOAP
public class Usuario {

    private final UsuarioBo usuarioBo;

    public Usuario() {
        this.usuarioBo = new UsuarioBo();
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<UsuarioDto> listarTodos() {
        return this.usuarioBo.listarTodos();
    }

    @WebMethod(operationName = "obtenerPorId")
    public UsuarioDto obtenerPorId(@WebParam(name = "usuarioId") int usuarioId) {
        // Simplemente retornamos lo que el BO nos da
        return this.usuarioBo.obtenerPorId(usuarioId);
    }


    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password,
            @WebParam(name = "correo") String correo,
            @WebParam(name = "activo") boolean activo) {

        // Llamamos al BO con los parámetros correctos
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

        // Llamamos al BO con los parámetros correctos
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
}