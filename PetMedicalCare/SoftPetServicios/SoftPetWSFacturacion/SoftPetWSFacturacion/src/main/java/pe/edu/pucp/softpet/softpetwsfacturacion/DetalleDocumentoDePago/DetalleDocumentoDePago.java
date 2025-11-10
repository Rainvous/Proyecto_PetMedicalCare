/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softpet.softpetwsfacturacion.DetalleDocumentoDePago;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.DetalleDocumentoDePagoBo;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;

/**
 *
 * @author snipe
 */
@WebService(serviceName = "DetalleDocumentoDePago")
public class DetalleDocumentoDePago {

    private DetalleDocumentoDePagoBo detalleDocumentoBo;
    
    public DetalleDocumentoDePago(){
        this.detalleDocumentoBo = new DetalleDocumentoDePagoBo();
    }
    
    @WebMethod(operationName = "insertar_Detalle_Documento_De_Pago")
    public Integer insertar(
            @WebParam(name = "documentoPagoId")int documentoPagoId,
            @WebParam(name = "servicioId")int servicioId,
            @WebParam(name = "productoId")int productoId,
            @WebParam(name = "nroItem")int nroItem,
            @WebParam(name = "descripcion")String descripcion,
            @WebParam(name = "cantidad")int cantidad,
            @WebParam(name = "precioUnitario")double precioUnitario,
            @WebParam(name = "valorVenta")double valorVenta,
            @WebParam(name = "activo")boolean activo)
    {
       return this.detalleDocumentoBo.insertar(documentoPagoId, servicioId, productoId, nroItem, descripcion, cantidad, precioUnitario, valorVenta, activo);
    }
    
    @WebMethod(operationName = "modificar_Detalle_Documento_De_Pago")
    public Integer modificar(
             @WebParam(name = "ddpId")int ddpId,
            @WebParam(name = "documentoPagoId")int documentoPagoId,
            @WebParam(name = "servicioId")int servicioId,
            @WebParam(name = "productoId")int productoId,
            @WebParam(name = "nroItem")int nroItem,
            @WebParam(name = "descripcion")String descripcion,
            @WebParam(name = "cantidad")int cantidad,
            @WebParam(name = "precioUnitario")double precioUnitario,
            @WebParam(name = "valorVenta")double valorVenta,
            @WebParam(name = "activo")boolean activo)
    {
       return this.detalleDocumentoBo.modificar(ddpId,documentoPagoId, servicioId, productoId, nroItem, descripcion, cantidad, precioUnitario, valorVenta, activo);
    }
    
    @WebMethod(operationName = "eliminar_Detalle_Documento_De_Pago")
    public Integer eliminar(int ddpId) {
        return this.detalleDocumentoBo.eliminar(ddpId);
    }
    
    @WebMethod(operationName = "obtener_por_id")
    public DetalleDocumentoPagoDto obtenerPorId(@WebParam(name = "id") int ddpId) {
        return this.detalleDocumentoBo.obtenerPorId(ddpId);
    }
    
    @WebMethod(operationName = "listar_todos")
    public ArrayList<DetalleDocumentoPagoDto> listarTodos() {
        return this.detalleDocumentoBo.listarTodos();
    }
}
