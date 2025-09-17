/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.productosDTO.TipoProductosDTO;

/**
 *
 * @author marti
 */
public interface TipoProductoDAO {

    public Integer insertar(TipoProductosDTO tipoProductos);

    public TipoProductosDTO obtenerPorId(Integer tipoProductosId);

    public ArrayList<TipoProductosDTO> listarTodos();

    public Integer modificar(TipoProductosDTO tipoProductos);



}
