using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftPetWA
{
    public partial class Cliente : System.Web.UI.MasterPage
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void lnkLogout_Click(object sender, EventArgs e)
        {
            // --- Lógica para cerrar sesión ---
            // Ejemplo:
            // Session.Abandon();
            // FormsAuthentication.SignOut();
            // Response.Redirect("~/Login.aspx"); 

            // Por ahora, solo redirigimos al Login para simular
            Response.Redirect("~/Login.aspx");
        }
    }
}