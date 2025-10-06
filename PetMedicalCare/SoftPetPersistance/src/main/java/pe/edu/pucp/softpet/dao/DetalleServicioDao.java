package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.servicios.DetalleServicioDto;

/**
 *
 * @author marti
 */
public interface DetalleServicioDao {

    public Integer insertar(DetalleServicioDto detallesServicio);

    public DetalleServicioDto obtenerPorId(Integer detallesServicioId);

    public ArrayList<DetalleServicioDto> listarTodos();

    public Integer modificar(DetalleServicioDto detallesServicio);

    public Integer eliminar(DetalleServicioDto detallesServicio);
}
