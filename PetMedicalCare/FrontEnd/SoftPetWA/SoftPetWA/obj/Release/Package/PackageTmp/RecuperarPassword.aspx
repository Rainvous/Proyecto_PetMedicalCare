<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="RecuperarPassword.aspx.cs" Inherits="SoftPetWA.RecuperarPassword" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>PetMedicalCare - Recuperar Cuenta</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
    <link href="Content/bootstrap.css" rel="stylesheet" />
    <link href="Content/Fonts/css/all.css" rel="stylesheet" />
    <link href="Content/login.css" rel="stylesheet" />

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <style>
        #loading-overlay {
            display: none;
            position: fixed;
            top: 0; left: 0;
            width: 100%; height: 100%;
            background: rgba(255, 255, 255, 0.8);
            z-index: 9999;
            align-items: center;
            justify-content: center;
            flex-direction: column;
        }
        .spinner {
            width: 50px; height: 50px;
            border: 5px solid #ccc;
            border-top: 5px solid #ffc107;
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
        .loading-text { margin-top: 15px; font-weight: bold; color: #333; }
    </style>

    <script type="text/javascript">
        function mostrarLoader() {
            // Solo mostramos si la validación de cliente pasa
            if (typeof Page_ClientValidate === "function") {
                if (Page_ClientValidate('Recuperar')) {
                    document.getElementById("loading-overlay").style.display = "flex";
                }
            } else {
                document.getElementById("loading-overlay").style.display = "flex";
            }
        }

        function mostrarExito() {
            // Ocultamos loader por si acaso
            document.getElementById("loading-overlay").style.display = "none";

            Swal.fire({
                icon: 'success',
                title: '¡Correo Enviado!',
                text: 'Revisa tu bandeja de entrada. Hemos enviado una contraseña temporal.',
                confirmButtonText: 'Ir al Login',
                confirmButtonColor: '#0097a7',
                allowOutsideClick: false
            }).then((result) => {
                if (result.isConfirmed) {
                    window.location.href = 'Login.aspx';
                }
            });
        }

        function mostrarError(mensaje) {
            // Ocultamos loader
            document.getElementById("loading-overlay").style.display = "none";

            Swal.fire({
                icon: 'error',
                title: 'Atención',
                text: mensaje,
                confirmButtonText: 'Intentar de nuevo',
                confirmButtonColor: '#dc3545'
            });
        }
    </script>
</head>
<body class="login-background">

    <div id="loading-overlay">
        <div class="spinner"></div>
        <div class="loading-text">Procesando solicitud...</div>
    </div>

    <form id="formRecuperar" runat="server">
        <asp:ScriptManager ID="smRecuperar" runat="server"></asp:ScriptManager>
        
        <div class="card login-card">
            <div class="row g-0">
                <%-- Panel Izquierdo --%>
                <div class="col-lg-5 d-none d-lg-block d-flex">
                    <div class="login-promo-panel h-100" style="background: linear-gradient(135deg, #f57c00 0%, #ff9800 100%);">
                        <div class="text-center text-white p-5 mt-5">
                            <i class="fas fa-lock-open fa-5x mb-4"></i>
                            <h2>Recuperación</h2>
                            <p class="lead">Te enviaremos una contraseña temporal para que puedas acceder de nuevo.</p>
                        </div>
                    </div>
                </div>

                <%-- Panel Derecho --%>
                <div class="col-lg-7">
                    <div class="login-form-panel p-5">
                        <div class="text-end">
                            <a href="Login.aspx" class="text-decoration-none text-muted"><i class="fas fa-arrow-left me-1"></i> Volver al Login</a>
                        </div>
                        
                        <h3 class="mb-3 fw-bold text-dark mt-4">¿Olvidaste tu contraseña?</h3>
                        <p class="text-muted mb-4">Ingresa tu correo electrónico registrado.</p>

                        <div class="mb-4">
                            <label class="form-label fw-bold text-secondary">Correo Electrónico</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="fas fa-envelope text-muted"></i></span>
                                <asp:TextBox ID="txtCorreo" runat="server" CssClass="form-control" placeholder="ejemplo@correo.com" TextMode="Email"></asp:TextBox>
                            </div>
                            <asp:RequiredFieldValidator ID="rfvCorreo" runat="server" ControlToValidate="txtCorreo" ErrorMessage="El correo es obligatorio." CssClass="text-danger small" Display="Dynamic" ValidationGroup="Recuperar"></asp:RequiredFieldValidator>
                            <asp:RegularExpressionValidator ID="revCorreo" runat="server" ControlToValidate="txtCorreo" ValidationExpression="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$" ErrorMessage="Formato de correo inválido." CssClass="text-danger small" Display="Dynamic" ValidationGroup="Recuperar"></asp:RegularExpressionValidator>
                        </div>

                        <div class="d-grid mb-3">
                            <%-- Botón con llamada a Loader --%>
                            <asp:Button ID="btnEnviar" runat="server" Text="Enviar Contraseña Temporal" 
                                CssClass="btn btn-warning text-white fw-bold py-2" 
                                OnClick="btnEnviar_Click" 
                                OnClientClick="mostrarLoader();"
                                ValidationGroup="Recuperar" />
                        </div>
                        
                        <div class="text-center">
                            <small class="text-muted">Si recuerdas tu contraseña, puedes <a href="CambiarPassword.aspx" class="text-decoration-none fw-bold text-info">cambiarla aquí</a>.</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    
    <script src="Scripts/bootstrap.bundle.min.js"></script>
    </body>
</html>