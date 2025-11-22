package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.DetalleDocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

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
        this.listaColumnas.add(new Columna("DOCUMENTO_DE_PAGO_ID", false, false));
        this.listaColumnas.add(new Columna("SERVICIO_ID", false, false));
        this.listaColumnas.add(new Columna("PRODUCTO_ID", false, false));
        this.listaColumnas.add(new Columna("NRO_ITEM", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("CANTIDAD", false, false));
        this.listaColumnas.add(new Columna("PRECIO_UNITARIO", false, false));
        this.listaColumnas.add(new Columna("VALOR_VENTA", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.detalleDocumento.getDocumentoPago().getDocumentoPagoId());
        if(this.detalleDocumento.getServicio().getServicioId()==null || this.detalleDocumento.getServicio().getServicioId()==0){
            this.statement.setNull(2, this.detalleDocumento.getServicio().getServicioId());
        }
        else{
            this.statement.setInt(2, this.detalleDocumento.getServicio().getServicioId());
        }
        if( this.detalleDocumento.getProducto().getProductoId()==null || this.detalleDocumento.getServicio().getServicioId()==0){
            this.statement.setInt(3, this.detalleDocumento.getProducto().getProductoId());
        }
        else{
            this.statement.setNull(3, this.detalleDocumento.getProducto().getProductoId());
        }
        
        
        this.statement.setInt(4, this.detalleDocumento.getNroItem());
        this.statement.setString(5, this.detalleDocumento.getDescripcion());
        this.statement.setInt(6, this.detalleDocumento.getCantidad());
        this.statement.setDouble(7, this.detalleDocumento.getPrecioUnitario());
        this.statement.setDouble(8, this.detalleDocumento.getValorVenta());
        this.statement.setInt(9, this.detalleDocumento.getActivo() == true ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.detalleDocumento.getDocumentoPago().getDocumentoPagoId());
        this.statement.setInt(2, this.detalleDocumento.getServicio().getServicioId());
        this.statement.setInt(3, this.detalleDocumento.getProducto().getProductoId());
        this.statement.setInt(4, this.detalleDocumento.getNroItem());
        this.statement.setString(5, this.detalleDocumento.getDescripcion());
        this.statement.setInt(6, this.detalleDocumento.getCantidad());
        this.statement.setDouble(7, this.detalleDocumento.getPrecioUnitario());
        this.statement.setDouble(8, this.detalleDocumento.getValorVenta());
        this.statement.setInt(9, this.detalleDocumento.getActivo() == true ? 1 : 0);

        this.statement.setInt(10, this.detalleDocumento.getDddpId());
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
        DocumentoPagoDto docpag = new DocumentoPagoDto();
        docpag.setDocumentoPagoId(this.resultSet.getInt("DOCUMENTO_DE_PAGO_ID"));

        this.detalleDocumento.setDocumentoPago(docpag);
        ServicioDto servi = new ServicioDto();
        servi.setServicioId(this.resultSet.getInt("SERVICIO_ID"));
        this.detalleDocumento.setServicio(servi);

        ProductoDto prd = new ProductoDto();
        prd.setProductoId(this.resultSet.getInt("PRODUCTO_ID"));
        this.detalleDocumento.setProducto(prd);
        this.detalleDocumento.setNroItem(this.resultSet.getInt("NRO_ITEM"));
        this.detalleDocumento.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.detalleDocumento.setCantidad(this.resultSet.getInt("CANTIDAD"));
        this.detalleDocumento.setPrecioUnitario(this.resultSet.getDouble("PRECIO_UNITARIO"));
        this.detalleDocumento.setValorVenta(this.resultSet.getDouble("VALOR_VENTA"));
        this.detalleDocumento.setActivo(this.resultSet.getInt("ACTIVO") == 1);
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
