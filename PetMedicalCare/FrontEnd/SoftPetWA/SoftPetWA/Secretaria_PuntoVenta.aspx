<%-- Nombre del archivo: Secretaria_PuntoVenta.aspx --%>
<%@ Page Title="Punto de Venta" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true" 
    CodeBehind="Secretaria_PuntoVenta.aspx.cs" 
    Inherits="SoftPetWA.Secretaria_PuntoVenta" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Punto de Venta
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Vinculamos el CSS específico para esta página --%>
    <link href="Content/puntoventa.css" rel="stylesheet" />
    <%-- Reutilizamos el CSS de filtros si existe --%>
    <link href="Content/servicios.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-cash-register me-2"></i>Punto de Venta</h3>
    </div>

    <div class="row">
        <%-- ======================================= --%>
        <%--        COLUMNA IZQUIERDA: PRODUCTOS     --%>
        <%-- ======================================= --%>
        <div class="col-md-8">
            <%-- Barra de Búsqueda --%>
            <div class="input-group mb-3">
                <asp:TextBox ID="txtBusquedaProducto" runat="server" CssClass="form-control" placeholder="Buscar producto o servicio..."></asp:TextBox>
                <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnBuscar_Click">
                    <i class="fas fa-search"></i> Buscar
                </asp:LinkButton>
            </div>

            <%-- Pestañas de Filtro --%>
            <div class="mb-3">
                <asp:Repeater ID="rptFiltroTipo" runat="server" OnItemCommand="rptFiltroTipo_ItemCommand">
                    <HeaderTemplate><ul class="nav nav-pills filter-tabs"></HeaderTemplate>
                    <ItemTemplate>
                        <li class="nav-item">
                            <asp:LinkButton ID="btnTipo" runat="server" 
                                CssClass='<%# Container.ItemIndex == 0 ? "nav-link active" : "nav-link" %>' 
                                CommandName="Filtrar" CommandArgument='<%# Eval("Tipo") %>'>
                                <i class="<%# Eval("IconoCss") %> me-1"></i> <%# Eval("Tipo") %>
                            </asp:LinkButton>
                        </li>
                    </ItemTemplate>
                    <FooterTemplate></ul></FooterTemplate>
                </asp:Repeater>
            </div>

            <%-- Grid de Productos --%>
            <div class="row">
                <asp:Repeater ID="rptProductos" runat="server" OnItemCommand="rptProductos_ItemCommand">
                    <ItemTemplate>
                        <div class="col-lg-4 col-md-6 mb-3">
                            <asp:LinkButton ID="btnAgregarProducto" runat="server" CssClass="product-card-pos" CommandName="Agregar" CommandArgument='<%# Eval("ProductoID") %>'>
                                <div class="product-icon-pos"><i class="<%# Eval("IconoCss") %>"></i></div>
                                <span class="product-name"><%# Eval("Nombre") %></span>
                                <span class="product-desc d-block"><%# Eval("Descripcion") %></span>
                                <div class="product-price"><%# Eval("PrecioFormateado") %></div>
                                <span class="product-stock"><%# Eval("StockTexto") %></span>
                            </asp:LinkButton>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
            </div>
        </div>
