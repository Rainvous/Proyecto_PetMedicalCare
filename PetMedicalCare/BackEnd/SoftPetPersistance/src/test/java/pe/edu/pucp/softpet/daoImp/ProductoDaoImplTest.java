//package pe.edu.pucp.softpet.daoImp;
//
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.softpet.daoImp.ProductoDaoImpl;
//import pe.edu.pucp.softpet.daoImp.ServicioDaoImpl;
//import pe.edu.pucp.softpet.dto.productos.ProductoDto;
//import pe.edu.pucp.softpet.dto.productos.TipoProductoDto;
//import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
//import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;
//import pe.edu.pucp.softpet.util.MotorDeBaseDeDatos;
//
//public class ProductoDaoImplTest {
//
////    public ProductoDaoImplTest() {
////    }
////
////    public void ImprimeCabecera(String titulo) {
////        System.out.println();
////        System.out.println("=======================================================");
////        System.out.println(" TEST: " + titulo.toUpperCase());
////        System.out.println("=======================================================");
////    }
////
////    /**
////     * Test of listarTodos method, of class ProductoDaoImpl.
////     */
////    @Test
////    public void TesteoCrud() {
////        ImprimeCabecera("Testeo de producto");
////        System.out.println("Insertar");
////        ProductoDto prod = new ProductoDto();
////        prod.setNombre("BACK1");
////        prod.setPresentacion("JAVA2");
////        prod.setPrecioUnitario(34.5);
////        TipoProductoDto tipoprod = new TipoProductoDto();
////        tipoprod.setTipoProductoId(1);
////        prod.setTipoProducto(tipoprod);
////        ProductoDaoImpl prodDao = new ProductoDaoImpl();
////        prod.setActivo(true);
////        Integer resultado = prodDao.insertar(prod);
////
////        assertTrue(resultado > 0);
////        System.out.println("OBETNER ID");
////
////        ProductoDto ProductoObtenido = prodDao.obtenerPorId(2);
////        System.out.println("producto ->" + ProductoObtenido.getNombre());
////        assertNotNull(ProductoObtenido);
////
////        ArrayList<ProductoDto> listarProducto = prodDao.listarTodos();
////        System.out.println("cant" + listarProducto.size());
////        assertTrue(listarProducto.isEmpty() == false);
////
////        System.out.println("modifica");
////        System.out.println("ID INSERTADO ANTES->" + resultado);
////        prod.setProductoId(resultado);
////        prod.setNombre("Modificacion Prueba");
////        prod.setPresentacion("Back_javav2");
////        Integer result2 = prodDao.modificar(prod);
////        assertTrue(result2 > 0);
////
////        System.out.println("elimina");
////        int inidice = 4;
////        System.out.println("inidce a eliminar-> " + inidice);
////        ProductoObtenido = new ProductoDto();
////        ProductoObtenido.setProductoId(resultado);
////        result2 = 0;
////        result2 = prodDao.eliminar(ProductoObtenido);
////        assertTrue(result2 > 0);
////
////    }
////
////    @Test
////    public void TesteoCrud2() throws SQLException {
////        ImprimeCabecera("Testeo de Servicio");
////        System.out.println("Insertar".toUpperCase());
////        ServicioDaoImpl serviDAO = new ServicioDaoImpl();
////        ServicioDto servicio = new ServicioDto();
////        servicio.setActivo(true);
////        servicio.setCosto(1000.2);
////        servicio.setDescripcion("DESPARACITACION perro by java");
////        servicio.setEstado("SIN USAR by java");
////        servicio.setNombre("DESPARACITACION");
////        TipoServicioDto tipo = new TipoServicioDto();
////        tipo.setTipoServicioId(2);
////        servicio.setTipoServicio(tipo);
////
////        Integer result = serviDAO.insertar(servicio);
////        System.out.println("resultado fue-> " + result);
////        assertTrue(result > 0);
////        int indice = 1;
////        ServicioDto DATO_Obtenido = null;
////        DATO_Obtenido = serviDAO.obtenerPorId(indice);
////        assertNotNull(DATO_Obtenido);
////
//////         
////        serviDAO.NombreDelUsuarioQueModifica("PEPITO");
////        System.out.println("modifica");
////        System.out.println("ID INSERTADO ANTES->" + result);
////        servicio.setServicioId(result);
////        servicio.setNombre("Modificacion Prueba");
////
////        Integer result2 = serviDAO.modificar(servicio);
////        assertTrue(result2 > 0);
////        System.out.println("obtener por id");
////        servicio = null;
////        servicio = serviDAO.obtenerPorId(2);
////        System.err.println("EL SERVICIO ES: " + servicio.getNombre() + " FK" + servicio.getServicioId());
////        assertNotNull(servicio);
////
////    }
//
//        @Test
//    public void testListarPorTipo() {
//        System.out.println("--- Prueba: Listar Productos por Tipo ---");
//        
//        // 1. Instanciamos el DAO
//        ProductoDaoImpl productoDao = new ProductoDaoImpl();
//        
//        // 2. Establecemos el motor de BD
//        productoDao.EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos.MYSQL.toString());
//        
//        // 3. Definimos el tipo a buscar. 
//        //    Tu lógica existente usa LIKE '%nombre%', así que "Medic" o "Alim" funciona.
//        String tipoBusqueda = "Medicamento"; // <-- Cambia esto por un tipo que exista en tu BD
//        System.out.println("Buscando productos de tipo LIKE: " + tipoBusqueda);
//
//        // 4. Llamamos al método
//        ArrayList<ProductoDto> productos = productoDao.ListarPorTipo(tipoBusqueda);
//        
//        // --- ASERCIONES (Assertions) ---
//        
//        // 5. Verificamos que la lista no sea nula
//        assertNotNull(productos, "La lista devuelta no debe ser nula.");
//        
//        System.out.println("Se encontraron " + productos.size() + " productos con ese tipo.");
//        
//        // 6. Iteramos y mostramos los resultados
//        for (ProductoDto p : productos) {
//            System.out.println(
//                "  -> ID: " + p.getProductoId() + //
//                ", Nombre: " + p.getNombre() + //
//                ", TipoID: " + p.getTipoProducto().getTipoProductoId() //
//            );
//            
//            // Verificamos que el producto individual no sea nulo
//            assertNotNull(p, "El objeto producto en la lista no debe ser nulo.");
//        }
//        
//        if (productos.isEmpty()) {
//            System.out.println("INFO: No se encontraron productos con ese tipo (la lista está vacía).");
//        }
//    }
//        
//    @Test
//    public void testListarProductosActivos() {
//        System.out.println("--- Prueba: Listar Productos Activos ---");
//
//        // 1. Instanciamos el DAO
//        ProductoDaoImpl productoDao = new ProductoDaoImpl();
//
//        // 2. Establecemos el motor de BD (siguiendo tu patrón)
//        productoDao.EstablecerMotorBaseDeDatos(MotorDeBaseDeDatos.MYSQL.toString());
//
//        // 3. Llamamos al método
//        ArrayList<ProductoDto> productosActivos = productoDao.listarProductosActivos();
//
//        // --- ASERCIONES (Assertions) ---
//        // 4. Verificamos que la lista no sea nula
//        assertNotNull(productosActivos, "La lista devuelta no debe ser nula.");
//
//        System.out.println("Se encontraron " + productosActivos.size() + " productos activos.");
//
//        // 5. Iteramos y verificamos que CADA producto esté activo
//        for (ProductoDto p : productosActivos) {
//            System.out.println(
//                    "  -> ID: " + p.getProductoId()
//                    + //
//                    ", Nombre: " + p.getNombre()
//                    + //
//                    ", Activo: " + p.getActivo() //
//            );
//
//        }
//
//        if (productosActivos.isEmpty()) {
//            System.out.println("INFO: No se encontraron productos activos (la lista está vacía).");
//        }
//    }
//}
