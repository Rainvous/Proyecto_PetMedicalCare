/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.actoresdto.MascotasDTO;

/**
 *
 * @author marti
 */
public interface MascotaDAO {

    public Integer insertar(MascotasDTO mascota);

    public MascotasDTO obtenerPorId(Integer mascotaId);

    public ArrayList<MascotasDTO> listarTodos();

    public Integer modificar(MascotasDTO mascota);

    public Integer eliminar(MascotasDTO mascota);

}

