using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using SoftPetBusiness;
using SoftPetBussiness.PersonaClient;
using SoftPetBussiness.RolClient; // Usamos el cliente de Roles directo

// Alias
using rolDto = SoftPetBussiness.RolClient.rolDto;

namespace SoftPetWA
{
    public partial class Veterinario : System.Web.UI.MasterPage
    {
        private PersonaBO boPersona = new PersonaBO();
        // OPTIMIZACIÓN: Usamos RolBO para buscar roles específicos del usuario
        private RolBO boRol = new RolBO();

        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["UsuarioId"] == null)
            {
                Response.Redirect("Login.aspx");
                return;
            }

            if (!IsPostBack)
            {
                // Cache en Sesión: Si ya tenemos los datos, no consultamos la BD
                if (Session["Master_Nombre"] != null && Session["Master_Rol"] != null)
                {
                    lblNombreUsuario.Text = Session["Master_Nombre"].ToString();
                    //lblRolUsuario.Text = Session["Master_Rol"].ToString();
                    litInicial.Text = Session["Master_Inicial"].ToString();
                }
                else
                {
                    CargarDatosUsuario();
                }
            }
        }

        private void CargarDatosUsuario()
        {
            try
            {
                int uid = Convert.ToInt32(Session["UsuarioId"]);
                string nombreMostrar = "Usuario";
                string rolMostrar = "VETERINARIO";

                // 1. Obtener Nombre (Persona)
                // Usamos ListarPersonasActivas y filtramos (idealmente habría un ObtenerPorUserId)
                var persona = boPersona.ListarPersonasActivas().FirstOrDefault(p => p.usuario != null && p.usuario.usuarioId == uid);

                if (persona != null)
                {
                    string[] partes = persona.nombre.Split(' ');
                    nombreMostrar = partes.Length > 0 ? partes[0] : persona.nombre;
                }
                else if (Session["UsuarioNombre"] != null)
                {
                    nombreMostrar = Session["UsuarioNombre"].ToString();
                }

                // 2. Obtener Rol (OPTIMIZADO)
                // En lugar de traer TODOS los roles del sistema, traemos solo los de este usuario.
                try
                {
                    List<rolDto> rolesUsuario = boRol.ObtenerRolesDelUsuario(uid);

                    if (rolesUsuario != null && rolesUsuario.Count > 0)
                    {
                        // Tomamos el primer rol activo que encuentre
                        var rolEncontrado = rolesUsuario.FirstOrDefault(r => r.activo == true);
                        if (rolEncontrado != null)
                        {
                            rolMostrar = rolEncontrado.nombre.ToUpper();
                        }
                    }
                }
                catch { /* Fallback a "VETERINARIO" si falla el servicio */ }

                // 3. Asignar y Guardar en Caché
                lblNombreUsuario.Text = nombreMostrar;
                //lblRolUsuario.Text = rolMostrar;

                string inicial = !string.IsNullOrEmpty(nombreMostrar) ? nombreMostrar.Substring(0, 1).ToUpper() : "U";
                litInicial.Text = inicial;

                // Guardamos en sesión
                Session["Master_Nombre"] = nombreMostrar;
                Session["Master_Rol"] = rolMostrar;
                Session["Master_Inicial"] = inicial;
            }
            catch
            {
                lblNombreUsuario.Text = "Doctor";
                litInicial.Text = "D";
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