/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpetws.reportes;

import java.sql.Connection;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import pe.edu.pucp.softpet.bo.DocumentoDePagoBo;
import pe.edu.pucp.softpet.db.DBManager;

/**
 *
 * @author User
 */
public class ReportesUtil {

    DocumentoDePagoAnalizer analizarDocPago;
    DocumentoDePagoBo doc;

    public ReportesUtil() {
        analizarDocPago = new DocumentoDePagoAnalizer();
        doc = new DocumentoDePagoBo();
    }

    private static byte[] invocarReporte(String nombreReporte, HashMap parametros) {
        byte[] reporte = null;
        //Como ya esta seteado el motor que se esta usando llamo normal

        Connection conexion = DBManager.getInstance().getConnection();
        String nmReporte = "/" + nombreReporte + ".jasper";
        try {
            JasperReport jr = (JasperReport) JRLoader.loadObject(ReportesUtil.class.getResource(nmReporte));
            JasperPrint jp = JasperFillManager.fillReport(jr, parametros, conexion);
            reporte = JasperExportManager.exportReportToPdf(jp);
        } catch (JRException ex) {
            Logger.getLogger(ReportesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reporte;
    }

    public static byte[] reporteComprobanteDePago(String tipoDocumento, int iddocumento) {
        HashMap parametros = new HashMap();
        String motor = DBManager.devolverMotor();
        parametros.put("P_DOCUMENTO_ID", iddocumento);
        if (motor.equals("MSSQL")) {
            if ("BOLETA".equals(tipoDocumento) || "Boleta".equals(tipoDocumento) || "boleta".equals(tipoDocumento)) {
                return invocarReporte("boleta_mssql", parametros);
            } else if ("FACTURA".equals(tipoDocumento) || "Factura".equals(tipoDocumento) || "factura".equals(tipoDocumento)) {
                return invocarReporte("factura_mssql", parametros);
            }

        } else {
            if ("BOLETA".equals(tipoDocumento) || "Boleta".equals(tipoDocumento) || "boleta".equals(tipoDocumento)) {
                return invocarReporte("boleta", parametros);
            } else if ("FACTURA".equals(tipoDocumento) || "Factura".equals(tipoDocumento) || "factura".equals(tipoDocumento)) {
                return invocarReporte("factura", parametros);
            }

        }

        return null;
    }

}
