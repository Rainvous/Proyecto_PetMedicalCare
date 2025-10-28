// Nombre del archivo: Cliente_ReservarCita_Paso4.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Cliente_ReservarCita_Paso4 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarResumenCita();
            }
        }

        private void CargarResumenCita()
        {
            // En un caso real, obtendrías estos datos de los pasos anteriores (Session, QueryString, etc.)

            // Datos de ejemplo basados en Figma y pasos anteriores
            lblMascotaSeleccionada.Text = "Max (Golden Retriever)";
            lblVeterinarioSeleccionado.Text = "Dr. García López"; // Asumiendo que se seleccionó
            lblFechaSeleccionada.Text = "Martes, 15 de Octubre 2024";
            lblHoraSeleccionada.Text = "11:00 AM";

            // Cargar servicios seleccionados (ejemplo)
            DataTable dtServicios = new DataTable();
            dtServicios.Columns.Add("NombrePrecio", typeof(string)); // Ej: "Consulta General (S/ 80.00)"
            dtServicios.Rows.Add("Consulta General (S/ 80.00)");
            // Si hubiera más servicios, se agregarían aquí
            // dtServicios.Rows.Add("Vacunación (S/ 50.00)");

            rptServiciosSeleccionados.DataSource = dtServicios;
            rptServiciosSeleccionados.DataBind();

            // Calcular y mostrar total (ejemplo)
            lblTotalAPagar.Text = (80.00m).ToString("C", CultureInfo.GetCultureInfo("es-PE"));
        }

        protected void btnConfirmarReserva_Click(object sender, EventArgs e)
        {
            // Aquí iría la lógica para guardar la cita en la base de datos
            // y luego redirigir a una página de éxito o al inicio.
            // Response.Redirect("Cliente_CitaConfirmada.aspx");
        }

        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            // Volver al paso anterior
            Response.Redirect("Cliente_ReservarCita_Paso3.aspx");
        }
    }
}