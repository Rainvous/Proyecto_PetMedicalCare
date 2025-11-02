/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import java.util.ArrayList;
import java.util.Optional;
import pe.edu.pucp.softpet.dto.productos.ProductoDto;
import pe.edu.pucp.softpet.dto.usuarios.RolDto;

/**
 *
 * @author User
 */
public interface ProductoDao extends DaoBase<ProductoDto>{
    public ProductoDto obtenerPorId(Integer idDto);
    

    
}
