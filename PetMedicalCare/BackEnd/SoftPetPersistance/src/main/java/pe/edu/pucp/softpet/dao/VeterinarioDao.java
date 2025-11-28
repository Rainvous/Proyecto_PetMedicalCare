package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import java.sql.Date; // Necesario porque tu Impl usa java.sql.Date
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;

public interface VeterinarioDao extends DaoBase<VeterinarioDto> {

    // --- Consultas Personalizadas ---
    ArrayList<VeterinarioDto> listarVeterinariosActivos();

    ArrayList<VeterinarioDto> ListasBusquedaAvanzadaVeterinario(
            String especialidad, String nombre, String nroDocumento, Integer estadoActivo);

    // --- Verificaciones ---
    // Verifica si ya existe un horario laboral asignado (retorna entero según lógica del SP)
    int VerificarSiExisteHorarioLaboral(Date fecha, Integer idVeterinario);

    // --- CRUD Transaccional Completo (Usuario + Persona + Veterinario) ---
    
    // Inserta en las 3 tablas en una sola transacción
    Integer insertarVeterinarioCompleto(
            String username, String password, String correo, boolean activoUsuario,
            String nombre, String direccion, String telefono, String sexo,
            Integer nroDocumento, Integer ruc, String tipoDocumento,
            String fechaContratacion, String estado, String especialidad);

    // Modifica en las 3 tablas en una sola transacción
    Integer modificarVeterinarioCompleto(
            Integer idVeterinario, Integer idPersona, Integer idUsuario,
            String username, String password, String correo, boolean activo,
            String nombre, String direccion, String telefono, String sexo,
            Integer nroDocumento, Integer ruc, String tipoDocumento,
            String fechaContratacion, String estado, String especialidad);

    // Eliminación lógica en cascada (Veterinario -> Persona -> Usuario)
    Integer eliminarVeterinarioCompleto(Integer idVeterinario);
}