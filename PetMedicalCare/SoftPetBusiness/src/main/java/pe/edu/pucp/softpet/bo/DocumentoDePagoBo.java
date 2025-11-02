package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import java.sql.Date;
import pe.edu.pucp.softpet.dao.DocumentoDePagoDao;
import pe.edu.pucp.softpet.daoImp.DocumentoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.MetodoDePagoDaoImpl;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.daoImp.util.enums.EstadoDocumentoDePago;
import pe.edu.pucp.softpet.daoImp.util.enums.TipoDocumentoDePago;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;

public class DocumentoDePagoBo {

    private final DocumentoDePagoDao documentoDePagoDao;

    public DocumentoDePagoBo() {
        this.documentoDePagoDao = new DocumentoDePagoDaoImpl();

    }

    public Integer insertar(int metodoDePagoId, int personaId,
            TipoDocumentoDePago tipoDocumento, String serie, String numero,
            Date fechaEmision, EstadoDocumentoDePago estado, double subtotal,
            double igvTotal, double total, boolean activo) {

        DocumentoPagoDto documentoPago = new DocumentoPagoDto();

        documentoPago.setMetodoDePago(new MetodoDePagoDaoImpl().obtenerPorId(metodoDePagoId));
        documentoPago.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        documentoPago.setTipoDocumento(tipoDocumento.toString());
        documentoPago.setSerie(serie);
        documentoPago.setNumero(numero);
        documentoPago.setFechaEmision(fechaEmision);
        documentoPago.setEstado(estado.toString());
        documentoPago.setSubtotal(subtotal);
        documentoPago.setIGVTotal(igvTotal);
        documentoPago.setTotal(total);
        documentoPago.setActivo(activo);

        return this.documentoDePagoDao.insertar(documentoPago);
    }

    public Integer modificar(int documentoPagoId, int metodoDePagoId, int personaId,
            TipoDocumentoDePago tipoDocumento, String serie, String numero,
            Date fechaEmision, EstadoDocumentoDePago estado, double subtotal,
            double igvTotal, double total, boolean activo) {

        DocumentoPagoDto documentoPago = new DocumentoPagoDto();

        documentoPago.setDocumentoPagoId(documentoPagoId);
        documentoPago.setMetodoDePago(new MetodoDePagoDaoImpl().obtenerPorId(metodoDePagoId));
        documentoPago.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        documentoPago.setTipoDocumento(tipoDocumento.toString());
        documentoPago.setSerie(serie);
        documentoPago.setNumero(numero);
        documentoPago.setFechaEmision(fechaEmision);
        documentoPago.setEstado(estado.toString());
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
