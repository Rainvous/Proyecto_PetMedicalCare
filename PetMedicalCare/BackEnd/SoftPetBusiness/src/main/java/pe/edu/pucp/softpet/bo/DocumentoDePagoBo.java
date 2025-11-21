package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.DocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.DocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoDocumentoDePago;
import pe.edu.pucp.softpet.dto.util.enums.TipoDocumentoDePago;

public class DocumentoDePagoBo {

    private final DocumentoDePagoDaoImpl documentoDePagoDao;

    public DocumentoDePagoBo() {
        this.documentoDePagoDao = new DocumentoDePagoDaoImpl();

    }

    public Integer insertar(int metodoDePagoId, int personaId,
            String tipoDocumento, String serie, String numero,
            String fechaEmision, String estado, double subtotal,
            double igvTotal, double total, boolean activo) {

        DocumentoPagoDto documentoPago = new DocumentoPagoDto();
        
        MetodoDePagoDto metodoPago = new MetodoDePagoDto();
        PersonaDto persona = new PersonaDto();
        
        EstadoDocumentoDePago estadoEnum = EstadoDocumentoDePago.valueOf(estado.toUpperCase());
        TipoDocumentoDePago tipoDocumentoEnum = TipoDocumentoDePago.valueOf(tipoDocumento.toUpperCase());
        Date fechaEmisionDate = Date.valueOf(fechaEmision);

        metodoPago.setMetodoDePagoId(metodoDePagoId);
        persona.setPersonaId(personaId);
        
        documentoPago.setMetodoDePago(metodoPago);
        documentoPago.setPersona(persona);
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
        
        MetodoDePagoDto metodoPago = new MetodoDePagoDto();
        PersonaDto persona = new PersonaDto();
        
        EstadoDocumentoDePago estadoEnum = EstadoDocumentoDePago.valueOf(estado.toUpperCase());
        TipoDocumentoDePago tipoDocumentoEnum = TipoDocumentoDePago.valueOf(tipoDocumento.toUpperCase());
        Date fechaEmisionDate = Date.valueOf(fechaEmision);

        metodoPago.setMetodoDePagoId(metodoDePagoId);
        persona.setPersonaId(personaId);
        
        documentoPago.setDocumentoPagoId(documentoPagoId);
        documentoPago.setMetodoDePago(metodoPago);
        documentoPago.setPersona(persona);
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
    
    public ArrayList<String> GeneracionDeSiguienteBoletaOFactura(String tipoDocumento) {
        return this.documentoDePagoDao.GeneracionDeSiguienteBoletaOFactura(tipoDocumento.toUpperCase());
    }
}
