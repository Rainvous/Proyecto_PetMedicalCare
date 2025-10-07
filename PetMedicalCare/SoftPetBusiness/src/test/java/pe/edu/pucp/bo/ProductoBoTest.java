/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pe.edu.pucp.bo;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;

/**
 *
 * @author marti
 */
public class ProductoBoTest {

    private ProductoBo productoBo;

    public ProductoBoTest() {
        this.productoBo = new ProductoBo();
    }

    /**
     * Test of insertar method, of class ProductoBo.
     */
    @Test
    public void testInsertar() {
        System.out.println("=== Test: insertar producto ===");

        // Datos de entrada (nombre y presentacion con espacios/minúsculas)
        String nombre = "   shampoo para perros  ";
        String presentacion = "  botella 250ml  ";
        double precio = 19.90;
        boolean activo = true;

        int tipoProductoId = 1;

        //insertar
        Integer idGenerado = productoBo.insertar(nombre, presentacion, precio, activo, tipoProductoId);

        // Solo verificar que el ID generado sea válido
        assertTrue(idGenerado > 0, "El ID generado debe ser mayor que 0");
    }

    /**
     * Test of modificar method, of class ProductoBO.
     */
    @Test
    public void testModificar() {
        System.out.println("=== Test: modificar producto existente ===");

        int idProductoExistente = 8; // Asumimos que ya existe
        String nuevoNombre = "Shampoo Premium para perros";
        String nuevaPresentacion = "botella 300ml";
        double nuevoPrecio = 25.50;
        boolean nuevoActivo = false;
        int tipoProductoId = 1;

        Integer resultado = productoBo.modificar(
                idProductoExistente,
                nuevoNombre,
                nuevaPresentacion,
                nuevoPrecio,
                nuevoActivo,
                tipoProductoId
        );

        assertTrue(resultado > 0, "El método modificar debe retornar true si la actualización fue exitosa");
    }

    /**
     * Test of eliminar method, of class ProductoBo.
     */
    @Test
    public void testEliminar() {
        System.out.println("=== Test: eliminar producto ===");

        // Insertamos un producto temporal para eliminarlo
        String nombre = "Collar para gatos";
        String presentacion = "Paquete individual";
        double precio = 10.50;
        boolean activo = true;
        int tipoProductoId = 1;

        Integer idGenerado = productoBo.insertar(nombre, presentacion, precio, activo, tipoProductoId);
        assertTrue(idGenerado > 0, "No se pudo insertar el producto para eliminar.");

        // Ahora eliminamos
        Integer resultado = productoBo.eliminar(idGenerado);
        assertTrue(resultado > 0, "El método eliminar debe retornar true si la eliminación fue exitosa");
    }

    /**
     * Test of obtenerPorId method, of class ProductoBo.
     */
    @Test
    public void testObtenerPorId() {
        System.out.println("=== Test: obtener producto por ID ===");

        int idProductoExistente = 1; // Asegúrate de que este ID exista
        ProductoDto producto = productoBo.obtenerPorId(idProductoExistente);

        assertNotNull(producto, "El producto no debe ser null");
        assertEquals(idProductoExistente, producto.getProductoId(), "El ID obtenido no coincide");

    }

    /**
     * Test of listarTodos method, of class ProductoBo.
     */
    @Test
    public void testListarTodos() {
        System.out.println("=== Test: listar todos los productos ===");

        ArrayList<ProductoDto> lista = productoBo.listarTodos();

        // Validaciones básicas
        assertNotNull(lista, "La lista no debe ser null");
        assertFalse(lista.isEmpty(), "La lista no debe estar vacía");

        // Mostrar en consola los productos obtenidos
        System.out.println("Productos encontrados: " + lista.size());
        for (ProductoDto p : lista) {
            System.out.println("-----------------------------------");
            System.out.println("ID: " + p.getProductoId());
            System.out.println("Nombre: " + p.getNombre());
            System.out.println("Presentación: " + p.getPresentacion());
            System.out.println("Precio: " + p.getPrecioUnitario());
            System.out.println("Activo: " + (p.getActivo()? "Sí" : "No"));
            if (p.getTipoProducto() != null) {
                System.out.println("Tipo de producto ID: " + p.getTipoProducto().getTipoProductoId());
            }
        }
    }

}
