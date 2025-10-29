package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import java.util.Date;
import pe.edu.pucp.softpet.dao.DocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.DocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.daoImp.util.enums.EstadoDocumentoDePago;
import pe.edu.pucp.softpet.daoImp.util.enums.TipoDocumentoDePago;

import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;

public class DocumentoDePagoBo {

    private DocumentoDePagoDao documentoDePagoDao;

    public DocumentoDePagoBo() {
        this.documentoDePagoDao = new DocumentoDePagoDaoImpl();

    }

    // Inserta un documento de pago
    public Integer insertar(String serie, String numero, double tasaIGV, Date fechaEmision, String metodoPago,
            EstadoDocumentoDePago estado, double subtotal, double igvTotal,  TipoDocumentoDePago tipoDocumento,
            double total, boolean activo, int MetodoDePagoId, int personaId) {
        DocumentoPagoDto documentoPago = new DocumentoPagoDto();
        
        // Normalizamos textos
        documentoPago.setSerie(serie.trim().toUpperCase());
        documentoPago.setNumero(numero.trim());

        documentoPago.setFechaEmision((java.sql.Date) fechaEmision);
        MetodoDePagoDto me = new MetodoDePagoDto();
        me.setMetodoDePagoId(MetodoDePagoId);
        documentoPago.setMetodoDePago(me);
        documentoPago.setEstado(estado.toString());
        documentoPago.setSubtotal(subtotal);
        documentoPago.setIGVTotal(igvTotal);
        documentoPago.setTotal(total);
        documentoPago.setActivo(activo);

        // Asignamos relaciones
        documentoPago.setTipoDocumento(tipoDocumento.toString());
        documentoPago.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));

        return this.documentoDePagoDao.insertar(documentoPago);
    }

    // Modifica un documento existente
    public Integer modificar(int documentoPagoId, String serie, String numero, double tasaIGV, Date fechaEmision, String metodoPago,
            EstadoDocumentoDePago estado, double subtotal, double igvTotal,  TipoDocumentoDePago tipoDocumento,
            double total, boolean activo, int MetodoDePagoId, int personaId) {

        java.sql.Date fechaEmisionSQL = new java.sql.Date(fechaEmision.getTime());

        DocumentoPagoDto documentoPago = new DocumentoPagoDto();

        documentoPago.setDocumentoPagoId(documentoPagoId);
        documentoPago.setSerie(serie.trim().toUpperCase());
        documentoPago.setNumero(numero.trim());

        documentoPago.setFechaEmision(fechaEmisionSQL);
        documentoPago.setFechaEmision((java.sql.Date) fechaEmision);
        MetodoDePagoDto me = new MetodoDePagoDto();
        me.setMetodoDePagoId(MetodoDePagoId);
        documentoPago.setEstado(estado.toString());
        documentoPago.setSubtotal(subtotal);
        documentoPago.setIGVTotal(igvTotal);
        documentoPago.setTotal(total);
        documentoPago.setActivo(activo);

        documentoPago.setTipoDocumento(tipoDocumento.toString());
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
