package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.DetalleDocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.DetalleDocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.DocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.ProductoDaoImpl;
import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;

public class DetalleDocumentoDePagoBo {

    private final DetalleDocumentoDePagoDao detalleDao;

    public DetalleDocumentoDePagoBo() {
        this.detalleDao = new DetalleDocumentoDePagoDaoImpl();
    }

    public Integer insertar(int documentoPagoId, int servicioId, int productoId,
            int nroItem, String descripcion, int cantidad,
            double precioUnitario, double valorVenta, boolean activo) {

        DetalleDocumentoPagoDto detalle = new DetalleDocumentoPagoDto();

        detalle.setDocumentoPago(new DocumentoDePagoDaoImpl().obtenerPorId(documentoPagoId));
        detalle.setServicio(new ServicioDaoImpl().obtenerPorId(servicioId));
        detalle.setProducto(new ProductoDaoImpl().obtenerPorId(productoId));
        detalle.setNroItem(nroItem);
        detalle.setDescripcion(descripcion);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setValorVenta(valorVenta);
        detalle.setActivo(activo);

        return this.detalleDao.insertar(detalle);
    }

    public Integer modificar(int ddpId, int documentoPagoId, int servicioId,
            int productoId, int nroItem, String descripcion, int cantidad,
            double precioUnitario, double valorVenta, boolean activo) {

        DetalleDocumentoPagoDto detalle = new DetalleDocumentoPagoDto();

        detalle.setDddpId(ddpId);
        detalle.setDocumentoPago(new DocumentoDePagoDaoImpl().obtenerPorId(documentoPagoId));
        detalle.setServicio(new ServicioDaoImpl().obtenerPorId(servicioId));
        detalle.setProducto(new ProductoDaoImpl().obtenerPorId(productoId));
        detalle.setNroItem(nroItem);
        detalle.setDescripcion(descripcion);
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setValorVenta(valorVenta);
        detalle.setActivo(activo);

        return this.detalleDao.modificar(detalle);
    }

    public Integer eliminar(int detalleId) {
        DetalleDocumentoPagoDto detalle = new DetalleDocumentoPagoDto();
        detalle.setDddpId(detalleId);
        return this.detalleDao.eliminar(detalle);
    }

    public DetalleDocumentoPagoDto obtenerPorId(int detalleId) {
        return this.detalleDao.obtenerPorId(detalleId);
    }

    public ArrayList<DetalleDocumentoPagoDto> listarTodos() {
        return this.detalleDao.listarTodos();
    }
}
