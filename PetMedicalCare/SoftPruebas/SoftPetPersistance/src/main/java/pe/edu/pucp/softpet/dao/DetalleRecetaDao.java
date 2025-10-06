/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.recetas.DetalleRecetaDto;

/**
 *
 * @author marti
 */
public interface DetalleRecetaDao {
    public Integer insertar(DetalleRecetaDto detalleReceta);

    public DetalleRecetaDto obtenerPorId(Integer detalleRecetaId);

    public ArrayList<DetalleRecetaDto> listarTodos();

    public Integer modificar(DetalleRecetaDto detalleReceta);

    public Integer eliminar(DetalleRecetaDto detalleReceta);
}
