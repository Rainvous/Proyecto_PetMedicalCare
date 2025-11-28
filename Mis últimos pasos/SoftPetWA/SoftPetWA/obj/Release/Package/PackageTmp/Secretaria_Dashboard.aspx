<%@ Page Title="Dashboard Secretaria" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" CodeBehind="Secretaria_Dashboard.aspx.cs" Inherits="SoftPetWA.Secretaria_Dashboard" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Dashboard General
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <style>
        /* --- ESTILOS OPTIMIZADOS (VETERINARIO STYLE) --- */
        .dash-container { padding: 0 5px; }
        
        /* Banner Principal (Compacto) */
        .dash-welcome-banner {
            background: linear-gradient(135deg, #00bcd4 0%, #0288d1 100%);
            color: white; border-radius: 12px; padding: 1.5rem 2rem;
            margin-bottom: 1.5rem; box-shadow: 0 8px 20px rgba(0, 188, 212, 0.2);
            position: relative; overflow: hidden;
        }
        .dash-welcome-banner h2 { font-weight: 700; font-size: 1.6rem; margin-bottom: 0.3rem; }
        .dash-welcome-banner p { color: rgba(255, 255, 255, 0.9); font-size: 0.95rem; margin: 0; }
        .dash-welcome-banner::after {
            content: ''; position: absolute; top: -40px; right: -40px; width: 200px; height: 200px;
            background: rgba(255, 255, 255, 0.1); border-radius: 50%;
        }

        /* Tarjetas de KPI */
        .dash-stat-card {
            background: white; border-radius: 12px; padding: 1rem 1.2rem;
            display: flex; align-items: center; justify-content: space-between;
            box-shadow: 0 4px 15px rgba(0,0,0,0.04); height: 100%;
            border-left: 5px solid transparent; transition: transform 0.2s ease;
        }
        .dash-stat-card:hover { transform: translateY(-3px); }
        
        .dash-stat-info span { display: block; color: #8898aa; font-size: 0.75rem; font-weight: 700; text-transform: uppercase; letter-spacing: 0.5px; margin-bottom: 3px; }
        .dash-stat-info h3 { font-size: 1.8rem; font-weight: 800; margin: 0; line-height: 1.1; }
        .dash-stat-icon { font-size: 1.5rem; opacity: 0.3; }

        .stat-primary { border-left-color: #00bcd4; } .stat-primary h3 { color: #00bcd4; }
        .stat-finance { border-left-color: #2dce89; } .stat-finance h3 { color: #2dce89; }
        .stat-purple  { border-left-color: #8898aa; } .stat-purple h3 { color: #5e72e4; }

        /* Títulos de Sección */
        .section-title { color: #32325d; font-weight: 800; font-size: 1.1rem; margin-bottom: 1rem; display: flex; align-items: center; }
        .section-title i { margin-right: 0.5rem; color: #00bcd4; }

        /* Items de Agenda */
        .dash-appt-item {
            background: white; border-radius: 10px; padding: 1rem; margin-bottom: 0.8rem;
            display: flex; align-items: center; justify-content: space-between;
            box-shadow: 0 2px 8px rgba(0,0,0,0.02); border: 1px solid #f1f3f9;
            transition: all 0.2s;
        }
        .dash-appt-item:hover { border-color: #00bcd4; transform: translateX(3px); }

        .appt-time { 
            font-weight: 700; color: #2c3e50; font-size: 0.95rem; min-width: 70px; 
            text-align: center; border-right: 2px solid #f1f3f9; margin-right: 1rem; padding-right: 10px; 
        }
        .appt-details h6 { margin: 0 0 3px 0; font-weight: 700; color: #32325d; font-size: 0.95rem; }
        .appt-details small { color: #8898aa; font-size: 0.8rem; display: block; }
        
        /* --- BADGES (Etiquetas) --- */
        .badge-soft { 
            padding: 5px 10px; /* Un poco más de padding para que se vea como en la imagen */
            border-radius: 6px; 
            font-size: 0.65rem; 
            font-weight: 800; 
            text-transform: uppercase; 
            display: inline-block;
        }
        /* Colores */
        .bg-warning-soft { background: #fff3cd; color: #ff9800; } /* Amarillo (Pendiente) */
        .bg-success-soft { background: #d1e7dd; color: #0f5132; } /* Verde (Activo/Atendida) */
        .bg-danger-soft  { background: #f8d7da; color: #dc3545; } /* Rojo (Cancelada) */
        .bg-secondary-soft { background: #e9ecef; color: #495057; }

        /* Lista de Veterinarios y Finanzas */
        .mini-card-list { background: white; border-radius: 12px; padding: 1.2rem; box-shadow: 0 4px 15px rgba(0,0,0,0.04); }
        
        .vet-item { display: flex; align-items: center; padding: 0.8rem 0; border-bottom: 1px solid #f6f9fc; }
        .vet-item:last-child { border-bottom: none; }
        
        .vet-avatar {
            width: 35px; height: 35px; border-radius: 50%; color: white;
            display: flex; align-items: center; justify-content: center;
            font-size: 0.8rem; font-weight: bold; margin-right: 12px;
        }
        .vet-info h6 { margin: 0; font-size: 0.85rem; font-weight: 700; color: #32325d; }
        .vet-info span { font-size: 0.75rem; color: #8898aa; }

        /* Tabla Finanzas */
        .finance-row { display: flex; justify-content: space-between; padding: 0.6rem 0; border-bottom: 1px dashed #e9ecef; font-size: 0.85rem; }
        .finance-row:last-child { border-bottom: none; font-weight: 700; color: #2dce89; font-size: 1rem; padding-top: 1rem; }
        
        .btn-link-custom { font-size: 0.8rem; font-weight: 700; color: #5e72e4; text-decoration: none; }
        .btn-link-custom:hover { text-decoration: underline; }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="dash-container">
        
        <div class="dash-welcome-banner">
            <div class="row align-items-center">
                <div class="col-md-8">
                    <h2>¡Hola, <asp:Literal ID="litNombreSecretaria" runat="server" Text="Secretaria"></asp:Literal>!</h2>
                    <p>Aquí tienes el resumen operativo de la clínica para hoy.</p>
                </div>
                <div class="col-md-4 text-md-end d-none d-md-block">
                    <i class="fas fa-notes-medical fa-3x" style="opacity: 0.2;"></i>
                </div>
            </div>
        </div>

        <div class="row mb-4 g-3">
            <div class="col-md-4">
                <div class="dash-stat-card stat-primary">
                    <div class="dash-stat-info">
                        <span>Citas Hoy</span>
                        <h3><asp:Literal ID="litCitasHoy" runat="server" Text="0"></asp:Literal></h3>
                    </div>
                    <div class="dash-stat-icon"><i class="fas fa-calendar-day text-info"></i></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dash-stat-card stat-finance">
                    <div class="dash-stat-info">
                        <span>Ingresos Hoy</span>
                        <h3><asp:Literal ID="litIngresosHoy" runat="server" Text="S/ 0.00"></asp:Literal></h3>
                    </div>
                    <div class="dash-stat-icon"><i class="fas fa-dollar-sign text-success"></i></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dash-stat-card stat-purple">
                    <div class="dash-stat-info">
                        <span>Vets. Disponibles</span>
                        <h3><asp:Literal ID="litVeterinariosActivos" runat="server" Text="0"></asp:Literal></h3>
                    </div>
                    <div class="dash-stat-icon"><i class="fas fa-user-md text-primary"></i></div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-8 mb-4">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <div class="section-title mb-0"><i class="far fa-calendar-alt"></i> Agenda del Día</div>
                    <a href="Secretaria_AgendaCitas.aspx" class="btn-link-custom">Ver calendario completo <i class="fas fa-arrow-right"></i></a>
                </div>

                <asp:Repeater ID="rptAgendaDia" runat="server">
                    <ItemTemplate>
                        <div class="dash-appt-item">
                            <div class="d-flex align-items-center flex-grow-1">
                                <div class="appt-time">
                                    <%# Eval("Hora") %>
                                </div>
                                <div class="appt-details ms-2">
                                    <h6><%# Eval("Encabezado") %></h6> <%-- Mascota - Servicio --%>
                                    <small><%# Eval("Subtitulo") %></small> <%-- Cliente - Dr --%>
                                </div>
                            </div>
                            <div class="ms-3 text-end">
                                <span class='badge-soft <%# ObtenerClaseBadge(Eval("Estado").ToString()) %>'>
                                    <%# Eval("Estado") %>
                                </span>
                            </div>
                        </div>
                    </ItemTemplate>
                    <FooterTemplate>
                        <asp:Panel ID="pnlNoData" runat="server" Visible='<%# rptAgendaDia.Items.Count == 0 %>'>
                            <div class="text-center py-4 text-muted bg-white rounded shadow-sm">
                                <i class="fas fa-coffee mb-2"></i>
                                <p class="mb-0 small">No hay citas programadas para hoy.</p>
                            </div>
                        </asp:Panel>
                    </FooterTemplate>
                </asp:Repeater>
            </div>

            <div class="col-lg-4">
                
                <div class="mb-4">
                     <div class="section-title"><i class="fas fa-cash-register"></i> Caja Diaria</div>
                     <div class="mini-card-list">
                         <asp:Repeater ID="rptResumenFinanciero" runat="server">
                             <ItemTemplate>
                                 <div class="finance-row">
                                     <span class="text-muted"><%# Eval("Concepto") %></span>
                                     <span class="fw-bold text-dark"><%# Eval("MontoFormateado") %></span>
                                 </div>
                             </ItemTemplate>
                         </asp:Repeater>
                     </div>
                </div>

                <div>
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div class="section-title mb-0"><i class="fas fa-users"></i> Staff Médico</div>
                        <a href="Secretaria_Veterinarios.aspx" class="btn-link-custom">Ver todos</a>
                    </div>
                    
                    <div class="mini-card-list p-0">
                        <div class="p-3">
                            <asp:Repeater ID="rptEstadoVeterinarios" runat="server">
                                <ItemTemplate>
                                    <div class="vet-item">
                                        <div class="vet-avatar" style='background-color: <%# Eval("ColorAvatar") %>'>
                                            <%# Eval("Iniciales") %>
                                        </div>
                                        <div class="vet-info flex-grow-1">
                                            <h6><%# Eval("NombreCompleto") %></h6>
                                            <span><%# Eval("Especialidad") %></span>
                                        </div>
                                        
                                        <span class="badge-soft bg-success-soft">Activo</span>

                                    </div>
                                </ItemTemplate>
                            </asp:Repeater>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</asp:Content>