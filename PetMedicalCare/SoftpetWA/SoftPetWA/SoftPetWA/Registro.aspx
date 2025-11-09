<%-- Registro.aspx (Versión 2 - Basada en image_f3f2dd.png) --%>
<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Registro.aspx.cs" Inherits="SoftPetWA.Registro" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>PetMedicalCare - Registro</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <link href="Content/bootstrap.css" rel="stylesheet" />
    <link href="Content/Fonts/css/all.css" rel="stylesheet" />
    
    <link href="Content/login.css" rel="stylesheet" />
</head>
<body class="login-background">

    <form id="formRegistro" runat="server">

        <div class="card login-card">
            <div class="row g-0">
                
                <div class="col-lg-6 d-none d-lg-block">
                    <div class="login-promo-panel promo-centered"> 
                        
                        <i class="fas fa-heart-pulse logo-icon"></i>
                        <h2>PetMedicalCare</h2>
                        <p>Sistema Integral de Gestión Veterinaria para Clave Canina</p>
                        
                        <img src="Images/pet_login.png" alt="Veterinaria" class="img-fluid" />
                    </div>
                </div>

                <div class="col-lg-6">
                    <div class="registro-form-panel"> 
                        <h3>Crea tu cuenta</h3>
                        <p class="text-muted">Regístrate para gestionar tus citas y mascotas.</p>

                        <div class="mb-3">
                            <asp:Label ID="lblRol" runat="server" Text="Tipo de Usuario" CssClass="form-label fw-bold"></asp:Label>
                            <asp:DropDownList ID="ddlRol" runat="server" CssClass="form-select">
                                <asp:ListItem Text="-- Seleccionar rol --" Value=""></asp:ListItem>
                                <asp:ListItem Text="Soy Cliente" Value="CLIENTE"></asp:ListItem>
                                <asp:ListItem Text="Soy Veterinario" Value="VETERINARIO"></asp:ListItem>
                            </asp:DropDownList>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <asp:Label ID="lblNombres" runat="server" Text="Nombres" CssClass="form-label fw-bold"></asp:Label>
                                <div class="form-field-icon">
                                    <i class="fas fa-user form-icon"></i>
                                    <asp:TextBox ID="txtNombres" runat="server" CssClass="form-control" placeholder="Ej. Ana"></asp:TextBox>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <asp:Label ID="lblApellidos" runat="server" Text="Apellidos" CssClass="form-label fw-bold"></asp:Label>
                                <div class="form-field-icon">
                                    <i class="fas fa-user form-icon"></i>
                                    <asp:TextBox ID="txtApellidos" runat="server" CssClass="form-control" placeholder="Ej. Solís"></asp:TextBox>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-3">
                            <div class="col-md-6">
                                <asp:Label ID="lblEmail" runat="server" Text="Correo Electrónico" CssClass="form-label fw-bold"></asp:Label>
                                <div class="form-field-icon">
                                    <i class="fas fa-envelope form-icon"></i>
                                    <asp:TextBox ID="txtEmail" runat="server" CssClass="form-control" placeholder="tu@correo.com" TextMode="Email"></asp:TextBox>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <asp:Label ID="lblTelefono" runat="server" Text="Teléfono" CssClass="form-label fw-bold"></asp:Label>
                                <div class="form-field-icon">
                                    <i class="fas fa-phone form-icon"></i>
                                    <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control" placeholder="987654321"></asp:TextBox>
                                </div>
                            </div>
                        </div>

                        <div class="row mb-4">
                            <div class="col-md-6">
                                <asp:Label ID="lblContrasena" runat="server" Text="Contraseña" CssClass="form-label fw-bold"></asp:Label>
                                <div class="form-field-icon">
                                    <i class="fas fa-lock form-icon"></i>
                                    <asp:TextBox ID="txtContrasena" runat="server" CssClass="form-control" TextMode="Password"></asp:TextBox>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <asp:Label ID="lblConfirmarContrasena" runat="server" Text="Confirmar Contraseña" CssClass="form-label fw-bold"></asp:Label>
                                <div class="form-field-icon">
                                    <i class="fas fa-lock form-icon"></i>
                                    <asp:TextBox ID="txtConfirmarContrasena" runat="server" CssClass="form-control" TextMode="Password"></asp:TextBox>
                                </div>
                            </div>
                        </div>

                        <div class="d-grid mb-3">
                            <asp:Button ID="btnRegistrarme" runat="server" Text="Registrarme" CssClass="btn btn-custom-teal" OnClick="btnRegistrarme_Click"/>
                        </div>

                        <div class="text-center">
                            <asp:HyperLink ID="hlLogin" runat="server" NavigateUrl="~/Login.aspx">¿Ya tienes una cuenta? Inicia Sesión</asp:HyperLink>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </form>
    
    <script src="Scripts/jquery-3.7.1.js"></script>
    <script src="Scripts/bootstrap.bundle.js"></script>
</body>
</html>