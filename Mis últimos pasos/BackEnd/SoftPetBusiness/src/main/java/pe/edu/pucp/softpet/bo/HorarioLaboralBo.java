package pe.edu.pucp.softpet.bo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ArrayList;
import pe.edu.pucp.softpet.dao.HorarioLaboralDao;
import pe.edu.pucp.softpet.daoImp.HorarioLaboralDaoImpl;
import pe.edu.pucp.softpet.dto.personas.HorarioLaboralDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoLaboral;

public class HorarioLaboralBo {

    private final HorarioLaboralDao horarioDao;

    public HorarioLaboralBo() {
        this.horarioDao = new HorarioLaboralDaoImpl();
    }

    public Integer insertar(int veterinarioId, Date fecha, EstadoLaboral estado,
            Date horaInicio, Date horaFin, boolean activo) {

        HorarioLaboralDto horarioDto = new HorarioLaboralDto();

        VeterinarioDto veterinario = new VeterinarioDto();
        veterinario.setVeterinarioId(veterinarioId);

        horarioDto.setVeterinario(veterinario);
        horarioDto.setFecha(fecha);
        horarioDto.setEstado(estado);
        horarioDto.setHoraInicio(horaInicio);
        horarioDto.setHoraFin(horaFin);
        horarioDto.setActivo(activo);

        return this.horarioDao.insertar(horarioDto);
    }

    public Integer modificar(int hotarioId, int veterinarioId, Date fecha, EstadoLaboral estado,
            Date horaInicio, Date horaFin, boolean activo) {

        HorarioLaboralDto horarioDto = new HorarioLaboralDto();

        VeterinarioDto veterinario = new VeterinarioDto();
        veterinario.setVeterinarioId(veterinarioId);

        horarioDto.setHorarioLaboralId(hotarioId);
        horarioDto.setVeterinario(veterinario);
        horarioDto.setFecha(fecha);
        horarioDto.setEstado(estado);
        horarioDto.setHoraInicio(horaInicio);
        horarioDto.setHoraFin(horaFin);
        horarioDto.setActivo(activo);

        return this.horarioDao.modificar(horarioDto);
    }

    public Integer eliminar(Integer horarioId) {
        HorarioLaboralDto horarioDto = new HorarioLaboralDto();
        horarioDto.setHorarioLaboralId(horarioId);
        return this.horarioDao.eliminar(horarioDto);
    }

    public HorarioLaboralDto obtenerPorId(Integer horarioId) {
        HorarioLaboralDto horarioDto = new HorarioLaboralDto();
        horarioDto.setHorarioLaboralId(horarioId);
        return this.horarioDao.obtenerPorId(horarioId);
    }

    public ArrayList<HorarioLaboralDto> listarTodos() {
        return this.horarioDao.listarTodos();
    }
    // ============================================================
    //  1. LISTAR POR VETERINARIO (Para pintar el calendario)
    // ============================================================
    public ArrayList<HorarioLaboralDto> listarPorVeterinario(int veterinarioId) {
        return this.horarioDao.listarPorVeterinario(veterinarioId);
    }

    // ============================================================
    //  2. REGISTRAR POR RANGO (Lógica del Bucle)
    // ============================================================
    public Integer registrarHorarioRango(int veterinarioId, Date fechaInicio, Date fechaFin, 
                                         String horaInicioStr, String horaFinStr, boolean activo) {
        int insertados = 0;

        // Configurar Calendarios para iterar
        Calendar start = Calendar.getInstance();
        start.setTime(fechaInicio);
        Calendar end = Calendar.getInstance();
        end.setTime(fechaFin);
        
        // Ajuste para incluir el último día en el loop (<=)
        end.add(Calendar.DATE, 1); 

        // Validar Strings de hora (Deben venir como "HH:mm")
        if(horaInicioStr == null || horaFinStr == null || horaInicioStr.isEmpty()) return 0;

        // Bucle: Iteramos día por día desde Inicio hasta Fin
        for (java.util.Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            
            try {
                // 1. Preparar el DTO para este día específico
                HorarioLaboralDto dto = new HorarioLaboralDto();
                
                // Set Veterinario
                VeterinarioDto vet = new VeterinarioDto();
                vet.setVeterinarioId(veterinarioId);
                dto.setVeterinario(vet);
                
                // Set Fecha (Solo día - java.sql.Date)
                Date fechaSql = new Date(date.getTime());
                dto.setFecha(fechaSql);
                
                // Set Estado y Activo
                dto.setEstado(EstadoLaboral.DISPONIBLE);
                dto.setActivo(activo);

                /// 2. Construir Timestamps (Fecha del Loop + Hora del Input)
                String fechaBase = fechaSql.toString(); 
                String tsInicioStr = fechaBase + " " + horaInicioStr + ":00"; 
                String tsFinStr = fechaBase + " " + horaFinStr + ":00";       
                
                // --- CORRECCIÓN AQUÍ ---
                // Creamos el Timestamp temporalmente
                Timestamp tsIni = Timestamp.valueOf(tsInicioStr);
                Timestamp tsFin = Timestamp.valueOf(tsFinStr);

                // Lo convertimos a java.sql.Date usando .getTime() para no perder la hora
                // Esto satisface al DTO que pide 'Date'
                dto.setHoraInicio(new Date(tsIni.getTime()));
                dto.setHoraFin(new Date(tsFin.getTime()));
                // ------------------------

                // 3. Llamar al DAO 
                int res = horarioDao.guardarHorario(dto);
                if(res > 0) insertados++;
                
            } catch (Exception e) {
                System.err.println("Error procesando fecha " + date + ": " + e.getMessage());
                // Continuamos con el siguiente día, no detenemos todo el proceso
            }
        }
        
        return insertados; // Retorna cuántos días se procesaron exitosamente
    }
    
}
