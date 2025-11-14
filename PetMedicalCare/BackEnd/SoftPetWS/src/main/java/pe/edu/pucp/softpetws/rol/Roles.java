package pe.edu.pucp.softpetws.rol;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.RolBO;

import pe.edu.pucp.softpet.dto.usuarios.RolDto;

@WebService(serviceName = "Roles")
public class Roles {

    private final RolBO rolBo;

    public Roles() {
        this.rolBo = new RolBO();
    }

    @WebMethod(operationName = "listarTodos")
    public ArrayList<RolDto> listarTodos() {
        return this.rolBo.listarTodos();
    }

    @WebMethod(operationName = "obtenerPorId")
    public RolDto obtenerPorId(@WebParam(name = "rolId") int rolId) {
        return this.rolBo.obtenerPorID(rolId);
    }

    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "activo") boolean activo) {

        return this.rolBo.insertar(
                nombre,
                activo
        );
    }

    @WebMethod(operationName = "modificar")
    public Integer modificar(
            @WebParam(name = "rolId") int rolId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "activo") boolean activo) {

        return this.rolBo.modificar(
                rolId,
                nombre,
                activo
        );
    }

    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "rolId") int rolId) {
        return this.rolBo.eliminar(rolId);
    }
}
