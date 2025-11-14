/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

/**
 *
 * @author User
 */
public class ProductoDaoImplTest {

    public ProductoDaoImplTest() {
    }

    public void ImprimeCabecera(String titulo) {
        System.out.println();
        System.out.println("=======================================================");
        System.out.println(" TEST: " + titulo.toUpperCase());
        System.out.println("=======================================================");
    }

    /**
     * Test of listarTodos method, of class ProductoDaoImpl.
     */
    @Test
    public void TesteoCrud() {
        ImprimeCabecera("Testeo de producto");
        System.out.println("Insertar");
        ProductoDto prod = new ProductoDto();
        prod.setNombre("BACK1");
        prod.setPresentacion("JAVA2");
        prod.setPrecioUnitario(34.5);
        TipoProductoDto tipoprod = new TipoProductoDto();
        tipoprod.setTipoProductoId(1);
        prod.setTipoProducto(tipoprod);
        ProductoDaoImpl prodDao = new ProductoDaoImpl();
        prod.setActivo(true);
        Integer resultado = prodDao.insertar(prod);

        assertTrue(resultado > 0);
        System.out.println("OBETNER ID");

        ProductoDto ProductoObtenido = prodDao.obtenerPorId(2);
        System.out.println("producto ->" + ProductoObtenido.getNombre());
        assertNotNull(ProductoObtenido);

        ArrayList<ProductoDto> listarProducto = prodDao.listarTodos();
        System.out.println("cant" + listarProducto.size());
        assertTrue(listarProducto.isEmpty() == false);

        System.out.println("modifica");
        System.out.println("ID INSERTADO ANTES->" + resultado);
        prod.setProductoId(resultado);
        prod.setNombre("Modificacion Prueba");
        prod.setPresentacion("Back_javav2");
        Integer result2 = prodDao.modificar(prod);
        assertTrue(result2 > 0);

        System.out.println("elimina");
        int inidice = 4;
        System.out.println("inidce a eliminar-> " + inidice);
        ProductoObtenido = new ProductoDto();
        ProductoObtenido.setProductoId(resultado);
        result2 = 0;
        result2 = prodDao.eliminar(ProductoObtenido);
        assertTrue(result2 > 0);

    }

    @Test
    public void TesteoCrud2() throws SQLException {
         ImprimeCabecera("Testeo de Servicio");
         System.out.println("Insertar".toUpperCase());
         ServicioDaoImpl serviDAO= new ServicioDaoImpl();
         ServicioDto servicio= new ServicioDto();
         servicio.setActivo(true);
         servicio.setCosto(1000.2);
         servicio.setDescripcion("DESPARACITACION perro by java");
         servicio.setEstado("SIN USAR by java");
         servicio.setNombre("DESPARACITACION");
         TipoServicioDto tipo= new TipoServicioDto();
         tipo.setTipoServicioId(2);
         servicio.setTipoServicio(tipo);
         
         Integer result= serviDAO.insertar(servicio);
         System.out.println("resultado fue-> "+result);
         assertTrue(result>0);
         int indice=1;
         ServicioDto DATO_Obtenido=null;
         DATO_Obtenido=serviDAO.obtenerPorId(indice);
         assertNotNull(DATO_Obtenido);
         
//         
         serviDAO.NombreDelUsuarioQueModifica("PEPITO");
          System.out.println("modifica");
        System.out.println("ID INSERTADO ANTES->" + result);
        servicio.setServicioId(result);
        servicio.setNombre("Modificacion Prueba");
        
        
        
        
        Integer result2 = serviDAO.modificar(servicio);
        assertTrue(result2 > 0);
         System.out.println("obtener por id");
        servicio=null;
        servicio= serviDAO.obtenerPorId(2);
        System.err.println("EL SERVICIO ES: "+servicio.getNombre()+" FK"+servicio.getServicioId());
        assertNotNull(servicio);
                 
    }

}
