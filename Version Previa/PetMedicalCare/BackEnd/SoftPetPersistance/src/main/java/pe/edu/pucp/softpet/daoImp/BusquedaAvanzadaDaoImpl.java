/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

/**
 *
 * @author User
 */
public class BusquedaAvanzadaDaoImpl extends DaoBaseImpl {

    private PersonaDto persona;
    public BusquedaAvanzadaDaoImpl(){
        super("NO_TABLE");
        
    } 
//    public ArrayList<PersonaDto> BuscarPersonaPorNombreDocRucTelef(PersonaDto persona) {
//        Map<Integer, Object> parametrosEntrada = new HashMap<>();
//        parametrosEntrada.put(1, persona.getNombre());
//        parametrosEntrada.put(2, persona.getNroDocumento());
//        parametrosEntrada.put(3, persona.getRuc());
//        parametrosEntrada.put(4, persona.getTelefono());
//
//        return (ArrayList<PersonaDto>) super.ejecutarProcedimientoLectura("sp_buscar_personas_avanzada", parametrosEntrada);
//    }
//    
//    public ArrayList<PersonaDto> BuscarPersonaAvanzadoV2(PersonaDto persona){
//        
//    }
    /*
    Select a usar
    SELECT
    PERSONA_ID,
    NOMBRE,
    DIRECCION,
    TELEFONO,
    SEXO,
    ACTIVO,
    TIPO_DOCUMENTO,
    NRO_DOCUMENTO,
    RUC,
    USUARIO_ID
FROM PERSONAS
WHERE
      NOMBRE        LIKE '%Carlos%'
   OR RUC           LIKE '%999%'
   OR TELEFONO      LIKE '%99%'
   OR NRO_DOCUMENTO LIKE '%12%'
ORDER BY NOMBRE ASC, PERSONA_ID ASC;

    
    
    
    */
    private String sqlParaBusquedaAvanzaV2(PersonaDto persona){
        //Parametros
        String name= persona.getNombre();
        int documento= persona.getNroDocumento();
        int ruc= persona.getRuc();
        String tele=persona.getTelefono();
        
        int flag1,flag2,flag3;
        
        return "";
        
    }

    @Override
    protected void configurarListaDeColumnas() {
        //NO INFO
        return;
    }

}
