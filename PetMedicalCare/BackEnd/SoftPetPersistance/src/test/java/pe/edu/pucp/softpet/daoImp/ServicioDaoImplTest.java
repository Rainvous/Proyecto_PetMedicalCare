package pe.edu.pucp.softpet.daoImp;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;

public class ServicioDaoImplTest {
    
    public ServicioDaoImplTest(){
        
    }
    
    @Test
    public void testListasBusquedaAvanzada() {
        ServicioDaoImpl servicioDao = new ServicioDaoImpl();
        ServicioDto servicio= new ServicioDto();
        servicio.setNombre("");
        String rango = "";
        String activo= "";
         
        ArrayList<ServicioDto> lista= servicioDao.ListasBusquedaAvanzada2(servicio,rango,activo);
        System.out.println("BUSQUEDA DE PERSONAS AVANZADO_2");
        for( ServicioDto p : lista){
            System.out.println("VERIFICAMOS LOS DATOS");
            System.out.println("->"+p.getNombre());
            System.out.println("->"+p.getDescripcion());
            System.out.println("->"+p.getEstado());
            System.out.println("VERIFICAMOS LOS DATOS INTERNOS DENTRO DEL OBJETO DEL TIPO_SERVICIO");
            System.out.println("->"+p.getTipoServicio().getNombre());
            System.out.println("->"+p.getTipoServicio().getDescripcion());
            System.out.println("->"+p.getTipoServicio().getActivo());
        }
        
    }
    
//    @Test
//    public void testListarServiciosActivos() {
//        System.out.println("--- Prueba: Listar Servicios Activos ---");
//        
//        // 1. Instanciamos el DAO
//        ServicioDaoImpl servicioDao = new ServicioDaoImpl();
//        
//        // 2. Establecemos el motor de BD (siguiendo tu patrón)
//        servicioDao.EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos.MYSQL.toString());
//        
//        // 3. Llamamos al método
//        ArrayList<ServicioDto> serviciosActivos = servicioDao.listarServiciosActivos();
//        
//        // --- ASERCIONES (Assertions) ---
//        
//        // 4. Verificamos que la lista no sea nula
//        assertNotNull(serviciosActivos, "La lista devuelta no debe ser nula.");
//        
//        System.out.println("Se encontraron " + serviciosActivos.size() + " servicios activos.");
//        
//        // 5. Iteramos y verificamos que CADA servicio esté activo
//        for (ServicioDto s : serviciosActivos) {
//            System.out.println(
//                "  -> ID: " + s.getServicioId() + //
//                ", Nombre: " + s.getNombre() + //
//                ", Activo: " + s.getActivo() //
//            );
//        }
//        
//        if (serviciosActivos.isEmpty()) {
//            System.out.println("INFO: No se encontraron servicios activos (la lista está vacía).");
//        }
//    }
}
