using System;
using System.Collections.Generic;
using System.Web.UI;
using SoftPetBusiness;
using SoftPetBussiness.UsuarioClient;

namespace SoftPetWA
{
    public partial class CambiarPassword : System.Web.UI.Page
    {
        private UsuarioBO usuarioBO;

        public CambiarPassword()
        {
            this.usuarioBO = new UsuarioBO();
        }

        protected void Page_Load(object sender, EventArgs e)
        {
            // Ya no necesitamos ocultar paneles al cargar
        }

        protected void btnCambiar_Click(object sender, EventArgs e)
        {
            if (!Page.IsValid) return;

            string correo = txtCorreo.Text.Trim();
            string passActual = txtPassActual.Text;
            string passNueva = txtPassNueva.Text;

            try
            {
                // PASO 1: Validar credenciales actuales
                List<usuarioDto> usuarios = usuarioBO.ObtenerPorCorreoYContra(correo, passActual);

                if (usuarios != null && usuarios.Count > 0)
                {
                    usuarioDto usuario = usuarios[0];

                    // PASO 2: Ejecutar el cambio
                    int resultado = usuarioBO.cambiarPassword(usuario.usuarioId, passActual, passNueva);

                    if (resultado > 0)
                    {
                        // ÉXITO: Limpiamos campos y mostramos Popup de éxito
                        LimpiarCampos();
                        MostrarExito();
                    }
                    else
                    {
                        // ERROR DE BASE DE DATOS O LÓGICA
                        MostrarError("No se pudo actualizar la contraseña. Inténtelo de nuevo.");
                    }
                }
                else
                {
                    // ERROR DE CREDENCIALES
                    MostrarError("El correo o la contraseña actual son incorrectos.");
                }
            }
            catch (Exception ex)
            {
                MostrarError("Error de conexión: " + ex.Message);
            }
        }

        private void LimpiarCampos()
        {
            txtCorreo.Text = "";
            txtPassActual.Text = "";
            txtPassNueva.Text = "";
            txtPassConfirm.Text = "";
        }

        // --- POPUP DE ÉXITO ---
        private void MostrarExito()
        {
            // Este script muestra el check verde y el botón "Ir al Login"
            // La redirección SOLO ocurre dentro del .then(...)
            string script = @"
                <script type='text/javascript'>
                    Swal.fire({
                        icon: 'success',
                        title: '¡Contraseña Actualizada!',
                        text: 'Tus credenciales han sido modificadas correctamente.',
                        confirmButtonText: 'Ir al Login',
                        confirmButtonColor: '#17a2b8', // Color info
                        allowOutsideClick: false
                    }).then((result) => {
                        if (result.isConfirmed) {
                            window.location.href = 'Login.aspx';
                        }
                    });
                </script>";

            ClientScript.RegisterStartupScript(this.GetType(), "SweetAlertExito", script);
        }

        // --- POPUP DE ERROR ---
        private void MostrarError(string mensaje)
        {
            // Este script muestra la X roja y permite cerrar para corregir
            string script = $@"
                <script type='text/javascript'>
                    Swal.fire({{
                        icon: 'error',
                        title: 'Error al cambiar',
                        text: '{mensaje}',
                        showCloseButton: true,
                        confirmButtonText: 'Intentar nuevamente',
                        confirmButtonColor: '#d33'
                    }});
                </script>";

            ClientScript.RegisterStartupScript(this.GetType(), "SweetAlertError", script);
        }
    }
}