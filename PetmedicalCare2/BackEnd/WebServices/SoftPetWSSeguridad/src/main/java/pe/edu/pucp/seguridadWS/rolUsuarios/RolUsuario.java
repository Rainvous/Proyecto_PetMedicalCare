/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.seguridadWS.rolUsuarios;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam; // IMPORTANTE: Añadir este import
import jakarta.jws.WebService;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.RolUsuarioBo;

// Importamos las clases necesarias
import pe.edu.pucp.softpet.dto.usuarios.RolUsuarioDto;

/**
 *
 * Servicio SOAP para la entidad RolUsuario (la relación entre Rol y Usuario).
 */
@WebService(serviceName = "RolUsuario") // Solo queda la anotación SOAP
public class RolUsuario {

    private final RolUsuarioBo rolUsuarioBo;

    public RolUsuario() {
        this.rolUsuarioBo = new RolUsuarioBo();
    }

    /**
     * Lista todas las relaciones Rol-Usuario.
     * @return ArrayList de RolUsuarioDto
     */
    @WebMethod(operationName = "listarTodos")
    public ArrayList<RolUsuarioDto> listarTodos() {
        return this.rolUsuarioBo.listarTodos();
    }

    /**
     * Obtiene una relación Rol-Usuario específica por su ID.
     * @param rolUsuarioId El ID de la relación.
     * @return El DTO o null si no se encuentra.
     */
    @WebMethod(operationName = "obtenerPorId")
    public RolUsuarioDto obtenerPorId(@WebParam(name = "rolUsuarioId") int rolUsuarioId) {
        // Usamos el nombre de método exacto de tu BO: obtenerPorID
        return this.rolUsuarioBo.obtenerPorID(rolUsuarioId);
    }

    /**
     * Inserta una nueva relación Rol-Usuario.
     * @param rolId
     * @param usuarioId
     * @param activo
     * @return El ID generado, o 0/null si falla.
     */
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

    /**
     * Modifica una relación Rol-Usuario existente.
     * @param rolUsuarioId
     * @param rolId
     * @param usuarioId
     * @param activo
     * @return 1 si se modificó, 0/null si falla.
     */
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

    /**
     * Elimina una relación Rol-Usuario por su ID.
     * @param rolUsuarioId El ID de la relación a eliminar.
     * @return 1 si se eliminó, 0/null si falla.
     */
    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "rolUsuarioId") int rolUsuarioId) {
        // El BO espera un Integer, pero Java puede auto-convertir int a Integer
        return this.rolUsuarioBo.eliminar(rolUsuarioId);
    }
}