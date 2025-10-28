/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.facturacion.DetalleDocumentoPagoDto;
import pe.edu.pucp.softpet.dto.facturacion.DocumentoPagoDto;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

/**
 *
 * @author User
 */
public class DetalleDocumentoDePagoDaoImplTest {
    
    public DetalleDocumentoDePagoDaoImplTest() {
    }

    @Test
    public void testSomeMethod() {
        System.out.println("DETALLE DOCUMENTO DE PAGO INGO");
        DocumentoPagoDto aux= new DocumentoPagoDto();
        aux.setDocumentoPagoId(1);
        ServicioDto ser= new ServicioDto();
        ser.setServicioId(1);
        ProductoDto pro= new ProductoDto();
        pro.setProductoId(2);
        //1, 1, "holamundo", 23, 45.2, 200, 30, 21, aux, ser, pro)
//        DetalleDocumentoPagoDto a= new DetalleDocumentoPagoDto();
//        a.setActivo(true);
//        a.setCantidad(12);
//        a.setDescripcion("Nueva des");
//        a.setDocumentoPago(aux);
//        a.setImporteTotal(12.2);
//        a.setNroItem(2);
//        a.setPrecioUnitario(10.1);
//        a.setProducto(pro);
//        a.setServicio(ser);
//        a.setValorVenta(30.2);
//        
       DetalleDocumentoDePagoDaoImpl dao= new DetalleDocumentoDePagoDaoImpl();
//        int result= dao.insertar(a);
      //  assertTrue(result>0);
        for (DetalleDocumentoPagoDto det : dao.listarTodos()){
            System.out.println("->"+det.getDescripcion()+"  - "+det.getDddpId());
        }
        
    }
    
}
