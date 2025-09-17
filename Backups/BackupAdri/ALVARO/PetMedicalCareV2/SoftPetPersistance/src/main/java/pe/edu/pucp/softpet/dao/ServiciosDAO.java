/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.serviciosdto.ServiciosDTO;

/**
 *
 * @author marti
 */
public interface ServiciosDAO {
      public Integer insertar(ServiciosDTO servicios);

    public ServiciosDTO obtenerPorId(Integer servicioId);

    public ArrayList<ServiciosDTO> listarTodos();

    public Integer modificar(ServiciosDTO servicio);

    public Integer eliminar(ServiciosDTO servicio);  
}
