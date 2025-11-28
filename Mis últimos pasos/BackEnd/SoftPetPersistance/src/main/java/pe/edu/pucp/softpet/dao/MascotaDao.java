package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;

public interface MascotaDao extends DaoBase<MascotaDto> {
    
    // Filtros específicos
    ArrayList<MascotaDto> ListasBusquedaAvanzada(String nombreMascota, String especie, String nombreDuenio, Integer activo);
    
    ArrayList<MascotaDto> listarPorIdPersona(Integer personaId);
    
    ArrayList<MascotaDto> listarMascotasActivas();

    // Validación para eliminación (Verifica si tiene historial médico, citas, etc.)
    // Nota: En tu Impl la variable se llama 'idServicio', aquí le puse 'idMascota' para que sea más claro, 
    // pero funcionará igual ya que lo importante es el tipo de dato (int).
    int VerificarSiLaMascotaTieneInformacion(int idMascota);
}