<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Registro.aspx.cs" Inherits="SoftPetWA.Registro" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>PetMedicalCare - Registro</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    
    <link href="Content/bootstrap.css" rel="stylesheet" />
    <link href="Content/Fonts/css/all.css" rel="stylesheet" />
    <link href="Content/login.css" rel="stylesheet" />
    
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <style>
        .registro-scroll {
            max-height: 75vh;
            overflow-y: auto;
            padding-right: 15px;
            overflow-x: hidden;
        }
        .registro-scroll::-webkit-scrollbar { width: 6px; }
        .registro-scroll::-webkit-scrollbar-track { background: #f1f1f1; }
        .registro-scroll::-webkit-scrollbar-thumb { background: #ccc; border-radius: 10px; }
        .registro-scroll::-webkit-scrollbar-thumb:hover { background: #aaa; }

        .val-error { color: #dc3545; font-size: 0.75rem; display: block; margin-top: 4px; font-weight: 500; }
        .form-label { font-weight: 600; color: #555; font-size: 0.9rem; }

        #loading-overlay {
            display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%;
            background: rgba(255, 255, 255, 0.9); z-index: 9999;
            align-items: center; justify-content: center; flex-direction: column;
        }
        .spinner {
            width: 50px; height: 50px; border: 5px solid #e0e0e0;
            border-top: 5px solid #0097a7; border-radius: 50%; animation: spin 0.8s linear infinite;
        }
        @keyframes spin { 0% { transform: rotate(0deg); } 100% { transform: rotate(360deg); } }
        
        .password-wrapper { position: relative; }
        .toggle-password {
            position: absolute; right: 10px; top: 50%; transform: translateY(-50%);
            background: none; border: none; cursor: pointer; color: #6c757d;
            display: none; 
        }
        .toggle-password:hover { color: #0097a7; }
    </style>

    <script type="text/javascript">
        function soloNumeros(e) {
            var charCode = (e.which) ? e.which : e.keyCode;
            if (charCode > 31 && (charCode < 48 || charCode > 57)) return false;
            return true;
        }

        function configurarDocumento() {
            var ddl = document.getElementById('<%= ddlTipoDoc.ClientID %>');
            var txt = document.getElementById('<%= txtNumDoc.ClientID %>');
            var selected = ddl.value;

            txt.value = ""; // Limpiar al cambiar
            if (selected === "DNI") {
                txt.setAttribute("maxLength", "8");
                txt.setAttribute("placeholder", "8 dígitos exactos");
            } else {
                txt.setAttribute("maxLength", "12");
                txt.setAttribute("placeholder", "Máx. 12 dígitos");
            }
        }

        function mostrarLoader() {
            if (typeof Page_ClientValidate === "function") {
                if (Page_ClientValidate('Registro')) {
                    document.getElementById("loading-overlay").style.display = "flex";
                }
            } else {
                document.getElementById("loading-overlay").style.display = "flex";
            }
        }

        function mostrarExito() {
            document.getElementById("loading-overlay").style.display = "none";
            Swal.fire({
                icon: 'success',
                title: '¡Bienvenido!',
                text: 'Tu cuenta ha sido creada exitosamente.',
                confirmButtonText: 'Iniciar Sesión',
                confirmButtonColor: '#0097a7',
                allowOutsideClick: false
            }).then((result) => {
                if (result.isConfirmed) window.location.href = 'Login.aspx';
            });
        }

        function mostrarError(mensaje) {
            document.getElementById("loading-overlay").style.display = "none";
            Swal.fire({
                icon: 'error',
                title: 'No se pudo registrar',
                text: mensaje,
                confirmButtonText: 'Corregir',
                confirmButtonColor: '#d33'
            });
        }
    </script>
</head>
<body class="login-background">

    <div id="loading-overlay">
        <div class="spinner"></div>
        <div class="text-muted fw-bold mt-3">Creando tu cuenta...</div>
    </div>

    <form id="formRegistro" runat="server">
        <asp:ScriptManager ID="smRegistro" runat="server"></asp:ScriptManager>

        <div class="card login-card" style="max-width: 1000px;">
            <div class="row g-0">
                <div class="col-lg-5 d-none d-lg-block d-flex">
                    <div class="login-promo-panel h-100" style="background: linear-gradient(135deg, #0097a7 0%, #00796b 100%);">
                        <div class="text-center text-white p-5 mt-5">
                            <i class="fas fa-paw fa-5x mb-4"></i>
                            <h2>Únete a Nosotros</h2>
                            <p class="lead">Registra a tus mascotas, agenda citas y lleva el control de su salud en un solo lugar.</p>
                        </div>
                    </div>
                </div>

                <div class="col-lg-7">
                    <div class="login-form-panel p-4">
                        <div class="text-end mb-3">
                            <span class="text-muted small">¿Ya tienes cuenta?</span>
                            <a href="Login.aspx" class="fw-bold text-info text-decoration-none ms-1">Inicia Sesión</a>
                        </div>
                        
                        <h3 class="mb-4 fw-bold text-dark border-start border-4 border-info ps-3">Crear Cuenta</h3>
                        
                        <div class="registro-scroll">
                            
                            <h6 class="text-muted text-uppercase small fw-bold mb-3"><i class="fas fa-user-circle me-1"></i> Datos Personales</h6>
                            
                            <div class="row g-2 mb-2">
                                <div class="col-md-6">
                                    <label class="form-label">Nombres *</label>
                                    <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" placeholder="Tus nombres"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="rfvNom" runat="server" ControlToValidate="txtNombre" ErrorMessage="Ingrese sus nombres." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="revNom" runat="server" ControlToValidate="txtNombre" ValidationExpression="^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$" ErrorMessage="No se permiten números." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RegularExpressionValidator>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Apellidos *</label>
                                    <asp:TextBox ID="txtApellido" runat="server" CssClass="form-control" placeholder="Tus apellidos"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="rfvApe" runat="server" ControlToValidate="txtApellido" ErrorMessage="Ingrese sus apellidos." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="revApe" runat="server" ControlToValidate="txtApellido" ValidationExpression="^[a-zA-ZñÑáéíóúÁÉÍÓÚ\s]+$" ErrorMessage="No se permiten números." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RegularExpressionValidator>
                                </div>
                            </div>

                            <div class="row g-2 mb-2">
                                <div class="col-md-4">
                                    <label class="form-label">Documento *</label>
                                    <asp:DropDownList ID="ddlTipoDoc" runat="server" CssClass="form-select" onchange="configurarDocumento()">
                                        <asp:ListItem Text="DNI" Value="DNI" Selected="True"></asp:ListItem>
                                        <asp:ListItem Text="C.E." Value="CE"></asp:ListItem>
                                    </asp:DropDownList>
                                </div>
                                <div class="col-md-8">
                                    <label class="form-label">N° Documento *</label>
                                    <asp:TextBox ID="txtNumDoc" runat="server" CssClass="form-control" MaxLength="8" placeholder="8 dígitos" onkeypress="return soloNumeros(event)"></asp:TextBox>
                                    <asp:RequiredFieldValidator ID="rfvDoc" runat="server" ControlToValidate="txtNumDoc" ErrorMessage="Ingrese su documento." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="revDoc" runat="server" ControlToValidate="txtNumDoc" ValidationExpression="^\d{8,12}$" ErrorMessage="Mínimo 8 dígitos." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RegularExpressionValidator>
                                </div>
                            </div>

                            <%-- FILA MODIFICADA: Teléfono, Sexo y RUC en 3 columnas --%>
                            <div class="row g-2 mb-3">
                                <div class="col-md-4">
                                    <label class="form-label">Teléfono</label>
                                    <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control" MaxLength="9" placeholder="9 dígitos" onkeypress="return soloNumeros(event)"></asp:TextBox>
                                    <asp:RegularExpressionValidator ID="revTel" runat="server" ControlToValidate="txtTelefono" ValidationExpression="^\d{9}$" ErrorMessage="Debe tener 9 dígitos." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RegularExpressionValidator>
                                </div>
                                <div class="col-md-4">
                                    <label class="form-label">Sexo</label>
                                    <asp:DropDownList ID="ddlSexo" runat="server" CssClass="form-select">
                                        <asp:ListItem Text="Masculino" Value="M"></asp:ListItem>
                                        <asp:ListItem Text="Femenino" Value="F"></asp:ListItem>
                                        <asp:ListItem Text="Prefiero no decir" Value="O" Selected="True"></asp:ListItem>
                                    </asp:DropDownList>
                                </div>
                                <%-- ... Código anterior ... --%>

                                <%-- RUC CORREGIDO --%>
                                <div class="col-md-4">
                                    <label class="form-label">RUC <span class="text-muted fw-normal small">(Opcional)</span></label>
                                    
                                    <asp:TextBox ID="txtRUC" runat="server" CssClass="form-control" MaxLength="11" placeholder="Inicia con 10 o 20" onkeypress="return soloNumeros(event)"></asp:TextBox>
                                    
                                    <%-- VALIDACIÓN DE 10 o 20 + 9 DÍGITOS --%>
                                    <asp:RegularExpressionValidator ID="revRuc" runat="server" 
                                        ControlToValidate="txtRUC" 
                                        ValidationExpression="^(10|20)\d{9}$" 
                                        ErrorMessage="RUC inválido (debe iniciar con 10 o 20)." 
                                        CssClass="val-error" 
                                        ValidationGroup="Registro" 
                                        Display="Dynamic">
                                    </asp:RegularExpressionValidator>
                                </div>

<%-- ... Resto del código ... --%>
                            </div>

                            <div class="row g-2 mb-3">
                                <div class="col-12">
                                    <label class="form-label">Dirección</label>
                                    <asp:TextBox ID="txtDireccion" runat="server" CssClass="form-control" placeholder="Dirección completa"></asp:TextBox>
                                </div>
                            </div>

                            <h6 class="text-muted text-uppercase small fw-bold mb-3 mt-4 border-top pt-3"><i class="fas fa-lock me-1"></i> Datos de Acceso</h6>

                            <div class="mb-2">
                                <label class="form-label">Correo Electrónico *</label>
                                <asp:TextBox ID="txtEmail" runat="server" CssClass="form-control" TextMode="Email" placeholder="nombre@ejemplo.com"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="rfvEmail" runat="server" ControlToValidate="txtEmail" ErrorMessage="El correo es obligatorio." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RequiredFieldValidator>
                                <asp:RegularExpressionValidator ID="revEmail" runat="server" ControlToValidate="txtEmail" ValidationExpression="^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$" ErrorMessage="Correo inválido." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RegularExpressionValidator>
                            </div>

                            <div class="mb-2">
                                <label class="form-label">Usuario *</label>
                                <asp:TextBox ID="txtUsuario" runat="server" CssClass="form-control" placeholder="Crea tu usuario único"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="rfvUser" runat="server" ControlToValidate="txtUsuario" ErrorMessage="El usuario es obligatorio." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RequiredFieldValidator>
                            </div>

                            <div class="row g-2 mb-4">
                                <div class="col-md-6">
                                    <label class="form-label">Contraseña *</label>
                                    <div class="password-wrapper">
                                        <asp:TextBox ID="txtPass" runat="server" CssClass="form-control" TextMode="Password" placeholder="********"></asp:TextBox>
                                        <button type="button" class="toggle-password" tabindex="-1"><i class="fas fa-eye"></i></button>
                                    </div>
                                    <asp:RequiredFieldValidator ID="rfvPass" runat="server" ControlToValidate="txtPass" ErrorMessage="Ingrese una contraseña." CssClass="val-error" ValidationGroup="Registro" Display="Dynamic"></asp:RequiredFieldValidator>
                                    <asp:RegularExpressionValidator ID="revPass" runat="server" ControlToValidate="txtPass" ValidationExpression="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$" ErrorMessage="Mín. 8 caracteres, 1 mayúscula y 1 número." CssClass="val-error" Display="Dynamic" ValidationGroup="Registro"></asp:RegularExpressionValidator>
                                </div>
                                <div class="col-md-6">
                                    <label class="form-label">Confirmar *</label>
                                    <div class="password-wrapper">
                                        <asp:TextBox ID="txtPassConfirm" runat="server" CssClass="form-control" TextMode="Password" placeholder="********"></asp:TextBox>
                                        <button type="button" class="toggle-password" tabindex="-1"><i class="fas fa-eye"></i></button>
                                    </div>
                                    <asp:CompareValidator ID="cvPass" runat="server" ControlToValidate="txtPassConfirm" ControlToCompare="txtPass" Operator="Equal" ErrorMessage="No coinciden." CssClass="val-error" Display="Dynamic" ValidationGroup="Registro"></asp:CompareValidator>
                                </div>
                            </div>

                            <div class="d-grid mb-3">
                                <asp:Button ID="btnRegistrarme" runat="server" Text="Registrarme" 
                                    CssClass="btn btn-custom-teal py-2 fw-bold text-uppercase" 
                                    OnClick="btnRegistrarme_Click" 
                                    OnClientClick="mostrarLoader();" 
                                    ValidationGroup="Registro" />
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </form>

    <script src="Scripts/jquery-3.7.1.min.js"></script>
    <script src="Scripts/bootstrap.bundle.min.js"></script>
    
    <script>
        $(document).ready(function () {
            $('.password-wrapper input').on('focus', function () {
                $(this).siblings('.toggle-password').fadeIn(200);
            });
            $('.password-wrapper input').on('blur', function () {
                var icon = $(this).siblings('.toggle-password');
                setTimeout(function () {
                    if (!icon.is(":focus")) icon.fadeOut(200);
                }, 150);
            });
            $('.toggle-password').on('mousedown', function (e) { e.preventDefault(); });
            $('.toggle-password').on('click', function () {
                var input = $(this).siblings('input');
                var icon = $(this).find('i');
                if (input.attr('type') === 'password') {
                    input.attr('type', 'text');
                    icon.removeClass('fa-eye').addClass('fa-eye-slash');
                } else {
                    input.attr('type', 'password');
                    icon.removeClass('fa-eye-slash').addClass('fa-eye');
                }
            });
        });
    </script>
</body>
</html>