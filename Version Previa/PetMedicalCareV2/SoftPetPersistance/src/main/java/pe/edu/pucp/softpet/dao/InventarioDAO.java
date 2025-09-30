/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.productosDTO.InventarioDTO;

/**
 *
 * @author marti
 */
public interface InventarioDAO {

    public Integer insertar(InventarioDTO inventario);

    public InventarioDTO obtenerPorId(Integer inventarioId);

    public ArrayList<InventarioDTO> listarTodos();

    public Integer modificar(InventarioDTO inventario);

    public Integer eliminar(InventarioDTO inventario);
}
