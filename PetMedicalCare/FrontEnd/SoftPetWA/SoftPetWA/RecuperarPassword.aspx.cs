using System;
using System.Web.UI;
using SoftPetBusiness;

namespace SoftPetWA
{
    public partial class RecuperarPassword : System.Web.UI.Page
    {
        private UsuarioBO usuarioBO;

        public RecuperarPassword()
        {
            this.usuarioBO = new UsuarioBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
        }

        protected void btnEnviar_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;

            string correo = txtCorreo.Text.Trim();

            try
            {
                // Llamada al servicio Java (devuelve 1 si envió, -1 si no existe correo)
                int resultado = usuarioBO.recuperarPassword(correo);

                if (resultado > 0)
                {
                    // Limpiar campo para evitar reenvíos accidentales
                    txtCorreo.Text = "";
                    EjecutarScript("mostrarExito();");
                }
                else if (resultado == -1)
                {
                    EjecutarScript("mostrarError('El correo ingresado no se encuentra en nuestros registros.');");
                }
                else
                {
                    EjecutarScript("mostrarError('No se pudo procesar la solicitud. Intente más tarde.');");
                }
            }
            catch (Exception ex)
            {
                // SANITIZACIÓN IMPORTANTE: 
                // Quitamos comillas y saltos de línea del error para que no rompa el JS
                string mensajeLimpio = ex.Message.Replace("'", "").Replace("\n", " ").Replace("\r", "");
                EjecutarScript($"mostrarError('Error de conexión: {mensajeLimpio}');");
            }
        }

        private void EjecutarScript(string script)
        {
            // Usamos ScriptManager porque tienes uno en el aspx
            ScriptManager.RegisterStartupScript(this, GetType(), "PopupScript", script, true);
        }
    }
}