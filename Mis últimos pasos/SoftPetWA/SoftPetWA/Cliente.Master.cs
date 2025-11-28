using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.PersonaClient;

namespace SoftPetWA
{
    public partial class Cliente : System.Web.UI.MasterPage
    {
        // Instancia del BO para buscar los datos
        private PersonaBO boPersona = new PersonaBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {
                CargarDatosUsuario();
            }
        }

        private void CargarDatosUsuario()
        {
            // 1. Verificamos si el usuario está logueado (ID de Usuario)
            if (Session["UsuarioId"] != null)
            {
                int usuarioId = Convert.ToInt32(Session["UsuarioId"]);
                string nombreReal = "Usuario"; // Valor por defecto

                // 2. BUSCAMOS EL NOMBRE REAL EN LA BASE DE DATOS
                // (Esto corrige el problema de que salga el username en vez del nombre)
                try
                {
                    // Traemos la lista de personas (o clientes)
                    // Nota: Si tienes un método 'ListarSoloClientes', úsalo, es más rápido.
                    // Si no, usamos 'listarTodos' que es seguro que tienes.
                    List<personaDto> personas = boPersona.ListarSoloClientes();

                    // Buscamos la persona que tenga este UsuarioId
                    // (Esto equivale a buscar en la tabla PERSONAS donde USUARIO_ID = 6)
                    personaDto miPersona = personas.FirstOrDefault(p => p.usuario != null && p.usuario.usuarioId == usuarioId);

                    if (miPersona != null)
                    {
                        nombreReal = miPersona.nombre; // "Carlos Torres"

                        // De paso, guardamos el ID de Persona para que las citas funcionen mejor
                        Session["PersonaId"] = miPersona.personaId;
                        // Actualizamos la sesión para no tener que buscar de nuevo
                        Session["UsuarioNombre"] = miPersona.nombre;
                    }
                    else
                    {
                        // Si no encontramos persona, usamos el username del login como respaldo
                        if (Session["UsuarioNombre"] != null)
                            nombreReal = Session["UsuarioNombre"].ToString();
                    }
                }
                catch
                {
                    // Si falla la conexión, usamos lo que haya en sesión
                    if (Session["UsuarioNombre"] != null)
                        nombreReal = Session["UsuarioNombre"].ToString();
                }

                // 3. MOSTRAR EN PANTALLA
                lblNombreUsuario.Text = nombreReal;

                // Inicial (Primera letra)
                if (!string.IsNullOrEmpty(nombreReal))
                {
                    litInicial.Text = nombreReal.Substring(0, 1).ToUpper();
                }
            }
            else
            {
                // Si no hay sesión, al login
                Response.Redirect("Login.aspx");
            }
        }

        protected void lnkLogout_Click(object sender, EventArgs e)
        {
            Session.Clear();
            Session.Abandon();
            Response.Redirect("Login.aspx");
        }
    }
}