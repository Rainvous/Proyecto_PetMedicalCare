package pe.edu.pucp.bo;

import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.softpet.dao.DocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.DocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.daoImp.TipoDocumentoDaoImpl;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author marti
 */
public class DocumentoDePagoBo {
        private DocumentoDePagoDao documentoDePagoDao;

    public DocumentoDePagoBo() {
        this.documentoDePagoDao = new DocumentoDePagoDaoImpl();
    }

    // Inserta un documento de pago
    public Integer insertar(String serie,String numero, double tasaIGV, Date fechaEmision, String metodoPago,
                            String estado, double subtotalSinIGV, double igvTotal,
                            double total, boolean activo, int tipoDocumentoId, int personaId) {
        DocumentoPagoDto documentoPago = new DocumentoPagoDto();

        // Normalizamos textos
        documentoPago.setSerie(serie.trim().toUpperCase());
        documentoPago.setNumero(numero.trim());
        documentoPago.setTasaIGV(tasaIGV);
        documentoPago.setFechaEmision((java.sql.Date) fechaEmision);
        documentoPago.setMetodoPago(metodoPago.trim().toUpperCase());
        documentoPago.setEstado(estado.trim().toUpperCase());
        documentoPago.setSubtotalSinIGV(subtotalSinIGV);
        documentoPago.setIGVTotal(igvTotal);
        documentoPago.setTotal(total);
        documentoPago.setActivo(activo);

        // Asignamos relaciones
        documentoPago.setTipoDocumento(new TipoDocumentoDaoImpl().obtenerPorId(tipoDocumentoId));
        documentoPago.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));

        return this.documentoDePagoDao.insertar(documentoPago);
    }

    // Modifica un documento existente
    public Integer modificar(int documentoPagoId, String serie,String numero, double tasaIGV, Date fechaEmision, String metodoPago,
                              String estado, double subtotalSinIGV, double igvTotal,
                              double total, boolean activo, int tipoDocumentoId, int personaId) {
        
        java.sql.Date fechaEmisionSQL = new java.sql.Date(fechaEmision.getTime());
        
        DocumentoPagoDto documentoPago = new DocumentoPagoDto();

        documentoPago.setDocumentoPagoId(documentoPagoId);
        documentoPago.setSerie(serie.trim().toUpperCase());
        documentoPago.setNumero(numero.trim());
        documentoPago.setTasaIGV(tasaIGV);
        documentoPago.setFechaEmision(fechaEmisionSQL);
        documentoPago.setMetodoPago(metodoPago.trim().toUpperCase());
        documentoPago.setEstado(estado.trim().toUpperCase());
        documentoPago.setSubtotalSinIGV(subtotalSinIGV);
        documentoPago.setIGVTotal(igvTotal);
        documentoPago.setTotal(total);
        documentoPago.setActivo(activo);

        documentoPago.setTipoDocumento(new TipoDocumentoDaoImpl().obtenerPorId(tipoDocumentoId));
        documentoPago.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));

        return this.documentoDePagoDao.modificar(documentoPago);
    }

    // Elimina un documento por ID
    public Integer eliminar(int documentoPagoId) {
        DocumentoPagoDto documentoPago = new DocumentoPagoDto();
        documentoPago.setDocumentoPagoId(documentoPagoId);
        return this.documentoDePagoDao.eliminar(documentoPago);
    }

    // Obtiene un documento por su ID
    public DocumentoPagoDto obtenerPorId(int documentoPagoId) {
        return this.documentoDePagoDao.obtenerPorId(documentoPagoId);
    }

    // Lista todos los documentos registrados
    public ArrayList<DocumentoPagoDto> listarTodos() {
        return this.documentoDePagoDao.listarTodos();
    }
}
