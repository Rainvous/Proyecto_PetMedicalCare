package pe.edu.pucp.softpet.softpetwsfacturacion.MetodoDePago;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.MetodoDePagoBo;
import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;

@WebService(serviceName = "MetodoDePago")
public class MetodoDePago {

    private final MetodoDePagoBo metodoDePagoBo;
    
    public MetodoDePago(){
        this.metodoDePagoBo = new MetodoDePagoBo();
    }
    
    @WebMethod(operationName = "insertar_MetodoDePago")
    public Integer insertar(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "activo") boolean activo)
    {
        return this.metodoDePagoBo.insertar(nombre, activo);
    }
    
    @WebMethod(operationName = "modificar_MetodoDePago")
    public Integer modificar(
            @WebParam(name = "metodoDePagoId") int metodoDePagoId,
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "activo") boolean activo)
    {
        return this.metodoDePagoBo.modificar(metodoDePagoId, nombre, activo);
    }
    
    @WebMethod(operationName = "eliminar_modificar_MetodoDePago")
    public Integer eliminar(int metodoDePagoId) {
        return this.metodoDePagoBo.eliminar(metodoDePagoId);
    }
    
    @WebMethod(operationName = "obtener_por_id")
    public MetodoDePagoDto obtenerPorId(@WebParam(name = "id") int metodoDePagoId) {
        return this.metodoDePagoBo.obtenerPorId(metodoDePagoId);
    }
    
    @WebMethod(operationName = "listar_todos")
    public ArrayList<MetodoDePagoDto> listarTodos() {
        return this.metodoDePagoBo.listarTodos();
    }
}
