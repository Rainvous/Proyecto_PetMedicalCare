package pe.edu.pucp.softpet.daoImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.ComprobanteDePagoDAO;
import pe.edu.pucp.softpet.daoImpl.base.DAOImplBase;
import pe.edu.pucp.softpet.daoImpl.util.Columna;
import pe.edu.pucp.softpet.model.atencionmedicaDTO.CitaAtencionDTO;
import pe.edu.pucp.softpet.model.facturaciondto.ComprobantesDePagoDTO;
import pe.edu.pucp.softpet.model.facturaciondto.TipoComprobantesDTO;

/**
 *
 * @author ferro
 */
public class ComprobanteDePagoDAOImpl extends DAOImplBase implements ComprobanteDePagoDAO {
    
    private ComprobantesDePagoDTO comprobante;
    
    public ComprobanteDePagoDAOImpl() {

        super("COMPROBANTE_DE_PAGO");
        this.comprobante = null;
        this.retornarLlavePrimaria = true;
    }

    public ComprobanteDePagoDAOImpl(String nombre_tabla) {

        super(nombre_tabla);
        this.comprobante = null;
        this.retornarLlavePrimaria = true;
    }
    
    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("COMPROBANTE_ID", true, true));
        this.listaColumnas.add(new Columna("TIPO_COMPROBANTE_ID", false, false));
        this.listaColumnas.add(new Columna("CITA_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA_EMISION", false, false));
        this.listaColumnas.add(new Columna("MONTO_TOTAL", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("IGV", false, false));
        this.listaColumnas.add(new Columna("METODO_DE_PAGO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setObject(1, this.comprobante.getTipoComprobante());
        this.statement.setObject(2, this.comprobante.getCitaAtencion());
        this.statement.setDate(3, (Date) this.comprobante.getFecha_emision());
        this.statement.setDouble(4, this.comprobante.getMonto_total());
        this.statement.setString(5, this.comprobante.getEstado());
        this.statement.setDouble(6, this.comprobante.getIgv());
        this.statement.setString(7, this.comprobante.getMetodo_de_pago());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {

        this.statement.setObject(1, this.comprobante.getTipoComprobante());
        this.statement.setObject(2, this.comprobante.getCitaAtencion());
        this.statement.setDate(3, (Date) this.comprobante.getFecha_emision());
        this.statement.setDouble(4, this.comprobante.getMonto_total());
        this.statement.setString(5, this.comprobante.getEstado());
        this.statement.setDouble(6, this.comprobante.getIgv());
        this.statement.setString(7, this.comprobante.getMetodo_de_pago());
        
        this.statement.setInt(8, this.comprobante.getComprobante_id());
    }

    @Override
    public Integer insertar(ComprobantesDePagoDTO comprobante) {

        this.comprobante = comprobante;
        return super.insertar();
    }

    @Override
    public ComprobantesDePagoDTO obtenerPorId(Integer comprobanteId) {
        this.comprobante = new ComprobantesDePagoDTO();
        this.comprobante.setComprobante_id(comprobanteId);
        super.obtenerPorId();
        return this.comprobante;
    }

    @Override
    public ArrayList<ComprobantesDePagoDTO> listarTodos() {
        return (ArrayList<ComprobantesDePagoDTO>) super.listarTodos();
    }

    @Override
    public Integer modificar(ComprobantesDePagoDTO comprobante) {
        this.comprobante = comprobante;
        return super.modificar();
    }
    
    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.comprobante.getComprobante_id());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.comprobante = new ComprobantesDePagoDTO();
        this.comprobante.setComprobante_id(this.resultSet.getInt("COMPROBANTE_ID"));
        this.comprobante.setTipoComprobante((TipoComprobantesDTO) this.resultSet.getObject("TIPO_COMPROBANTE_ID"));
        this.comprobante.setCitaAtencion((CitaAtencionDTO) this.resultSet.getObject("CITA_ID"));
        this.comprobante.setFecha_emision(this.resultSet.getDate("FECHA_EMISION"));
        this.comprobante.setMonto_total(this.resultSet.getDouble("MONTO_TOTAL"));
        this.comprobante.setEstado(this.resultSet.getString("ESTADO"));
        this.comprobante.setIgv(this.resultSet.getDouble("IGV"));
        this.comprobante.setMetodo_de_pago(this.resultSet.getString("METODO_DE_PAGO"));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.comprobante = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.comprobante);
    }
}
