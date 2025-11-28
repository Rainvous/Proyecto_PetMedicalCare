<%@ Page Title="Inicio" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_Inicio.aspx.cs" Inherits="SoftPetWA.Cliente_Inicio" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Inicio
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <style>
        /* Banner */
        .welcome-banner {
            background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
            border-radius: 15px; padding: 2rem; color: white;
            position: relative; overflow: hidden; margin-bottom: 2rem;
            box-shadow: 0 4px 15px rgba(37, 117, 252, 0.3);
        }
        .welcome-banner h2 { font-weight: 700; margin-bottom: 0.5rem; font-size: 1.8rem; }
        .welcome-banner p { font-size: 1rem; opacity: 0.9; margin: 0; }
        .welcome-banner i {
            position: absolute; right: 20px; top: 50%; transform: translateY(-50%);
            font-size: 5rem; opacity: 0.2;
        }

        /* KPIs - Ajustados a 2 columnas */
        .kpi-card {
            background: white; border-radius: 12px; padding: 1.5rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05); border-left: 5px solid;
            display: flex; align-items: center; justify-content: space-between;
            height: 100%; transition: transform 0.2s;
        }
        .kpi-card:hover { transform: translateY(-3px); }
        .kpi-info h6 { font-size: 0.9rem; color: #6c757d; text-transform: uppercase; font-weight: 700; margin-bottom: 0.5rem; }
        .kpi-info h3 { font-size: 2.5rem; font-weight: 800; margin: 0; line-height: 1; color: #343a40; }
        .kpi-icon { width: 60px; height: 60px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-size: 1.8rem; }

        .kpi-primary { border-color: #667eea; } .kpi-primary .kpi-icon { background: #eef2ff; color: #667eea; }
        .kpi-orange { border-color: #fd7e14; } .kpi-orange .kpi-icon { background: #fff4e6; color: #fd7e14; }

        /* Mascotas Cards */
        .pet-dash-card {
            background: white; border-radius: 12px; padding: 1rem;
            box-shadow: 0 2px 8px rgba(0,0,0,0.05); display: flex; align-items: center;
            border: 1px solid #f0f0f0; height: 100%;
        }
        .pet-dash-avatar {
            width: 50px; height: 50px; border-radius: 50%; display: flex; align-items: center; justify-content: center;
            margin-right: 1rem; font-size: 1.2rem; flex-shrink: 0;
        }
        .bg-soft-primary { background-color: #e3f2fd; } .text-primary { color: #1976d2 !important; }
        .bg-soft-warning { background-color: #fff3e0; } .text-warning { color: #ef6c00 !important; }

        /* Citas List */
        .cita-row {
            background: white; border-radius: 10px; padding: 1rem; margin-bottom: 0.8rem;
            display: flex; align-items: center; border: 1px solid #f1f3f9;
            transition: border-color 0.2s;
        }
        .cita-row:hover { border-color: #667eea; }
        .cita-time {
            min-width: 80px; text-align: center; border-right: 2px solid #f8f9fa; margin-right: 1rem;
        }
        .cita-time .hour { display: block; font-size: 1.2rem; font-weight: 800; color: #343a40; line-height: 1; }
        .cita-time .ampm { font-size: 0.75rem; font-weight: 700; color: #adb5bd; }
        
        .badge-soft-warning { background: #fff3cd; color: #ff9800; padding: 4px 8px; border-radius: 4px; font-size: 0.7rem; font-weight: 700; }
        .badge-soft-success { background: #d1e7dd; color: #0f5132; padding: 4px 8px; border-radius: 4px; font-size: 0.7rem; font-weight: 700; }
        .badge-soft-secondary { background: #e2e3e5; color: #383d41; padding: 4px 8px; border-radius: 4px; font-size: 0.7rem; font-weight: 700; }
        .badge-soft-danger { background: #f8d7da; color: #842029; padding: 4px 8px; border-radius: 4px; font-size: 0.7rem; font-weight: 700; }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="container-fluid px-0">
        
        <%-- Banner --%>
        <div class="welcome-banner">
            <div class="position-relative z-index-1">
                <h2>¡Hola, <asp:Label ID="lblNombreCliente" runat="server"></asp:Label>!</h2>
                <p>Aquí tienes el resumen de tu día.</p>
            </div>
            <i class="fas fa-paw"></i>
        </div>

        <%-- KPIs (2 Columnas) --%>
        <div class="row g-4 mb-5">
            <div class="col-md-6">
                <div class="kpi-card kpi-primary">
                    <div class="kpi-info">
                        <h6>Mis Mascotas</h6>
                        <h3><asp:Literal ID="litTotalMascotas" runat="server" Text="0"></asp:Literal></h3>
                    </div>
                    <div class="kpi-icon"><i class="fas fa-dog"></i></div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="kpi-card kpi-orange">
                    <div class="kpi-info">
                        <h6>Citas de Hoy</h6>
                        <h3><asp:Literal ID="litCitasHoy" runat="server" Text="0"></asp:Literal></h3>
                    </div>
                    <div class="kpi-icon"><i class="far fa-calendar-check"></i></div>
                </div>
            </div>
        </div>

        <div class="row g-4">
            <%-- Sección Mascotas --%>
            <div class="col-lg-12">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="fw-bold text-dark m-0"><i class="fas fa-paw me-2 text-primary"></i>Mis Mascotas</h5>
                    <asp:HyperLink runat="server" NavigateUrl="~/Cliente_Mascotas.aspx" CssClass="text-decoration-none fw-bold small">
                        Ver todas <i class="fas fa-arrow-right ms-1"></i>
                    </asp:HyperLink>
                </div>
                
                <div class="row g-3">
                    <asp:Repeater ID="rptrMascotas" runat="server">
                        <ItemTemplate>
                            <div class="col-md-4">
                                <div class="pet-dash-card">
                                    <div class='pet-dash-avatar <%# Eval("AvatarClass") %>'>
                                        <i class='<%# Eval("IconoClass") %>'></i>
                                    </div>
                                    <div>
                                        <h6 class="fw-bold mb-1 text-dark"><%# Eval("Nombre") %></h6>
                                        <small class="d-block text-muted mb-1"><%# Eval("Detalles") %></small>
                                        <small class='fw-bold <%# Eval("EstadoCss") %>'>
                                            <i class="fas fa-circle me-1" style="font-size: 8px;"></i><%# Eval("Estado") %>
                                        </small>
                                    </div>
                                </div>
                            </div>
                        </ItemTemplate>
                        <FooterTemplate>
                            <asp:Label ID="lblNoPets" runat="server" Visible='<%# rptrMascotas.Items.Count == 0 %>' 
                                Text="<div class='col-12 text-muted small fst-italic py-2'>No tienes mascotas registradas.</div>"></asp:Label>
                        </FooterTemplate>
                    </asp:Repeater>
                </div>
            </div>

            <%-- Sección Citas de Hoy --%>
            <div class="col-lg-12 mt-5">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="fw-bold text-dark m-0"><i class="far fa-clock me-2 text-warning"></i>Citas de Hoy</h5>
                    <asp:HyperLink runat="server" NavigateUrl="~/Cliente_AgendaCitas.aspx" CssClass="text-decoration-none fw-bold small">
                        Nueva Cita <i class="fas fa-plus ms-1"></i>
                    </asp:HyperLink>
                </div>

                <asp:Repeater ID="rptrCitasHoy" runat="server">
                    <ItemTemplate>
                        <div class="cita-row">
                            <div class="cita-time">
                                <span class="hour"><%# Eval("Hora") %></span>
                                <span class="ampm"><%# Eval("AmPm") %></span>
                            </div>
                            <div class="flex-grow-1">
                                <div class="d-flex align-items-center mb-1">
                                    <h6 class="fw-bold mb-0 text-dark me-2"><%# Eval("Servicio") %></h6>
                                    <span class='<%# Eval("EstadoCss") %>'><%# Eval("Estado") %></span>
                                </div>
                                <div class="small text-muted">
                                    <i class="fas fa-user-md me-1"></i> <%# Eval("Doctor") %>
                                    <span class="mx-2">|</span>
                                    <i class="fas fa-paw me-1"></i> Paciente: <strong><%# Eval("Mascota") %></strong>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                    <FooterTemplate>
                        <%-- Mensaje cuando no hay citas --%>
                        <asp:Panel ID="pnlNoCitas" runat="server" Visible='<%# rptrCitasHoy.Items.Count == 0 %>'>
                            <div class="alert alert-light text-center border mb-0 text-muted">
                                <i class="far fa-calendar-times me-2"></i>No tienes citas programadas para hoy.
                            </div>
                        </asp:Panel>
                    </FooterTemplate>
                </asp:Repeater>
            </div>
        </div>

    </div>
</asp:Content>