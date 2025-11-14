// Nombre del archivo: Secretaria_Expedientes.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Secretaria_Expedientes : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarFiltros();
                CargarDatosExpediente();
            }
        }

        private void CargarFiltros()
        {
            // Cargar Tipos (ejemplo)
            DataTable dtTipo = new DataTable();
            dtTipo.Columns.Add("TipoID", typeof(int));
            dtTipo.Columns.Add("Nombre", typeof(string));
            dtTipo.Rows.Add(0, "Todos");
            dtTipo.Rows.Add(1, "Perro");
            dtTipo.Rows.Add(2, "Gato");

            ddlTipo.DataSource = dtTipo;
            ddlTipo.DataTextField = "Nombre";
            ddlTipo.DataValueField = "TipoID";
            ddlTipo.DataBind();
        }

        private void CargarDatosExpediente()
        {
            // En un caso real, buscarías el ID de la mascota y cargarías sus datos.
            // Aquí, cargamos los datos de "Max" directamente.

            // 1. Cargar Info de Mascota
            CargarDatosMascota();

            // 2. Cargar Historial Médico
            CargarHistorialMedico();
        }

        private void CargarDatosMascota()
        {
            // Datos de ejemplo para la tarjeta izquierda
            imgAvatarMascota.ImageUrl = "Images/Avatars/dog-avatar.png"; // Asegúrate de tener esta imagen
            lblMascotaNombre.Text = "Max";
            lblMascotaRaza.Text = "Golden Retriever";
            lblCodigo.Text = "M-001234";
            lblEdad.Text = "3 años";
            lblSexo.Text = "Macho";
            lblColor.Text = "Dorado";
            lblPesoActual.Text = "32.5 kg";
            lblEstado.Text = "Activo";
            // Datos del propietario
            lblPropietarioNombre.Text = "María González Pérez";
            lblPropietarioDNI.Text = "DNI: 72458963";
            lblPropietarioTelefono.Text = "987 654 321";
            lblPropietarioEmail.Text = "maria.gonzalez@email.com";
        }

        private void CargarHistorialMedico()
        {
            DataTable dt = ObtenerHistorialEjemplo();
            rptHistorialMedico.DataSource = dt;
            rptHistorialMedico.DataBind();
        }

        private DataTable ObtenerHistorialEjemplo()
        {
            DataTable dt = new DataTable();
            // Columnas generales
            dt.Columns.Add("Tipo", typeof(string)); // "Consulta" o "Vacunacion"
            dt.Columns.Add("FechaHora", typeof(string));
            dt.Columns.Add("Titulo", typeof(string));
            dt.Columns.Add("Doctor", typeof(string));

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
                "Consulta", "6 de Octubre, 2024 - 08:00 AM", "Consulta General", "Dr. García López",
                "Dermatitis alérgica leve", "Motivo: Control de rutina y revisión de piel.\nObservaciones: Paciente presenta enrojecimiento leve en zona abdominal. Se recomienda cambio de alimentación y tratamiento tópico.",
                "Cetirizina 10mg", "1 tableta cada 12 horas por 7 días",
                "Crema Hidrocortisona 1%", "Aplicar 2 veces al día en zona afectada",
                null, null, // Vacunación (null)
                "32.5 kg", "38.5°C", "90 lpm"
            );

            // Fila 2: Vacunación (basada en Figma)
            dt.Rows.Add(
                "Vacunacion", "15 de Septiembre, 2024 - 10:30 AM", "Vacunación", "Dra. Martínez Ruiz",
                null, null, null, null, null, null, // Consulta (null)
                "Vacuna Antirrábica - Refuerzo Anual", "Observaciones: Paciente en buen estado general. Vacuna aplicada sin complicaciones. Próxima dosis programada para septiembre 2025.",
                "31.8 kg", "38.2°C", "Normal"
            );

            return dt;
        }

        // --- Eventos de Botones (Vacíos por ahora) ---

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            // Lógica de búsqueda iría aquí
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            // Lógica para limpiar filtros iría aquí
        }

        protected void btnNuevaConsulta_Click(object sender, EventArgs e)
        {
            // Response.Redirect("Secretaria_NuevaConsulta.aspx");
        }

        protected void rptHistorialMedico_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Lógica para Ver, Editar, Imprimir
        }
    }
}