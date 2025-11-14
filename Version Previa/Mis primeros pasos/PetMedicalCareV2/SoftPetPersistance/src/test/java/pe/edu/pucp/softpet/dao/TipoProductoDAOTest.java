package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.daoImpl.TipoProductoDAOImpl;
import pe.edu.pucp.softpet.model.productosDTO.TipoProductosDTO;

public class TipoProductoDAOTest {

    private final TipoProductoDAO dao = new TipoProductoDAOImpl();

    @Test
    public void testInsertarYObtener() {
        TipoProductosDTO t = new TipoProductosDTO();
        t.setNombre("Medicamentos");
        t.setDescripcion("Productos de farmacia veterinaria");
        Integer id = dao.insertar(t);
        assertNotNull(id);

//        TipoProductosDTO tBD = dao.obtenerPorId(id);
//        assertEquals(id, tBD.getTipo_producto_id());
//        assertEquals("Medicamentos", tBD.getNombre());
    }

    @Test
    public void testListarTodos() {
        ArrayList<TipoProductosDTO> lista = dao.listarTodos();
        assertNotNull(lista);
    }

    @Test
    public void testModificar() {
        TipoProductosDTO t = new TipoProductosDTO();
        t.setNombre("Accesorios");
        t.setDescripcion("Accesorios iniciales");
        Integer id = dao.insertar(t);
        assertNotNull(id);
        t.setTipo_producto_id(id);

        t.setDescripcion("Accesorios para mascotas (editado)");
        Integer r = dao.modificar(t);
        assertNotEquals(0, r);

//        TipoProductosDTO tBD = dao.obtenerPorId(id);
//        assertEquals("Accesorios para mascotas (editado)", tBD.getDescripcion());
    }
}
