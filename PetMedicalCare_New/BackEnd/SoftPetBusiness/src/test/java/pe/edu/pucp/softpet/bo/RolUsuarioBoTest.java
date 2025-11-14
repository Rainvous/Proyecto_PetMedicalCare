//package pe.edu.pucp.softpet.bo;
//
//import java.util.ArrayList;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//import pe.edu.pucp.softpet.dto.usuarios.RolDto;
//import pe.edu.pucp.softpet.dto.usuarios.RolUsuarioDto;
//import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;
//
//public class RolUsuarioBoTest {
//
//    private RolUsuarioBo rolUsuarioBo;
//
//    public RolUsuarioBoTest() {
//        this.rolUsuarioBo = new RolUsuarioBo();
//    }
//
//    @Test
//    public void testInsertar() {
//        System.out.println("=== Test: Insertar - ROLES_USUARIO ===");
//        
//        ArrayList<Integer> listaRolID = new ArrayList<>();
//        insertarRolUsuario(listaRolID);
//        //eliminarTodo();
//    }
//
//    @Test
//    private void insertarRolUsuario(ArrayList<Integer> listaRolID) {
//        Integer resultado = this.rolUsuarioBo.insertar(1, 1, true);
//        assertTrue(resultado != 0);
//        listaRolID.add(resultado);
//    }
//
//    @Test
//    public void testObtenerPorId() {
//        System.out.println("=== Test: Obtener por ID - ROLES_USUARIO ===");
//        
//        ArrayList<Integer> listaRolUsuarioId = new ArrayList<>();
//        insertarRolUsuario(listaRolUsuarioId);
//        RolUsuarioDto rolUsuarioDto = this.rolUsuarioBo.obtenerPorID(listaRolUsuarioId.get(0));
//        assertEquals(rolUsuarioDto.getRolUsuarioId(), listaRolUsuarioId.get(0));
//        //eliminarTodo();
//    }
//
//    @Test
//    public void testListarTodos() {
//        System.out.println("=== Test: Listar todos - ROLES_USUARIO ===");
//        
//        ArrayList<Integer> listaRolId = new ArrayList<>();
//        insertarRolUsuario(listaRolId);
//
//        ArrayList<RolUsuarioDto> listaRol = this.rolUsuarioBo.listarTodos();
//        assertTrue(listaRolId.size() > 0);
//        for (Integer i = 0; i < listaRolId.size(); i++) {
//            assertTrue(listaRolId.get(i) > 0);
//        }
//        //eliminarTodo();
//    }
//
//    @Test
//    public void testModificar() {
//        System.out.println("=== Test: Modificar - ROLES_USUARIO ===");
//
//        int rolUsuarioId = 1; // Asumimos que ya existe
//        UsuarioDto usuario = new UsuarioDto();
//        RolDto rol = new RolDto();
//        Boolean activo = true;
//
//        rol.setRolId(1);
//        usuario.setUsuarioId(2);
//        Integer resultado = rolUsuarioBo.modificar(
//                rolUsuarioId, usuario, rol, activo
//        );
//
//        assertTrue(resultado > 0, "El método modificar debe retornar true si la actualización fue exitosa");
//    }
//
//    @Test
//    public void testEliminar() {
//        System.out.println("=== Test: Eliminar - ROLES_USUARIO ===");
//        
//        //ArrayList<Integer> listaRolUsuarioId = new ArrayList<>();
//        rolUsuarioBo.eliminar(33);
//        //eliminarTodo();
//    }
//}
