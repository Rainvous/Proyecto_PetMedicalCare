<%-- Nombre del archivo: Cliente_Perfil.aspx --%>
<%@ Page Title="Mi Perfil" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_Perfil.aspx.cs" 
    Inherits="SoftPetWA.Cliente_Perfil" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Mi Perfil
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico --%>
    <link href="Content/perfilcliente.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-user-circle me-2"></i>Mi Perfil</h3>
    </div>

    <%-- 1. Profile Header Card --%>
    <div class="profile-header-card">
        <div class="profile-avatar-lg">
            <asp:Label ID="lblAvatarIniciales" runat="server" Text="MG"></asp:Label>
        </div>
        <div class="profile-info">
            <h3><asp:Label ID="lblNombreCompletoHeader" runat="server"></asp:Label></h3>
            <span class="email"><asp:Label ID="lblEmailHeader" runat="server"></asp:Label></span>
            <div class="profile-stats">
                <span><i class="fas fa-paw"></i> <asp:Label ID="lblNumMascotasHeader" runat="server"></asp:Label> Mascotas</span>
                <span><i class="fas fa-calendar-check"></i> <asp:Label ID="lblNumCitasHeader" runat="server"></asp:Label> Citas realizadas</span>
                <span><i class="fas fa-user-clock"></i> Miembro desde <asp:Label ID="lblMiembroDesdeHeader" runat="server"></asp:Label></span>
            </div>
        </div>
    </div>

    <%-- 2. Profile Content Area --%>
    <div class="profile-content">
        
        <%-- Left: Settings Navigation --%>
        <div class="profile-settings-nav">
            <ul class="nav flex-column">
                <li class="nav-item">
                    <a class="nav-link active" href="#"> <%-- Active state on Information Personal --%>
                        <i class="fas fa-user"></i> Información Personal
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-shield-alt"></i> Seguridad
                    </a>
                </li>
                 <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-bell"></i> Notificaciones
                    </a>
                </li>
                 <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-credit-card"></i> Métodos de Pago
                    </a>
                </li>
                 <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-history"></i> Historial de Pagos
                    </a>
                </li>
                 <li class="nav-item">
                    <a class="nav-link" href="#">
                        <i class="fas fa-sliders-h"></i> Preferencias
                    </a>
                </li>
                <hr />
                 <li class="nav-item">
                    <asp:LinkButton ID="lnkCerrarSesion" runat="server" CssClass="nav-link nav-link-logout" OnClick="lnkCerrarSesion_Click">
                        <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
                    </asp:LinkButton>
                </li>
            </ul>
        </div>

        <%-- Right: Details Panel --%>
        <div class="profile-details-panel">
            <div class="profile-details-header">
                <h5><i class="fas fa-info-circle me-2"></i>Información Personal</h5>
                <asp:LinkButton ID="btnEditar" runat="server" CssClass="btn btn-outline-secondary btn-sm" OnClick="btnEditar_Click">
                    <i class="fas fa-pencil-alt me-1"></i> Editar
                </asp:LinkButton>
            </div>

            <div class="row">
                <div class="col-md-6">
                    <div class="info-field">
                        <span class="label">Nombre Completo</span>
                        <span class="value"><asp:Label ID="lblNombreCompletoInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
                 <div class="col-md-6">
                    <div class="info-field">
                        <span class="label">DNI</span>
                        <span class="value"><asp:Label ID="lblDNIInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
                 <div class="col-md-6">
                    <div class="info-field">
                        <span class="label">Correo Electrónico</span>
                        <span class="value"><asp:Label ID="lblCorreoInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
                 <div class="col-md-6">
                    <div class="info-field">
                        <span class="label">Teléfono</span>
                        <span class="value"><asp:Label ID="lblTelefonoInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
                 <div class="col-md-6">
                    <div class="info-field">
                        <span class="label">Fecha de Nacimiento</span>
                        <span class="value"><asp:Label ID="lblFechaNacInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="info-field">
                        <span class="label">Género</span>
                        <span class="value"><asp:Label ID="lblGeneroInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
                 <div class="col-12">
                    <div class="info-field">
                        <span class="label">Dirección</span>
                        <span class="value"><asp:Label ID="lblDireccionInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
                 <div class="col-md-6">
                    <div class="info-field">
                        <span class="label">Distrito</span>
                        <span class="value"><asp:Label ID="lblDistritoInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="info-field">
                        <span class="label">Código Postal</span>
                        <span class="value"><asp:Label ID="lblCodigoPostalInfo" runat="server"></asp:Label></span>
                    </div>
                </div>
            </div>
        </div>
    </div>

</asp:Content>