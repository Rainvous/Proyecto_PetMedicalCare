<%@ Page Title="Inicio Veterinario" Language="C#" MasterPageFile="~/Veterinario.Master" AutoEventWireup="true" CodeBehind="Veterinario_Inicio.aspx.cs" Inherits="SoftPetWA.Veterinario_Inicio" %>

<asp:Content ID="ContentTitulo" ContentPlaceHolderID="cphTitulo" runat="server">
    Inicio Veterinario
</asp:Content>

<asp:Content ID="ContentHead" ContentPlaceHolderID="cphHead" runat="server">
    <style>
        .dash-container { padding: 0 10px; }
        .dash-welcome-banner {
            background: linear-gradient(135deg, #00bcd4 0%, #0288d1 100%);
            color: white; border-radius: 15px; padding: 3rem 2.5rem;
            margin-bottom: 2.5rem; box-shadow: 0 10px 25px rgba(0, 188, 212, 0.25);
            position: relative; overflow: hidden;
        }
        .dash-welcome-banner h1 { color: white; font-weight: 800; font-size: 2.5rem; margin-bottom: 0.5rem; }
        .dash-welcome-banner p { color: rgba(255, 255, 255, 0.9); font-size: 1.2rem; margin: 0; }
        .dash-welcome-banner::after {
            content: ''; position: absolute; top: -50px; right: -50px; width: 300px; height: 300px;
            background: rgba(255, 255, 255, 0.1); border-radius: 50%;
        }
        .dash-stat-card {
            background: white; border-radius: 16px; padding: 1.5rem 2rem;
            display: flex; align-items: center; justify-content: space-between;
            box-shadow: 0 5px 20px rgba(0,0,0,0.06); height: 100%;
            border-left: 6px solid transparent; transition: transform 0.3s ease;
        }
        .dash-stat-card:hover { transform: translateY(-5px); }
        .dash-stat-info span { display: block; color: #8898aa; font-size: 0.85rem; font-weight: 700; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 5px; }
        .dash-stat-info h3 { font-size: 2.8rem; font-weight: 800; margin: 0; line-height: 1; }
        .dash-stat-icon { font-size: 2rem; opacity: 0.3; }
        .stat-primary { border-left-color: #00bcd4; } .stat-primary h3 { color: #00bcd4; }
        .stat-warning { border-left-color: #fb6340; } .stat-warning h3 { color: #fb6340; }
        .stat-success { border-left-color: #2dce89; } .stat-success h3 { color: #2dce89; }
        .filter-btn-group .btn {
            border-radius: 30px; padding: 8px 20px; font-size: 0.85rem; font-weight: 700;
            margin-left: 8px; border: 1px solid transparent; transition: all 0.2s;
            text-decoration: none; display: inline-block;
        }
        .btn-active { background-color: #00bcd4; color: white !important; box-shadow: 0 4px 10px rgba(0, 188, 212, 0.3); }
        .btn-inactive { background-color: white; color: #8898aa !important; border-color: #e9ecef; }
        .btn-inactive:hover { border-color: #00bcd4; color: #00bcd4 !important; background-color: #fbfbfb; }
        .dash-appt-item {
            background: white; border-radius: 12px; padding: 1.5rem; margin-bottom: 1rem;
            display: flex; align-items: center; justify-content: space-between;
            box-shadow: 0 3px 10px rgba(0,0,0,0.03); border: 1px solid #f1f3f9;
            transition: all 0.2s;
        }
        .dash-appt-item:hover { border-color: #00bcd4; transform: translateX(5px); }
        .appt-time { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; font-weight: 800; color: #2c3e50; font-size: 1.3rem; min-width: 90px; text-align: center; border-right: 3px solid #f1f3f9; margin-right: 1rem; line-height: 1; padding-right: 10px; }
        .appt-time small { display: block; font-size: 0.75rem; color: #95a5a6; font-weight: 700; margin-top: 4px; text-transform: uppercase; }
        .appt-avatar {
            width: 55px; height: 55px; border-radius: 50%;
            display: flex; align-items: center; justify-content: center;
            font-size: 1.6rem; margin-right: 20px; flex-shrink: 0;
            box-shadow: 0 3px 6px rgba(0,0,0,0.05);
        }
        .avatar-dog { background-color: #e3f2fd; color: #1565c0; border: 2px solid #bbdefb; }
        .avatar-cat { background-color: #fff3e0; color: #ef6c00; border: 2px solid #ffe0b2; }
        .avatar-default { background-color: #f5f5f5; color: #757575; border: 2px solid #e0e0e0; }
        .appt-details h5 { margin: 0 0 5px 0; font-weight: 700; color: #32325d; font-size: 1.1rem; }
        .appt-details p { margin: 0; color: #8898aa; font-size: 0.9rem; }
        .appt-owner { font-size: 0.85rem; color: #00bcd4; font-weight: 600; margin-top: 5px; display: flex; align-items: center; }
        .btn-action { border-radius: 8px; padding: 8px 20px; font-weight: 700; font-size: 0.85rem; text-transform: uppercase; letter-spacing: 0.5px; box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11); border: none; cursor: pointer; }
        .btn-primary-soft { background-color: #00bcd4; color: white; }
        .btn-success-soft { background-color: #2dce89; color: white; }
        .badge-soft { padding: 5px 10px; border-radius: 6px; font-size: 0.7rem; font-weight: 800; text-transform: uppercase; margin-left: 10px; vertical-align: middle; }
        .badge-soft-warning { background: #fff3cd; color: #ff9800; }
        .badge-soft-success { background: #d1e7dd; color: #0f5132; }
        .badge-soft-secondary { background: #e9ecef; color: #495057; }
        .badge-soft-danger { background: #f8d7da; color: #dc3545; }
        .card-weekly-total {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white; border: none; border-radius: 15px; padding: 2rem 1.5rem;
            text-align: center; box-shadow: 0 10px 20px rgba(118, 75, 162, 0.2); margin-bottom: 1rem; position: relative; overflow: hidden;
        }
        .card-weekly-total::before { content: ''; position: absolute; top: -20px; left: -20px; width: 100px; height: 100px; background: rgba(255,255,255,0.1); border-radius: 50%; }
        .card-weekly-total h2 { font-size: 3.5rem; font-weight: 800; margin: 10px 0; color: white; text-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .card-weekly-total h6 { font-weight: 700; letter-spacing: 1px; opacity: 0.9; font-size: 0.9rem; }
        .card-weekly-mini {
            background: white; border: none; border-radius: 15px; padding: 1.2rem;
            display: flex; align-items: center; justify-content: space-between;
            box-shadow: 0 5px 15px rgba(0,0,0,0.05); transition: transform 0.3s;
        }
        .card-weekly-mini:hover { transform: translateY(-3px); }
        .weekly-icon { width: 45px; height: 45px; border-radius: 12px; display: flex; align-items: center; justify-content: center; font-size: 1.2rem; }
        .icon-vac { background-color: #e0f2f1; color: #009688; }
        .icon-cir { background-color: #ffebee; color: #e53935; }
        .weekly-data h3 { font-size: 1.8rem; font-weight: 800; margin: 0; line-height: 1; color: #333; }
        .weekly-data span { font-size: 0.75rem; font-weight: 700; text-transform: uppercase; color: #8898aa; }
        .empty-state { text-align: center; padding: 3rem; background: white; border-radius: 15px; border: 2px dashed #e9ecef; margin-top: 20px; }
        .empty-state i { color: #e9ecef; margin-bottom: 1rem; }
        .empty-state h5 { color: #8898aa; font-weight: 600; }
    </style>
</asp:Content>

<asp:Content ID="ContentMain" ContentPlaceHolderID="cphContenido" runat="server">
    <div class="dash-container">
        <div class="dash-welcome-banner">
            <h1>¡Hola, <asp:Literal ID="litNombreVeterinario" runat="server" Text="Doctor"></asp:Literal>!</h1>
            <p>Tienes <asp:Literal ID="litNumCitas" runat="server" Text="0"></asp:Literal> citas programadas para hoy. ¡A por ello!</p>
        </div>

        <div class="row mb-5">
            <div class="col-md-4 mb-3 mb-md-0">
                <div class="dash-stat-card stat-primary">
                    <div class="dash-stat-info">
                        <span>Citas Hoy</span>
                        <h3><asp:Literal ID="litCitasHoy" runat="server" Text="0"></asp:Literal></h3>
                    </div>
                    <div class="dash-stat-icon"><i class="fas fa-calendar-day"></i></div>
                </div>
            </div>
            <div class="col-md-4 mb-3 mb-md-0">
                <div class="dash-stat-card stat-warning">
                    <div class="dash-stat-info">
                        <span>Pendientes</span>
                        <h3><asp:Literal ID="litCitasPendientes" runat="server" Text="0"></asp:Literal></h3>
                    </div>
                    <div class="dash-stat-icon"><i class="far fa-clock"></i></div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="dash-stat-card stat-success">
                    <div class="dash-stat-info">
                        <span>Atendidas</span>
                        <h3><asp:Literal ID="litCitasAtendidas" runat="server" Text="0"></asp:Literal></h3>
                    </div>
                    <div class="dash-stat-icon"><i class="fas fa-check-circle"></i></div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-8">
                <div class="d-flex flex-wrap justify-content-between align-items-center mb-4">
                     <h4 class="mb-0" style="color: #32325d; font-weight: 800;"><i class="far fa-calendar-alt me-2"></i>Agenda del Día</h4>
                     <div class="filter-btn-group mt-2 mt-sm-0">
                         <asp:LinkButton ID="btnFiltroTodos" runat="server" OnClick="btnFiltroTodos_Click" CssClass="btn btn-active">Todos</asp:LinkButton>
                         <asp:LinkButton ID="btnFiltroPendientes" runat="server" OnClick="btnFiltroPendientes_Click" CssClass="btn btn-inactive">Pendientes</asp:LinkButton>
                         <asp:LinkButton ID="btnFiltroAtendidas" runat="server" OnClick="btnFiltroAtendidas_Click" CssClass="btn btn-inactive">Atendidas</asp:LinkButton>
                     </div>
                </div>

                <asp:Repeater ID="rptrCitas" runat="server" OnItemCommand="rptrCitas_ItemCommand">
                    <ItemTemplate>
                        <div class="dash-appt-item">
                            <div class="d-flex align-items-center flex-grow-1">
                                <div class="appt-time">
                                    <%# Eval("HoraCita") %>
                                    <small><%# Eval("AmPm") %></small>
                                </div>
                                <div class='appt-avatar <%# Eval("ClaseAvatar") %>'>
                                    <i class='<%# Eval("IconoMascota") %>'></i>
                                </div>
                                <div class="appt-details">
                                    <div class="d-flex align-items-center">
                                        <h5><%# Eval("NombrePaciente") %></h5>
                                        <span class='badge-soft <%# Eval("EstadoCss") %>'><%# Eval("EstadoCita") %></span>
                                    </div>
                                    <p><%# Eval("DetallesPaciente") %></p>
                                    <div class="appt-owner"><i class="far fa-user me-2"></i> <%# Eval("NombreCliente") %></div>
                                </div>
                            </div>
                            <div class="appt-actions ms-3">
                                <asp:Button ID="btnAccionPrincipal" runat="server" 
                                    Text='<%# Eval("BotonPrincipalTexto") %>' 
                                    CommandName="Atender" 
                                    CommandArgument='<%# Eval("IdCita") %>' 
                                    CssClass='<%# "btn-action " + (Eval("EstadoCita").ToString().Contains("ATENDIDA") ? "btn-success-soft" : "btn-primary-soft") %>' 
                                    Visible='<%# !Eval("EstadoCita").ToString().Contains("CANCELADA") %>'
                                    />
                            </div>
                        </div>
                    </ItemTemplate>
                    <FooterTemplate>
                        <asp:Panel ID="pnlNoData" runat="server" Visible='<%# rptrCitas.Items.Count == 0 %>'>
                            <div class="empty-state">
                                <i class="fas fa-clipboard-list fa-3x"></i>
                                <h5>No se encontraron citas en esta categoría.</h5>
                            </div>
                        </asp:Panel>
                    </FooterTemplate>
                </asp:Repeater>
                
                <div class="text-end mt-3">
                    <asp:LinkButton ID="btnVerAgendaCompleta" runat="server" PostBackUrl="~/Veterinario_Agenda.aspx" CssClass="text-primary text-decoration-none fw-bold small">Ver agenda completa <i class="fas fa-arrow-right ms-1"></i></asp:LinkButton>
                </div>
            </div>

            <div class="col-lg-4">
                 <h4 class="mb-4" style="color: #32325d; font-weight: 800;"><i class="fas fa-chart-line me-2"></i>Esta Semana</h4>
                 <div class="card-weekly-total">
                     <h6 class="text-uppercase">Total Atenciones</h6>
                     <h2><asp:Literal ID="litConsultasSemana" runat="server" Text="0"></asp:Literal></h2>
                     <div class="progress mt-3" style="height: 6px; background-color: rgba(255,255,255,0.2); border-radius: 10px;">
                         <div class="progress-bar bg-white" role="progressbar" style="width: 70%; opacity: 0.8;"></div>
                     </div>
                     <small class="d-block mt-2 opacity-75">Lunes a Domingo</small>
                 </div>
                 <div class="row g-3">
                     <div class="col-12">
                         <div class="card-weekly-mini">
                             <div class="weekly-data">
                                 <span>Vacunaciones</span>
                                 <h3><asp:Literal ID="litVacunasSemana" runat="server" Text="0"></asp:Literal></h3>
                             </div>
                             <div class="weekly-icon icon-vac"><i class="fas fa-syringe"></i></div>
                         </div>
                     </div>
                     <div class="col-12">
                         <div class="card-weekly-mini">
                             <div class="weekly-data">
                                 <span>Cirugías</span>
                                 <h3><asp:Literal ID="litCirugiasSemana" runat="server" Text="0"></asp:Literal></h3>
                             </div>
                             <div class="weekly-icon icon-cir"><i class="fas fa-procedures"></i></div>
                         </div>
                     </div>
                 </div>
            </div>
       </div>
   </div>
</asp:Content>

<asp:Content ID="ContentScripts" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>