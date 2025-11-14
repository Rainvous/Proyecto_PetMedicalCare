package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.DocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.DocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.MetodoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoDocumentoDePago;
import pe.edu.pucp.softpet.dto.util.enums.TipoDocumentoDePago;

public class DocumentoDePagoBo {

    private final DocumentoDePagoDao documentoDePagoDao;

    public DocumentoDePagoBo() {
        this.documentoDePagoDao = new DocumentoDePagoDaoImpl();

    }

        public Integer insertar(int metodoDePagoId, int personaId,
            String tipoDocumento, String serie, String numero,
            String fechaEmision, String estado, double subtotal,
            double igvTotal, double total, boolean activo) {

        DocumentoPagoDto documentoPago = new DocumentoPagoDto();
        MetodoDePagoDto metodoPago= new MetodoDePagoDto();
        //Modificado para el SOAP
        EstadoDocumentoDePago estadoEnum = EstadoDocumentoDePago.valueOf(estado.toUpperCase());
        TipoDocumentoDePago tipoDocumentoEnum = TipoDocumentoDePago.valueOf(tipoDocumento.toUpperCase());
        java.sql.Date fechaEmisionDate = java.sql.Date.valueOf(fechaEmision);
        
        metodoPago.setMetodoDePago(metodoDePagoId);
        documentoPago.setMetodoDePago(metodoPago);
        
        documentoPago.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        documentoPago.setTipoDocumento(tipoDocumentoEnum);
        documentoPago.setSerie(serie);
        documentoPago.setNumero(numero);
        documentoPago.setFechaEmision(fechaEmisionDate);
        documentoPago.setEstado(estadoEnum);
        documentoPago.setSubtotal(subtotal);
        documentoPago.setIGVTotal(igvTotal);
        documentoPago.setTotal(total);
        documentoPago.setActivo(activo);

        return this.documentoDePagoDao.insertar(documentoPago);
    }

    public Integer modificar(int documentoPagoId, int metodoDePagoId, int personaId,
            String tipoDocumento, String serie, String numero,
            String fechaEmision, String estado, double subtotal,
            double igvTotal, double total, boolean activo) {

        DocumentoPagoDto documentoPago = new DocumentoPagoDto();
        
        //Modificado para el SOAP
        EstadoDocumentoDePago estadoEnum = EstadoDocumentoDePago.valueOf(estado.toUpperCase());
        TipoDocumentoDePago tipoDocumentoEnum = TipoDocumentoDePago.valueOf(tipoDocumento.toUpperCase());
        java.sql.Date fechaEmisionDate = java.sql.Date.valueOf(fechaEmision);

        documentoPago.setDocumentoPagoId(documentoPagoId);
        documentoPago.setMetodoDePago(new MetodoDePagoDaoImpl().obtenerPorId(metodoDePagoId));
        documentoPago.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        documentoPago.setTipoDocumento(tipoDocumentoEnum);
        documentoPago.setSerie(serie);
        documentoPago.setNumero(numero);
        documentoPago.setFechaEmision(fechaEmisionDate);
        documentoPago.setEstado(estadoEnum);
        documentoPago.setSubtotal(subtotal);
        documentoPago.setIGVTotal(igvTotal);
        documentoPago.setTotal(total);
        documentoPago.setActivo(activo);

        return this.documentoDePagoDao.modificar(documentoPago);
    }

    public Integer eliminar(int documentoPagoId) {
        DocumentoPagoDto documentoPago = new DocumentoPagoDto();
        documentoPago.setDocumentoPagoId(documentoPagoId);
        return this.documentoDePagoDao.eliminar(documentoPago);
    }

    public DocumentoPagoDto obtenerPorId(int documentoPagoId) {
        return this.documentoDePagoDao.obtenerPorId(documentoPagoId);
    }

    public ArrayList<DocumentoPagoDto> listarTodos() {
        return this.documentoDePagoDao.listarTodos();
    }
}
