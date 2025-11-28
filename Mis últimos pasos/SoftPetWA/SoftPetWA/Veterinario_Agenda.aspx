<%@ Page Title="Mi Agenda" Language="C#" MasterPageFile="~/Veterinario.Master" AutoEventWireup="true" CodeBehind="Veterinario_Agenda.aspx.cs" Inherits="SoftPetWA.Veterinario_Agenda" %>

<asp:Content ID="ContentTitulo" ContentPlaceHolderID="cphTitulo" runat="server">
    Mi Agenda
</asp:Content>

<asp:Content ID="ContentHead" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/GestionVeterinarios.css" rel="stylesheet" /> 
    
    <%-- ESTILOS ESPECÍFICOS INCRUSTADOS PARA ASEGURAR QUE SE VEAN --%>
    <style>
        /* Input de fecha como título */
        .date-input-title {
            border: none;
            background: transparent;
            font-size: 1.25rem;
            font-weight: 700;
            color: #5a5c69;
            text-align: center;
            cursor: pointer;
            outline: none;
            width: auto;
            display: inline-block;
        }
        .date-input-title:hover {
            background-color: #f8f9fa;
            border-radius: 5px;
            color: #4e73df;
        }

        /* --- ETIQUETAS DE ESTADO (BADGES) --- */
        .status-tag {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 50px;       /* Bordes muy redondos */
            font-size: 0.7rem;         /* Texto pequeño */
            font-weight: 800;          /* Texto negrita */
            text-transform: uppercase;
            letter-spacing: 0.5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.08);
        }

        /* 1. COMPLETADA (Verde) */
        .tag-completada {
            background-color: #d1e7dd;
            color: #0f5132;
            border: 1px solid #badbcc;
        }

        /* 2. PENDIENTE (Amarillo/Naranja) */
        .tag-pendiente {
            background-color: #fff3cd;
            color: #856404;
            border: 1px solid #ffecb5;
        }

        /* 3. CANCELADA (Rojo) */
        .tag-cancelada {
            background-color: #f8d7da;
            color: #842029;
            border: 1px solid #f5c2c7;
        }

        /* Asegura que la cabecera de la tarjeta separe los elementos */
        .agenda-card-header {
            display: flex !important;
            justify-content: space-between !important;
            align-items: center !important;
        }
    </style>
</asp:Content>

