/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pe.edu.pucp.softpet.dao;

import pe.edu.pucp.softpet.dto.servicios.ServicioDto;

/**
 *
 * @author User
 */
public interface ServicioDao extends DaoBase<ServicioDto> {
    public ServicioDto obtenerPorId(Integer idDto);
}