<%-- En Secretaria_PuntoVenta.aspx --%>
        <%-- ======================================= --%>
        <%--        COLUMNA DERECHA: CARRITO         --%>
        <%-- ======================================= --%>
        <div class="col-lg-4">
            <div class="cart-card d-flex flex-column h-100">
                <div class="cart-header">
                    <i class="fas fa-shopping-cart"></i>
                    Carrito de Compra
                </div>

                <div class="client-info-block">
                    <div>
                        <div class="client-name">
                            <asp:Label ID="lblClienteNombre" runat="server"></asp:Label> <%-- Se llena desde CS --%>
                        </div>
                        <div class="client-details">
                            <asp:Label ID="lblClienteInfo" runat="server"></asp:Label> <%-- Se llena desde CS --%>
                        </div>
                         <%-- Link Cambiar Cliente (opcional, como en tu archivo original) --%>
                         <asp:LinkButton ID="btnCambiarCliente" runat="server" CssClass="text-primary small">Cambiar Cliente</asp:LinkButton>
                    </div>
                    <i class="fas fa-user-circle client-icon"></i>
                </div>

                <%-- Contenedor de Items --%>
                <div class="cart-items-container">
                    <asp:Repeater ID="rptCarrito" runat="server" OnItemCommand="rptCarrito_ItemCommand">
                        <ItemTemplate>
                            <div class="cart-item">
                                <%-- Detalles (Nombre y Cantidad) --%>
                                <div class="cart-item-details">
                                    <span class="cart-item-name"><%# Eval("Nombre") %></span>
                                    <div class="cart-item-quantity-control">
                                        <asp:LinkButton ID="btnRestar" runat="server"
                                            CssClass="quantity-button" CommandName="Restar"
                                            CommandArgument='<%# Eval("ItemID") %>'>
                                            <i class="fas fa-minus"></i>
                                        </asp:LinkButton>
                                        <asp:TextBox ID="txtCantidad" runat="server"
                                            CssClass="quantity-input"
                                            Text='<%# Eval("Cantidad") %>'
                                            AutoPostBack="true" OnTextChanged="txtQuantity_TextChanged"
                                            CommandArgument='<%# Eval("ItemID") %>'></asp:TextBox>
                                        <asp:LinkButton ID="btnSumar" runat="server"
                                            CssClass="quantity-button" CommandName="Sumar"
                                            CommandArgument='<%# Eval("ItemID") %>'>
                                            <i class="fas fa-plus"></i>
                                        </asp:LinkButton>
                                    </div>
                                </div>
                                <%-- Precio y Botón Eliminar --%>
                                <div class="cart-item-price-delete">
                                    <span class="cart-item-price"><%# Eval("PrecioTotalFormateado") %></span>
                                    <asp:LinkButton ID="btnQuitar" runat="server"
                                        CssClass="btn-remove-item" CommandName="Quitar"
                                        CommandArgument='<%# Eval("ItemID") %>' ToolTip="Eliminar">
                                        <i class="fas fa-trash-alt"></i>
                                    </asp:LinkButton>
                                </div>
                            </div>
                        </ItemTemplate>
                    </asp:Repeater>
                </div>

                <%-- Footer (Totales y Pago) --%>
                <div class="cart-footer">
                    <%-- Resumen --%>
                    <div class="cart-summary">
                        <div class="summary-row">
                            <span>Subtotal:</span>
                            <span><asp:Label ID="lblSubtotal" runat="server"></asp:Label></span>
                        </div>
                        <div class="summary-row">
                            <span>IGV (18%):</span>
                            <span><asp:Label ID="lblIGV" runat="server"></asp:Label></span>
                        </div>
                        <div class="summary-row total">
                            <span>TOTAL:</span>
                            <span><asp:Label ID="lblTotal" runat="server"></asp:Label></span>
                        </div>
                    </div>
                    <%-- Sección Pago (sin cambios estructurales mayores) --%>
                    <div class="payment-section">
                        <div class="mb-3">
                            <label class="form-label">Método de Pago</label>
                            <asp:DropDownList ID="ddlMetodoPago" runat="server" CssClass="form-select"></asp:DropDownList>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Tipo de Comprobante</label>
                            <asp:DropDownList ID="ddlTipoComprobante" runat="server" CssClass="form-select"></asp:DropDownList>
                        </div>
                        <div class="d-grid gap-2">
                            <asp:LinkButton ID="btnGenerarComprobante" runat="server" CssClass="btn btn-success" OnClick="btnGenerarComprobante_Click">
                                <i class="fas fa-check me-2"></i>Generar Comprobante de pago
                            </asp:LinkButton>
                            <asp:LinkButton ID="btnCancelar" runat="server" CssClass="btn btn-danger" OnClick="btnCancelar_Click">
                                <i class="fas fa-ban me-2"></i>Cancelar
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</asp:Content>