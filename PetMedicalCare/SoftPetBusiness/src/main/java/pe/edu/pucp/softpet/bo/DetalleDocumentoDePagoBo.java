package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.DetalleDocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.DetalleDocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.DocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.ProductoDaoImpl;
import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;

public class DetalleDocumentoDePagoBo {

    private DetalleDocumentoDePagoDao detalleDao;

    public DetalleDocumentoDePagoBo() {
        this.detalleDao = new DetalleDocumentoDePagoDaoImpl();
    }

    // Inserta un nuevo detalle de documento de pago
    public Integer insertar(int nroItem, String descripcion, int cantidad,
            double precioUnitario, double valorVenta,
           
            int documentoPagoId, int servicioId, int productoId) {

        DetalleDocumentoPagoDto detalle = new DetalleDocumentoPagoDto();
        detalle.setNroItem(nroItem);
        detalle.setDescripcion(descripcion.trim().toUpperCase());
        detalle.setCantidad(cantidad);
        detalle.setPrecioUnitario(precioUnitario);
        detalle.setValorVenta(valorVenta);
        detalle.setActivo(Boolean.TRUE);
        
      

        // Relaciones con otras entidades
        detalle.setDocumentoPago(new DocumentoDePagoDaoImpl().obtenerPorId(documentoPagoId));
        detalle.setServicio(new ServicioDaoImpl().obtenerPorId(servicioId));
        detalle.setProducto(new ProductoDaoImpl().obtenerPorId(productoId));

        return this.detalleDao.insertar(detalle);
    }

    // Modifica un detalle existente
    public Integer modificar(int detalleId, int nroItem, String descripcion, int cantidad,
            double precioUnitario, double valorVenta,
             double importeTotal,
            int documentoPagoId, int servicioId, int productoId, boolean esActivo) {

        DetalleDocumentoPagoDto detalle = new DetalleDocumentoPagoDto();
        detalle.setDddpId(detalleId);
        detalle.setNroItem(nroItem);
        detalle.setDescripcion(descripcion.trim().toUpperCase());
        detalle.setCantidad(cantidad);
         detalle.setPrecioUnitario(precioUnitario);
        detalle.setValorVenta(valorVenta);
        detalle.setActivo(esActivo);
        

        detalle.setDocumentoPago(new DocumentoDePagoDaoImpl().obtenerPorId(documentoPagoId));
        detalle.setServicio(new ServicioDaoImpl().obtenerPorId(servicioId));
        detalle.setProducto(new ProductoDaoImpl().obtenerPorId(productoId));

        return this.detalleDao.modificar(detalle);
    }

    // Elimina un detalle por su ID
    public Integer eliminar(int detalleId) {
        DetalleDocumentoPagoDto detalle = new DetalleDocumentoPagoDto();
        detalle.setDddpId(detalleId);
        return this.detalleDao.eliminar(detalle);
    }

    // Obtiene un detalle por ID
    public DetalleDocumentoPagoDto obtenerPorId(int detalleId) {
        return this.detalleDao.obtenerPorId(detalleId);
    }

    // Lista todos los detalles registrados
    public ArrayList<DetalleDocumentoPagoDto> listarTodos() {
        return this.detalleDao.listarTodos();
    }
}
