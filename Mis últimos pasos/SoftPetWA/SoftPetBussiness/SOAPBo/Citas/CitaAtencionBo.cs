using SoftPetBussiness.CitaAtencionClient;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class CitaAtencionBO
    {
        private CitaAtencionClient clienteSOAP;
        //dtio de cita
        //citaAtencionDto cita= new citaAtencionDto();

        //    cita.activo;
        //    cita.citaId;
        //    cita.estado;
        //    cita.fechaHoraFinStr;
        //    cita.fechaHoraInicioStr;
        //    cita.mascota.mascotaId;
        //    cita.veterinario.veterinarioId;
        //    cita.monto;
        //    cita.citaId;
        //    cita.observacion;
        //    cita.pesoMascota;
        //    cita.fechaHoraFin;
        //    cita.fechaHoraInicio;


        public CitaAtencionBO()
        {
            this.clienteSOAP = new CitaAtencionClient();
        }

        // ===================================================
        //  INSERTAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  insertar_cita(int veterinarioId, int mascotaId,
        //                String fechaHoraInicio, String fechaHoraFin,
        //                double pesoMascota, double monto,
        //                String estado, String observacion, boolean activo)
        // ===================================================
        public int Insertar(
            int veterinarioId,
            int mascotaId,
            string fechaHoraInicio,   // yyyyMMddHHmmss
            string fechaHoraFin,      // yyyyMMddHHmmss
            double pesoMascota,
            double monto,
            string estado,
            string observacion,
            bool activo)
        {
            return this.clienteSOAP.insertar_cita(
                veterinarioId,
                mascotaId,
                fechaHoraInicio,
                fechaHoraFin,
                pesoMascota,
                monto,
                estado,
                observacion,
                activo
            );
        }

        // Sobrecarga opcional con DateTime (convierte interno a string)
        public int Insertar(
            int veterinarioId,
            int mascotaId,
            DateTime fechaHoraInicio,
            DateTime fechaHoraFin,
            double pesoMascota,
            double monto,
            string estado,
            string observacion,
            bool activo)
        {
            string fechaHoraInicioStr = FormatearFechaHora(fechaHoraInicio);
            string fechaHoraFinStr = FormatearFechaHora(fechaHoraFin);

            return Insertar(
                veterinarioId,
                mascotaId,
                fechaHoraInicioStr,
                fechaHoraFinStr,
                pesoMascota,
                monto,
                estado,
                observacion,
                activo
            );
        }

        // ===================================================
        //  MODIFICAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  modificar_cita(int citaId, int veterinarioId, int mascotaId,
        //                 String fechaHoraInicio, String fechaHoraFin,
        //                 double pesoMascota, double monto,
        //                 String estado, String observacion, boolean activo)
        // ===================================================
        public int Modificar(
            int citaId,
            int veterinarioId,
            int mascotaId,
            string fechaHoraInicio,   // yyyyMMddHHmmss
            string fechaHoraFin,      // yyyyMMddHHmmss
            double pesoMascota,
            double monto,
            string estado,
            string observacion,
            bool activo)
        {
            return this.clienteSOAP.modificar_cita(
                citaId,
                veterinarioId,
                mascotaId,
                fechaHoraInicio,
                fechaHoraFin,
                pesoMascota,
                monto,
                estado,
                observacion,
                activo
            );
        }

        // Sobrecarga opcional con DateTime (convierte interno a string)
        public int Modificar(
            int citaId,
            int veterinarioId,
            int mascotaId,
            DateTime fechaHoraInicio,
            DateTime fechaHoraFin,
            double pesoMascota,
            double monto,
            string estado,
            string observacion,
            bool activo)
        {
            string fechaHoraInicioStr = FormatearFechaHora(fechaHoraInicio);
            string fechaHoraFinStr = FormatearFechaHora(fechaHoraFin);

            return Modificar(
                citaId,
                veterinarioId,
                mascotaId,
                fechaHoraInicioStr,
                fechaHoraFinStr,
                pesoMascota,
                monto,
                estado,
                observacion,
                activo
            );
        }

        // =========================
        //    BÚSQUEDA AVANZADA
        // =========================
        public List<citaAtencionDto> ListasBusquedaAvanzadaPoridVet(string fecha, int idveterinario)
        {
            fecha = fecha ?? string.Empty;
            idveterinario = idveterinario == null ? 0 : idveterinario;
            //busca las citas que tiene un veterinario en una fecha determinada
            // formato de fecha: 'yyyy-MM-dd'
            return this.clienteSOAP.listas_busqueda_avanzada_2(fecha, idveterinario).ToList<citaAtencionDto>();
        }

        public List<citaAtencionDto> ListarCitasPorMascota(int idmascota)
        {

            return this.clienteSOAP.listar_citas_por_mascota(idmascota).ToList<citaAtencionDto>();
        }
        public List<citaProgramadaDto> ListarCitasProgramadaEseDia(int idveterinario, string fecha)
        {
            //te devuelve la citaprogramadaDto que tiene la hora de inicio y valor de true o false
            //String fechaFormateada = fecha.ToString("yyyy-MM-dd");
            /*
             * el procedure que me dan en la bd para () CALL sp_generar_horario_disponible(1,'2025-11-03');
 
             * 2025-11-03 09:00:00	0
                2025-11-03 10:00:00	0
                2025-11-03 11:00:00	1
                2025-11-03 12:00:00	1
                2025-11-03 13:00:00	1
                2025-11-03 14:00:00	0
                2025-11-03 15:00:00	0
                2025-11-03 16:00:00	1
             * 
             * 
             */
            //citaProgramadaDto ci=new citaProgramadaDto();

            return this.clienteSOAP.listar_citas_Programadas(idveterinario, fecha).ToList<citaProgramadaDto>();
        }

        // =========================
        //      OBTENER POR ID
        // =========================
        public citaAtencionDto ObtenerPorId(int citaAtencionId)
        {
            return this.clienteSOAP.obtener_por_id(citaAtencionId);
        }

        // =========================
        //      LISTAR TODOS
        // =========================
        public List<citaAtencionDto> ListarTodos()
        {
            return this.clienteSOAP.listar_todos().ToList<citaAtencionDto>();
        }

        // =========================
        //  SOBRECARGAS CON DTO
        // =========================

        // Inserta usando DTO, pero respeta la firma base (strings)
        public int Insertar(citaAtencionDto cita)
        {
            int veterinarioId = cita.veterinario.veterinarioId;
            int mascotaId = cita.mascota.mascotaId;

            // Asumiendo que agregaste estos string en tu DTO:
            // fechaHoraInicioStr, fechaHoraFinStr (formato yyyyMMddHHmmss)
            return Insertar(
                veterinarioId,
                mascotaId,
                cita.fechaHoraInicioStr,
                cita.fechaHoraFinStr,
                cita.pesoMascota,
                cita.monto,
                cita.estado.ToString(),
                cita.observacion,
                cita.activo
            );
        }

        public int Modificar(citaAtencionDto cita)
        {
            int veterinarioId = cita.veterinario.veterinarioId;
            int mascotaId = cita.mascota.mascotaId;

            return Modificar(
                cita.citaId,
                veterinarioId,
                mascotaId,
                cita.fechaHoraInicioStr,
                cita.fechaHoraFinStr,
                cita.pesoMascota,
                cita.monto,
                cita.estado.ToString(),
                cita.observacion,
                cita.activo
            );
        }

        // =========================
        //   MÉTODOS PRIVADOS
        // =========================
        private string FormatearFechaHora(DateTime fechaHora)
        {
            // yyyyMMddHHmmss
            return fechaHora.ToString("yyyyMMddHHmmss");
        }

        private DateTime FromServiceDateTime(DateTime fecha)
        {
            return fecha;
        }

        // En CitaAtencionBO.cs

        public List<citaAtencionDto> ListarCitasPorMascotaYFecha(int idMascota, string fecha)
        {
            // Llama al nuevo método del servicio SOAP
            // (El nombre puede variar ligeramente según cómo lo generó VS, revisa IntelliSense)
            var resultado = this.clienteSOAP.Listas_Citas_Por_Mascotas_Y_Fechas(idMascota, fecha);

            if (resultado == null)
                return new List<citaAtencionDto>();

            return resultado.ToList();
        }
        // En CitaAtencionBO.cs

        public List<citaAtencionDto> ListarBusquedaAvanzada2(string fecha, int idVeterinario)
        {
            // 1. Valida que la fecha no sea nula
            if (string.IsNullOrEmpty(fecha)) fecha = "";

            // 2. Llama al servicio (Asegúrate de haber hecho "Update Service Reference")
            // Escribe "this.clienteSOAP." y espera a que salga la lista para elegir el nombre correcto
            var resultado = this.clienteSOAP.listas_busqueda_avanzada_2(fecha, idVeterinario);

            // 3. Validación de respuesta nula para evitar caídas
            if (resultado == null)
            {
                return new List<citaAtencionDto>();
            }

            // 4. Conversión a lista
            return resultado.ToList();
        }
    }
}
