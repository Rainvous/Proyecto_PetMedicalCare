package pe.edu.pucp.softpetws.docpago;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.DocumentoDePagoBo;
import pe.edu.pucp.softpet.bo.utils.GmailService;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
import pe.edu.pucp.softpetws.reportes.ReportesUtil;

@WebService(serviceName = "DocumentosDePago")
public class DocumentosDePago {

    private final DocumentoDePagoBo documentoBo;
    private final GmailService gmailservice;

    public DocumentosDePago() {
        this.documentoBo = new DocumentoDePagoBo();
        this.gmailservice= new GmailService();
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

        return this.documentoBo.modificar(documentoPagoId, metodoDePagoId, personaId, tipoDocumento,
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

    @WebMethod(operationName = "GeneracionDeSiguienteBoletaOFactura")
    public ArrayList<String> GeneracionDeSiguienteBoletaOFactura(
            @WebParam(name = "tipoDocumento") String tipoDocumento
    ) {
        return this.documentoBo.GeneracionDeSiguienteBoletaOFactura(tipoDocumento);
    }

    @WebMethod(operationName = "listar_por_fecha")
    public ArrayList<DocumentoPagoDto> listarporcita(@WebParam(name = "fecha") String fecha) {
        return this.documentoBo.listarPorFechaEmision(fecha);
    }

    @WebMethod(operationName = "generar_comprobante_de_pago_pdf")
    public byte[] retornarComprobanteDePago(@WebParam(name = "tipoDocumento") String TipoDocumento,
            @WebParam(name = "idcomprobante") int idcomprobante) {

        return ReportesUtil.reporteComprobanteDePago(TipoDocumento, idcomprobante);
    }
    @WebMethod(operationName = "enviar_comprobante_al_correo")
    public String enviarComprobanteAlCorreo(@WebParam(name = "correo")String correo, @WebParam(name = "tipoDocumento") String TipoDocumento,
            @WebParam(name = "idcomprobante") int idcomprobante) {
        byte[] archivo= ReportesUtil.reporteComprobanteDePago(TipoDocumento, idcomprobante);
        return gmailservice.enviarCorreo_ComprobantePago(correo, archivo);
    }

}
