<%@ Page Title="Detalle de Expediente" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_ExpedienteDetalle.aspx.cs"
    Inherits="SoftPetWA.Secretaria_ExpedienteDetalle" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Expediente Clínico
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/expedientes.css" rel="stylesheet" />
    <style>
        /* (Estilos anteriores se mantienen) */
        .pet-summary-card {
            background: white; border-radius: 10px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); padding: 20px;
            border-top: 5px solid #00bcd4; position: sticky; top: 20px;
        }
        .pet-big-avatar {
            width: 100px; height: 100px; background-color: #e0f7fa; color: #006064; border-radius: 50%;
            display: flex; align-items: center; justify-content: center; margin: 0 auto 15px; font-size: 3rem;
        }
        .info-row { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px dashed #eee; font-size: 0.95rem; }
        .info-row:last-child { border-bottom: none; }
        .info-label { color: #6c757d; font-weight: 500; }
        .info-value { color: #343a40; font-weight: 600; }
        .tab-filter-bar { background-color: #f8f9fa; padding: 15px; border-radius: 8px; margin-bottom: 20px; border: 1px solid #e9ecef; }
        .history-card {
            border: 1px solid #e9ecef; border-radius: 8px; background: white; margin-bottom: 15px;
            transition: transform 0.2s; overflow: hidden;
        }
        .history-header {
            padding: 12px 15px; background-color: #f1f8e9; border-bottom: 1px solid #e0e0e0;
            display: flex; justify-content: space-between; align-items: center;
        }
        .history-body { padding: 15px; }
        .recipe-card {
            border: 1px solid #ffe082; border-radius: 8px; background: #ffffff; margin-bottom: 15px;
        }
        .recipe-header { 
            background-color: #fff8e1; padding: 10px 15px; border-bottom: 1px solid #ffe082;
            display: flex; justify-content: space-between; align-items: center;
        }
        .recipe-body { padding: 15px; }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:UpdatePanel ID="updPanelDetalle" runat="server">
        <ContentTemplate>

            <asp:HiddenField ID="hdMascotaID" runat="server" Value="0" />

            <div class="d-flex justify-content-between align-items-center mb-4">
                <h3 class="fw-bold text-secondary mb-0">
                    <i class="fas fa-folder-open me-2 text-primary"></i>Expediente Clínico
                </h3>
                <asp:HyperLink ID="lnkVolver" runat="server" NavigateUrl="~/Secretaria_Expedientes.aspx" CssClass="btn btn-outline-secondary">
                    <i class="fas fa-arrow-left me-2"></i>Volver
                </asp:HyperLink>
            </div>

            <div class="row">
                <%-- COLUMNA IZQUIERDA: INFO MASCOTA --%>
                <div class="col-lg-4 mb-4">
                    <div class="pet-summary-card">
                        <div class="pet-big-avatar">
                            <asp:Literal ID="litIconoEspecie" runat="server"><i class="fas fa-paw"></i></asp:Literal>
                        </div>
                        <h4 class="text-center fw-bold mb-1"><asp:Label ID="lblMascotaNombre" runat="server"></asp:Label></h4>
                        <p class="text-center text-muted mb-4"><asp:Label ID="lblMascotaRaza" runat="server"></asp:Label></p>

                        <div class="info-row"><span class="info-label">Código</span> <span class="info-value"><asp:Label ID="lblCodigo" runat="server"></asp:Label></span></div>
                        <div class="info-row"><span class="info-label">Sexo</span> <span class="info-value"><asp:Label ID="lblSexo" runat="server"></asp:Label></span></div>
                        <div class="info-row"><span class="info-label">Color</span> <span class="info-value"><asp:Label ID="lblColor" runat="server"></asp:Label></span></div>
                        <div class="info-row"><span class="info-label">Estado</span> <asp:Label ID="lblEstado" runat="server"></asp:Label></div>
                        <hr class="my-3 opacity-25" />
                        <h6 class="fw-bold text-primary mb-3"><i class="fas fa-user me-2"></i>Propietario</h6>
                        <div class="d-flex align-items-center mb-2"><div class="me-3 text-center" style="width: 20px;"><i class="fas fa-user-circle text-muted"></i></div><div><asp:Label ID="lblPropietarioNombre" runat="server" CssClass="fw-bold text-dark"></asp:Label></div></div>
                        <div class="d-flex align-items-center mb-2"><div class="me-3 text-center" style="width: 20px;"><i class="fas fa-id-card text-muted"></i></div><div><asp:Label ID="lblPropietarioDNI" runat="server" CssClass="small"></asp:Label></div></div>
                        <div class="d-flex align-items-center"><div class="me-3 text-center" style="width: 20px;"><i class="fas fa-phone text-muted"></i></div><div><asp:Label ID="lblPropietarioTelefono" runat="server" CssClass="small"></asp:Label></div></div>
                    </div>
                </div>

                <%-- COLUMNA DERECHA --%>
                <div class="col-lg-8">
                    <div class="card shadow-sm border-0">
                        <div class="card-header bg-white border-bottom-0 pt-4 px-4 d-flex justify-content-between align-items-center">
                            <ul class="nav nav-tabs card-header-tabs" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <asp:LinkButton ID="btnTabConsultas" runat="server" CssClass="nav-link active text-dark fw-bold" OnClick="btnTabConsultas_Click">
                                        <i class="fas fa-stethoscope me-2 text-primary"></i>Consultas
                                    </asp:LinkButton>
                                </li>
                                <li class="nav-item">
                                    <asp:LinkButton ID="btnTabRecetas" runat="server" CssClass="nav-link text-muted" OnClick="btnTabRecetas_Click">
                                        <i class="fas fa-prescription-bottle-alt me-2 text-warning"></i>Recetas
                                    </asp:LinkButton>
                                </li>
                            </ul>
                            <asp:LinkButton ID="btnNuevaConsulta" runat="server" CssClass="btn btn-sm btn-outline-success fw-bold" OnClick="btnNuevaConsulta_Click">
                                <i class="fas fa-plus-circle me-1"></i> Nueva Consulta
                            </asp:LinkButton>
                        </div>

                        <div class="card-body p-4 bg-light">
                            <asp:MultiView ID="mvHistorial" runat="server" ActiveViewIndex="0">

                                <%-- VISTA 1: CONSULTAS --%>
                                <asp:View ID="vwConsultas" runat="server">
                                    <div class="tab-filter-bar d-flex align-items-center justify-content-between">
                                        <div class="d-flex align-items-center gap-2 w-75">
                                            <label class="small fw-bold text-muted mb-0" style="white-space: nowrap;">Filtrar por Fecha:</label>
                                            <asp:TextBox ID="txtFiltroFechaCita" runat="server" CssClass="form-control form-control-sm" TextMode="Date"></asp:TextBox>
                                        </div>
                                        <div class="d-flex gap-2">
                                            <asp:LinkButton ID="btnBuscarCitaFecha" runat="server" CssClass="btn btn-sm btn-primary" OnClick="btnBuscarCitaFecha_Click"><i class="fas fa-search"></i></asp:LinkButton>
                                            <asp:LinkButton ID="btnLimpiarCita" runat="server" CssClass="btn btn-sm btn-outline-secondary" OnClick="btnLimpiarCita_Click" ToolTip="Limpiar"><i class="fas fa-sync-alt"></i></asp:LinkButton>
                                        </div>
                                    </div>

                                    <%-- Panel Inicial (Vacío) --%>
                                    <asp:Panel ID="pnlInicioCitas" runat="server" CssClass="text-center py-5 text-muted">
                                        <i class="fas fa-search fa-3x mb-3 opacity-50"></i>
                                        <p class="mb-0">Seleccione una fecha para ver el historial.</p>
                                    </asp:Panel>

                                    <asp:Repeater ID="rptHistorialMedico" runat="server">
                                        <ItemTemplate>
                                            <div class="history-card">
                                                <div class="history-header">
                                                    <div><span class="fw-bold text-dark me-2"><i class="far fa-calendar-alt me-1"></i><%# Eval("Fecha") %></span><span class="badge bg-white text-dark border"><%# Eval("Hora") %></span></div>
                                                    <span class="badge <%# Eval("ClaseEstado") %>"><%# Eval("Estado") %></span>
                                                </div>
                                                <div class="history-body">
                                                    <div class="row mb-2">
                                                        <div class="col-md-6"><small class="text-muted d-block">VETERINARIO</small><span class="fw-bold text-primary"><i class="fas fa-user-md me-1"></i><%# Eval("NombreVeterinario") %></span></div>
                                                        <div class="col-md-3"><small class="text-muted d-block">PESO</small><span><%# Eval("Peso") %> kg</span></div>
                                                        <div class="col-md-3"><small class="text-muted d-block">MONTO</small><span>S/ <%# Eval("Monto") %></span></div>
                                                    </div>
                                                    <div class="bg-light p-2 rounded border border-light"><small class="text-muted fw-bold">OBSERVACIONES:</small><p class="mb-0 small text-dark"><%# Eval("Observacion") %></p></div>
                                                </div>
                                            </div>
                                        </ItemTemplate>
                                        <FooterTemplate>
                                            <%-- MENSAJE NO ENCONTRADO --%>
                                            <asp:Panel ID="pnlNoCitas" runat="server" Visible='<%# rptHistorialMedico.Items.Count == 0 %>' CssClass="text-center py-4 text-muted">
                                                <i class="fas fa-file-medical-alt fa-2x mb-2 opacity-50"></i><br />
                                                No se encontraron consultas para la fecha seleccionada.
                                            </asp:Panel>
                                        </FooterTemplate>
                                    </asp:Repeater>
                                </asp:View>

                                <%-- VISTA 2: RECETAS --%>
                                <asp:View ID="vwRecetas" runat="server">
                                    <div class="tab-filter-bar d-flex align-items-center justify-content-between">
                                        <div class="d-flex align-items-center gap-2 w-75">
                                            <label class="small fw-bold text-muted mb-0" style="white-space: nowrap;">Filtrar por Fecha:</label>
                                            <asp:TextBox ID="txtFiltroFechaReceta" runat="server" CssClass="form-control form-control-sm" TextMode="Date"></asp:TextBox>
                                        </div>
                                        <div class="d-flex gap-2">
                                            <asp:LinkButton ID="btnBuscarRecetaFecha" runat="server" CssClass="btn btn-sm btn-primary" OnClick="btnBuscarRecetaFecha_Click"><i class="fas fa-search"></i></asp:LinkButton>
                                            <asp:LinkButton ID="btnLimpiarReceta" runat="server" CssClass="btn btn-sm btn-outline-secondary" OnClick="btnLimpiarReceta_Click" ToolTip="Limpiar"><i class="fas fa-sync-alt"></i></asp:LinkButton>
                                        </div>
                                    </div>

                                    <%-- Panel Inicial (Vacío) --%>
                                    <asp:Panel ID="pnlInicioRecetas" runat="server" CssClass="text-center py-5 text-muted">
                                        <i class="fas fa-pills fa-3x mb-3 opacity-50"></i>
                                        <p class="mb-0">Seleccione una fecha para ver las recetas.</p>
                                    </asp:Panel>

                                    <asp:Repeater ID="rptRecetasLista" runat="server">
                                        <ItemTemplate>
                                            <div class="recipe-card">
                                                <div class="recipe-header">
                                                    <strong class="text-warning-dark"><i class="fas fa-file-prescription me-2"></i>Receta Médica</strong>
                                                    <span class="small fw-bold text-dark"><%# Eval("Fecha") %></span>
                                                </div>
                                                <div class="recipe-body">
                                                    <div class="mb-3"><small class="text-muted text-uppercase d-block">Diagnóstico</small><div class="fw-bold text-dark"><%# Eval("Diagnostico") %></div></div>
                                                    <div class="bg-light p-2 rounded border border-light"><small class="text-muted text-uppercase d-block">Indicaciones</small><p class="mb-0 small text-dark" style="white-space: pre-line;"><%# Eval("Indicaciones") %></p></div>
                                                    <div class="text-end mt-2"><small class="text-muted fst-italic">Dr. <%# Eval("NombreVeterinario") %></small></div>
                                                </div>
                                            </div>
                                        </ItemTemplate>
                                        <FooterTemplate>
                                            <%-- MENSAJE NO ENCONTRADO --%>
                                            <asp:Panel ID="pnlNoRecetas" runat="server" Visible='<%# rptRecetasLista.Items.Count == 0 %>' CssClass="text-center py-4 text-muted">
                                                <i class="fas fa-file-prescription fa-2x mb-2 opacity-50"></i><br />
                                                No se encontraron recetas para la fecha seleccionada.
                                            </asp:Panel>
                                        </FooterTemplate>
                                    </asp:Repeater>
                                </asp:View>

                            </asp:MultiView>
                        </div>
                    </div>
                </div>
            </div>

        </ContentTemplate>
    </asp:UpdatePanel>
</asp:Content>