/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import pe.edu.pucp.softpet.model.facturaciondto.TipoComprobantesDTO;


/**
 *
 * @author User
 */
public interface TipoDeComprobanteDAO {
    public Integer insertar(TipoComprobantesDTO tipo_comprobante);

    public TipoComprobantesDTO obtenerPorId(Integer tipo_comprobante_id);

    public ArrayList<TipoComprobantesDTO> listarTodos();

    public Integer modificar(TipoComprobantesDTO tipo_comprobante);

    public Integer eliminar(TipoComprobantesDTO tipo_comprobante);
    
    
}
