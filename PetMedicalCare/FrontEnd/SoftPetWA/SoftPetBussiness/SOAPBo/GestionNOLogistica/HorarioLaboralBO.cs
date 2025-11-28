using SoftPetBussiness.HorarioLaboralClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace SoftPetBusiness
{
    public class HorarioLaboralBO
    {
        private HorarioLaboralClient clienteSOAP;

        public HorarioLaboralBO()
        {
            this.clienteSOAP = new HorarioLaboralClient();
        }

        // ==============================================================================
        // 1. REGISTRAR HORARIO POR RANGO (O DÍA ÚNICO)
        // ==============================================================================
        // Este método conecta con el bucle que creamos en Java.
        // Convierte las fechas de C# (DateTime) a String "yyyy-MM-dd" para el WS.
        public int RegistrarHorarioRango(int veterinarioId, DateTime fechaInicio, DateTime fechaFin,
                                         string horaInicio, string horaFin, bool activo)
        {
            // Formateamos las fechas para que el Java SimpleDateFormat las entienda
            string fInicioStr = fechaInicio.ToString("yyyy-MM-dd");
            string fFinStr = fechaFin.ToString("yyyy-MM-dd");

            // horaInicio y horaFin ya vienen como string "HH:mm" desde el TextBox
            return clienteSOAP.registrar_horario_rango(
                veterinarioId,
                fInicioStr,
                fFinStr,
                horaInicio,
                horaFin,
                activo
            );
        }

        // ==============================================================================
        // 2. LISTAR POR VETERINARIO (PARA PINTAR EL CALENDARIO)
        // ==============================================================================
        public List<horarioLaboralDto> ListarPorVeterinario(int veterinarioId)
        {
            var lista = clienteSOAP.listar_por_veterinario(veterinarioId);

            // Validación de nulos para evitar errores en el Front
            if (lista == null)
                return new List<horarioLaboralDto>();

            return lista.ToList();
        }

        // ==============================================================================
        // MÉTODOS CRUD ESTÁNDAR (OPCIONALES, PERO ÚTILES)
        // ==============================================================================

        public List<horarioLaboralDto> ListarTodos()
        {
            var lista = clienteSOAP.listar_todos();
            return lista != null ? lista.ToList() : new List<horarioLaboralDto>();
        }

        // Método para insertar un solo día (si se requiere fuera del rango)
        // Nota: El método RegistrarHorarioRango también sirve para un solo día si fechaInicio == fechaFin
        public int InsertarIndividual(int vetId, DateTime fecha, string estado, string horaIni, string horaFin, bool activo)
        {
            // "yyyy-MM-dd HH:mm:ss" es un formato seguro para pasar DateTime completos si el WS lo requiere así
            // Pero como tu WS de insertar individual espera Strings complejos, 
            // RECOMIENDO USAR SIEMPRE 'RegistrarHorarioRango' incluso para 1 día.
            // Es más robusto por la lógica del UPSERT en Java.
            return RegistrarHorarioRango(vetId, fecha, fecha, horaIni, horaFin, activo);
        }
    }
}