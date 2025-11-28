using System;
using System.Web.UI;
using SoftPetBusiness;

namespace SoftPetWA
{
    public partial class Registro : System.Web.UI.Page
    {
        private PersonaBO personaBO;

        public Registro()
        {
            this.personaBO = new PersonaBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
        }

        protected void btnRegistrarme_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;

            try
            {
                // Datos generales
                string nombreCompleto = $"{txtNombre.Text.Trim()} {txtApellido.Text.Trim()}";
                string usuario = txtUsuario.Text.Trim();
                string email = txtEmail.Text.Trim();
                string password = txtPass.Text;
                string telefono = txtTelefono.Text.Trim();
                string direccion = txtDireccion.Text.Trim();
                string tipoDoc = ddlTipoDoc.SelectedValue;
                string sexo = ddlSexo.SelectedValue;

                // Parseo Documento
                int nroDoc = 0;
                if (!int.TryParse(txtNumDoc.Text.Trim(), out nroDoc))
                {
                    EjecutarScript("mostrarError('El número de documento no es válido.');");
                    return;
                }

                // =================================================================
                // CORRECCIÓN RUC: Usamos 'long' (Int64) porque el RUC supera al 'int'
                // =================================================================
                long ruc = 0;
                if (!string.IsNullOrEmpty(txtRUC.Text.Trim()))
                {
                    if (!long.TryParse(txtRUC.Text.Trim(), out ruc))
                    {
                        EjecutarScript("mostrarError('El RUC ingresado no es válido.');");
                        return;
                    }
                }

                // NOTA: Si tu método 'InsertarCompleto' en Java/BO espera un 'int', 
                // tendrás un problema de desbordamiento. Deberías actualizar tu BO 
                // para recibir 'long' o 'String' para el RUC.

                // Por ahora, lo casteamos a (int) solo si tu BO lo obliga, 
                // pero ten en cuenta que datos reales (20...) fallarán si el BO es int.
                int rucParaBO = (int)ruc;

                // Llamada al Negocio
                int resultado = personaBO.InsertarCompleto(
                    usuario,
                    password,
                    email,
                    nombreCompleto,
                    direccion,
                    telefono,
                    sexo,
                    nroDoc,
                    rucParaBO,
                    tipoDoc
                );

                if (resultado > 0)
                {
                    EjecutarScript("mostrarExito();");
                }
                else
                {
                    EjecutarScript("mostrarError('No se pudo registrar. Es posible que el Usuario, Correo o DNI ya estén registrados.');");
                }
            }
            catch (Exception ex)
            {
                string mensajeLimpio = ex.Message.Replace("'", "").Replace("\n", " ");
                EjecutarScript($"mostrarError('Error del sistema: {mensajeLimpio}');");
            }
        }

        private void EjecutarScript(string script)
        {
            ScriptManager.RegisterStartupScript(this, GetType(), "PopupScript", script, true);
        }
    }
}