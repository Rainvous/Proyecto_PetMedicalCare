<%-- Nombre del archivo: Secretaria_Inventario.aspx --%>
<%@ Page Title="Inventario" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" 
    CodeBehind="Secretaria_Inventario.aspx.cs" 
    Inherits="SoftPetWA.Secretaria_Inventario" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Inventario
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/inventario.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-boxes me-2"></i>Gestión de Productos e Inventario</h3>
    </div>

    <%-- 1. Pestañas de Filtro por Tipo --%>
    <div class="mb-3">
        <h6 class="form-label">Filtrar por Tipo de Producto</h6>
        <asp:Repeater ID="rptFiltroTipo" runat="server" OnItemCommand="rptFiltroTipo_ItemCommand">
            <HeaderTemplate>
                <ul class="nav nav-pills product-filter-tabs">
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
                    <label for="<%= txtNombreProducto.ClientID %>" class="form-label">Nombre del Producto</label>
                    <asp:TextBox ID="txtNombreProducto" runat="server" CssClass="form-control" placeholder="Buscar por nombre..."></asp:TextBox>
                </div>
                <div class="col-md-3">
                    <label for="<%= txtPresentacion.ClientID %>" class="form-label">Presentación</label>
                    <asp:TextBox ID="txtPresentacion" runat="server" CssClass="form-control" placeholder="Ej: 500ml, 1Kg..."></asp:TextBox>
                </div>
                <div class="col-md-2">
                    <label for="<%= ddlEstadoStock.ClientID %>" class="form-label">Estado de Stock</label>
                    <asp:DropDownList ID="ddlEstadoStock" runat="server" CssClass="form-select"></asp:DropDownList>
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
            productos
        </div>
        <div>
            <asp:LinkButton ID="btnNuevoProducto" runat="server" CssClass="btn btn-success" OnClick="btnNuevoProducto_Click">
                <i class='fas fa-plus me-2'></i>Nuevo Producto
            </asp:LinkButton>
        </div>
    </div>

    <%-- 4. Tabla de Productos --%>
    <div class="card shadow-sm border-0">
        <div class="table-responsive">
            <table class="table table-hover align-middle mb-0">
                <thead class="table-light">
                    <tr>
                        <th scope="col" style="width: 35%;">Producto</th>
                        <th scope="col" style="width: 10%;">Tipo</th>
                        <th scope="col"style="width: 15%;">Presentación</th>
                        <th scope="col">Precio Unitario</th>
                        <th scope="col">Stock</th>
                        <th scope="col">Estado</th>
                        <th scope="col" class="text-center">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <asp:Repeater ID="rptInventario" runat="server" OnItemCommand="rptInventario_ItemCommand">
                        <ItemTemplate>
                            <tr>
                                <%-- Producto (Columna 1: Icono + Nombre/Desc) --%>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <%-- Icono Circular --%>
                                        <div class="product-icon-circle <%# Eval("IconoColorCss") %> me-3"> <%-- Clase para el color de fondo --%>
                                            <i class="<%# Eval("IconoCss") %>"></i>
                                        </div>
                                        <%-- Detalles del Producto --%>
                                        <div class="product-details">
                                            <div class="product-name"><%# Eval("Nombre") %></div>
                                            <div class="product-description"><%# Eval("Descripcion") %></div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <span class="type-badge <%# Eval("TipoClaseCss") %>"> <%-- Clase para color del badge --%>
                                        <%# Eval("Tipo") %>
                                    </span>
                                </td>
                                <%-- Presentación (Columna 3) --%>
                                <td><%# Eval("Presentacion") %></td>
                                <%-- Precio Unitario (Columna 4) --%>
                                <td><%# Eval("PrecioFormateado") %></td>
                                <%-- Stock (Columna 5) --%>
                                <td>
                                    <span class="<%# Eval("StockCss") %>"><%# Eval("StockTexto") %></span>
                                </td>
                                <%-- Estado (Columna 6: Badge de Estado) --%>
                                <td>
                                    <span class="status-badge <%# Eval("EstadoClaseCss") %>"> <%-- Clase para color del badge --%>
                                        <%# Eval("Estado") %>
                                    </span>
                                </td>
                                <td class="text-center"> <%-- Centrado como en la imagen --%>
                                    <div class="action-buttons">
                                        <asp:LinkButton ID="btnVer" runat="server" CssClass="btn btn-action-view me-1" ToolTip="Ver Detalles" CommandName="Ver" CommandArgument='<%# Eval("ProductoID") %>'>
                                            <i class='fas fa-eye'></i>
                                        </asp:LinkButton>
                                        <asp:LinkButton ID="btnEditar" runat="server" CssClass="btn btn-action-edit me-1" ToolTip="Editar Producto" CommandName="Editar" CommandArgument='<%# Eval("ProductoID") %>'>
                                            <i class='fas fa-pencil-alt'></i>
                                        </asp:LinkButton>
                                        <asp:LinkButton ID="btnEliminar" runat="server" CssClass="btn btn-action-delete" ToolTip="Eliminar Producto" CommandName="Eliminar" CommandArgument='<%# Eval("ProductoID") %>'>
                                            <i class='fas fa-trash-alt'></i>
                                        </asp:LinkButton>
                                    </div>
                                </td>
                            </tr>
                        </ItemTemplate>
                        <FooterTemplate>
                             <%-- Mensaje si no hay productos --%>
                             <asp:Panel ID="pnlNoProductos" runat="server" Visible='<%# rptInventario.Items.Count == 0 %>'>
                                 <tr>
                                     <td colspan="7" class="text-center"> <%-- Colspan ajustado a 7 columnas --%>
                                         <div class="alert alert-info mt-3 mb-0" role="alert">
                                             <i class="fas fa-info-circle me-2"></i> No se encontraron productos con los filtros aplicados.
                                         </div>
                                     </td>
                                 </tr>
                             </asp:Panel>
                        </FooterTemplate>
                    </asp:Repeater>
                </tbody>
            </table>
        </div>
        
        <%-- Paginación (Estática como en Figma) --%>
        <div class="card-footer d-flex justify-content-between align-items-center">
            <span class="text-muted small">Página 1 de 5</span>
            <nav class="custom-pagination">
                <ul class="pagination mb-0">
                    <li class="page-item"><a class="page-link" href="#"><i class="fas fa-chevron-left"></i></a></li>
                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">4</a></li>
                    <li class="page-item"><a class="page-link" href="#">5</a></li>
                    <li class="page-item"><a class="page-link" href="#"><i class="fas fa-chevron-right"></i></a></li>
                </ul>
            </nav>
        </div>
    </div>

</asp:Content>