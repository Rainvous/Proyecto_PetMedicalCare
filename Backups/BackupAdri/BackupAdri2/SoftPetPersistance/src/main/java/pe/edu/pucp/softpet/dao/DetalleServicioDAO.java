/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.DetalleDTO.DetalleServiciosDTO;

/**
 *
 * @author marti
 */
public interface DetalleServicioDAO {
    public Integer insertar(DetalleServiciosDTO detalleServicios);

    public DetalleServiciosDTO obtenerPorId(Integer detalleServiciosId);

    public ArrayList<DetalleServiciosDTO> listarTodos();

    public Integer modificar(DetalleServiciosDTO detalleServicios);
    
    public Integer eliminar(DetalleServiciosDTO detalleServicios);

}
