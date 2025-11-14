// Nombre del archivo: Cliente_ReservarCita_Paso3.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Cliente_ReservarCita_Paso3 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarVeterinarios();
                // Simular carga inicial del calendario y horarios (para el día 15)
                lblMesAnio.Text = "Octubre 2024";
                lblHorariosDisponibles.Text = "Horarios Disponibles - Martes 15 de Octubre";
                CargarHorariosDisponibles(); // Carga los botones de hora
            }
        }

        private void CargarVeterinarios()
        {
            DataTable dtVets = new DataTable();
            dtVets.Columns.Add("VeterinarioID", typeof(int));
            dtVets.Columns.Add("Nombre", typeof(string));
            dtVets.Rows.Add(0, "Seleccionar veterinario...");
            dtVets.Rows.Add(1, "Dr. García López");
            dtVets.Rows.Add(2, "Dra. Martínez Ruiz");
            dtVets.Rows.Add(3, "Dr. Jiménez Silva");

            ddlVeterinario.DataSource = dtVets;
            ddlVeterinario.DataTextField = "Nombre";
            ddlVeterinario.DataValueField = "VeterinarioID";
            ddlVeterinario.DataBind();
        }

        private void CargarHorariosDisponibles()
        {
            // Simulación: Horarios para el Martes 15 de Octubre (como en Figma)
            DataTable dtHorarios = new DataTable();
            dtHorarios.Columns.Add("Hora", typeof(string)); // Ej: "08:00 AM"
            dtHorarios.Columns.Add("Disponible", typeof(bool)); // Para habilitar/deshabilitar

            dtHorarios.Rows.Add("08:00 AM", true);
            dtHorarios.Rows.Add("09:00 AM", true);
            dtHorarios.Rows.Add("10:00 AM", false); // No disponible
            dtHorarios.Rows.Add("11:00 AM", true); // Seleccionado en Figma
            dtHorarios.Rows.Add("12:00 PM", true);
            dtHorarios.Rows.Add("01:00 PM", true);
            dtHorarios.Rows.Add("02:00 PM", true);
            dtHorarios.Rows.Add("03:00 PM", true);
            dtHorarios.Rows.Add("04:00 PM", false); // No disponible
            dtHorarios.Rows.Add("05:00 PM", true);

            rptHorarios.DataSource = dtHorarios;
            rptHorarios.DataBind();
        }

        protected void rptHorarios_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Lógica para manejar la selección de hora
        }

        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            // Redirigir al siguiente paso
            Response.Redirect("Cliente_ReservarCita_Paso4.aspx");
        }

        protected void btnAnterior_Click(object sender, EventArgs e)
        {
            // Volver al paso anterior
            Response.Redirect("Cliente_ReservarCita_Paso2.aspx");
        }
    }
}