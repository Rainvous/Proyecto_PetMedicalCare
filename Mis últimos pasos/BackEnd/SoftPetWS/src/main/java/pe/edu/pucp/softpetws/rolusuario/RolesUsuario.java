package pe.edu.pucp.softpetws.rolusuario;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.RolUsuarioBo;

import pe.edu.pucp.softpet.dto.usuarios.RolUsuarioDto;

@WebService(serviceName = "RolesUsuario")
public class RolesUsuario {

    private final RolUsuarioBo rolUsuarioBo;

    public RolesUsuario() {
        this.rolUsuarioBo = new RolUsuarioBo();
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<RolUsuarioDto> listarTodos() {
        return this.rolUsuarioBo.listarTodos();
    }

    @WebMethod(operationName = "obtenerPorId")
    public RolUsuarioDto obtenerPorId(@WebParam(name = "rolUsuarioId") int rolUsuarioId) {
        // Usamos el nombre de método exacto de tu BO: obtenerPorID
        return this.rolUsuarioBo.obtenerPorID(rolUsuarioId);
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "rolId") int rolId,
            @WebParam(name = "usuarioId") int usuarioId,
            @WebParam(name = "activo") boolean activo) {

        return this.rolUsuarioBo.insertar(
                rolId,
                usuarioId,
                activo
        );
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "rolUsuarioId") int rolUsuarioId,
            @WebParam(name = "rolId") int rolId,
            @WebParam(name = "usuarioId") int usuarioId,
            @WebParam(name = "activo") boolean activo) {

        return this.rolUsuarioBo.modificar(
                rolUsuarioId,
                rolId,
                usuarioId,
                activo
        );
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "rolUsuarioId") int rolUsuarioId) {
        // El BO espera un Integer, pero Java puede auto-convertir int a Integer
        return this.rolUsuarioBo.eliminar(rolUsuarioId);
    }
}
