<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="SoftPetWA.Login" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>PetMedicalCare - Iniciar Sesión</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
    <link href="Content/bootstrap.css" rel="stylesheet" />
    <link href="Content/Fonts/css/all.css" rel="stylesheet" />
    <link href="Content/login.css" rel="stylesheet" />

    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <style>
        /* Oculto por defecto */
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
            width: 50px;
            height: 50px;
            border: 5px solid #ccc;
            border-top: 5px solid #1abc9c; /* Tu color teal */
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }
        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
        .loading-text {
            margin-top: 15px;
            font-weight: bold;
            color: #333;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
    </style>
</head>
<body class="login-background">

    <div id="loading-overlay">
        <div class="spinner"></div>
        <div class="loading-text">Validando credenciales...</div>
    </div>

    <form id="formLogin" runat="server">
        <div class="card login-card">
            <div class="row g-0">
                <%-- Panel Izquierdo (Igual que antes) --%>
                <div class="col-lg-6 d-none d-lg-block d-flex">
                    <div class="login-promo-panel h-100">
                        <i class="fas fa-heart-pulse logo-icon"></i>
                        <h2>PetMedicalCare</h2>
                        <p>Sistema Integral de Gestión Veterinaria para Clave Canina</p>
                        <img src="Images/pet_login.png" alt="Veterinaria" class="img-fluid" />
                    </div>
                </div>

                <%-- Panel Derecho --%>
                <div class="col-lg-6">
                    <div class="login-form-panel">
                        <h3>¡Bienvenido de vuelta!</h3>
                        <p class="text-muted">Ingresa tus credenciales para acceder al sistema</p>

                        <div class="mb-3">
                            <asp:Label ID="lblUsuario" runat="server" Text="Usuario" CssClass="form-label fw-bold"></asp:Label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-user"></i></span>
                                <asp:TextBox ID="txtUsuario" runat="server" CssClass="form-control" placeholder="Ingresa tu correo"></asp:TextBox>
                            </div>
                        </div>

                        <div class="mb-3">
                            <asp:Label ID="lblContrasena" runat="server" Text="Contraseña" CssClass="form-label fw-bold"></asp:Label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                <asp:TextBox ID="txtContrasena" runat="server" CssClass="form-control" placeholder="Ingresa tu contraseña" TextMode="Password"></asp:TextBox>
                            </div>
                        </div>

                        <div class="d-flex justify-content-between align-items-center mb-4 small">
                            <asp:HyperLink ID="hlOlvido" runat="server" NavigateUrl="RecuperarPassword.aspx" CssClass="text-decoration-none text-muted">¿Olvidaste tu contraseña?</asp:HyperLink>
                            <asp:HyperLink ID="hlCambioPass" runat="server" NavigateUrl="CambiarPassword.aspx" CssClass="text-decoration-none fw-bold text-info">
                                <i class="fas fa-key me-1"></i>Cambiar Contraseña
                            </asp:HyperLink>
                        </div>

                        <div class="d-grid">
                            <asp:Button ID="btnIniciarSesion" runat="server" Text="Iniciar Sesión" 
                                CssClass="btn btn-custom-teal" 
                                OnClick="btnIniciarSesion_Click" 
                                OnClientClick="mostrarLoader();" />
                        </div>

                        <div class="text-center border-top pt-3">
                            <span class="text-muted small">¿No tienes una cuenta?</span>
                            <asp:HyperLink ID="hlRegistro" runat="server" NavigateUrl="Registro.aspx" CssClass="text-decoration-none fw-bold text-custom-teal ms-1">
                                Regístrate aquí
                            </asp:HyperLink>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <script src="Scripts/jquery-3.7.1.min.js"></script>
    <script src="Scripts/bootstrap.bundle.min.js"></script>

    <script type="text/javascript">
        // Función para mostrar el loader al hacer click
        function mostrarLoader() {
            // Verificamos si los campos tienen algo básico antes de mostrar el loader
            // (Opcional: Si usas validadores de ASP.NET, esto debe coordinarse con Page_ClientValidate)
            var u = document.getElementById('<%= txtUsuario.ClientID %>').value;
            var p = document.getElementById('<%= txtContrasena.ClientID %>').value;

            if (u && p) {
                document.getElementById("loading-overlay").style.display = "flex";
            }
        }
    </script>
</body>
</html>