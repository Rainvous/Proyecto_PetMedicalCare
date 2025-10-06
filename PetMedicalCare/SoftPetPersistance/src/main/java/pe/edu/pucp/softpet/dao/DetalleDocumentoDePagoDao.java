package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;

/**
 *
 * @author marti
 */
public interface DetalleDocumentoDePagoDao {

    public Integer insertar(DetalleDocumentoPagoDto detalleDocumento);

    public DetalleDocumentoPagoDto obtenerPorId(Integer detalleDocumentoId);

    public ArrayList<DetalleDocumentoPagoDto> listarTodos();

    public Integer modificar(DetalleDocumentoPagoDto detalleDocumento);

    public Integer eliminar(DetalleDocumentoPagoDto detalleDocumento);
}
