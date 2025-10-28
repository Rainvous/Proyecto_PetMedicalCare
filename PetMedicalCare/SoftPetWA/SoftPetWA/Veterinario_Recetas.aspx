<%-- Nombre del archivo: Veterinario_Recetas.aspx --%>
<%@ Page Title="Recetas" Language="C#" MasterPageFile="~/Veterinario.Master" AutoEventWireup="true" 
    CodeBehind="Veterinario_Recetas.aspx.cs" 
    Inherits="SoftPetWA.Veterinario_Recetas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Recetas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico --%>
    <link href="Content/recetas.css" rel="stylesheet" />
    <%-- Reutilizamos CSS de filtros si es necesario --%>
    <link href="Content/inventario.css" rel="stylesheet" /> 
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <%-- 1. Barra de Filtros --%>
    <div class="card shadow-sm mb-4 filter-bar">
        <div class="card-body">
            <div class="row g-3 align-items-end">
                <div class="col-md-3">
                    <label for="<%= txtMascota.ClientID %>" class="form-label">Mascota</label>
                    <asp:TextBox ID="txtMascota" runat="server" CssClass="form-control" placeholder="Buscar por mascota..."></asp:TextBox>
                </div>
                 <div class="col-md-3">
                    <label for="<%= txtPropietario.ClientID %>" class="form-label">Propietario</label>
                    <asp:TextBox ID="txtPropietario" runat="server" CssClass="form-control" placeholder="Buscar por propietario..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= txtFechaDesde.ClientID %>" class="form-label">Fecha Desde</label>
                    <asp:TextBox ID="txtFechaDesde" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                </div>
                 <div class="col-md-2">
                    <label for="<%= txtFechaHasta.ClientID %>" class="form-label">Fecha Hasta</label>
                    <asp:TextBox ID="txtFechaHasta" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox>
                </div>
                <div class="col-md-2 d-flex">
                    <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2" OnClick="btnBuscar_Click">
                        <i class='fas fa-search me-1'></i> 
                    </asp:LinkButton>
                    <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnLimpiar_Click">
                        <i class='fas fa-times'></i>
                    </asp:LinkButton>
                </div>
            </div>
        </div>
    </div>

    <%-- 2. Barra de Acciones y Conteo --%>
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="text-muted">
            Mostrando 
            <asp:Literal ID="litRegistrosActuales" runat="server" Text="0-0"></asp:Literal> 
            de 
            <asp:Literal ID="litRegistrosTotales" runat="server" Text="0"></asp:Literal> 
            recetas
        </div>
        <div>
            <asp:LinkButton ID="btnNuevaReceta" runat="server" CssClass="btn btn-success" OnClick="btnNuevaReceta_Click">
                <i class='fas fa-plus me-2'></i>Nueva Receta
            </asp:LinkButton>
        </div>
    </div>

    <%-- 3. Lista de Recetas --%>
    <div>
        <asp:Repeater ID="rptRecetas" runat="server" OnItemCommand="rptRecetas_ItemCommand">
            <ItemTemplate>
                <div class="recipe-list-item">
                    <div class="recipe-info">
                        <div class="recipe-icon">
                            <i class="<%# Eval("IconoCss") %>"></i>
                        </div>
                        <div class="recipe-details">
                            <span class="date"><%# Eval("Fecha") %></span>
                            <h6 class="pet-name"><%# Eval("MascotaNombre") %></h6>
                            <span class="owner-name"><i class="fas fa-user me-1"></i><%# Eval("PropietarioNombre") %></span>
                        </div>
                    </div>
                    <div class="recipe-actions text-end">
                        <span class="status-badge <%# Eval("EstadoCss") %>"><%# Eval("EstadoTexto") %></span>
                        <div class="mt-1">
                            <asp:LinkButton ID="btnVerReceta" runat="server" CssClass="btn btn-sm btn-outline-secondary" CommandName="Ver" CommandArgument='<%# Eval("RecetaID") %>'>
                                <i class="fas fa-eye"></i> Ver
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnImprimirReceta" runat="server" CssClass="btn btn-sm btn-outline-secondary ms-1" CommandName="Imprimir" CommandArgument='<%# Eval("RecetaID") %>'>
                                <i class="fas fa-print"></i> Imprimir
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </ItemTemplate>
             <FooterTemplate>
                <%-- Mensaje si no hay recetas --%>
                <asp:Panel ID="pnlNoRecetas" runat="server" Visible='<%# rptRecetas.Items.Count == 0 %>'>
                    <div class="alert alert-info text-center" role="alert">
                       <i class="fas fa-info-circle me-2"></i> No se encontraron recetas con los filtros aplicados.
                    </div>
                </asp:Panel>
            </FooterTemplate>
        </asp:Repeater>
    </div>

    <%-- Paginación (Opcional, similar a otras pantallas si es necesario) --%>
    <%-- 
    <div class="d-flex justify-content-end mt-4">
        <nav class="custom-pagination">
            <ul class="pagination mb-0">
                <li class="page-item disabled"><a class="page-link" href="#"><i class="fas fa-chevron-left"></i></a></li>
                <li class="page-item active"><a class="page-link" href="#">1</a></li>
                <li class="page-item"><a class="page-link" href="#">2</a></li>
                <li class="page-item"><a class="page-link" href="#"><i class="fas fa-chevron-right"></i></a></li>
            </ul>
        </nav>
    </div> 
    --%>

</asp:Content>