<asp:Content ID="ContentMain" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="page-header">
        <div class="page-header-title">
            <h1>Mi Agenda de Citas</h1>
            <p class="text-muted">Gestiona tus consultas del día.</p>
        </div>
    </div>
    
    <%-- BARRA DE NAVEGACIÓN DE FECHAS --%>
    <div class="date-selector-bar">
        <div class="date-selector-nav">
            <asp:Button ID="btnDiaAnterior" runat="server" Text="<" CssClass="btn btn-light" OnClick="btnDiaAnterior_Click" />
        </div>
        
        <div class="text-center date-selector-info d-flex flex-column align-items-center">
            <asp:TextBox ID="txtFechaFiltro" runat="server" 
                TextMode="Date" 
                CssClass="date-input-title" 
                AutoPostBack="true" 
                OnTextChanged="txtFechaFiltro_TextChanged">
            </asp:TextBox>
            <small>Tienes <asp:Literal ID="litConteoCitas" runat="server" Text="0"></asp:Literal> citas programadas</small>
        </div>

        <div class="date-selector-nav d-flex">
            <asp:Button ID="btnDiaSiguiente" runat="server" Text=">" CssClass="btn btn-light" OnClick="btnDiaSiguiente_Click" />
            <asp:LinkButton ID="btnHoy" runat="server" OnClick="btnHoy_Click" CssClass="btn btn-primary btn-hoy" style="margin-left: 15px;">
                <i class='fas fa-calendar-check me-1'></i> Hoy
            </asp:LinkButton>
        </div>
    </div>

    <%-- TARJETAS DE RESUMEN --%>
    <div class="row mb-4">
        <div class="col-lg-3 col-md-6 mb-4"><div class="card shadow-sm summary-card h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-primary text-uppercase mb-1">Total</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litTotalCitas" runat="server" Text="0"></asp:Literal></div></div><div class="col-auto"><div class="icon-circle bg-primary"><i class="fas fa-calendar-day"></i></div></div></div></div></div></div>
        <div class="col-lg-3 col-md-6 mb-4"><div class="card shadow-sm summary-card h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-success text-uppercase mb-1">Completadas</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litCompletadas" runat="server" Text="0"></asp:Literal></div></div><div class="col-auto"><div class="icon-circle bg-success"><i class="fas fa-check"></i></div></div></div></div></div></div>
        <div class="col-lg-3 col-md-6 mb-4"><div class="card shadow-sm summary-card h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-warning text-uppercase mb-1">Pendientes</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litPendientes" runat="server" Text="0"></asp:Literal></div></div><div class="col-auto"><div class="icon-circle bg-warning"><i class="fas fa-hourglass-half"></i></div></div></div></div></div></div>
        <div class="col-lg-3 col-md-6 mb-4"><div class="card shadow-sm summary-card h-100"><div class="card-body"><div class="row no-gutters align-items-center"><div class="col mr-2"><div class="text-xs font-weight-bold text-danger text-uppercase mb-1">Canceladas</div><div class="h5 mb-0 font-weight-bold text-gray-800"><asp:Literal ID="litCanceladas" runat="server" Text="0"></asp:Literal></div></div><div class="col-auto"><div class="icon-circle bg-danger"><i class="fas fa-times"></i></div></div></div></div></div></div>
    </div>

    <%-- CRONOGRAMA --%>
    <div class="row">
        <div class="col-12">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3 class="mb-0"><i class="fas fa-stream me-2"></i> Cronograma de Atenciones</h3>
                <div class="agenda-filters btn-group" role="group">
                    <asp:LinkButton ID="lnkFiltroTodas" runat="server" CssClass="btn btn-sm btn-outline-primary active" CommandName="Filtro" CommandArgument="Todas" OnCommand="filtro_Command">Todas</asp:LinkButton>
                    <asp:LinkButton ID="lnkFiltroPendientes" runat="server" CssClass="btn btn-sm btn-outline-primary" CommandName="Filtro" CommandArgument="Pendientes" OnCommand="filtro_Command">Pendientes</asp:LinkButton>
                    <asp:LinkButton ID="lnkFiltroConfirmadas" runat="server" CssClass="btn btn-sm btn-outline-primary" CommandName="Filtro" CommandArgument="Confirmadas" OnCommand="filtro_Command">Confirmadas</asp:LinkButton>
                    <asp:LinkButton ID="lnkFiltroCompletadas" runat="server" CssClass="btn btn-sm btn-outline-primary" CommandName="Filtro" CommandArgument="Completadas" OnCommand="filtro_Command">Completadas</asp:LinkButton>
                </div>
            </div>
            <hr class="mt-0 mb-4"/>
            
            <ul class="agenda-timeline">
                <asp:Repeater ID="rptrAgenda" runat="server" OnItemCommand="rptrAgenda_ItemCommand">
                    <ItemTemplate>
                        <li class="agenda-item">
                            
                            <%-- Icono Flotante --%>
                            <div class='agenda-icon <%# Eval("IconoEstado") %>'>
                                <i class='<%# Eval("IconoClase") %>'></i>
                            </div>
                            
                            <%-- Tarjeta --%>
                            <div class='card agenda-card <%# Eval("CardClass") %> shadow-sm'>
                                
                                <%-- HEADER: AQUÍ ESTÁ LA ETIQUETA --%>
                                <div class="agenda-card-header">
                                    <span class="agenda-time"><i class="far fa-clock"></i> <%# Eval("HoraRango") %></span>
                                    
                                    <%-- ETIQUETA DE ESTADO VISIBLE --%>
                                    <span class='status-tag <%# Eval("EstadoCss") %>'>
                                        <%# Eval("EstadoCita") %>
                                    </span>
                                </div>
                                
                                <div class="agenda-card-body">
                                    <div class="d-flex align-items-center mb-3">
                                        <div class='paciente-avatar'><i class='<%# Eval("IconoMascota") %>'></i></div>
                                        <div class="paciente-info">
                                            <div class="nombre"><%# Eval("NombrePaciente") %></div>
                                            <div class="details"><%# Eval("DetallesPaciente") %></div>
                                        </div>
                                    </div>
                                    <ul class="servicios-list">
                                        <asp:Panel ID="pnlS1" runat="server" Visible='<%# !string.IsNullOrEmpty(Eval("Servicio1").ToString()) %>'>
                                            <li><i class='<%# Eval("IconoServ1") %>'></i> <%# Eval("Servicio1") %></li>
                                        </asp:Panel>
                                        <asp:Panel ID="pnlS2" runat="server" Visible='<%# !string.IsNullOrEmpty(Eval("Servicio2").ToString()) %>'>
                                            <li><i class='<%# Eval("IconoServ2") %>'></i> <%# Eval("Servicio2") %></li>
                                        </asp:Panel>
                                    </ul>
                                </div>

                             <div class="agenda-card-footer text-end">
    <%-- CAMBIO IMPORTANTE: Enviamos Eval("IdCita") en lugar de IdMascota --%>
    <asp:Button ID="btnVerExpediente" runat="server" 
        Text="Ver Expediente" 
        CommandName="VerExpediente" 
        CommandArgument='<%# Eval("IdCita") %>' 
        CssClass="btn-ver-expediente" />
</div>
                            </div>
                        </li>
                    </ItemTemplate>
                    <FooterTemplate>
                        <asp:Panel ID="pnlVacio" runat="server" Visible='<%# rptrAgenda.Items.Count == 0 %>'>
                            <div class="alert alert-light text-center mt-4 p-4 border rounded">
                                <i class="fas fa-info-circle fa-2x mb-2 text-muted"></i>
                                <p class="text-muted mb-0">No hay citas registradas para este filtro.</p>
                            </div>
                        </asp:Panel>
                    </FooterTemplate>
                </asp:Repeater>
            </ul>
        </div>
    </div>
</asp:Content>

<asp:Content ID="ContentScripts" ContentPlaceHolderID="cphScripts" runat="server">
</asp:Content>