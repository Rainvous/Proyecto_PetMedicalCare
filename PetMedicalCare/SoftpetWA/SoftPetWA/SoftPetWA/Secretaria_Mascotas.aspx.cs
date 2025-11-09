// Nombre del archivo: Secretaria_Mascotas.aspx.cs
using System;
using System.Collections.Generic;
using System.Data; // <--- Usando System.Data
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Secretaria_Mascotas : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarFiltros();
                CargarMascotas();
            }
        }

        private void CargarFiltros()
        {
            // Cargar Especies (ejemplo)
            DataTable dtEspecie = new DataTable();
            dtEspecie.Columns.Add("EspecieID", typeof(int));
            dtEspecie.Columns.Add("Nombre", typeof(string));
            dtEspecie.Rows.Add(0, "Todas");
            dtEspecie.Rows.Add(1, "Perro");
            dtEspecie.Rows.Add(2, "Gato");
            dtEspecie.Rows.Add(3, "Conejo");

            ddlEspecie.DataSource = dtEspecie;
            ddlEspecie.DataTextField = "Nombre";
            ddlEspecie.DataValueField = "EspecieID";
            ddlEspecie.DataBind();
        }

        private void CargarMascotas()
        {
            DataTable dt = ObtenerMascotasEjemplo();
            rptMascotas.DataSource = dt;
            rptMascotas.DataBind();

            // Actualizar contadores de ejemplo
            litRegistrosTotales.Text = dt.Rows.Count.ToString();
            litRegistrosActuales.Text = $"1-{dt.Rows.Count}";
        }

        private DataTable ObtenerMascotasEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("MascotaID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("EspecieRaza", typeof(string));
            dt.Columns.Add("AvatarURL", typeof(string)); // Ruta a la imagen del avatar
            dt.Columns.Add("Estado", typeof(string));
            dt.Columns.Add("CssEstado", typeof(string)); // Clase para el badge (ej. bg-success)
            dt.Columns.Add("Sexo", typeof(string));
            dt.Columns.Add("Color", typeof(string));
            dt.Columns.Add("PropietarioNombre", typeof(string));
            dt.Columns.Add("PropietarioTelefono", typeof(string));
            dt.Columns.Add("PropietarioIniciales", typeof(string));
            dt.Columns.Add("PropietarioAvatarColor", typeof(string)); // Color de fondo del avatar del dueño

            // Datos de ejemplo basados en el Figma
            // NOTA: Debes tener imágenes de ejemplo en 'Images/Avatars/' o cambiar la ruta
            dt.Rows.Add(1, "MAX", "Perro • Labrador", "Images/Avatars/dog-avatar.png", "Activo", "bg-success", "Macho", "Dorado", "María González", "987 654 321", "MG", "#4a6fa5");
            dt.Rows.Add(2, "LUNA", "Gato • Persa", "Images/Avatars/cat-avatar.png", "Activo", "bg-success", "Hembra", "Blanco", "Carlos Pérez", "956 321 478", "CP", "#f7a36c");
            dt.Rows.Add(3, "ROCKY", "Perro • Bulldog", "Images/Avatars/dog-avatar-2.png", "Activo", "bg-success", "Macho", "Blanco/Marrón", "Ana Martínez", "912 345 678", "AM", "#904c77");

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

        protected void btnNuevaMascota_Click(object sender, EventArgs e)
        {
            // Lógica para redirigir a "Nueva Mascota"
            // Response.Redirect("Secretaria_NuevaMascota.aspx");
        }

        protected void rptMascotas_ItemCommand(object source, RepeaterCommandEventArgs e)
        {
            string mascotaID = e.CommandArgument.ToString();

            if (e.CommandName == "Historial")
            {
                // Response.Redirect($"Secretaria_HistorialMascota.aspx?id={mascotaID}");
            }
            else if (e.CommandName == "Editar")
            {
                // Response.Redirect($"Secretaria_EditarMascota.aspx?id={mascotaID}");
            }
            else if (e.CommandName == "Eliminar")
            {
                // Lógica de eliminación...
            }
        }
    }
}