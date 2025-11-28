using System;
using System.Collections.Generic;
using System.Web.UI;
using SoftPetBusiness;
using SoftPetBussiness.UsuarioClient;
using SoftPetBussiness.RolClient;
using System.Linq;

namespace SoftPetWA
{
    public partial class Login : System.Web.UI.Page
    {
        private UsuarioBO usuarioBO;
        private RolBO rolBO;
        private PersonaBO personaBO;

        public Login()
        {
            this.usuarioBO = new UsuarioBO();
            this.rolBO = new RolBO();
            this.personaBO = new PersonaBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // Ya no necesitamos ocultar lblError aquí porque usaremos Popup
        }

        protected void btnIniciarSesion_Click(object sender, EventArgs e)
        {
            validar_datos_real();
        }

        private void validar_datos_real()
        {
            string correo = txtUsuario.Text.Trim();
            string password = txtContrasena.Text;

            // Validación simple
            if (string.IsNullOrEmpty(correo) || string.IsNullOrEmpty(password))
            {
                MostrarError("El correo y la contraseña son obligatorios.");
                return;
            }

            try
            {
                // PASO 1: Validar usuario
                List<usuarioDto> usuarios = this.usuarioBO.ObtenerPorCorreoYContra(correo, password);

                if (usuarios == null || usuarios.Count == 0)
                {
                    MostrarError("Correo o contraseña incorrectos.");
                    return;
                }

                usuarioDto usuario = usuarios[0];

                // PASO 2: Obtener rol
                List<rolDto> roles = this.rolBO.ObtenerRolesDelUsuario(usuario.usuarioId);

                if (roles == null || roles.Count == 0)
                {
                    MostrarError("Este usuario existe pero no tiene un rol asignado.");
                    return;
                }

                rolDto rol = roles[0];

                // PASO 3: Sesión y Redirección
                Session["UsuarioId"] = usuario.usuarioId;
                Session["UsuarioNombre"] = usuario.username;
                Session["UsuarioRol"] = rol.nombre;

                int uid = Convert.ToInt32(Session["UsuarioId"]);
                var todas = personaBO.ListarBusquedaAvanzada("", "", "", -1);
                var p = todas.FirstOrDefault(x => x.usuario != null && x.usuario.usuarioId == uid);
                if (p != null)
                {
                    Session["PersonaId"] = p.personaId;
                }

                switch (rol.nombre.ToUpper())
                {
                    case "VET":
                        Response.Redirect("Veterinario_Inicio.aspx");
                        break;
                    case "CLIENTE":
                        Response.Redirect("Cliente_Inicio.aspx");
                        break;
                    case "ADMIN":
                    case "RECEPCION":
                        Response.Redirect("Secretaria_Dashboard.aspx");
                        break;
                    default:
                        MostrarError("Rol no configurado: " + rol.nombre);
                        break;
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error de conexión: " + ex.Message);
            }
        }

        // METODO MEJORADO: Inyecta SweetAlert en lugar de usar Label
        private void MostrarError(string mensaje)
        {
            // Usamos ClientID para asegurarnos que JS encuentre los textbox correctos
            string idUsuario = txtUsuario.ClientID;
            string idPass = txtContrasena.ClientID;

            // Construimos el script de SweetAlert
            // icon: 'error' -> Muestra la X roja animada
            // showCloseButton: true -> Muestra la X en la esquina superior
            // confirmButtonText -> Texto del botón
            // .then(...) -> Función que se ejecuta al cerrar o dar click en el botón
            string script = $@"
                <script type='text/javascript'>
                    Swal.fire({{
                        icon: 'error',
                        title: '¡Error de Ingreso!',
                        text: '{mensaje}',
                        showCloseButton: true,
                        confirmButtonText: 'Intentar nuevamente',
                        confirmButtonColor: '#d33',
                        allowOutsideClick: false
                    }}).then((result) => {{
                        if (result.isConfirmed || result.isDismissed) {{
                            // Limpiar los campos cuando el usuario cierra el popup
                            document.getElementById('{idUsuario}').value = '';
                            document.getElementById('{idPass}').value = '';
                            // Poner el foco en el usuario de nuevo
                            document.getElementById('{idUsuario}').focus();
                        }}
                    }});
                </script>";

            // Registramos el script para que se ejecute al renderizar la página
            ClientScript.RegisterStartupScript(this.GetType(), "SweetAlertError", script);
        }
    }
}