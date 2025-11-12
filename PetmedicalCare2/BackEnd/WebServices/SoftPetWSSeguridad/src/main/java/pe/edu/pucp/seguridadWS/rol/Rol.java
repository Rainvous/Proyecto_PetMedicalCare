/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.seguridadWS.rol;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam; // IMPORTANTE: Añadir este import
import jakarta.jws.WebService;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.RolBO;

// Importamos las clases de Rol
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

/**
 *
 * Servicio SOAP para la entidad Rol.
 */
@WebService(serviceName = "Rol") // Solo queda la anotación SOAP
public class Rol {

    private final RolBO rolBo;

    public Rol() {
        this.rolBo = new RolBO();
    }

    /**
     * Lista todos los roles.
     *
     * @return ArrayList de RolDto
     */
    @WebMethod(operationName = "listarTodos")
    public ArrayList<RolDto> listarTodos() {
        return this.rolBo.listarTodos();
    }

    /**
     * Obtiene un rol específico por su ID.
     *
     * @param rolId El ID del rol.
     * @return El DTO del rol o null si no se encuentra.
     */
    @WebMethod(operationName = "obtenerPorId")
    public RolDto obtenerPorId(@WebParam(name = "rolId") int rolId) {
        // Usamos el nombre de método exacto de tu BO: obtenerPorID
        return this.rolBo.obtenerPorID(rolId);
    }

    /**
     * Inserta un nuevo rol.
     * @param nombre
     * @param activo
     * @return El ID generado, o 0/null si falla.
     */
    @WebMethod(operationName = "insertar")
    public Integer insertar(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "activo") boolean activo) {

        return this.rolBo.insertar(
                nombre,
                activo
        );
    }

    /**
     * Modifica un rol existente.
     * @param rolId
     * @param nombre
     * @param activo
     * @return 1 si se modificó, 0/null si falla.
     */
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

    /**
     * Elimina un rol por su ID.
     *
     * @param rolId El ID del rol a eliminar.
     * @return 1 si se eliminó, 0/null si falla.
     */
    @WebMethod(operationName = "eliminar")
    public Integer eliminar(@WebParam(name = "rolId") int rolId) {
        // El BO espera un int, así que lo pasamos directo
        return this.rolBo.eliminar(rolId);
    }
}