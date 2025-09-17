package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.facturaciondto.ComprobantesDePagoDTO;

/**
 *
 * @author ferro
 */
public interface ComprobanteDePagoDAO {
    
    public Integer insertar(ComprobantesDePagoDTO comprobante);

    public ComprobantesDePagoDTO obtenerPorId(Integer comprobanteId);

    public ArrayList<ComprobantesDePagoDTO> listarTodos();

    public Integer modificar(ComprobantesDePagoDTO comprobante);
}
