package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.DocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.daoImp.util.enums.EstadoDocumentoDePago;
import pe.edu.pucp.softpet.daoImp.util.enums.TipoDocumentoDePago;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

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
        this.listaColumnas.add(new Columna("SERIE", false, false));
        this.listaColumnas.add(new Columna("NUMERO", false, false));
        this.listaColumnas.add(new Columna("FECHA_EMISION", false, false));
        this.listaColumnas.add(new Columna("TIPO_DOCUMENTO", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("SUBTOTAL", false, false));
        this.listaColumnas.add(new Columna("IGV_TOTAL", false, false));
        this.listaColumnas.add(new Columna("TOTAL", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("METODO_DE_PAGO_ID", false, false));
        this.listaColumnas.add(new Columna("PERSONA_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setString(1, this.documentoPago.getSerie());
        this.statement.setString(2, this.documentoPago.getNumero());
       
        this.statement.setDate(3, new java.sql.Date(this.documentoPago.getFechaEmision().getTime()));
        //FORMA DE CASTEAR UN SQL DATE
        this.statement.setString(4, sacarTipoDocumento(this.documentoPago.getTipoDocumento()));
        this.statement.setString(5, sacarEstado(this.documentoPago.getEstado()));
        this.statement.setDouble(6, this.documentoPago.getSubtotal());
        this.statement.setDouble(7, this.documentoPago.getIGVTotal());
        this.statement.setDouble(8, this.documentoPago.getTotal());
        this.statement.setInt(9, this.documentoPago.getActivo() ? 1 : 0);
        this.statement.setInt(10, this.documentoPago.getMetodoDePago().getMetodoDePagoId());
        this.statement.setInt(11, this.documentoPago.getPersona().getPersonaId());
    }
    protected String sacarTipoDocumento(String data){
        if(data.equals(TipoDocumentoDePago.BOLETA.toString())
                ||  data.equals("boleta")){
            return TipoDocumentoDePago.BOLETA.toString();
        }
        else{
            return TipoDocumentoDePago.FACTURA.toString();
        }
    }
    protected String sacarEstado(String data){
        if(data.equals(EstadoDocumentoDePago.EMITIDO.toString()) ||
                data.equals("emitido")){
            return EstadoDocumentoDePago.EMITIDO.toString();
        }
        else if( data.equals(EstadoDocumentoDePago.PAGADO.toString())
                    || data.equals("pagado")){
            return EstadoDocumentoDePago.PAGADO.toString();
        }
        else{
            return EstadoDocumentoDePago.ANULADO.toString();
        }
    }
    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.documentoPago.getSerie());
        this.statement.setString(2, this.documentoPago.getNumero());
       
        this.statement.setDate(3, new java.sql.Date(this.documentoPago.getFechaEmision().getTime()));
        //FORMA DE CASTEAR UN SQL DATE
        this.statement.setString(4, sacarTipoDocumento(this.documentoPago.getTipoDocumento()));
        this.statement.setString(5, sacarTipoDocumento(this.documentoPago.getEstado()));
        this.statement.setDouble(6, this.documentoPago.getSubtotal());
        this.statement.setDouble(7, this.documentoPago.getIGVTotal());
        this.statement.setDouble(8, this.documentoPago.getTotal());
        this.statement.setInt(9, this.documentoPago.getActivo() ? 1 : 0);
        this.statement.setInt(10, this.documentoPago.getMetodoDePago().getMetodoDePagoId());
        this.statement.setInt(11, this.documentoPago.getPersona().getPersonaId());

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
        this.documentoPago.setSerie(this.resultSet.getString("SERIE"));
        this.documentoPago.setNumero(this.resultSet.getString("NUMERO"));
    
        this.documentoPago.setFechaEmision(this.resultSet.getDate("FECHA_EMISION"));
        
        this.documentoPago.setTipoDocumento(this.resultSet.getString("TIPO_DOCUMENTO")); //NO LO CASTEO CON ENUMS PQ YA ESTÁ
        this.documentoPago.setEstado(this.resultSet.getString("ESTADO"));
        this.documentoPago.setSubtotal(this.resultSet.getDouble("SUBTOTAL"));
        this.documentoPago.setIGVTotal(this.resultSet.getDouble("IGV_TOTAL"));
        this.documentoPago.setTotal(this.resultSet.getDouble("TOTAL"));
        this.documentoPago.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        MetodoDePagoDto tp= new MetodoDePagoDto();
        tp.setMetodoDePagoId(this.resultSet.getInt("METODO_DE_PAGO_ID"));
        this.documentoPago.setMetodoDePago(tp);
        PersonaDto aux= new PersonaDto();
        aux.setPersonaId(this.resultSet.getInt("PERSONA_ID"));
        this.documentoPago.setPersona(aux);
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
