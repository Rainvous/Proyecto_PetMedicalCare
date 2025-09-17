/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.serviciosdto.TipoServiciosDTO;

/**
 *
 * @author User
 */
public interface TipoDeServicioDAO {
    
    public Integer insertar(TipoServiciosDTO tipoServicio);

    public TipoServiciosDTO obtenerPorId(Integer tipoServicio_id);

    public ArrayList<TipoServiciosDTO> listarTodos();

    public Integer modificar(TipoServiciosDTO tipoServicio);

    public Integer eliminar(TipoServiciosDTO tipoServicio);
}
