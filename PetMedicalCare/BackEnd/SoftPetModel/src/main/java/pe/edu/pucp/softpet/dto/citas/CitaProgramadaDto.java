/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.dto.citas;

/**
 *
 * @author User
 */
public class CitaProgramadaDto {


    private String fecha;
    
    private boolean estaProgramada;
    
    
        /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the estaProgramada
     */
    public boolean isEstaProgramada() {
        return estaProgramada;
    }

    /**
     * @param estaProgramada the estaProgramada to set
     */
    public void setEstaProgramada(boolean estaProgramada) {
        this.estaProgramada = estaProgramada;
    }
    
    
}
