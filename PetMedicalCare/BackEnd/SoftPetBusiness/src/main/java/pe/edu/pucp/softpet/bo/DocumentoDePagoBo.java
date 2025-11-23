package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
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
    
    public ArrayList<DocumentoPagoDto>listarPorFechaEmision(String fecha){
//          if (!esFechaValida(fecha)) {
//        throw new IllegalArgumentException(
//            "La fecha debe tener el formato yyyy-MM-dd. Ejemplo: 2025-11-20"
//        );
   // }
        return this.documentoDePagoDao.listarComprobantesPorFecha(fecha);
    }
    
    public ArrayList<DocumentoPagoDto> listarTodos() {
        return this.documentoDePagoDao.listarTodos();
    }
    private boolean esFechaValida(String fecha) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                                                   .withResolverStyle(ResolverStyle.STRICT);
    try {
        LocalDate.parse(fecha, formatter);
        return true;
    } catch (Exception e) {
        return false;
    }
}

    

    public ArrayList<String> GeneracionDeSiguienteBoletaOFactura(String tipoDocumento) {
        return this.documentoDePagoDao.GeneracionDeSiguienteBoletaOFactura(tipoDocumento.toUpperCase());
    }
}

