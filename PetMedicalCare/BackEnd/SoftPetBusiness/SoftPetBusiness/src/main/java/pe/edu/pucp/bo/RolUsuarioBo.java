/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.RolDao;
import pe.edu.pucp.softpet.dao.RolUsuarioDao;
import pe.edu.pucp.softpet.daoImp.RolDaoImpl;
import pe.edu.pucp.softpet.daoImp.RolUsuarioDaoImpl;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;
import pe.edu.pucp.softpet.dto.usuarios.RolUsuarioDto;
import pe.edu.pucp.softpet.dto.usuarios.UsuarioDto;

/**
 *
 * @author snipe
 */
public class RolUsuarioBo {
    
    private RolUsuarioDao rolUsuarioDao;
    
    public RolUsuarioBo() {
        this.rolUsuarioDao = new RolUsuarioDaoImpl();
    }
    
    public Integer insertar (Integer usuarioId,Integer rolId ,Boolean activo){
       RolUsuarioDto rolUsuarioDto = new RolUsuarioDto();
       UsuarioDto usuario = new UsuarioDto() ;
       usuario.setUsuarioId(usuarioId);
       RolDto rol = new RolDto() ;
       rol.setRolId(rolId);
       rolUsuarioDto.setUsuario(usuario);
       rolUsuarioDto.setRol(rol);
       rolUsuarioDto.setActivo(activo);
       return this.rolUsuarioDao.insertar(rolUsuarioDto);
    }
    
    public RolUsuarioDto obtenerPorID(Integer rolUsuarioId){
        RolUsuarioDto rolUsuarioDto = new RolUsuarioDto();
        rolUsuarioDto.setRolUsuarioId(rolUsuarioId);
        return this.rolUsuarioDao.obtenerPorId(rolUsuarioId);
    }

    public ArrayList<RolUsuarioDto> listarTodos(){
        return this.rolUsuarioDao.listarTodos();
    }
    
    public Integer modificar (Integer rolUsuarioId,UsuarioDto usuario, RolDto rol,
            Boolean activo){
        RolUsuarioDto rolUsuarioDto = new RolUsuarioDto();
        rolUsuarioDto.setRolUsuarioId(rolUsuarioId);
        rolUsuarioDto.setRol(rol);
        rolUsuarioDto.setUsuario(usuario);
        rolUsuarioDto.setActivo(activo);
        return this.rolUsuarioDao.modificar(rolUsuarioDto);
    }
       public Integer eliminar(Integer rolUsuarioId){
        RolUsuarioDto rolUsuarioDto = new RolUsuarioDto();
        rolUsuarioDto.setRolUsuarioId(rolUsuarioId);
        return this.rolUsuarioDao.eliminar(rolUsuarioDto);
    }
}
