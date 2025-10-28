<%-- Login.aspx --%>
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
</head>
<body class="login-background">

    <form id="formLogin" runat="server">

        <div class="card login-card">
            <div class="row g-0">
                
                <div class="col-lg-6 d-none d-lg-block d-flex">
                    <div class="login-promo-panel h-100">
                        <i class="fas fa-heart-pulse logo-icon"></i>
                        <h2>PetMedicalCare</h2>
                        <p>Sistema Integral de Gestión Veterinaria para Clave Canina</p>
                        
                        <img src="Images/pet_login.png" alt="Veterinaria" class="img-fluid" />
                    </div>
                </div>

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

                        <div class="mb-4"> <%-- Aumentado el margen inferior --%>
                            <asp:Label ID="lblContrasena" runat="server" Text="Contraseña" CssClass="form-label fw-bold"></asp:Label>
                            <div class="input-group">
                                <span class="input-group-text"><i class="fas fa-lock"></i></span>
                                <asp:TextBox ID="txtContrasena" runat="server" CssClass="form-control" placeholder="Ingresa tu contraseña" TextMode="Password"></asp:TextBox>
                            </div>
                        </div>

                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <div class="form-check">
                                <%-- Checkbox sin texto --%>
                                <asp:CheckBox ID="cbRecordarme" runat="server" CssClass="form-check-input" />
                                <%-- Label externo para mejor control --%>
                                <label class="form-check-label" for="<%= cbRecordarme.ClientID %>">Recordarme</label>
                            </div>
                            <asp:HyperLink ID="hlOlvido" runat="server" NavigateUrl="#">¿Olvidaste tu contraseña?</asp:HyperLink>
                        </div>

                        <div class="d-grid">
                            <asp:Button ID="btnIniciarSesion" runat="server" Text="Iniciar Sesión" CssClass="btn btn-custom-teal" OnClick="btnIniciarSesion_Click"/>
                        </div>

                        <%-- 
                        ==============================================
                                AÑADE ESTE BLOQUE DE CÓDIGO AQUÍ
                        ==============================================
                        --%>
                        <div class="text-center mt-4">
                            <asp:HyperLink ID="hlRegistro" runat="server" NavigateUrl="~/Registro.aspx">
                                ¿No tienes una cuenta? <span class="fw-bold">Crear Cuenta</span>
                            </asp:HyperLink>
                        </div>
                        <%-- ============================================ --%>
                    </div>
                </div>
            </div>
        </div>

    </form>
    
    <script src="Scripts/jquery-3.7.1.min.js"></script>
    <script src="Scripts/bootstrap.bundle.min.js"></script>
</body>
</html>