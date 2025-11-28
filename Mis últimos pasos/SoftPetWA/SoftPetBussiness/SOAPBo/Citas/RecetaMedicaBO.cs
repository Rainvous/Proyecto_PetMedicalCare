using SoftPetBussiness.MascotaClient;
using SoftPetBussiness.RecetaMedicaClient;
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class RecetaMedicaBO
    {
        private RecetasMedicaClient clienteSOAP;

        public RecetaMedicaBO()
        {
            this.clienteSOAP = new RecetasMedicaClient();
        }

        public int Insertar(int citaId, string fechaEmision, string vigenciaHasta, string diagnostico, string observaciones, bool activo)
        {
            return this.clienteSOAP.insertar_receta(citaId, fechaEmision, vigenciaHasta, diagnostico, observaciones, activo);
        }

        public int Modificar(int recetaId, int citaId, string fechaEmision, string vigenciaHasta, string diagnostico, string observaciones, bool activo)
        {
            return this.clienteSOAP.modificar_receta(recetaId, citaId, fechaEmision, vigenciaHasta, diagnostico, observaciones, activo);
        }

        // --- AGREGADO: Necesario para el Modal "Ver Detalle" ---
        public recetaMedicaDto ObtenerPorId(int recetaId)
        {
            return this.clienteSOAP.obtener_por_id(recetaId);
        }

        public int Eliminar(int recetaId)
        {
            return this.clienteSOAP.eliminar_receta(recetaId);
        }
        // -------------------------------------------------------

        public recetaMedicaDto ObtenerPorIdCita(int citaId)
        {
            return this.clienteSOAP.obtener_receta_por_cita(citaId);
        }

        public List<recetaMedicaDto> ListarTodos()
        {
            return this.clienteSOAP.listar_todos().ToList<recetaMedicaDto>();
        }

        public List<recetaMedicaDto> ListarPorMascotaYFecha(int mascotaId, DateTime fecha)
        {
            string fechaStr = fecha.ToString("yyyy-MM-dd");
            var lista = clienteSOAP.listar_recetas_por_mascota_y_fecha(mascotaId, fechaStr);
            return lista != null ? lista.ToList() : new List<recetaMedicaDto>();
        }

        public List<recetaMedicaDto> ListarBusquedaAvanzada(string mascota, string duenio, string fecha, string activo)
        {
            mascota = mascota ?? "";
            duenio = duenio ?? "";
            fecha = fecha ?? "";
            activo = activo ?? "";

            var lista = clienteSOAP.listar_busqueda_avanzada(mascota, duenio, fecha, activo);
            return lista != null ? lista.ToList() : new List<recetaMedicaDto>();
        }
    }
}