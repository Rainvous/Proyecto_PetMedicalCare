// Nombre del archivo: Cliente_ReservarCita_Paso1.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    // Nombre de la clase actualizado
    public partial class Cliente_ReservarCita_Paso1 : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarMascotasCliente();
            }
        }

        private void CargarMascotasCliente()
        {
            DataTable dt = ObtenerMascotasEjemplo();
            rptMascotas.DataSource = dt;
            rptMascotas.DataBind();
        }

        private DataTable ObtenerMascotasEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("MascotaID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("EspecieRaza", typeof(string));
            dt.Columns.Add("AvatarURL", typeof(string));

            // Datos de ejemplo basados en Figma
            dt.Rows.Add(1, "Max", "Golden Retriever", "Images/Avatars/dog-avatar.png");
            dt.Rows.Add(2, "Luna", "Persa", "Images/Avatars/cat-avatar.png");
            dt.Rows.Add(3, "Rocky", "Labrador", "Images/Avatars/dog-avatar.png");

            return dt;
        }

        protected void rptMascotas_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            // Lógica para manejar la selección de mascota iría aquí
            // Por ahora, solo simula la selección en el frontend
        }

        protected void btnSiguiente_Click(object sender, EventArgs e)
        {
            // Redirigir al siguiente paso
            Response.Redirect("Cliente_ReservarCita_Paso2.aspx");
        }

        protected void btnCancelar_Click(object sender, EventArgs e)
        {
            // Volver a la página anterior o al dashboard del cliente
            Response.Redirect("Cliente_Inicio.aspx");
        }
    }
}