<%-- Nombre del archivo: Cliente_HistorialMedico.aspx --%>
<%@ Page Title="Historial Médico" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_HistorialMedico.aspx.cs" 
    Inherits="SoftPetWA.Cliente_HistorialMedico" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Historial Médico
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico --%>
    <link href="Content/historialmedico.css" rel="stylesheet" />
    <%-- Incluimos el de expedientes por si reutilizamos estilos --%>
    <link href="Content/expedientes.css" rel="stylesheet" /> 
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-notes-medical me-2"></i>Historial Médico</h3>
        <asp:LinkButton ID="btnImprimir" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnImprimir_Click">
             <i class="fas fa-print me-2"></i>Imprimir
        </asp:LinkButton>
    </div>

    <div class="row">
        <%-- ======================================= --%>
        <%--     COLUMNA IZQUIERDA: INFO MASCOTA     --%>
        <%-- ======================================= --%>
        <div class="col-lg-4 mb-4">
            <div class="pet-summary-card">
                 <div class="pet-summary-avatar">
                    <asp:Image ID="imgAvatarMascotaHist" runat="server" />
                </div>
                <h3 class="pet-name"><asp:Label ID="lblNombreMascotaHist" runat="server"></asp:Label></h3>
                <span class="pet-breed d-block"><asp:Label ID="lblRazaMascotaHist" runat="server"></asp:Label></span>

                <ul class="pet-summary-details">
                    <li><i class="fas fa-birthday-cake"></i><span class="label">Edad</span> <span class="value"><asp:Label ID="lblEdadHist" runat="server"></asp:Label></span></li>
                    <li><i class="fas fa-venus-mars"></i><span class="label">Sexo</span> <span class="value"><asp:Label ID="lblSexoHist" runat="server"></asp:Label></span></li>
                    <li><i class="fas fa-weight"></i><span class="label">Peso Actual</span> <span class="value"><asp:Label ID="lblPesoHist" runat="server"></asp:Label></span></li>
                    <li><i class="fas fa-palette"></i><span class="label">Color</span> <span class="value"><asp:Label ID="lblColorHist" runat="server"></asp:Label></span></li>
                    <li><i class="fas fa-file-medical-alt"></i><span class="label">Total Consultas</span> <span class="value"><asp:Label ID="lblVisitasHist" runat="server"></asp:Label></span></li>
                </ul>

                <div class="d-grid gap-2">
                     <asp:LinkButton ID="btnAgendarCita" runat="server" CssClass="btn btn-primary" OnClick="btnAgendarCita_Click">
                         <i class="fas fa-calendar-plus me-2"></i>Agendar Nueva Cita
                     </asp:LinkButton>
                     <asp:LinkButton ID="btnCambiarMascota" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnCambiarMascota_Click">
                         <i class="fas fa-exchange-alt me-2"></i>Cambiar Mascota
                     </asp:LinkButton>
                </div>
            </div>
        </div>

        <%-- ======================================= --%>
        <%--     COLUMNA DERECHA: HISTORIAL          --%>
        <%-- ======================================= --%>
        <div class="col-lg-8">
            <div class="d-flex justify-content-between align-items-center mb-3">
                 <h4 class="mb-0"><i class="fas fa-history me-2"></i>Historial de Atenciones</h4>
            </div>

            <%-- Pestañas de Filtro --%>
            <div class="mb-3">
                <asp:Repeater ID="rptFiltroTipoHist" runat="server" OnItemCommand="rptFiltroTipoHist_ItemCommand">
                    <HeaderTemplate><ul class="nav nav-pills history-filter-tabs"></HeaderTemplate>
                    <ItemTemplate>
                        <li class="nav-item">
                            <asp:LinkButton ID="btnTipoHist" runat="server" 
                                CssClass='<%# Container.ItemIndex == 0 ? "nav-link active" : "nav-link" %>' 
                                CommandName="FiltrarHist" 
                                CommandArgument='<%# Eval("Tipo") %>'>
                                <i class="<%# Eval("IconoCss") %>"></i> <%# Eval("Tipo") %>
                            </asp:LinkButton>
                        </li>
                    </ItemTemplate>
                    <FooterTemplate></ul></FooterTemplate>
                </asp:Repeater>
            </div>

            <%-- Timeline (Repeater) --%>
            <div>
                <asp:Repeater ID="rptHistorialCliente" runat="server" OnItemCommand="rptHistorialCliente_ItemCommand">
                    <ItemTemplate>
                        <div class="history-entry">
                            <%-- Cabecera --%>
                            <div class="history-entry-header">
                                <div>
                                    <span class="date-time"><%# Eval("FechaHora") %></span>
                                    <h5 class="entry-title"><%# Eval("Titulo") %></h5>
                                    <span class="doctor"><i class="fas fa-user-md me-1"></i><%# Eval("Doctor") %></span>
                                </div>
                                <span class="entry-type-badge <%# Eval("TipoBadgeCss") %>"><%# Eval("Tipo") %></span>
                            </div>
                            <%-- Cuerpo --%>
                            <div class="history-entry-body">
                                <%-- Paneles reutilizados de Secretaria_Expedientes --%>
                                <%-- Panel para Consultas --%>
                                <asp:Panel ID="pnlConsulta" runat="server" Visible='<%# Eval("Tipo").ToString() == "Consulta" %>'>
                                    <div class="section-block">
                                        <span class="section-header bg-diagnostico">Diagnóstico</span>
                                        <div class="section-body"><%# Eval("Diagnostico") %></div>
                                    </div>
                                    <div class="section-block">
                                        <span class="section-header" style="background-color: #f0f0f0; color: #555;">Motivo y Observaciones</span>
                                        <div class="section-body"><%# Eval("Motivo") %></div>
                                    </div>
                                    <asp:Panel ID="pnlReceta" runat="server" Visible='<%# Eval("RecetaTitulo1") != DBNull.Value %>'>
                                        <div class="section-block">
                                            <span class="section-header bg-receta">Receta Médica</span>
                                            <div class="section-body">
                                                <div class="receta-item">
                                                    <span class="receta-titulo"><%# Eval("RecetaTitulo1") %></span>
                                                    <div class="receta-indicacion"><%# Eval("RecetaIndicacion1") %></div>
                                                </div>
                                                <asp:Panel ID="pnlReceta2" runat="server" Visible='<%# Eval("RecetaTitulo2") != DBNull.Value %>' CssClass="receta-item mt-2">
                                                    <span class="receta-titulo"><%# Eval("RecetaTitulo2") %></span>
                                                    <div class="receta-indicacion"><%# Eval("RecetaIndicacion2") %></div>
                                                </asp:Panel>
                                            </div>
                                        </div>
                                    </asp:Panel>
                                </asp:Panel>

                                <%-- Panel para Vacunación --%>
                                <asp:Panel ID="pnlVacunacion" runat="server" Visible='<%# Eval("Tipo").ToString() == "Vacunacion" %>'>
                                    <div class="section-block">
                                        <span class="section-header bg-procedimiento">Procedimiento</span>
                                        <div class="section-body"><%# Eval("Procedimiento") %></div>
                                    </div>
                                     <div class="section-block">
                                        <span class="section-header" style="background-color: #f0f0f0; color: #555;">Observaciones</span>
                                        <div class="section-body"><%# Eval("Observaciones") %></div>
                                    </div>
                                </asp:Panel>

                                <%-- Tabla de Vitals (Común a ambas) --%>
                                <div class="vitals-table row" Visible='<%# Eval("Peso") != DBNull.Value %>'>
                                    <div class="col-4">
                                        <span class="label d-block">Peso</span>
                                        <span class="value"><%# Eval("Peso") %></span>
                                    </div>
                                    <div class="col-4">
                                        <span class="label d-block">Temperatura</span>
                                        <span class="value"><%# Eval("Temperatura") %></span>
                                    </div>
                                    <div class="col-4">
                                        <span class="label d-block">Frecuencia</span>
                                        <span class="value"><%# Eval("Frecuencia") %></span>
                                    </div>
                                </div>
                            </div>
                             <%-- Footer (Opcional: botones Ver Detalle / Descargar) --%>
                             <div class="history-entry-footer">
                                 <asp:LinkButton ID="btnVerDetalle" runat="server" CssClass="btn btn-sm btn-outline-secondary me-1" CommandName="VerDetalle">
                                     <i class="fas fa-search-plus me-1"></i> Ver Detalle
                                 </asp:LinkButton>
                                  <asp:LinkButton ID="btnDescargar" runat="server" CssClass="btn btn-sm btn-outline-secondary" CommandName="Descargar">
                                     <i class="fas fa-download me-1"></i> Descargar
                                 </asp:LinkButton>
                             </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>
        </div>
    </div>

</asp:Content>