using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Login : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void btnIniciarSesion_Click(object sender, EventArgs e)
        {
            validar_datos();
        }

        private void validar_datos()
        {
            if (txtUsuario.Text == "veterinario")
            {
                Response.Redirect("Veterinario_Inicio.aspx");
            }
            else if (txtUsuario.Text == "cliente")
            {
                Response.Redirect("Cliente_Inicio.aspx");
            }
            else if (txtUsuario.Text == "secretaria")
            {
                Response.Redirect("Secretaria_Dashboard.aspx");
            }
            
        }
    }
}