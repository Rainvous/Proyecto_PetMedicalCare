/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softpet.softpetws.facturacion;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.DocumentoDePagoBo;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;

/**
 *
 * @author snipe
 */

@WebService(serviceName = "DocumentosDePago")
public class DocumentosDePago {

    private final DocumentoDePagoBo documentoBo;
    
    public DocumentosDePago(){
        this.documentoBo = new DocumentoDePagoBo();
    }
    
    @WebMethod(operationName = "insertar_Documento_De_Pago")
    public Integer insertar(
            @WebParam(name = "metodoDePagoId") int metodoDePagoId, 
            @WebParam(name = "personaId") int personaId,
            @WebParam(name = "tipoDocumento") String tipoDocumento,
            @WebParam(name = "serie") String serie, 
            @WebParam(name = "numero") String numero,
            @WebParam(name = "fechaEmision") String fechaEmision, 
            @WebParam(name = "estado") String estado, 
            @WebParam(name = "subtotal") double subtotal,
            @WebParam(name = "igvTotal") double igvTotal, 
            @WebParam(name = "total") double total, 
            @WebParam(name = "activo") boolean activo) {
        
        return this.documentoBo.insertar(metodoDePagoId, personaId, tipoDocumento, 
                serie, numero, fechaEmision, estado, subtotal, igvTotal, total, activo);  
    }
    
    @WebMethod(operationName = "modificar_Documento_De_Pago")
    public Integer modificar(
            @WebParam(name = "documentoPagoId") int documentoPagoId, 
            @WebParam(name = "metodoDePagoId") int metodoDePagoId, 
            @WebParam(name = "personaId") int personaId,
            @WebParam(name = "tipoDocumento") String tipoDocumento,
            @WebParam(name = "serie") String serie, 
            @WebParam(name = "numero") String numero,
            @WebParam(name = "fechaEmision") String fechaEmision, 
            @WebParam(name = "estado") String estado, 
            @WebParam(name = "subtotal") double subtotal,
            @WebParam(name = "igvTotal") double igvTotal, 
            @WebParam(name = "total") double total, 
            @WebParam(name = "activo") boolean activo) {
        
        return this.documentoBo.modificar (documentoPagoId ,metodoDePagoId, personaId, tipoDocumento, 
                serie, numero, fechaEmision, estado, subtotal, igvTotal, total, activo);  
    }
    
    @WebMethod(operationName = "eliminar_Documento_De_Pago")
    public Integer eliminar(int documentoPagoId) {
        return this.documentoBo.eliminar(documentoPagoId);
    }
    
    @WebMethod(operationName = "obtener_por_id")
    public DocumentoPagoDto obtenerPorId(@WebParam(name = "id") int documentoPagoId) {
        return this.documentoBo.obtenerPorId(documentoPagoId);
    }
    
    @WebMethod(operationName = "listar_todos")
    public ArrayList<DocumentoPagoDto> listarTodos() {
        return this.documentoBo.listarTodos();
    }
    
}
