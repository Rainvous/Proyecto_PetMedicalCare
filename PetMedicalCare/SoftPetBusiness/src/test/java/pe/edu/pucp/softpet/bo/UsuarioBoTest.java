package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

public class UsuarioBoTest {

    private UsuarioBo usuarioBo;

    public UsuarioBoTest() {
        this.usuarioBo = new UsuarioBo();
    }

    @Test
    public void testInsertar() {
        System.out.println("=== Test: Insertar - USUARIOS ===");

        ArrayList<Integer> listaUsuarioID = new ArrayList<>();
        insertarUsuario(listaUsuarioID);
        //eliminarTodo();
    }

    private void insertarUsuario(ArrayList<Integer> listaRolID) {
        Date fechaModificacion = Date.valueOf("2025-10-07");
        Date fechaCreacion = Date.valueOf("2025-10-07");

        Integer resultado = this.usuarioBo.insertar(
                "Snipe",
                "AA00",
                "HOLA@pucp.edu.pe",
                true,
                fechaModificacion,
                "Snipe",
                "Snipe",
                fechaCreacion
        );

        assertTrue(resultado != 0);
        listaRolID.add(resultado);
    }

    @Test
    public void testObtenerPorId() {
        System.out.println("=== Test: Obtener por ID - USUARIOS ===");

        ArrayList<Integer> listaUsuarioId = new ArrayList<>();
        insertarUsuario(listaUsuarioId);
        UsuarioDto usuarioDto = this.usuarioBo.obtenerPorId(listaUsuarioId.get(0));
        assertEquals(usuarioDto.getUsuarioId(), listaUsuarioId.get(0));
        //eliminarTodo();
    }

    @Test
    public void testListarTodos() {
        System.out.println("=== Test: Listar todos - USUARIOS ===");

        ArrayList<Integer> listaUsuarioId = new ArrayList<>();
        insertarUsuario(listaUsuarioId);

        ArrayList<UsuarioDto> listaUsuario = this.usuarioBo.listarTodos();
        assertTrue(listaUsuarioId.size() > 0);
        for (Integer i = 0; i < listaUsuarioId.size(); i++) {
            assertTrue(listaUsuarioId.get(i) > 0);
        }
        //eliminarTodo();
    }

    @Test
    public void testModificar() {
        System.out.println("=== Test: Modificar - USUARIOS ===");

        Date fechaModificacion = Date.valueOf("2025-10-07");
        Date fechaCreacion = Date.valueOf("2025-10-07");

        int rolUsuarioId = 1; // Asumimos que ya existe
        Integer resultado = usuarioBo.modificar(
                rolUsuarioId,
                "Snipe",
                "AA00",
                "HOLA@pucp.edu.pe",
                true,
                fechaModificacion,
                "Snipe",
                "Snipe",
                fechaCreacion
        );

        assertTrue(resultado > 0, "El método modificar debe retornar true si la actualización fue exitosa");
    }

    @Test
    public void testEliminar() {
        System.out.println("=== Test: Eliminar - USUARIOS ===");

        //ArrayList<Integer> listaRolUsuarioId = new ArrayList<>();
        usuarioBo.eliminar(10);
        //eliminarTodo();
    }
}
