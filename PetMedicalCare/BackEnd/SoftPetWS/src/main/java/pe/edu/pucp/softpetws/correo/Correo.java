/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package pe.edu.pucp.softpetws.correo;

import pe.edu.pucp.softpetws.correo.GmailService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

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
    public Correo(){
        this.servicioGmail=new GmailService();
        
    }
    @WebMethod(operationName = "enviar_correo")
    public int enviarCorreo_sinArchivos(String destinatariao){
        return this.servicioGmail.enviarCorreo_VerficarCuenta(destinatario);
    }
}
