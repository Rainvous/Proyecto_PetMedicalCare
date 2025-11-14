/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.RolUsuarioDao;
import pe.edu.pucp.softpet.dao.UsuarioDAO;
import pe.edu.pucp.softpet.daoImp.RolUsuarioDaoImpl;
import pe.edu.pucp.softpet.daoImp.UsuarioDaoImpl;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

/**
 *
 * @author snipe
 */
public class UsuarioBo {
    private UsuarioDAO usuarioDao;
    
    public UsuarioBo() {
        this.usuarioDao = new UsuarioDaoImpl();
    }
    
    public Integer insertar (String username, String password,
            String correo, Boolean activo, Date fechaModificacion,
            String usuarioModificador, String usuarioCreador, Date fechaCreacion){
        
        UsuarioDto usuarioDto = new UsuarioDto();
        
        usuarioDto.setUsername(username);
        usuarioDto.setPassword(password);
        usuarioDto.setCorreo(correo);
        usuarioDto.setActivo(activo);
        usuarioDto.setFechaModificacion(fechaModificacion);
        usuarioDto.setUsuarioModificador(usuarioModificador);
        usuarioDto.setUsuarioCreador(usuarioCreador);
        usuarioDto.setFechaCreacion(fechaCreacion);
        
        return this.usuarioDao.insertar(usuarioDto);
        
    }
    
     public UsuarioDto obtenerPorId(Integer usuarioId){
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuarioId(usuarioId);
        return this.usuarioDao.obtenerPorId(usuarioId);
    }
    
    public ArrayList<UsuarioDto> listarTodos(){
        return this.usuarioDao.listarTodos();
    }
    
    public Integer modificar (Integer usuarioId,String username, String password,
            String correo, Boolean activo, Date fechaModificacion,
            String usuarioModificador, String usuarioCreador, Date fechaCreacion){
        
        UsuarioDto usuarioDto = new UsuarioDto();
        
        usuarioDto.setUsuarioId(usuarioId);
        usuarioDto.setUsername(username);
        usuarioDto.setPassword(password);
        usuarioDto.setCorreo(correo);
        usuarioDto.setActivo(activo);
        usuarioDto.setFechaModificacion(fechaModificacion);
        usuarioDto.setUsuarioModificador(usuarioModificador);
        usuarioDto.setUsuarioCreador(usuarioCreador);
        usuarioDto.setFechaCreacion(fechaCreacion);
        
        return this.usuarioDao.modificar(usuarioDto);
        
    }
    public Integer eliminar(Integer usuarioId){
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setUsuarioId(usuarioId);
        return this.usuarioDao.eliminar(usuarioDto);
    }
}
