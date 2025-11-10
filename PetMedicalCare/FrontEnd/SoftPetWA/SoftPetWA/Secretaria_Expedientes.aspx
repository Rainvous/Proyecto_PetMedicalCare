<%-- Nombre del archivo: Secretaria_Expedientes.aspx --%>
<%@ Page Title="Expediente Clínico" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" 
    CodeBehind="Secretaria_Expedientes.aspx.cs" 
    Inherits="SoftPetWA.Secretaria_Expedientes" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Expediente Clínico
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/expedientes.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-folder-open me-2"></i>Expediente Clínico</h3>
    </div>

    <%-- 1. Barra de Filtros (para buscar el expediente) --%>
    <div class="card shadow-sm mb-4 filter-bar">
        <div class="card-body">
            <div class="row g-3 align-items-end">
                <div class="col-md-3">
                    <label for="<%= txtNombre.ClientID %>" class="form-label">Nombre o Razón Social</label>
                    <asp:TextBox ID="txtNombre" runat="server" CssClass="form-control" placeholder="Buscar por nombre..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= txtDocumento.ClientID %>" class="form-label">Documento</label>
                    <asp:TextBox ID="txtDocumento" runat="server" CssClass="form-control" placeholder="DNI o RUC..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= txtTelefono.ClientID %>" class="form-label">Teléfono</label>
                    <asp:TextBox ID="txtTelefono" runat="server" CssClass="form-control" placeholder="Número..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= ddlTipo.ClientID %>" class="form-label">Tipo</label>
                    <asp:DropDownList ID="ddlTipo" runat="server" CssClass="form-select"></asp:DropDownList>
                </div>
                <div class="col-md-3 d-flex">
                    <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2" OnClick="btnBuscar_Click">
                        <i class='fas fa-search me-1'></i> Buscar
                    </asp:LinkButton>
                    <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnLimpiar_Click">
                        <i class='fas fa-times'></i>
                    </asp:LinkButton>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        
        <%-- Columna Izquierda: Info Mascota --%>
        <div class="col-md-4">
            <div class="pet-info-card">
                <div class="pet-info-avatar">
                    <asp:Image ID="imgAvatarMascota" runat="server" />
                </div>
                <h3 class="pet-name"><asp:Label ID="lblMascotaNombre" runat="server"></asp:Label></h3>
                <span class="pet-breed"><asp:Label ID="lblMascotaRaza" runat="server"></asp:Label></span>
                
                <ul class="pet-details-list mt-3">
                    <li><span class="label">Código</span> <span class="value"><asp:Label ID="lblCodigo" runat="server"></asp:Label></span></li>
                    <li><span class="label">Edad</span> <span class="value"><asp:Label ID="lblEdad" runat="server"></asp:Label></span></li>
                    <li><span class="label">Sexo</span> <span class="value"><asp:Label ID="lblSexo" runat="server"></asp:Label></span></li>
                    <li><span class="label">Color</span> <span class="value"><asp:Label ID="lblColor" runat="server"></asp:Label></span></li>
                    <li><span class="label">Peso Actual</span> <span class="value"><asp:Label ID="lblPesoActual" runat="server"></asp:Label></span></li>
                    <li><span class="label">Estado</span> <span class="value"><asp:Label ID="lblEstado" runat="server" CssClass="badge bg-success"></asp:Label></span></li>
                </ul>

                <div class="owner-info-card">
                    <h6 class="owner-title">Propietario</h6>
                    <span class="info-item"><i class="fas fa-user"></i><asp:Label ID="lblPropietarioNombre" runat="server"></asp:Label></span>
                    <span class="info-item"><i class="fas fa-id-card"></i><asp:Label ID="lblPropietarioDNI" runat="server"></asp:Label></span>
                    <span class="info-item"><i class="fas fa-phone"></i><asp:Label ID="lblPropietarioTelefono" runat="server"></asp:Label></span>
                    <span class="info-item"><i class="fas fa-envelope"></i><asp:Label ID="lblPropietarioEmail" runat="server"></asp:Label></span>
                </div>
            </div>
        </div>

        <%-- Columna Derecha: Historial Médico --%>
        <div class="col-md-8">
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h4 class="mb-0">Historial Médico</h4>
                <asp:LinkButton ID="btnNuevaConsulta" runat="server" CssClass="btn btn-success" OnClick="btnNuevaConsulta_Click">
                    <i class="fas fa-plus me-2"></i>Nueva Consulta
                </asp:LinkButton>
            </div>

            <%-- Tabs (Estáticos por ahora) --%>
            <ul class="nav nav-tabs mb-3">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Todas las Consultas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Recetas</a>
                </li>
            </ul>

            <%-- Timeline (Repeater) --%>
            <asp:Repeater ID="rptHistorialMedico" runat="server" OnItemCommand="rptHistorialMedico_ItemCommand">
                <ItemTemplate>
                    <div class="timeline-entry">
                        <%-- Cabecera de la entrada --%>
                        <div class="timeline-header d-flex justify-content-between align-items-start">
                            <div>
                                <div class="date"><%# Eval("FechaHora") %></div>
                                <h5 class="title"><%# Eval("Titulo") %></h5>
                                <span class="doctor"><%# Eval("Doctor") %></span>
                            </div>
                            <div>
                                <asp:LinkButton ID="btnVer" runat="server" CssClass="btn btn-sm btn-outline-secondary me-1" CommandName="Ver"><i class="fas fa-eye"></i> Ver</asp:LinkButton>
                                <asp:LinkButton ID="btnEditar" runat="server" CssClass="btn btn-sm btn-outline-secondary me-1" CommandName="Editar"><i class="fas fa-pencil-alt"></i> Editar</asp:LinkButton>
                                <asp:LinkButton ID="btnImprimir" runat="server" CssClass="btn btn-sm btn-outline-secondary" CommandName="Imprimir"><i class="fas fa-print"></i> Imprimir</asp:LinkButton>
                            </div>
                        </div>

                        <%-- Cuerpo de la entrada --%>
                        <div class="timeline-body">
                            
                            <%-- Panel para Consultas --%>
                            <asp:Panel ID="pnlConsulta" runat="server" Visible='<%# Eval("Tipo").ToString() == "Consulta" %>'>
                                <div class="section-block">
                                    <span class="section-header bg-diagnostico">Diagnóstico</span>
                                    <div class="section-body" style="white-space: pre-line;"><%# Eval("Diagnostico") %></div>
                                </div>
                                <div class="section-block">
                                    <span class="section-header bg-receta">Motivo y Observaciones</span>
                                    <div class="section-body" style="white-space: pre-line;"><%# Eval("Motivo") %></div>
                                </div>
                                <div class="section-block">
                                    <span class="section-header bg-receta">Receta Médica</span>
                                    <div class="section-body">
                                        <div class="receta-item">
                                            <span class="receta-titulo"><%# Eval("RecetaTitulo1") %></span>
                                            <div class="receta-indicacion"><%# Eval("RecetaIndicacion1") %></div>
                                        </div>
                                        <div class="receta-item mt-2">
                                            <span class="receta-titulo"><%# Eval("RecetaTitulo2") %></span>
                                            <div class="receta-indicacion"><%# Eval("RecetaIndicacion2") %></div>
                                        </div>
                                    </div>
                                </div>
                            </asp:Panel>

                            <%-- Panel para Vacunación --%>
                            <asp:Panel ID="pnlVacunacion" runat="server" Visible='<%# Eval("Tipo").ToString() == "Vacunacion" %>'>
                                <div class="section-block">
                                    <span class="section-header bg-procedimiento">Procedimiento</span>
                                    <div class="section-body"><%# Eval("Procedimiento") %></div>
                                </div>
                                 <div class="section-block">
                                    <span class="section-header bg-procedimiento">Observaciones</span>
                                    <div class="section-body" style="white-space: pre-line;"><%# Eval("Observaciones") %></div>
                                </div>
                            </asp:Panel>

                            <%-- Tabla de Vitals (Común a ambas) --%>
                            <div class="vitals-table row" Visible='<%# !string.IsNullOrEmpty(Eval("Peso").ToString()) %>'>
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
                    </div>
                </ItemTemplate>
            </asp:Repeater>
        </div>
    </div>

</asp:Content>