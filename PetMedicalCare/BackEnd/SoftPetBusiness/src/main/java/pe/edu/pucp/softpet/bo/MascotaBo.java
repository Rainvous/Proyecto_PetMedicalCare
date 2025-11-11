package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.MascotaDao;
import pe.edu.pucp.softpet.daoImp.MascotaDaoImpl;
import pe.edu.pucp.softpet.daoImp.PersonaDaoImpl;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;

public class MascotaBo {
    
    private final MascotaDao mascotaDao;

    public MascotaBo() {
        this.mascotaDao = new MascotaDaoImpl();
    }
    
    public Integer insertar(int personaId, String nombre, String especie, 
            String sexo, String raza, String color, String fechaDefuncion,
            boolean activo) {

        MascotaDto mascota = new MascotaDto();

        mascota.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setSexo(sexo);
        mascota.setRaza(raza);
        mascota.setColor(color);
        if(fechaDefuncion != null)mascota.setFechaDefuncion(Date.valueOf(fechaDefuncion));
        mascota.setActivo(activo);

        return this.mascotaDao.insertar(mascota);
    }

    public Integer modificar(int mascotaId, int personaId, String nombre, String especie, 
            String sexo, String raza, String color, String fechaDefuncion,
            boolean activo) {

        MascotaDto mascota = new MascotaDto();

        mascota.setMascotaId(mascotaId);
        mascota.setPersona(new PersonaDaoImpl().obtenerPorId(personaId));
        mascota.setNombre(nombre);
        mascota.setEspecie(especie);
        mascota.setSexo(sexo);
        mascota.setRaza(raza);
        mascota.setColor(color);
        mascota.setFechaDefuncion(Date.valueOf(fechaDefuncion));
        mascota.setActivo(activo);

        return this.mascotaDao.insertar(mascota);
    }

    public Integer eliminar(int mascotaId) {
        MascotaDto mascota = new MascotaDto();
        mascota.setMascotaId(mascotaId);
        return this.mascotaDao.eliminar(mascota);
    }

    public MascotaDto obtenerPorId(int mascotaId) {
        return this.mascotaDao.obtenerPorId(mascotaId);
    }

    public ArrayList<MascotaDto> listarTodos() {
        return this.mascotaDao.listarTodos();
    }
}
