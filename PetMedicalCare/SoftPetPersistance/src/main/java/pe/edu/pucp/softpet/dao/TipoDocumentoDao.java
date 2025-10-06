package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.facturacion.TipoDocumentoDto;

/**
 *
 * @author marti
 */
public interface TipoDocumentoDao {

    public Integer insertar(TipoDocumentoDto tipoDocumento);

    public TipoDocumentoDto obtenerPorId(Integer tipoDocumentoId);

    public ArrayList<TipoDocumentoDto> listarTodos();

    public Integer modificar(TipoDocumentoDto tipoDocumento);

    public Integer eliminar(TipoDocumentoDto tipoDocumento);
}
