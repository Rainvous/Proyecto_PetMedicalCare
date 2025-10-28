// Nombre del archivo: Cliente_HistorialMedico.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Cliente_HistorialMedico : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                // En un caso real, obtendrías el ID de la mascota (ej. de QueryString)
                // int mascotaId = Convert.ToInt32(Request.QueryString["mascotaId"]);
                CargarDetallesMascota(); // Carga la tarjeta izquierda
                CargarHistorial();      // Carga la lista derecha
            }
        }

        private void CargarDetallesMascota()
        {
            // Datos de ejemplo para "Max"
            imgAvatarMascotaHist.ImageUrl = "Images/Avatars/dog-avatar.png";
            lblNombreMascotaHist.Text = "Max";
            lblRazaMascotaHist.Text = "Golden Retriever";
            lblEdadHist.Text = "3 años, 5 meses";
            lblSexoHist.Text = "Macho";
            lblPesoHist.Text = "32.5 kg";
            lblColorHist.Text = "Dorado";
            lblVisitasHist.Text = "12 visitas";
        }

        private void CargarHistorial()
        {
            DataTable dt = ObtenerHistorialEjemplo(); // Usamos el mismo método de expedientes
            rptHistorialCliente.DataSource = dt;
            rptHistorialCliente.DataBind();

            // Cargar Pestañas de Filtro (ejemplo) - Puedes calcular esto desde 'dt'
            DataTable dtTipos = new DataTable();
            dtTipos.Columns.Add("Tipo", typeof(string));
            dtTipos.Columns.Add("IconoCss", typeof(string));
            dtTipos.Rows.Add("Todas", "fas fa-list");
            dtTipos.Rows.Add("Consultas", "fas fa-stethoscope");
            dtTipos.Rows.Add("Vacunas", "fas fa-syringe");
            dtTipos.Rows.Add("Cirugías", "fas fa-cut");
            dtTipos.Rows.Add("Controles", "fas fa-notes-medical");

            rptFiltroTipoHist.DataSource = dtTipos;
            rptFiltroTipoHist.DataBind();
        }

        // Reutilizamos el método de ejemplo de Secretaria_Expedientes.aspx.cs
        private DataTable ObtenerHistorialEjemplo()
        {
            DataTable dt = new DataTable();
            // Columnas generales
            dt.Columns.Add("Tipo", typeof(string)); // "Consulta" o "Vacunacion"
            dt.Columns.Add("FechaHora", typeof(string));
            dt.Columns.Add("Titulo", typeof(string));
            dt.Columns.Add("Doctor", typeof(string));
            dt.Columns.Add("TipoBadgeCss", typeof(string)); // Clase para el badge (badge-consulta)

            // Columnas para Consulta
            dt.Columns.Add("Diagnostico", typeof(string));
            dt.Columns.Add("Motivo", typeof(string));
            dt.Columns.Add("RecetaTitulo1", typeof(string));
            dt.Columns.Add("RecetaIndicacion1", typeof(string));
            dt.Columns.Add("RecetaTitulo2", typeof(string));
            dt.Columns.Add("RecetaIndicacion2", typeof(string));

            // Columnas para Vacunación
            dt.Columns.Add("Procedimiento", typeof(string));
            dt.Columns.Add("Observaciones", typeof(string));

            // Columnas para Vitals (pueden ser comunes)
            dt.Columns.Add("Peso", typeof(string));
            dt.Columns.Add("Temperatura", typeof(string));
            dt.Columns.Add("Frecuencia", typeof(string));

            // Fila 1: Consulta General (basada en Figma)
            dt.Rows.Add(
                "Consulta", "6 de Octubre, 2024 - 08:00 AM", "Consulta General", "Dr. García López", "badge-consulta",
                "Dermatitis alérgica leve", "Motivo: Control de rutina y revisión de piel.\nObservaciones: Paciente presenta enrojecimiento leve en zona abdominal. Se recomienda cambio de alimentación y tratamiento tópico.",
                "Cetirizina 10mg", "1 tableta cada 12 horas por 7 días",
                "Crema Hidrocortisona 1%", "Aplicar 2 veces al día en zona afectada",
                null, null, // Vacunación (null)
                "32.5 kg", "38.5°C", "90 lpm"
            );

            // Fila 2: Vacunación (basada en Figma)
            dt.Rows.Add(
                "Vacunacion", "15 de Septiembre, 2024 - 10:30 AM", "Vacunación", "Dra. Martínez Ruiz", "badge-vacuna",
                null, null, null, null, null, null, // Consulta (null)
                "Vacuna Antirrábica - Refuerzo Anual", "Observaciones: Paciente en buen estado general. Vacuna aplicada sin complicaciones. Próxima dosis programada para septiembre 2025.",
                "31.8 kg", "38.2°C", "Normal"
            );

            return dt;
        }

        // --- Eventos ---
        protected void rptFiltroTipoHist_ItemCommand(object source, RepeaterCommandEventArgs e) { }
        protected void rptHistorialCliente_ItemCommand(object source, RepeaterCommandEventArgs e) { }
        protected void btnImprimir_Click(object sender, EventArgs e) { }
        protected void btnAgendarCita_Click(object sender, EventArgs e)
        {
            // Response.Redirect("Cliente_ReservarCita_Paso1.aspx");
        }
        protected void btnCambiarMascota_Click(object sender, EventArgs e)
        {
            // Podría mostrar un modal o redirigir a Cliente_Mascotas.aspx
        }
    }
}