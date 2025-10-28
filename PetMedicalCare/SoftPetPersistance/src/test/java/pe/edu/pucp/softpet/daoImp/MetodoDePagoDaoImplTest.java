package pe.edu.pucp.softpet.daoImp;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import pe.edu.pucp.softpet.dto.facturacion.MetodoDePagoDto;

public class MetodoDePagoDaoImplTest {

    public MetodoDePagoDaoImplTest() {
    }

    // =============================
    // Test insertar método de pago
    // =============================
    @Test
    public void testInsertarMetodoDePago() {
        System.out.println("Insertar Método de Pago");

        MetodoDePagoDaoImpl dao = new MetodoDePagoDaoImpl();

        // Crear objeto de prueba
        MetodoDePagoDto metodo = new MetodoDePagoDto();
        metodo.setNombre("Pago con Yape");
        metodo.setActivo(true);

        Integer result = dao.insertar(metodo);

        assertNotNull(result, "El ID retornado no debe ser nulo");
        assertTrue(result > 0, "El registro debe insertarse correctamente");

        System.out.println("✅ Método de pago insertado con ID: " + result);
    }

    // =============================
    // Test listar todos los métodos
    // =============================
    @Test
    public void testListarTodosMetodos() {
        System.out.println("Listar Métodos de Pago");

        MetodoDePagoDaoImpl dao = new MetodoDePagoDaoImpl();
        ArrayList<MetodoDePagoDto> lista = dao.listarTodos();

        assertNotNull(lista, "La lista no debe ser nula");
        assertTrue(lista.size() >= 0, "Debe devolver una lista válida");

        System.out.println("✅ Métodos de Pago encontrados: " + lista.size());
        lista.forEach(m -> {
            System.out.println("ID: " + m.getMetodoDePagoId()
                    + " | Nombre: " + m.getNombre()
                    + " | Activo: " + m.getActivo());
        });
    }
}
