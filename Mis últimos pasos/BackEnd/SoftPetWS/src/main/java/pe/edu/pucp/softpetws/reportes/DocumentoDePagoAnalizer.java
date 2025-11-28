/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpetws.reportes;

import pe.edu.pucp.softpet.bo.DocumentoDePagoBo;

/**
 *
 * @author User
 */
public class DocumentoDePagoAnalizer extends DocumentoDePagoBo {
    
    public String VerificarDocumentoDePago(){
        return this.RetornarMotorBaseDeDatos();
    }
    
}
