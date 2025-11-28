/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softpetws.correo;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import pe.edu.pucp.softpet.bo.utils.GmailService;
import pe.edu.pucp.softpetws.reportes.ReportesUtil;

/**
 *
 * @author aml
 */
@WebService(serviceName = "Correos")
public class Correo {

    /**
     * This is a sample web service operation
     */
    private GmailService servicioGmail;

    public Correo() {
        this.servicioGmail = new GmailService();
    }

    @WebMethod(operationName = "enviar_correo")
    public String enviarCorreo_sinArchivos(String destinatario) { // <-- OJO: String
        return this.servicioGmail.enviarCorreo_VerficarCuenta(destinatario);
    }
    @WebMethod(operationName = "enviar_credenciales")
    public String enviarCredenciales(@WebParam(name = "destinatario") String destinatario, 
                                     @WebParam(name = "password") String password){
        return this.servicioGmail.enviarCorreo_Credenciales(destinatario, password);
    }
    @WebMethod(operationName = "enviar_comprobante")
    public String enviarComprobante(@WebParam(name = "destinatario") String destinatario,
           @WebParam(name = "tipodocumento") String TipoDocumento,@WebParam(name = "idcomprobante") int idcomprobante) {
        
        // MODO PRUEBA: Pasamos 'null' como pediste para probar solo el diseño HTML
         
        
        // Si ya tuvieras el PDF, aquí recibirías los bytes en lugar de null.
        byte[] pdfEnviado=ReportesUtil.reporteComprobanteDePago(TipoDocumento, idcomprobante);
        return this.servicioGmail.enviarCorreo_ComprobantePago(destinatario, pdfEnviado);
    }
}
