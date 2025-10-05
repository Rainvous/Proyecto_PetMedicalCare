/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;

/**
 *
 * @author marti
 */
public interface DetalleDocumentoDePagoDao {
     public Integer insertar(DetalleDocumentoPagoDto detalleDocumento);

    public DetalleDocumentoPagoDto obtenerPorId(Integer detalleDocumentoId);

    public ArrayList<DetalleDocumentoPagoDto> listarTodos();

    public Integer modificar(DetalleDocumentoPagoDto detalleDocumento);

    public Integer eliminar(DetalleDocumentoPagoDto detalleDocumento);
}
