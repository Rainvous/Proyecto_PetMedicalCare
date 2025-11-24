/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dto.util.enums;

/**
 *
 * @author User
 */
public enum TipoRol {
    ADMIN(1),
    VET(2),
    RECEPCION(3),
    CLIENTE(4),
    GUEST(5);
    
     private final int valor;

    private TipoRol(int valor) {
        this.valor = 0;
    }
    public int getValor(){
        return valor;
    }
     
    
}
