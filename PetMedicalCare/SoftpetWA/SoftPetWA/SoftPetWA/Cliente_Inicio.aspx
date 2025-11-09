<%-- Nombre del archivo: Cliente_Inicio.aspx --%>
<%@ Page Title="Inicio" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_Inicio.aspx.cs" 
    Inherits="SoftPetWA.Cliente_Inicio" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Inicio
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/clientedashboard.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <%-- CÓDIGO Para el nombre del cliente de Inicio--%>
<h1 class="mt-4 display-4">¡Hola, <asp:Label ID="lblNombreCliente" runat="server"></asp:Label>!</h1>
    <p class="text-muted mb-4">Bienvenido a tu portal de atención veterinaria</p>

    <%-- 1. Tarjetas de Acción --%>
    <div class="row mb-4">
        <div class="col-lg-3 col-md-6 mb-3">
            <a href="#" class="text-decoration-none">
                <div class="action-card">
                    <div class="action-card-icon bg-blue"><i class="fas fa-calendar-plus"></i></div>
                    <div class="action-card-info">
                        <h5>Reservar Cita</h5>
                        <p>Agenda una consulta para tus mascotas de forma rápida y sencilla</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-3 col-md-6 mb-3">
            <a href="#" class="text-decoration-none">
                <div class="action-card">
                    <div class="action-card-icon bg-green"><i class="fas fa-notes-medical"></i></div>
                    <div class="action-card-info">
                        <h5>Ver Historial Médico</h5>
                        <p>Consulta el historial completo de atenciones de tus mascotas</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-3 col-md-6 mb-3">
            <a href="#" class="text-decoration-none">
                <div class="action-card">
                    <div class="action-card-icon bg-purple"><i class="fas fa-pills"></i></div>
                    <div class="action-card-info">
                        <h5>Recetas y Tratamientos</h5>
                        <p>Revisa las recetas médicas y tratamientos indicados</p>
                    </div>
                </div>
            </a>
        </div>
        <div class="col-lg-3 col-md-6 mb-3">
            <a href="#" class="text-decoration-none">
                <div class="action-card">
                    <div class="action-card-icon bg-orange"><i class="fas fa-info-circle"></i></div>
                    <div class="action-card-info">
                        <h5>Información de Contacto</h5>
                        <p>Horarios, ubicación y teléfonos de la clínica</p>
                    </div>
                </div>
            </a>
        </div>
    </div>

    <div class="row">
        <%-- ======================================= --%>
        <%--      COLUMNA IZQUIERDA: MASCOTAS Y CITAS  --%>
        <%-- ======================================= --%>
        <div class="col-lg-8">
            <%-- Sección Mis Mascotas --%>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="mb-0"><i class="fas fa-paw me-2"></i>Mis Mascotas</h4>
                <asp:HyperLink NavigateUrl="~/Cliente_Mascotas.aspx" CssClass="btn btn-link btn-sm" runat="server">
                    Ver Todas <i class="fas fa-arrow-right ms-1"></i>
                </asp:HyperLink>
            </div>
            <div class="row mb-4">
                <asp:Repeater ID="rptMascotas" runat="server">
                    <ItemTemplate>
                        <div class="col-md-4 mb-3">
                            <div class="pet-card-sm">
                                <div class="pet-avatar-sm">
                                    <asp:Image ID="imgAvatarPetSm" runat="server" ImageUrl='<%# Eval("AvatarURL") %>' />
                                </div>
                                <h6 class="pet-name-sm"><%# Eval("Nombre") %></h6>
                                <span class="pet-breed-sm d-block"><%# Eval("EspecieRaza") %></span>
                                <span class="badge rounded-pill <%# Eval("EstadoCss") %>"><%# Eval("Estado") %></span>
                            </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>

            <%-- Sección Próximas Citas --%>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="mb-0"><i class="fas fa-calendar-alt me-2"></i>Próximas Citas</h4>
                <a href="#" class="btn btn-link btn-sm">Ver Todas <i class="fas fa-arrow-right ms-1"></i></a>
            </div>
            <div>
                <asp:Repeater ID="rptProximasCitas" runat="server">
                    <ItemTemplate>
                        <div class="appointment-item <%# Eval("CssEstado") %>">
                            <div class="appointment-date">
                                <span class="day"><%# Eval("Dia") %></span>
                                <span class="month"><%# Eval("Mes") %></span>
                            </div>
                            <div class="appointment-info flex-grow-1">
                                <p class="time-doctor"><%# Eval("HoraDoctor") %></p>
                                <p class="pet-service"><%# Eval("MascotaServicio") %></p>
                            </div>
                            <span class="badge rounded-pill <%# Eval("Estado") == "Confirmada" ? "bg-success" : "bg-warning" %>">
                                <%# Eval("Estado") %>
                            </span>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>
        </div>

        <%-- ======================================= --%>
        <%--      COLUMNA DERECHA: NOTIFICACIONES    --%>
        <%-- ======================================= --%>
        <div class="col-lg-4">
             <div class="card shadow-sm border-0">
                <div class="card-header bg-light">
                    <h5 class="mb-0"><i class="fas fa-bell me-2"></i>Notificaciones</h5>
                </div>
                <div class="card-body">
                    <asp:Repeater ID="rptNotificaciones" runat="server">
                        <ItemTemplate>
                            <div class="notification-item">
                                <div class="notification-icon <%# Eval("IconoColorCss") %>">
                                    <i class="<%# Eval("IconoCss") %>"></i>
                                </div>
                                <div class="notification-content">
                                    <p class="title"><%# Eval("Titulo") %></p>
                                    <p class="message"><%# Eval("Mensaje") %></p>
                                    <p class="time"><%# Eval("Tiempo") %></p>
                                </div>
                            </div>
                        </ItemTemplate>
                    </asp:Repeater>
                </div>
             </div>
        </div>
    </div>

</asp:Content>