// Nombre del archivo: Cliente_Perfil.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // Using System.Data (though less needed here)
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Cliente_Perfil : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarDatosPerfil();
            }
        }

        private void CargarDatosPerfil()
        {
            // En un caso real, cargarías los datos del cliente logueado (Session, etc.)

            // Datos de ejemplo basados en Figma

            // Header
            lblAvatarIniciales.Text = "MG"; // Podrías calcular esto
            lblNombreCompletoHeader.Text = "Miguel Guanira";
            lblEmailHeader.Text = "Miguel.Guanira@email.com";
            lblNumMascotasHeader.Text = "2";
            lblNumCitasHeader.Text = "24";
            lblMiembroDesdeHeader.Text = "Mayo 1960";

            // Panel de Información Personal
            lblNombreCompletoInfo.Text = "Miguel Guanira"; // Nombre completo aquí
            lblDNIInfo.Text = "72345678"; // Solo número
            lblCorreoInfo.Text = "Miguel.Guanira@email.com";
            lblTelefonoInfo.Text = "+51 987 654 321";
            lblFechaNacInfo.Text = "1960-05-15"; // Formato YYYY-MM-DD
            lblGeneroInfo.Text = "Masculino";
            lblDireccionInfo.Text = "Av. Larco 1234, Miraflores, Lima";
            lblDistritoInfo.Text = "Miraflores";
            lblCodigoPostalInfo.Text = "15074";
        }

        // --- Eventos (Vacíos por ahora) ---
        protected void btnEditar_Click(object sender, EventArgs e)
        {
            // Lógica para habilitar edición o redirigir a página de edición
        }

        protected void lnkCerrarSesion_Click(object sender, EventArgs e)
        {
            // Lógica para cerrar sesión
            // Response.Redirect("Login.aspx");
        }
    }
}