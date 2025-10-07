/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.VeterinarioDao;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.daoImp.VeterinarioDaoImpl;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

/**
 *
 * @author marti
 */
public class VeterinarioBo {

    private VeterinarioDao veterinarioDao;

    public VeterinarioBo() {
        this.veterinarioDao = new VeterinarioDaoImpl();
    }

    // Inserta un nuevo veterinario
    public Integer insertar(String especializacion, Date fechaContratacion,
            String estado, boolean activo, int personaId,
            Date fechaInicioJornada, Date fechaFinJornada) {
        VeterinarioDto veterinario = new VeterinarioDto();

        // Conversión de java.util.Date a java.sql.Date
        java.sql.Date fechaContratacionSQL = new java.sql.Date(fechaContratacion.getTime());
        java.sql.Date fechaInicioSQL = fechaInicioJornada != null
                ? new java.sql.Date(fechaInicioJornada.getTime()) : null;
        java.sql.Date fechaFinSQL = fechaFinJornada != null
                ? new java.sql.Date(fechaFinJornada.getTime()) : null;

        veterinario.setEspecializacion(especializacion.trim().toUpperCase());
        veterinario.setFechaContratacion(fechaContratacionSQL);
        veterinario.setEstado(estado.trim());
        veterinario.setActivo(activo);
        veterinario.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        veterinario.setFechaInicioJornada(fechaInicioSQL);
        veterinario.setFechaFinJornada(fechaFinSQL);

        return this.veterinarioDao.insertar(veterinario);
    }

    // Modifica un veterinario existente
    public Integer modificar(int veterinarioId, String especializacion,
            Date fechaContratacion, String estado, boolean activo,
            int personaId, Date fechaInicioJornada, Date fechaFinJornada) {
        VeterinarioDto veterinario = new VeterinarioDto();

        // Conversión de java.util.Date a java.sql.Date
        java.sql.Date fechaContratacionSQL = new java.sql.Date(fechaContratacion.getTime());
        java.sql.Date fechaInicioSQL = fechaInicioJornada != null
                ? new java.sql.Date(fechaInicioJornada.getTime()) : null;
        java.sql.Date fechaFinSQL = fechaFinJornada != null
                ? new java.sql.Date(fechaFinJornada.getTime()) : null;

        veterinario.setVeterinarioId(veterinarioId);
        veterinario.setEspecializacion(especializacion.trim().toUpperCase());
        veterinario.setFechaContratacion(fechaContratacionSQL);
        veterinario.setEstado(estado.trim());
        veterinario.setActivo(activo);
        veterinario.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        veterinario.setFechaInicioJornada(fechaInicioSQL);
        veterinario.setFechaFinJornada(fechaFinSQL);

        return this.veterinarioDao.modificar(veterinario);
    }

    // Elimina un veterinario
    public Integer eliminar(int veterinarioId) {
        VeterinarioDto veterinario = new VeterinarioDto();
        veterinario.setVeterinarioId(veterinarioId);
        return this.veterinarioDao.eliminar(veterinario);
    }

    // Obtiene un veterinario por su ID
    public VeterinarioDto obtenerPorId(int veterinarioId) {
        return this.veterinarioDao.obtenerPorId(veterinarioId);
    }

    // Lista todos los veterinarios
    public ArrayList<VeterinarioDto> listarTodos() {
        return this.veterinarioDao.listarTodos();
    }
}
