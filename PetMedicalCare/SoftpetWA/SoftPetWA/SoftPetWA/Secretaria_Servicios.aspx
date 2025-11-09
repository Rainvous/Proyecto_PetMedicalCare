<%-- Nombre del archivo: Secretaria_Servicios.aspx --%>
<%@ Page Title="Gestión de Servicios" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" 
    CodeBehind="Secretaria_Servicios.aspx.cs" 
    Inherits="SoftPetWA.Secretaria_Servicios" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Servicios
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/servicios.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-briefcase-medical me-2"></i>Gestión de Servicios</h3>
    </div>

    <%-- 1. Pestañas de Filtro por Tipo --%>
    <div class="mb-3">
        <h6 class="form-label">Filtrar por Tipo de Servicio</h6>
        <asp:Repeater ID="rptFiltroTipo" runat="server" OnItemCommand="rptFiltroTipo_ItemCommand">
            <HeaderTemplate>
                <ul class="nav nav-pills filter-tabs">
            </HeaderTemplate>
            <ItemTemplate>
                <li class="nav-item">
                    <asp:LinkButton ID="btnTipo" runat="server" 
                        CssClass='<%# Container.ItemIndex == 0 ? "nav-link active" : "nav-link" %>' 
                        CommandName="Filtrar" 
                        CommandArgument='<%# Eval("Tipo") %>'>
                        <%# Eval("Tipo") %>
                        <span class="badge bg-secondary"><%# Eval("Cantidad") %></span>
                    </asp:LinkButton>
                </li>
            </ItemTemplate>
            <FooterTemplate>
                </ul>
            </FooterTemplate>
        </asp:Repeater>
    </div>

    <%-- 2. Barra de Filtros Principales --%>
    <div class="card shadow-sm mb-4 filter-bar">
        <div class="card-body">
            <div class="row g-3 align-items-end">
                <div class="col-md-4">
                    <label for="<%= txtNombreServicio.ClientID %>" class="form-label">Nombre del Servicio</label>
                    <asp:TextBox ID="txtNombreServicio" runat="server" CssClass="form-control" placeholder="Buscar por nombre..."></asp:TextBox>
                </div>
                <div class="col-md-3">
                    <label for="<%= ddlRangoCosto.ClientID %>" class="form-label">Rango de Costo</label>
                    <asp:DropDownList ID="ddlRangoCosto" runat="server" CssClass="form-select"></asp:DropDownList>
                </div>
                <div class="col-md-2">
                    <label for="<%= ddlEstado.ClientID %>" class="form-label">Estado</label>
                    <asp:DropDownList ID="ddlEstado" runat="server" CssClass="form-select"></asp:DropDownList>
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

    <%-- 3. Barra de Acciones y Conteo --%>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="text-muted">
            Mostrando 
            <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal> 
            de 
            <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> 
            servicios
        </div>
        <div>
            <asp:LinkButton ID="btnNuevoServicio" runat="server" CssClass="btn btn-success" OnClick="btnNuevoServicio_Click">
                <i class='fas fa-plus me-2'></i>Nuevo Servicio
            </asp:LinkButton>
        </div>
    </div>

    <%-- 4. Lista de Servicios (Repeater en modo Grid) --%>
    <div class="row">
        <asp:Repeater ID="rptServicios" runat="server" OnItemCommand="rptServicios_ItemCommand">
           <%-- En Secretaria_Servicios.aspx, dentro del ItemTemplate --%>
            <ItemTemplate>
                <div class="col-lg-3 col-md-6 mb-4 d-flex">
                    <div class="service-card flex-fill">
                        
                        <%-- 1. Header (Icono y Estado - Icono AJUSTADO) --%>
                        <div class="service-card-header">
                            <%-- *** CAMBIO AQUÍ: Añadida clase de color dinámica *** --%>
                            <div class="service-icon <%# Eval("IconoColorCss") %>"> 
                                <i class="<%# Eval("IconoCss") %>"></i>
                            </div>
                            <span class="status-badge <%# Eval("EstadoClaseCss") %>"> 
                                <%# Eval("Estado") %>
                            </span>
                        </div>

                        <%-- 2. Body (Info y Precio - Precio MOVIDO AQUÍ) --%>
                        <div class="service-card-body">
                            <h6 class="service-name"><%# Eval("Nombre") %></h6>
                            <p class="service-desc"><%# Eval("Descripcion") %></p>
                        </div>

                        <%-- 3. Footer (Precio y Acciones con Hover - AJUSTADO) --%>
                        <div class="service-card-footer"> 
                            <span class="service-price-footer"><%# Eval("PrecioFormateado") %></span> 
                            <div class="service-actions">
                                <asp:LinkButton ID="btnVer" runat="server" 
                                    CssClass="btn btn-sm btn-action-view" 
                                    CommandName="Ver" CommandArgument='<%# Eval("ServicioID") %>' ToolTip="Ver detalle">
                                    <i class="fas fa-eye"></i>
                                </asp:LinkButton>
                                <asp:LinkButton ID="btnEditar" runat="server" 
                                    CssClass="btn btn-sm btn-action-edit" 
                                    CommandName="Editar" CommandArgument='<%# Eval("ServicioID") %>' ToolTip="Editar">
                                    <i class="fas fa-pencil-alt"></i>
                                </asp:LinkButton>
                                <asp:LinkButton ID="btnEliminar" runat="server" 
                                    CssClass="btn btn-sm btn-action-delete" 
                                    CommandName="Eliminar" CommandArgument='<%# Eval("ServicioID") %>' ToolTip="Eliminar"
                                    OnClientClick="return confirm('¿Está seguro de que desea eliminar este servicio?');">
                                    <i class="fas fa-trash-alt"></i>
                                </asp:LinkButton>
                            </div>
                        </div>
                    </div>
                </div>
            </ItemTemplate>
        </asp:Repeater>
    </div>
    
    <%-- En Secretaria_Servicios.aspx --%>

    <%-- Paginación --%>
    <div class="d-flex justify-content-between align-items-center">
        <span class="text-muted small">Página 1 de 2</span>
        <nav class="custom-pagination"> 
            <ul class="pagination mb-0">
                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#"><i class="fas fa-chevron-right"></i></a></li>
            </ul>
        </nav>
    </div>

</asp:Content>