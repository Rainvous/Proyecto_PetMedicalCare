package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.DocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;

/**
 *
 * @author marti
 */
public class DocumentoDePagoDaoImpl extends DaoBaseImpl implements DocumentoDePagoDao {

    private DocumentoPagoDto documentoPago;

    public DocumentoDePagoDaoImpl() {
        super("DOCUMENTOS_DE_PAGO");
        this.documentoPago = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DOCUMENTO_DE_PAGO_ID", true, true));
        this.listaColumnas.add(new Columna("SERIE", false, false));
        this.listaColumnas.add(new Columna("NUMERO", false, false));
        this.listaColumnas.add(new Columna("TASA_IGV", false, false));
        this.listaColumnas.add(new Columna("FECHA_EMISION", false, false));
        this.listaColumnas.add(new Columna("METODO_DE_PAGO", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL_SIN_IGV", false, false));
        this.listaColumnas.add(new Columna("IGV_TOTAL", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO_ID", false, false));
        this.listaColumnas.add(new Columna("PERSONA_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.documentoPago.getSerie());
        this.statement.setString(2, this.documentoPago.getNumero());
        this.statement.setDouble(3, this.documentoPago.getTasaIGV());
        this.statement.setDate(4, this.documentoPago.getFechaEmision());
        this.statement.setString(5, this.documentoPago.getMetodoPago());
        this.statement.setString(6, this.documentoPago.getEstado());
        this.statement.setDouble(7, this.documentoPago.getSubtotalSinIGV());
        this.statement.setDouble(8, this.documentoPago.getIGVTotal());
        this.statement.setDouble(9, this.documentoPago.getTotal());
        this.statement.setInt(10, this.documentoPago.getActivo() ? 1 : 0);
        this.statement.setInt(11, this.documentoPago.getTipoDocumento().getTipoDocumentoId());
        this.statement.setInt(12, this.documentoPago.getPersona().getPersonaId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.documentoPago.getSerie());
        this.statement.setString(2, this.documentoPago.getNumero());
        this.statement.setDouble(3, this.documentoPago.getTasaIGV());
        this.statement.setDate(4, this.documentoPago.getFechaEmision());
        this.statement.setString(5, this.documentoPago.getMetodoPago());
        this.statement.setString(6, this.documentoPago.getEstado());
        this.statement.setDouble(7, this.documentoPago.getSubtotalSinIGV());
        this.statement.setDouble(8, this.documentoPago.getIGVTotal());
        this.statement.setDouble(9, this.documentoPago.getTotal());
        this.statement.setInt(10, this.documentoPago.getActivo() ? 1 : 0);
        this.statement.setInt(11, this.documentoPago.getTipoDocumento().getTipoDocumentoId());
        this.statement.setInt(12, this.documentoPago.getPersona().getPersonaId());

        this.statement.setInt(13, this.documentoPago.getDocumentoPagoId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.documentoPago.getDocumentoPagoId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.documentoPago.getDocumentoPagoId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.documentoPago = new DocumentoPagoDto();
        this.documentoPago.setDocumentoPagoId(this.resultSet.getInt("DOCUMENTO_DE_PAGO"));
        this.documentoPago.setSerie(this.resultSet.getString("SERIE"));
        this.documentoPago.setNumero(this.resultSet.getString("NUMERO"));
        this.documentoPago.setTasaIGV(this.resultSet.getDouble("TASA_IGV"));
        this.documentoPago.setFechaEmision(this.resultSet.getDate("FECHA_EMISION"));
        this.documentoPago.setMetodoPago(this.resultSet.getString("METODO_DE_PAGO"));
        this.documentoPago.setEstado(this.resultSet.getString("ESTADO"));
        this.documentoPago.setSubtotalSinIGV(this.resultSet.getDouble("SUBTOTAL_SIN_IGV"));
        this.documentoPago.setIGVTotal(this.resultSet.getDouble("IGV_TOTAL"));
        this.documentoPago.setTotal(this.resultSet.getDouble("TOTAL"));
        this.documentoPago.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        this.documentoPago.setTipoDocumento(new TipoDocumentoDaoImpl().
                obtenerPorId(this.resultSet.getInt("TIPO_DOCUMENTO_ID")));
        this.documentoPago.setPersona(new PersonaDaoImpl().
                obtenerPorId(this.resultSet.getInt("PERSONA_ID")));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.documentoPago = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.documentoPago);
    }

    @Override
    public Integer insertar(DocumentoPagoDto documentoPago) {
        this.documentoPago = documentoPago;
        return super.insertar();
    }

    @Override
    public DocumentoPagoDto obtenerPorId(Integer documentoPagoId) {
        this.documentoPago = new DocumentoPagoDto();
        this.documentoPago.setDocumentoPagoId(documentoPagoId);
        super.obtenerPorId();
        return this.documentoPago;
    }

    @Override
    public ArrayList<DocumentoPagoDto> listarTodos() {
        return (ArrayList<DocumentoPagoDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(DocumentoPagoDto documentoPago) {
        this.documentoPago = documentoPago;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DocumentoPagoDto documentoPago) {
        this.documentoPago = documentoPago;
        return super.eliminar();
    }
}
