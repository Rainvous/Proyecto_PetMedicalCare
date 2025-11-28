package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;

public class ProductoBoTest {

    private final ProductoBo productoBo;

    public ProductoBoTest() {
        this.productoBo = new ProductoBo();
    }

    /**
     * Test of insertar method, of class ProductoBo.
     */
    @Test
    public void testInsertar() {
//        System.out.println("=== Test: Insertar - PRODUCTOS ===");
//
//        // Datos de entrada (nombre y presentacion con espacios/minúsculas)
//        String nombre = "Shampoo para perros";
//        String presentacion = "Botella 250ml";
//        double precio = 19.90;
//        boolean activo = true;
//        int stock = 10;
//
//        int tipoProductoId = 1;
//
//        //insertar
//        Integer idGenerado = productoBo.insertar(
//                tipoProductoId, nombre, presentacion, precio, stock, activo
//        );
//
//        // Solo verificar que el ID generado sea válido
//        assertTrue(idGenerado > 0, "El ID generado debe ser mayor que 0");
    }

    /**
     * Test of modificar method, of class ProductoBO.
     */
    @Test
    public void testModificar() {
//        System.out.println("=== Test: Modificar - PRODUCTOS ===");
//
//        int idProductoExistente = 8; // Asumimos que ya existe
//        String nuevoNombre = "Shampoo Premium para perros";
//        String nuevaPresentacion = "Botella 300ml";
//        double nuevoPrecio = 25.50;
//        boolean nuevoActivo = false;
//        int stock = 3;
//        int tipoProductoId = 1;
//
//        Integer resultado = productoBo.modificar(
//                idProductoExistente, tipoProductoId, nuevoNombre, nuevaPresentacion,
//                nuevoPrecio, stock, nuevoActivo
//        );
//
//        assertTrue(resultado > 0, "El método modificar debe retornar true si la actualización fue exitosa");
    }

    /**
     * Test of eliminar method, of class ProductoBo.
     */
    @Test
    public void testEliminar() {
//        System.out.println("=== Test: Eliminar - PRODUCTOS ===");
//
//        // Insertamos un producto temporal para eliminarlo
//        String nombre = "Collar para gatos";
//        String presentacion = "Paquete individual";
//        double precio = 10.50;
//        boolean activo = true;
//        int stock = 5;
//        int tipoProductoId = 1;
//
//        Integer idGenerado = productoBo.insertar(
//                tipoProductoId, nombre, presentacion, precio, stock, activo
//        );
//        assertTrue(idGenerado > 0, "No se pudo insertar el producto para eliminar.");
//
//        // Ahora eliminamos
//        Integer resultado = productoBo.eliminar(idGenerado);
//        assertTrue(resultado > 0, "El método eliminar debe retornar true si la eliminación fue exitosa");
    }

    /**
     * Test of obtenerPorId method, of class ProductoBo.
     */
    @Test
    public void testObtenerPorId() {
//        System.out.println("=== Test: Obtener por ID - PRODUCTOS ===");
//
//        int idProductoExistente = 1; // Asegurarse de que este ID exista
//        ProductoDto producto = productoBo.obtenerPorId(idProductoExistente);
//
//        assertNotNull(producto, "El producto no debe ser null");
//        assertEquals(idProductoExistente, producto.getProductoId(), "El ID obtenido no coincide");
    }

    /**
     * Test of listarTodos method, of class ProductoBo.
     */
    @Test
    public void testListarTodos() {
        System.out.println("=== Test: Listar todos - PRODUCTOS ===");

        ArrayList<ProductoDto> lista = productoBo.buscarProductosPaginados("", "", true, 1);

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
            System.out.println("Activo: " + (p.getActivo() ? "Sí" : "No"));
            System.out.println("Stock: " + p.getStock());
            if (p.getTipoProducto() != null) {
                System.out.println("Tipo de producto ID: " + p.getTipoProducto().getTipoProductoId());
            }
        }
        
        System.out.println("=== Test: Listar todos 2222 - PRODUCTOS ===");
         lista = productoBo.buscarProductosPaginados("", "", true, 2);

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
            System.out.println("Activo: " + (p.getActivo() ? "Sí" : "No"));
            System.out.println("Stock: " + p.getStock());
            if (p.getTipoProducto() != null) {
                System.out.println("Tipo de producto ID: " + p.getTipoProducto().getTipoProductoId());
            }
        }
    }
}
