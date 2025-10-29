package pe.edu.pucp.softpet.services.softpetws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.ServicioBo;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

@WebService(serviceName = "Servicios")
public class Servicios {

    private final ServicioBo servicioBo;
    
    public Servicios(){
        this.servicioBo = new ServicioBo();
    }
    
    @WebMethod(operationName = "insertar_servicios")
    public Integer insertar(@WebParam(name = "nombre") String nombre, 
            @WebParam(name = "costo") double costo, @WebParam(name = "estado") String estado,
            @WebParam(name = "descripcion") String descripcion, 
            @WebParam(name = "activo") boolean activo, @WebParam(name = "tipoServicioId") int tipoServicioId){
        return this.servicioBo.insertar(nombre, costo, estado, descripcion, activo, tipoServicioId);
    }
    
    @WebMethod(operationName = "modificar_servicios")
    public Integer modificar(@WebParam(name = "servicioId") int servicioId, 
            @WebParam(name = "nombre") String nombre, @WebParam(name = "costo") double costo, 
            @WebParam(name = "estado") String estado, @WebParam(name = "descripcion") String descripcion, 
            @WebParam(name = "activo") boolean activo, @WebParam(name = "tipoServicioId") int tipoServicioId){
        return this.servicioBo.modificar(servicioId, nombre, costo, estado, descripcion, activo, tipoServicioId);
    }
    
    @WebMethod(operationName = "eliminar_servicios")
    public Integer eliminar(int servicioId){
        return this.servicioBo.eliminar(servicioId);
    }
    
    @WebMethod(operationName = "obtener_por_id")
    // Obtiene un servicio por su ID
    public ServicioDto obtenerPorId(@WebParam(name = "id") int servicioId) {
        return this.servicioBo.obtenerPorId(servicioId);
    }

    @WebMethod(operationName = "listar_todos")
    // Lista todos los servicios registrados
    public ArrayList<ServicioDto> listarTodos() {
        return this.servicioBo.listarTodos();
    }
}
