/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

/**
 *
 * @author marti
 */
public interface TipoServicioDao {
    public Integer insertar(TipoServicioDto tipoSservicio);

    public TipoServicioDto obtenerPorId(Integer tipoSservicioId);

    public ArrayList<TipoServicioDto> listarTodos();

    public Integer modificar(TipoServicioDto tipoSservicio);

    public Integer eliminar(TipoServicioDto tipoSservicio);
}
