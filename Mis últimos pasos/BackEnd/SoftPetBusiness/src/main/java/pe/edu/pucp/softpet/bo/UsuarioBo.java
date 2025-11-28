package pe.edu.pucp.softpet.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.bo.utils.GmailService;
import pe.edu.pucp.softpet.dao.UsuarioDAO;
import pe.edu.pucp.softpet.daoImp.UsuarioDaoImpl;
import pe.edu.pucp.softpet.bo.utils.SecurityUtil;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

public class UsuarioBo {

    private final UsuarioDAO usuarioDao;

    // Necesitamos GmailService aquí también si no lo tenías instanciado
    private final GmailService gmailService; 
    
    public UsuarioBo() {
        this.usuarioDao = new UsuarioDaoImpl();
        
        this.gmailService = new GmailService();
    }

    public Integer insertar(String username, String password,
            String correo, boolean activo) {

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setUsername(username);
        usuarioDto.setPassword(password);
        usuarioDto.setCorreo(correo);
        usuarioDto.setActivo(activo);

        return this.usuarioDao.insertar(usuarioDto);
    }

    public Integer modificar(int usuarioId, String username, String password,
            String correo, boolean activo) {

        UsuarioDto usuarioDto = new UsuarioDto();

        usuarioDto.setUsuarioId(usuarioId);
        usuarioDto.setUsername(username);
        usuarioDto.setPassword(password);
        usuarioDto.setCorreo(correo);
        usuarioDto.setActivo(activo);

        return this.usuarioDao.modificar(usuarioDto);
    }

    public Integer eliminar(Integer usuarioId) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuarioId(usuarioId);
        return this.usuarioDao.eliminar(usuarioDto);
    }

    public UsuarioDto obtenerPorId(Integer usuarioId) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuarioId(usuarioId);
        return this.usuarioDao.obtenerPorId(usuarioId);
    }

    public ArrayList<UsuarioDto> listarTodos() {
        return this.usuarioDao.listarTodos();
    }

//    public ArrayList<UsuarioDto> ObtenerPorCorreoYContra(String correo, String contra) {
//        return this.usuarioDao.ObtenerPorCorreoYContra(correo, contra);
//    }
    
    // MÉTODO MODIFICADO: Login seguro con Hashing
    public ArrayList<UsuarioDto> ObtenerPorCorreoYContra(String correo, String contraRaw) {
        // 1. El usuario ingresa "123456"
        // 2. Nosotros lo convertimos a Hash SHA-256
        String contraHash = SecurityUtil.sha256(contraRaw);
        
        // 3. Consultamos a la BD comparando HASH vs HASH
        return this.usuarioDao.ObtenerPorCorreoYContra(correo, contraHash);
    }

    // MÉTODO NUEVO: Cambiar Contraseña
    public Integer cambiarPassword(int idUsuario, String passwordActualRaw, String passwordNuevaRaw) {
        // 1. Obtener Hash almacenado en BD
        String currentHashDB = usuarioDao.obtenerPasswordActual(idUsuario);
        
        if (currentHashDB == null) return 0; // Usuario no existe

        // 2. Hashear la contraseña que ingresó el usuario para validar
        String inputHash = SecurityUtil.sha256(passwordActualRaw);
        
        if (currentHashDB.equals(inputHash)) {
            // 3. Si coincide, hashear la nueva y actualizar
            String newHash = SecurityUtil.sha256(passwordNuevaRaw);
            return usuarioDao.actualizarPassword(idUsuario, newHash);
        } else {
            return -1; // Contraseña actual incorrecta
        }
    }
    
    // NUEVO: Recuperar Contraseña
    public Integer recuperarPassword(String correo) {
        // 1. Verificar si el correo existe en BD
        UsuarioDto usuario = usuarioDao.obtenerPorCorreo(correo);
        
        if (usuario == null) {
            return -1; // Correo no registrado o inactivo
        }

        // 2. Generar nueva contraseña temporal
        String passTemporal = SecurityUtil.generarPasswordAleatoria(); // Ej: "Xy9Za2"
        
        // 3. Hashear para guardar en BD
        String passHash = SecurityUtil.sha256(passTemporal);
        
        // 4. Actualizar en BD
        int resultado = usuarioDao.actualizarPassword(usuario.getUsuarioId(), passHash);
        
        if (resultado > 0) {
            // 5. Enviar correo con la clave TEMPORAL (Raw)
            // Reutilizamos enviarCorreo_Credenciales que ya tienes configurado
            new Thread(() -> {
                gmailService.enviarCorreo_Credenciales(correo, passTemporal);
            }).start();
        }
        
        return resultado;
    }
}
