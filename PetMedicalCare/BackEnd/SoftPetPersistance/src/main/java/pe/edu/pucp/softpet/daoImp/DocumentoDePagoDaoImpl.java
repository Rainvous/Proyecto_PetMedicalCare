package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.softpet.dao.DocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoDocumentoDePago;
import pe.edu.pucp.softpet.dto.util.enums.TipoDocumentoDePago;

public class DocumentoDePagoDaoImpl extends DaoBaseImpl implements DocumentoDePagoDao {

    private DocumentoPagoDto documentoPago;

    public DocumentoDePagoDaoImpl() {
        super("DOCUMENTOS_DE_PAGO");
        this.documentoPago = null;
        this.retornarLlavePrimaria = true;
        this.usuario = "user_backend";
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DOCUMENTO_DE_PAGO_ID", true, true));
        this.listaColumnas.add(new Columna("METODO_DE_PAGO_ID", false, false));
        this.listaColumnas.add(new Columna("PERSONA_ID", false, false));
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("SERIE", false, false));
        this.listaColumnas.add(new Columna("NUMERO", false, false));
        this.listaColumnas.add(new Columna("FECHA_EMISION", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
        this.listaColumnas.add(new Columna("IGV_TOTAL", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.documentoPago.getMetodoDePago().getMetodoDePagoId());
        this.statement.setInt(2, this.documentoPago.getPersona().getPersonaId());
        this.statement.setString(3, this.documentoPago.getTipoDocumento().toString());
        this.statement.setString(4, this.documentoPago.getSerie());
        this.statement.setString(5, this.documentoPago.getNumero());
        this.statement.setDate(6, this.documentoPago.getFechaEmision());
        this.statement.setString(7, this.documentoPago.getEstado().toString());
        this.statement.setDouble(8, this.documentoPago.getSubtotal());
        this.statement.setDouble(9, this.documentoPago.getIGVTotal());
        this.statement.setDouble(10, this.documentoPago.getTotal());
        this.statement.setInt(11, this.documentoPago.getActivo() ? 1 : 0);
    }

//    protected String sacarTipoDocumento(String data) {
//        if (data.equals(TipoDocumentoDePago.BOLETA.toString())
//                || data.equals("boleta")) {
//            return TipoDocumentoDePago.BOLETA.toString();
//        } else {
//            return TipoDocumentoDePago.FACTURA.toString();
//        }
//    }
//
//    protected String sacarEstado(String data) {
//        if (data.equals(EstadoDocumentoDePago.EMITIDO.toString())
//                || data.equals("emitido")) {
//            return EstadoDocumentoDePago.EMITIDO.toString();
//        } else if (data.equals(EstadoDocumentoDePago.PAGADO.toString())
//                || data.equals("pagado")) {
//            return EstadoDocumentoDePago.PAGADO.toString();
//        } else {
//            return EstadoDocumentoDePago.ANULADO.toString();
//        }
//    }
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.documentoPago.getMetodoDePago().getMetodoDePagoId());
        this.statement.setInt(2, this.documentoPago.getPersona().getPersonaId());
        this.statement.setString(3, this.documentoPago.getTipoDocumento().toString());
        this.statement.setString(4, this.documentoPago.getSerie());
        this.statement.setString(5, this.documentoPago.getNumero());
        this.statement.setDate(6, this.documentoPago.getFechaEmision());
        this.statement.setString(7, this.documentoPago.getEstado().toString());
        this.statement.setDouble(8, this.documentoPago.getSubtotal());
        this.statement.setDouble(9, this.documentoPago.getIGVTotal());
        this.statement.setDouble(10, this.documentoPago.getTotal());
        this.statement.setInt(11, this.documentoPago.getActivo() ? 1 : 0);

        this.statement.setInt(12, this.documentoPago.getDocumentoPagoId());
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
        this.documentoPago.setDocumentoPagoId(this.resultSet.getInt("DOCUMENTO_DE_PAGO_ID"));
        MetodoDePagoDto met = new MetodoDePagoDto();
        PersonaDto per = new PersonaDto();
        met.setMetodoDePagoId(this.resultSet.getInt("METODO_DE_PAGO_ID"));

        this.documentoPago.setMetodoDePago(met);
        per.setPersonaId(this.resultSet.getInt("PERSONA_ID"));
        this.documentoPago.setPersona(per);
        this.documentoPago.setTipoDocumento(TipoDocumentoDePago.
                valueOf(this.resultSet.getString("TIPO_DOCUMENTO")));
        this.documentoPago.setSerie(this.resultSet.getString("SERIE"));
        this.documentoPago.setNumero(this.resultSet.getString("NUMERO"));
        this.documentoPago.setFechaEmision(this.resultSet.getDate("FECHA_EMISION"));
        this.documentoPago.setEstado(EstadoDocumentoDePago.
                valueOf(this.resultSet.getString("ESTADO")));
        this.documentoPago.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
        this.documentoPago.setIGVTotal(this.resultSet.getDouble("IGV_TOTAL"));
        this.documentoPago.setTotal(this.resultSet.getDouble("TOTAL"));
        this.documentoPago.setActivo(this.resultSet.getInt("ACTIVO") == 1);
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

    public ArrayList<String> GeneracionDeSiguienteBoletaOFactura(String tipoDocumento) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        Map<Integer, Object> parametrosSalida = new HashMap<>();
        String NombreProcedure = "sp_generar_correlativo_pago";
        parametrosEntrada.put(1, tipoDocumento);
        parametrosSalida.put(2, Types.VARCHAR);
        parametrosSalida.put(3, Types.VARCHAR);
        ejecutarProcedimiento(NombreProcedure, parametrosEntrada, parametrosSalida);
        ArrayList<String> SerieYNumeroDocumento = new ArrayList();
        SerieYNumeroDocumento.add((String) parametrosSalida.get(2));
        SerieYNumeroDocumento.add((String) parametrosSalida.get(3));
        return SerieYNumeroDocumento;
    }
}
