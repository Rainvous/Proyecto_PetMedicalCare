using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftpetWA2
{
    public partial class GestionClientes : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarClientes();
            }
        }

        private void CargarClientes()
        {
            // Aquí deberías conectarte a tu base de datos
            // Por ahora, creo datos de ejemplo
            DataTable dt = ObtenerDatosEjemplo();

            gvClientes.DataSource = dt;
            gvClientes.DataBind();

            // Actualizar contadores
            litRegistrosActuales.Text = "1-" + Math.Min(8, dt.Rows.Count).ToString();
            litRegistrosTotales.Text = dt.Rows.Count.ToString();
        }

        private DataTable ObtenerDatosEjemplo()
        {
            DataTable dt = new DataTable();
            dt.Columns.Add("ClienteID", typeof(int));
            dt.Columns.Add("Nombre", typeof(string));
            dt.Columns.Add("Email", typeof(string));
            dt.Columns.Add("TipoDocumento", typeof(string));
            dt.Columns.Add("NumeroDocumento", typeof(string));
            dt.Columns.Add("RUC", typeof(string));
            dt.Columns.Add("Telefono", typeof(string));
            dt.Columns.Add("NumeroMascotas", typeof(int));
            dt.Columns.Add("Activo", typeof(bool));

            // Datos de ejemplo basados en la imagen
            dt.Rows.Add(1, "María González Pérez", "maria.gonzalez@email.com", "DNI", "72458963", "12345678901", "987 654 321", 3, true);
            dt.Rows.Add(2, "Carlos Pérez Torres", "carlos.perez@email.com", "DNI", "45789632", "", "956 321 478", 1, true);
            dt.Rows.Add(3, "Alvaro Ferro Domínguez", "alvaro.ferro@email.com", "DNI", "73024143", "09876543210", "988 528 244", 15, true);
            dt.Rows.Add(4, "Ana Martínez López", "ana.martinez@email.com", "DNI", "65478932", "", "912 345 678", 2, true);
            dt.Rows.Add(5, "Juan Torres Ramírez", "juan.torres@email.com", "DNI", "78945632", "", "923 456 789", 4, false);

            return dt;
        }

        protected void btnBuscar_Click(object sender, EventArgs e)
        {
            // Implementar lógica de búsqueda con los filtros
            BuscarClientes();
        }

        private void BuscarClientes()
        {
            // Aquí implementarías la búsqueda con los filtros
            DataTable dt = ObtenerDatosEjemplo();

            // Aplicar filtros si existen
            if (!string.IsNullOrEmpty(txtNombre.Text))
            {
                dt = dt.AsEnumerable()
                    .Where(row => row.Field<string>("Nombre").ToLower().Contains(txtNombre.Text.ToLower()))
                    .CopyToDataTable();
            }

            if (!string.IsNullOrEmpty(txtDocumento.Text))
            {
                dt = dt.AsEnumerable()
                    .Where(row => row.Field<string>("NumeroDocumento").Contains(txtDocumento.Text))
                    .CopyToDataTable();
            }

            gvClientes.DataSource = dt;
            gvClientes.DataBind();

            litRegistrosActuales.Text = "1-" + Math.Min(8, dt.Rows.Count).ToString();
            litRegistrosTotales.Text = dt.Rows.Count.ToString();
        }

        protected void btnLimpiar_Click(object sender, EventArgs e)
        {
            txtNombre.Text = string.Empty;
            txtDocumento.Text = string.Empty;
            txtTelefono.Text = string.Empty;
            txtRUC.Text = string.Empty;

            CargarClientes();
        }

        protected void btnNuevoCliente_Click(object sender, EventArgs e)
        {
            // Redirigir a página de nuevo cliente
            Response.Redirect("NuevoCliente.aspx");
        }

        protected void btnVolver_Click(object sender, EventArgs e)
        {
            // Volver al dashboard
            Response.Redirect("Dashboard.aspx");
        }

        protected void gvClientes_RowCommand(object sender, GridViewCommandEventArgs e)
        {
            int clienteID = Convert.ToInt32(e.CommandArgument);

            switch (e.CommandName)
            {
                case "Ver":
                    Response.Redirect($"DetalleCliente.aspx?id={clienteID}");
                    break;
                case "Editar":
                    Response.Redirect($"EditarCliente.aspx?id={clienteID}");
                    break;
                case "Eliminar":
                    EliminarCliente(clienteID);
                    break;
            }
        }

        private void EliminarCliente(int clienteID)
        {
            // Implementar lógica de eliminación
            // Por ahora solo recargamos la página
            CargarClientes();
        }

        protected void gvClientes_RowDataBound(object sender, GridViewRowEventArgs e)
        {
            if (e.Row.RowType == DataControlRowType.DataRow)
            {
                // Puedes agregar lógica adicional aquí si es necesario
            }
        }

        // Método helper para obtener iniciales
        // Método helper para obtener iniciales - debe ser público y aceptar object
        public string ObtenerInicialesHelper(object nombreObj)
        {
            if (nombreObj == null)
                return "??";

            string nombre = nombreObj.ToString();

            if (string.IsNullOrEmpty(nombre))
                return "??";

            string[] palabras = nombre.Trim().Split(new[] { ' ' }, StringSplitOptions.RemoveEmptyEntries);

            if (palabras.Length >= 2)
            {
                return (palabras[0][0].ToString() + palabras[1][0].ToString()).ToUpper();
            }
            else if (palabras.Length == 1 && palabras[0].Length >= 2)
            {
                return palabras[0].Substring(0, 2).ToUpper();
            }
            else if (palabras.Length == 1 && palabras[0].Length == 1)
            {
                return palabras[0][0].ToString().ToUpper();
            }

            return "??";
        }
        protected static string ObtenerIniciales(string nombre)
        {
            if (string.IsNullOrEmpty(nombre))
                return "??";

            string[] palabras = nombre.Split(' ');
            if (palabras.Length >= 2)
            {
                return (palabras[0][0].ToString() + palabras[1][0].ToString()).ToUpper();
            }
            else if (palabras.Length == 1 && palabras[0].Length >= 2)
            {
                return palabras[0].Substring(0, 2).ToUpper();
            }
            else
            {
                return palabras[0][0].ToString().ToUpper();
            }
        }
    }
}