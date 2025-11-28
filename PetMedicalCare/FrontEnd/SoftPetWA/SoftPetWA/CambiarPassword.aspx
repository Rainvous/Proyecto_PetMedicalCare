<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="CambiarPassword.aspx.cs" Inherits="SoftPetWA.CambiarPassword" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>PetMedicalCare - Cambiar Contraseña</title>
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
            border-top: 5px solid #17a2b8; /* Color info/teal */
            border-radius: 50%;
            animation: spin 1s linear infinite;
        }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
        .loading-text { margin-top: 15px; font-weight: bold; color: #333; }
    </style>
</head>
<body class="login-background">

    <div id="loading-overlay">
        <div class="spinner"></div>
        <div class="loading-text">Procesando cambio...</div>
    </div>

    <form id="formCambioPass" runat="server">
        <div class="card login-card">
            <div class="row g-0">
                
                <%-- Panel Izquierdo --%>
                <div class="col-lg-5 d-none d-lg-block d-flex">
                    <div class="login-promo-panel h-100" style="background: linear-gradient(135deg, #0097a7 0%, #00acc1 100%);">
                        <div class="text-center text-white p-5 mt-5">
                            <i class="fas fa-shield-alt fa-5x mb-4"></i>
                            <h2>Seguridad</h2>
                            <p class="lead">Actualiza tu contraseña periódicamente para mantener tu cuenta protegida.</p>
                        </div>
                    </div>
                </div>

                <%-- Panel Derecho --%>
                <div class="col-lg-7">
                    <div class="login-form-panel p-5">
                        <div class="text-end">
                            <a href="Login.aspx" class="text-decoration-none text-muted"><i class="fas fa-arrow-left me-1"></i> Volver al Login</a>
                        </div>
                        <h3 class="mb-2 fw-bold text-dark">Cambiar Contraseña</h3>
                        <p class="text-muted mb-4">Ingresa tus credenciales actuales y define una nueva clave.</p>

                        <%-- NOTA: Se eliminaron pnlError y pnlExito, ya no se usan --%>

                        <%-- Correo --%>
                        <div class="mb-3">
                            <label class="form-label fw-bold text-secondary">Correo Electrónico</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="fas fa-envelope text-muted"></i></span>
                                <asp:TextBox ID="txtCorreo" runat="server" CssClass="form-control" placeholder="ejemplo@correo.com" TextMode="Email"></asp:TextBox>
                            </div>
                            <asp:RequiredFieldValidator ID="rfvCorreo" runat="server" ControlToValidate="txtCorreo" ErrorMessage="Ingrese su correo." CssClass="text-danger small" Display="Dynamic" ValidationGroup="CambioPass"></asp:RequiredFieldValidator>
                        </div>

                        <%-- Contraseña Actual --%>
                        <div class="mb-3">
                            <label class="form-label fw-bold text-secondary">Contraseña Actual</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="fas fa-lock text-muted"></i></span>
                                <asp:TextBox ID="txtPassActual" runat="server" CssClass="form-control" placeholder="********" TextMode="Password"></asp:TextBox>
                            </div>
                            <asp:RequiredFieldValidator ID="rfvPassActual" runat="server" ControlToValidate="txtPassActual" ErrorMessage="Ingrese su contraseña actual." CssClass="text-danger small" Display="Dynamic" ValidationGroup="CambioPass"></asp:RequiredFieldValidator>
                        </div>

                        <hr class="my-4" />

                        <%-- Nueva Contraseña --%>
                        <div class="mb-3">
                            <label class="form-label fw-bold text-secondary">Nueva Contraseña</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="fas fa-key text-muted"></i></span>
                                <asp:TextBox ID="txtPassNueva" runat="server" CssClass="form-control" placeholder="Mínimo 8 caracteres" TextMode="Password"></asp:TextBox>
                            </div>
                            <asp:RequiredFieldValidator ID="rfvPassNueva" runat="server" ControlToValidate="txtPassNueva" ErrorMessage="Ingrese la nueva contraseña." CssClass="text-danger small" Display="Dynamic" ValidationGroup="CambioPass"></asp:RequiredFieldValidator>
                            <asp:RegularExpressionValidator ID="revPassNueva" runat="server" ControlToValidate="txtPassNueva" ValidationExpression="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" ErrorMessage="Debe tener 8 caracteres, 1 mayúscula, 1 minúscula y 1 número." CssClass="text-danger small" Display="Dynamic" ValidationGroup="CambioPass"></asp:RegularExpressionValidator>
                        </div>

                        <%-- Confirmar Nueva Contraseña --%>
                        <div class="mb-4">
                            <label class="form-label fw-bold text-secondary">Confirmar Nueva Contraseña</label>
                            <div class="input-group">
                                <span class="input-group-text bg-light"><i class="fas fa-check-double text-muted"></i></span>
                                <asp:TextBox ID="txtPassConfirm" runat="server" CssClass="form-control" placeholder="Repita la contraseña" TextMode="Password"></asp:TextBox>
                            </div>
                            <asp:RequiredFieldValidator ID="rfvPassConfirm" runat="server" ControlToValidate="txtPassConfirm" ErrorMessage="Confirme la contraseña." CssClass="text-danger small" Display="Dynamic" ValidationGroup="CambioPass"></asp:RequiredFieldValidator>
                            <asp:CompareValidator ID="cvPass" runat="server" ControlToValidate="txtPassConfirm" ControlToCompare="txtPassNueva" Operator="Equal" ErrorMessage="Las contraseñas no coinciden." CssClass="text-danger small" Display="Dynamic" ValidationGroup="CambioPass"></asp:CompareValidator>
                        </div>

                        <div class="d-grid">
                            <%-- 3. OnClientClick para el Loader --%>
                            <asp:Button ID="btnCambiar" runat="server" Text="Actualizar Contraseña" 
                                CssClass="btn btn-info text-white fw-bold py-2" 
                                OnClick="btnCambiar_Click" 
                                OnClientClick="if(Page_ClientValidate('CambioPass')) { mostrarLoader(); }"
                                ValidationGroup="CambioPass" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
    <script src="Scripts/bootstrap.bundle.js"></script>
    
    <script>
        function mostrarLoader() {
            document.getElementById("loading-overlay").style.display = "flex";
        }
    </script>
</body>
</html>