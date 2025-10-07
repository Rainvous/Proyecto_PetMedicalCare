/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.bo;

import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.RecetaMedicaDao;
import pe.edu.pucp.softpet.daoImp.CitaAtencionDaoImpl;
import pe.edu.pucp.softpet.daoImp.RecetaMedicaDaoImpl;
import pe.edu.pucp.softpet.dto.recetas.RecetaMedicaDto;

/**
 *
 * @author marti
 */
public class RecetaMedicaBo {
    private RecetaMedicaDao recetaDao;

    public RecetaMedicaBo() {
        this.recetaDao = new RecetaMedicaDaoImpl();
    }

    // Inserta una receta médica
    public Integer insertar(String diagnostico, boolean activo, int citaId) {
        RecetaMedicaDto receta = new RecetaMedicaDto();

        // Normaliza texto
        receta.setDiagnostico(diagnostico.trim().toUpperCase());
        receta.setActivo(activo);
        receta.setCita(new CitaAtencionDaoImpl().obtenerPorId(citaId));

        return this.recetaDao.insertar(receta);
    }

    // Modifica una receta médica existente
    public Integer modificar(int recetaMedicaId, String diagnostico, boolean activo, int citaId) {
        RecetaMedicaDto receta = new RecetaMedicaDto();

        receta.setRecetaMedicaId(recetaMedicaId);
        receta.setDiagnostico(diagnostico.trim().toUpperCase());
        receta.setActivo(activo);
        receta.setCita(new CitaAtencionDaoImpl().obtenerPorId(citaId));

        return this.recetaDao.modificar(receta);
    }

    // Elimina una receta médica por ID
    public Integer eliminar(int recetaMedicaId) {
        RecetaMedicaDto receta = new RecetaMedicaDto();
        receta.setRecetaMedicaId(recetaMedicaId);
        return this.recetaDao.eliminar(receta);
    }

    // Obtiene una receta médica por su ID
    public RecetaMedicaDto obtenerPorId(int recetaMedicaId) {
        return this.recetaDao.obtenerPorId(recetaMedicaId);
    }

    // Lista todas las recetas médicas
    public ArrayList<RecetaMedicaDto> listarTodos() {
        return this.recetaDao.listarTodos();
    }
}
