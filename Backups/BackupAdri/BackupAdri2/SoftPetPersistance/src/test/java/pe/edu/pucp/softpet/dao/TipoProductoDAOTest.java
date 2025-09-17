/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.daoImpl.TipoProductoDAOImpl;
import pe.edu.pucp.softpet.model.productosDTO.TipoProductosDTO;

/**
 *
 * @author User
 */
public class TipoProductoDAOTest {
    
    private TipoProductoDAO tipo_producto;
    
    public TipoProductoDAOTest() {
        this.tipo_producto = new TipoProductoDAOImpl();
    }

    @Test
    public void testInsertar() {
        System.out.println("TipoProducto - insertar");
        TipoProductosDTO t = nuevo("Antiparasitarios " + suf());
        Integer filas = tipo_producto.insertar(t);
        assertNotEquals(0, filas);
        assertNotNull(t.getTipo_producto_id());

        TipoProductosDTO fromDb = tipo_producto.obtenerPorId(t.getTipo_producto_id());
        assertNotNull(fromDb);
        assertEquals(t.getNombre(), fromDb.getNombre());
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("TipoProducto - obtenerPorId");
        TipoProductosDTO t = nuevo("Higiene " + suf());
        tipo_producto.insertar(t);

        TipoProductosDTO fromDb = tipo_producto.obtenerPorId(t.getTipo_producto_id());
        assertNotNull(fromDb);
        assertEquals(t.getTipo_producto_id(), fromDb.getTipo_producto_id());
    }

    @Test
    public void testListarTodos() {
        System.out.println("TipoProducto - listarTodos");
        tipo_producto.insertar(nuevo("Alimentos " + suf()));
        tipo_producto.insertar(nuevo("Accesorios " + suf()));

        ArrayList<TipoProductosDTO> lista = tipo_producto.listarTodos();
        assertNotNull(lista);
        assertTrue(lista.size() >= 2);
    }

    @Test
    public void testModificar() {
        System.out.println("TipoProducto - modificar");
        TipoProductosDTO t = nuevo("Cuidado Dental " + suf());
        tipo_producto.insertar(t);

        t.setNombre("Cuidado Bucal " + suf());
        t.setDescripcion("Productos para higiene bucal");
        Integer filas = tipo_producto.modificar(t);
        assertNotEquals(0, filas);
        
        TipoProductosDTO fromDb = tipo_producto.obtenerPorId(t.getTipo_producto_id());
        assertEquals(t.getNombre(), fromDb.getNombre());
        assertEquals("Productos para higiene bucal", fromDb.getDescripcion());
    }

    @Test
    public void testEliminar() {
        
        System.out.println("TipoProducto - eliminar");
        Integer i=1;
               
        TipoProductosDTO t = new TipoProductosDTO( i, "Accesorios", "Accesorios iniciales");//nuevo("Descartables " + suf());
        tipo_producto.insertar(t);

        Integer filas = tipo_producto.
        assertNotEquals(0, filas);
        
        assertNull(tipo_producto.obtenerPorId(t.getTipo_producto_id()));
    }

    // Helpers
    private TipoProductosDTO nuevo(String nombre) {
        TipoProductosDTO t = new TipoProductosDTO();
        t.setNombre(nombre);
        t.setDescripcion("Creado en prueba");
        return t;
    }

    private String suf() { return String.valueOf(System.currentTimeMillis() % 100000); }
    
}