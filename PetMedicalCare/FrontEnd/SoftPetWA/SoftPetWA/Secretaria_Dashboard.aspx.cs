using System;
using System.Collections.Generic;
using System.Data;
using System.Globalization;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
// Importación de DTOs
using SoftPetBussiness.CitaAtencionClient;
using SoftPetBussiness.VeterinarioClient;
using SoftPetBussiness.DocumentoDePagoClient;
using SoftPetBussiness.MetodoDePagoClient;

// Alias para evitar conflictos
using veterinarioDto = SoftPetBussiness.VeterinarioClient.veterinarioDto;
using citaAtencionDto = SoftPetBussiness.CitaAtencionClient.citaAtencionDto;
using documentoDePagoDto = SoftPetBussiness.DocumentoDePagoClient.documentoPagoDto;
using metodoDePagoDto = SoftPetBussiness.MetodoDePagoClient.metodoDePagoDto;

namespace SoftPetWA
{
    public partial class Secretaria_Dashboard : System.Web.UI.Page
    {
        private CitaAtencionBO boCita = new CitaAtencionBO();
        private VeterinarioBO boVet = new VeterinarioBO();
        private DocumentoDePagoBO boDocumento = new DocumentoDePagoBO();
        private MetodoDePagoBO boMetodo = new MetodoDePagoBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // Verifica sesión de usuario
                if (Session["UsuarioNombre"] != null)
                    litNombreSecretaria.Text = Session["UsuarioNombre"].ToString();

