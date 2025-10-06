/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;

/**
 *
 * @author marti
 */
public interface DocumentoDePagoDao {
    public Integer insertar(DocumentoPagoDto documentoPago);

    public DocumentoPagoDto obtenerPorId(Integer documentoPagoId);

    public ArrayList<DocumentoPagoDto> listarTodos();

    public Integer modificar(DocumentoPagoDto documentoPago);

    public Integer eliminar(DocumentoPagoDto documentoPago);
}
