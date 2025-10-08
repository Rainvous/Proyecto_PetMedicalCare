package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.DetalleDocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;

public class DetalleDocumentoDePagoDaoImpl extends DaoBaseImpl implements DetalleDocumentoDePagoDao {

    private DetalleDocumentoPagoDto detalleDocumento;

    public DetalleDocumentoDePagoDaoImpl() {
        super("DETALLES_DOCUMENTO_DE_PAGO");
        this.detalleDocumento = null;
        this.retornarLlavePrimaria = true;
        this.usuario = "user_backend";
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("DDDP_ID", true, true));
        this.listaColumnas.add(new Columna("NRO_ITEM", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO_SIN_IGV", false, false));
        this.listaColumnas.add(new Columna("VALOR_VENTA", false, false));
        this.listaColumnas.add(new Columna("IGV_ITEM", false, false));
        this.listaColumnas.add(new Columna("IMPORTE_TOTAL", false, false));
        this.listaColumnas.add(new Columna("DOCUMENTO_DE_PAGO_ID", false, false));
        this.listaColumnas.add(new Columna("SERVICIO_ID", false, false));
        this.listaColumnas.add(new Columna("PRODUCTO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.detalleDocumento.getNroItem());
        this.statement.setString(2, this.detalleDocumento.getDescripcion());
        this.statement.setInt(3, this.detalleDocumento.getCantidad());
        this.statement.setDouble(4, this.detalleDocumento.getPrecioUnitarioSinIGV());
        this.statement.setDouble(5, this.detalleDocumento.getValorVenta());
        this.statement.setDouble(6, this.detalleDocumento.getIGVItem());
        this.statement.setDouble(7, this.detalleDocumento.getImporteTotal());
        this.statement.setInt(8, this.detalleDocumento.getDocumentoPago().getDocumentoPagoId());
        this.statement.setInt(9, this.detalleDocumento.getServicio().getServicioId());
        this.statement.setInt(10, this.detalleDocumento.getProducto().getProductoId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detalleDocumento.getNroItem());
        this.statement.setString(2, this.detalleDocumento.getDescripcion());
        this.statement.setInt(3, this.detalleDocumento.getCantidad());
        this.statement.setDouble(4, this.detalleDocumento.getPrecioUnitarioSinIGV());
        this.statement.setDouble(5, this.detalleDocumento.getValorVenta());
        this.statement.setDouble(6, this.detalleDocumento.getIGVItem());
        this.statement.setDouble(7, this.detalleDocumento.getImporteTotal());
        this.statement.setInt(8, this.detalleDocumento.getDocumentoPago().getDocumentoPagoId());
        this.statement.setInt(9, this.detalleDocumento.getServicio().getServicioId());
        this.statement.setInt(10, this.detalleDocumento.getProducto().getProductoId());

        this.statement.setInt(11, this.detalleDocumento.getDddpId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.detalleDocumento.getDddpId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.detalleDocumento.getDddpId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.detalleDocumento = new DetalleDocumentoPagoDto();
        this.detalleDocumento.setDddpId(this.resultSet.getInt("DDDP_ID"));
        this.detalleDocumento.setNroItem(this.resultSet.getInt("NRO_ITEM"));
        this.detalleDocumento.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.detalleDocumento.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detalleDocumento.setPrecioUnitarioSinIGV(this.resultSet.getDouble("PRECIO_UNITARIO_SIN_IGV"));
        this.detalleDocumento.setValorVenta(this.resultSet.getDouble("VALOR_VENTA"));
        this.detalleDocumento.setIGVItem(this.resultSet.getDouble("IGV_ITEM"));
        this.detalleDocumento.setImporteTotal(this.resultSet.getDouble("IMPORTE_TOTAL"));
        this.detalleDocumento.setDocumentoPago(new DocumentoDePagoDaoImpl().
                obtenerPorId(this.resultSet.getInt("DOCUMENTO_DE_PAGO_ID")));
        this.detalleDocumento.setServicio(new ServicioDaoImpl().
                obtenerPorId(this.resultSet.getInt("SERVICIO_ID")));
        this.detalleDocumento.setProducto(new ProductoDaoImpl().
                obtenerPorId(this.resultSet.getInt("PRODUCTO_ID")));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.detalleDocumento = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.detalleDocumento);
    }

    @Override
    public Integer insertar(DetalleDocumentoPagoDto detalleDocumento) {
        this.detalleDocumento = detalleDocumento;
        return super.insertar();
    }

    @Override
    public DetalleDocumentoPagoDto obtenerPorId(Integer detalleDocumentoId) {
        this.detalleDocumento = new DetalleDocumentoPagoDto();
        this.detalleDocumento.setDddpId(detalleDocumentoId);
        super.obtenerPorId();
        return this.detalleDocumento;
    }

    @Override
    public ArrayList<DetalleDocumentoPagoDto> listarTodos() {
        return (ArrayList<DetalleDocumentoPagoDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(DetalleDocumentoPagoDto detalleDocumento) {
        this.detalleDocumento = detalleDocumento;
        return super.modificar();
    }

    @Override
    public Integer eliminar(DetalleDocumentoPagoDto detalleDocumento) {
        this.detalleDocumento = detalleDocumento;
        return super.eliminar();
    }
}
