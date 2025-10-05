/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

/**
 *
 * @author User
 */
public class ProductoDaoImplTest {
    
    public ProductoDaoImplTest() {
    }



    /**
     * Test of listarTodos method, of class ProductoDaoImpl.
     */
    @Test
    public void TesteoCrud(){
         System.out.println("Insertar");
         ProductoDto prod= new ProductoDto();
         prod.setNombre("BACK1");
         prod.setPresentacion("JAVA2");
         prod.setPrecioUnitario(34.5);
         TipoProductoDto tipoprod= new TipoProductoDto();
         tipoprod.setTipoProductoId(1);
         prod.setTipoProducto(tipoprod);
         ProductoDaoImpl prodDao= new ProductoDaoImpl();
         prod.setActivo(true);
         Integer resultado=prodDao.insertar(prod);
         
        assertTrue(resultado>0);
        System.out.println("OBETNER ID");
        
        
        ProductoDto ProductoObtenido=prodDao.obtenerPorId(2);
        System.out.println("producto ->"+ProductoObtenido.getNombre());
        assertNotNull(ProductoObtenido);
        
        ArrayList<ProductoDto>listarProducto=prodDao.listarTodos();
        System.out.println("cant"+listarProducto.size());
        assertTrue(listarProducto.isEmpty()==false);
        
        System.out.println("modifica");
        System.out.println("ID INSERTADO ANTES->"+resultado);
        prod.setProductoId(resultado);
        prod.setNombre("Modificacion Prueba");
        prod.setPresentacion("Back_javav2");
        Integer result2= prodDao.modificar(prod);
        assertTrue(result2>0);
        
        System.out.println("elimina");
        int inidice=4;
        System.out.println("inidce a mod-> "+inidice );
        ProductoObtenido= new ProductoDto();
        ProductoObtenido.setProductoId(inidice);
        result2=0;
        result2=prodDao.eliminar(ProductoObtenido);
        assertTrue(result2>0);
        
        
    }

}
