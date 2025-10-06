package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;

/**
 *
 * @author marti
 */
public interface DocumentoDePagoDao {

    public Integer insertar(DocumentoPagoDto documentoPago);

    public DocumentoPagoDto obtenerPorId(Integer documentoPagoId);

    public ArrayList<DocumentoPagoDto> listarTodos();

    public Integer modificar(DocumentoPagoDto documentoPago);

    public Integer eliminar(DocumentoPagoDto documentoPago);
}
