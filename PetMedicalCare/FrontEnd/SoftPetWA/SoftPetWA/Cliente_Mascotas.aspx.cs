// Nombre del archivo: Cliente_Mascotas.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Cliente_Mascotas : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarMascotas();
            }
        }

        private void CargarMascotas()
        {
            DataTable dt = ObtenerMascotasEjemplo();
            rptMascotasCliente.DataSource = dt;
            rptMascotasCliente.DataBind();
        }

        private DataTable ObtenerMascotasEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("MascotaID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("EspecieRaza", typeof(string));
            dt.Columns.Add("AvatarURL", typeof(string));
            dt.Columns.Add("Edad", typeof(string)); // Ej: "3 años"
            dt.Columns.Add("Sexo", typeof(string)); // Ej: "Macho"

            // Datos de ejemplo
            dt.Rows.Add(1, "Max", "Golden Retriever", "Images/Avatars/dog-avatar.png", "3 años", "Macho");
            dt.Rows.Add(2, "Luna", "Persa", "Images/Avatars/cat-avatar.png", "2 años", "Hembra");
            dt.Rows.Add(3, "Rocky", "Labrador", "Images/Avatars/dog-avatar.png", "5 años", "Macho");
            // Puedes añadir más mascotas aquí si quieres probar el layout
            dt.Rows.Add(4, "Bella", "Siames", "Images/Avatars/cat-avatar.png", "1 año", "Hembra");

            return dt;
        }

        protected void rptMascotasCliente_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            string mascotaID = e.CommandArgument.ToString();

            if (e.CommandName == "VerHistorial")
            {
                // Redirigir a la pantalla de historial médico para esta mascota
                // Response.Redirect($"Cliente_HistorialMedico.aspx?mascotaId={mascotaID}");
            }
        }
    }
}