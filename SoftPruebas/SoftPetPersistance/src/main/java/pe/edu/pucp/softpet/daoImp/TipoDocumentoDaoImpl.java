package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.TipoDocumentoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.facturacion.TipoDocumentoDto;

/**
 *
 * @author marti
 */
public class TipoDocumentoDaoImpl extends DaoBaseImpl implements TipoDocumentoDao {

    private TipoDocumentoDto tipoDocumento;

    public TipoDocumentoDaoImpl() {
        super("TIPOS_DOCUMENTO");
        this.tipoDocumento = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.tipoDocumento.getNombre());
        this.statement.setInt(2, this.tipoDocumento.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.tipoDocumento.getNombre());
        this.statement.setInt(2, this.tipoDocumento.getActivo() ? 1 : 0);

        this.statement.setInt(3, this.tipoDocumento.getTipoDocumentoId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.tipoDocumento.getTipoDocumentoId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.tipoDocumento.getTipoDocumentoId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.tipoDocumento = new TipoDocumentoDto();
        this.tipoDocumento.setTipoDocumentoId(this.resultSet.getInt("TIPO_DOCUMENTO_ID"));
        this.tipoDocumento.setNombre(this.resultSet.getString("NOMBRE"));
        this.tipoDocumento.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.tipoDocumento = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.tipoDocumento);
    }

    @Override
    public Integer insertar(TipoDocumentoDto tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return super.insertar();
    }

    @Override
    public TipoDocumentoDto obtenerPorId(Integer tipoDocumentoId) {
        this.tipoDocumento = new TipoDocumentoDto();
        this.tipoDocumento.setTipoDocumentoId(tipoDocumentoId);
        super.obtenerPorId();
        return this.tipoDocumento;
    }

    @Override
    public ArrayList<TipoDocumentoDto> listarTodos() {
        return (ArrayList<TipoDocumentoDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(TipoDocumentoDto tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return super.modificar();
    }

    @Override
    public Integer eliminar(TipoDocumentoDto tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
        return super.eliminar();
    }
}