                CargarDashboard();
            }
        }

        private void CargarDashboard()
        {
            try
            {
                DateTime hoy = DateTime.Today;
                string fechaHoyStr = hoy.ToString("yyyy-MM-dd");
                CultureInfo culturePE = CultureInfo.GetCultureInfo("es-PE");

                // 1. OBTENER DATOS
                List<veterinarioDto> listaVets = boVet.ListarBusquedaAvanzada("", "", "", 1);
                List<citaAtencionDto> listaCitasHoy = boCita.ListarBusquedaAvanzada2(fechaHoyStr, 0);
                List<documentoDePagoDto> listaDocumentos = boDocumento.ListarTodos();
                List<metodoDePagoDto> listaMetodos = boMetodo.ListarTodos();

                // Inicializar listas vacías si son nulas
                if (listaVets == null) listaVets = new List<veterinarioDto>();
                if (listaCitasHoy == null) listaCitasHoy = new List<citaAtencionDto>();
                if (listaDocumentos == null) listaDocumentos = new List<documentoDePagoDto>();
                if (listaMetodos == null) listaMetodos = new List<metodoDePagoDto>();

                var dicMetodos = listaMetodos.ToDictionary(m => m.metodoDePagoId, m => m.nombre.ToString());

                // 2. FILTRAR PAGOS DE HOY
                var pagosHoy = listaDocumentos
                    .Where(d => {
                        if (d.fechaEmision == null) return false;
                        string fechaStr = d.fechaEmisionString.ToString();
                        DateTime dtDoc;

                        // Intento de parseo robusto
                        bool esFechaValida = DateTime.TryParseExact(fechaStr, "yyyy-MM-dd", CultureInfo.InvariantCulture, DateTimeStyles.None, out dtDoc);
                        if (!esFechaValida) esFechaValida = DateTime.TryParse(fechaStr, out dtDoc);

                        if (!esFechaValida) return false;

                        // Solo pagos confirmados de hoy
                        return dtDoc.Date == hoy && d.estado.ToString() == "PAGADO";
                    })
                    .ToList();

                // 3. ASIGNAR KPIs
                int cantidadCitas = listaCitasHoy.Count(c => c.estado.ToString() != "CANCELADA");
                litCitasHoy.Text = cantidadCitas.ToString();

                decimal totalIngresos = pagosHoy.Count > 0 ? (decimal)pagosHoy.Sum(d => d.total) : 0;
                litIngresosHoy.Text = totalIngresos.ToString("C", culturePE);

                int cantidadVets = listaVets.Count;
                litVeterinariosActivos.Text = cantidadVets.ToString();

                // 4. LLENAR REPEATERS (Tablas visuales)
                CargarAgendaRepeater(listaCitasHoy);
                CargarVeterinariosRepeater(listaVets);
                CargarResumenFinancieroReal(pagosHoy, dicMetodos, culturePE);
            }
            catch (Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("Error Dashboard: " + ex.Message);
                litCitasHoy.Text = "0";
                litIngresosHoy.Text = "S/ 0.00";
                litVeterinariosActivos.Text = "0";
            }
        }

        // --- Método Auxiliar para el ASPX ---
        public string ObtenerClaseBadge(string estado)
        {
            if (string.IsNullOrEmpty(estado)) return "bg-secondary-soft";
            estado = estado.ToUpper();
            if (estado == "PROGRAMADA" || estado == "PENDIENTE") return "bg-warning-soft"; // Amarillo suave
            if (estado == "ATENDIDA" || estado == "PAGADO") return "bg-success-soft"; // Verde suave
            if (estado == "CANCELADA" || estado == "ANULADO") return "bg-danger-soft"; // Rojo suave
            return "bg-secondary-soft";
        }

        private void CargarAgendaRepeater(List<citaAtencionDto> citas)
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("Hora");
            dt.Columns.Add("Encabezado");
            dt.Columns.Add("Subtitulo");
            dt.Columns.Add("Estado");

            // Ordenar por hora
            var citasOrdenadas = citas.OrderBy(x => x.fechaHoraInicioStr).ToList();

            foreach (var c in citasOrdenadas)
            {
                string horaLegible = "--:--";
                try
                {
                    DateTime dtIni;
                    if (DateTime.TryParse(c.fechaHoraInicioStr, out dtIni))
                        horaLegible = dtIni.ToString("hh:mm tt");
                    else
                    {
                        dtIni = DateTime.ParseExact(c.fechaHoraInicioStr, "yyyyMMddHHmmss", null, DateTimeStyles.None);
                        horaLegible = dtIni.ToString("hh:mm tt");
                    }
                }
                catch { }

                // Texto para mostrar
                string estadoTexto = c.estado.ToString();
                if (estadoTexto == "PROGRAMADA") estadoTexto = "Pendiente";
                if (estadoTexto == "ATENDIDA") estadoTexto = "Finalizada";

                string mascota = c.mascota != null ? c.mascota.nombre : "Mascota";
                string servicio = !string.IsNullOrEmpty(c.observacion) ? c.observacion : "Consulta";
                if (servicio.Length > 25) servicio = servicio.Substring(0, 22) + "...";

                string cliente = (c.mascota != null && c.mascota.persona != null) ? c.mascota.persona.nombre.Split(' ')[0] : "Cliente";
                string doctor = (c.veterinario != null && c.veterinario.persona != null) ? "Dr. " + c.veterinario.persona.nombre.Split(' ')[0] : "Dr.";

                dt.Rows.Add(horaLegible, $"{mascota} - {servicio}", $"{cliente} • {doctor}", estadoTexto);
            }

            rptAgendaDia.DataSource = dt;
            rptAgendaDia.DataBind();
        }

        private void CargarVeterinariosRepeater(List<veterinarioDto> vets)
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("Iniciales");
            dt.Columns.Add("ColorAvatar");
            dt.Columns.Add("NombreCompleto");
            dt.Columns.Add("Especialidad");

            foreach (var v in vets)
            {
                string nombre = (v.persona != null) ? v.persona.nombre : "Vet";
                string iniciales = (!string.IsNullOrEmpty(nombre)) ? nombre.Substring(0, 1).ToUpper() : "V";

                dt.Rows.Add(
                    iniciales,
                    ObtenerColorPorLetra(iniciales), // Método existente
                    nombre,
                    v.especialidad
                );
            }
            rptEstadoVeterinarios.DataSource = dt;
            rptEstadoVeterinarios.DataBind();
        }

        private void CargarResumenFinancieroReal(List<documentoDePagoDto> pagosHoy, Dictionary<int, string> dicMetodos, CultureInfo culture)
        {
            var resumen = pagosHoy
                .GroupBy(p =>
                {
                    int id = (p.metodoDePago != null) ? p.metodoDePago.metodoDePagoId : 0;
                    return dicMetodos.ContainsKey(id) ? dicMetodos[id] : "Otros";
                })
                .Select(g => new
                {
                    Concepto = g.Key,
                    Monto = g.Sum(x => x.total)
                })
                .ToList();

            DataTable dt = new DataTable();
            dt.Columns.Add("Concepto");
            dt.Columns.Add("MontoFormateado");

            if (resumen.Count > 0)
            {
                foreach (var item in resumen)
                {
                    dt.Rows.Add(item.Concepto, ((decimal)item.Monto).ToString("C", culture));
                }
                // Total General
                decimal granTotal = (decimal)resumen.Sum(x => x.Monto);
                dt.Rows.Add("TOTAL", granTotal.ToString("C", culture));
            }
            else
            {
                dt.Rows.Add("Sin movimientos", "S/ 0.00");
            }

            rptResumenFinanciero.DataSource = dt;
            rptResumenFinanciero.DataBind();
        }

        // Método original para colorear avatares
        private string ObtenerColorPorLetra(string letra)
        {
            string[] colores = { "#5e72e4", "#2dce89", "#11cdef", "#fb6340", "#f5365c", "#8898aa" };
            if (string.IsNullOrEmpty(letra)) return "#8898aa";
            int indice = (char.ToUpper(letra[0]) - 'A') % colores.Length;
            if (indice < 0) indice = 0;
            return colores[indice];
        }
    }
}