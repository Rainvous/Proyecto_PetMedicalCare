<%@ Page Title="Punto de Venta" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_PuntoVenta.aspx.cs"
    Inherits="SoftPetWA.Secretaria_PuntoVenta" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Punto de Venta
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/puntoventa.css" rel="stylesheet" />
    <link href="Content/servicios.css" rel="stylesheet" />
    <%-- 1. AGREGAMOS LA LIBRERÍA SWEETALERT2 --%>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <script type="text/javascript">
        // Función para mostrar alerta de carrito vacío
        function mostrarAlertaCarritoVacio() {
            Swal.fire({
                icon: 'warning',
                title: '¡Tu carrito está vacío!',
                text: 'Para emitir un comprobante, debes seleccionar al menos un producto o servicio.',
                confirmButtonText: 'Entendido',
                confirmButtonColor: '#f0ad4e', // Color naranja/advertencia
                backdrop: `
                    rgba(0,0,123,0.1)
                `
            });
        }

        // Función para mostrar el éxito (opcional, si quieres reemplazar el modal bootstrap en el futuro)
        // Por ahora mantenemos tu modal Bootstrap para el éxito porque ya tiene lógica compleja.
    </script>
    <style>
        /* Estilos simples para la paginación */
        .pagination-container {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 15px;
            margin-top: 20px;
        }
        .page-indicator {
            font-weight: bold;
            color: #555;
        }
        
        /* Estilos del Modal de Éxito (Mantenidos) */
        .modal-success-header { background-color: #198754; color: white; border-top-left-radius: 8px; border-top-right-radius: 8px; padding: 1.5rem; text-align: center; }
        .success-icon-container { background-color: #fff; width: 80px; height: 80px; border-radius: 50%; display: flex; align-items: center; justify-content: center; margin: -55px auto 15px auto; box-shadow: 0 4px 15px rgba(0,0,0,0.15); }
        .success-icon { color: #198754; font-size: 3rem; }
        .receipt-info { background-color: #f8f9fa; border: 2px dashed #dee2e6; border-radius: 8px; padding: 1rem; margin: 1rem 0; text-align: center; }
        .receipt-number { font-family: 'Courier New', Courier, monospace; font-weight: 900; font-size: 1.5rem; color: #333; letter-spacing: 2px; }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3><i class="fas fa-cash-register me-2"></i>Punto de Venta</h3>
    </div>

    <div class="row">
        <%-- COLUMNA IZQUIERDA: CATÁLOGO --%>
        <div class="col-md-8">
            
            <%-- Barra de Búsqueda --%>
            <div class="input-group mb-3">
                <asp:TextBox ID="txtBusquedaProducto" runat="server" CssClass="form-control" 
                    placeholder="Buscar..."></asp:TextBox>
                <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary" OnClick="btnBuscar_Click">
                    <i class="fas fa-search"></i>
                </asp:LinkButton>
            </div>

            <%-- Pestañas de Filtro (Simplificadas) --%>
            <div class="mb-3">
                <ul class="nav nav-pills nav-fill bg-white p-1 rounded shadow-sm border">
                    <li class="nav-item">
                        <asp:LinkButton ID="btnTabProductos" runat="server" CssClass="nav-link active" OnClick="btnTabProductos_Click">
                            <i class="fas fa-box me-2"></i>PRODUCTOS
                        </asp:LinkButton>
                    </li>
                    <li class="nav-item">
                        <asp:LinkButton ID="btnTabServicios" runat="server" CssClass="nav-link" OnClick="btnTabServicios_Click">
                            <i class="fas fa-stethoscope me-2"></i>SERVICIOS
                        </asp:LinkButton>
                    </li>
                </ul>
            </div>

            <%-- Grilla de Resultados --%>
            <asp:UpdatePanel ID="updCatalogo" runat="server" UpdateMode="Conditional">
                <ContentTemplate>
                    <div class="row" style="min-height: 400px;">
                        <asp:Repeater ID="rptProductos" runat="server" OnItemCommand="rptProductos_ItemCommand">
                            <ItemTemplate>
                                <div class="col-lg-4 col-md-6 mb-3">
                                    <asp:LinkButton ID="btnAgregarProducto" runat="server" CssClass="product-card-pos h-100" 
                                        CommandName="Agregar" 
                                        CommandArgument='<%# Eval("Tipo") + "|" + Eval("ID") %>'>
                
                                        <div class="product-icon-pos"><i class="<%# Eval("IconoCss") %>"></i></div>
                                        <span class="product-name"><%# Eval("Nombre") %></span>
                                        <span class="product-desc d-block text-truncate"><%# Eval("Descripcion") %></span>
                                        <div class="product-price mt-2"><%# Eval("PrecioFormateado") %></div>
                                        <span class="product-stock small text-muted"><%# Eval("StockTexto") %></span>
                                    </asp:LinkButton>
                                </div>
                            </ItemTemplate>
                            <FooterTemplate>
                                <div runat="server" visible="<%# rptProductos.Items.Count == 0 %>" class="col-12 text-center mt-5 text-muted">
                                    <i class="fas fa-box-open fa-3x mb-3"></i>
                                    <p>No se encontraron resultados en esta página.</p>
                                </div>
                            </FooterTemplate>
                        </asp:Repeater>
                    </div>

                    <%-- Controles de Paginación --%>
                    <div class="pagination-container border-top pt-3">
                        <asp:LinkButton ID="btnPaginaAnterior" runat="server" CssClass="btn btn-outline-secondary btn-sm" OnClick="btnPaginaAnterior_Click">
                            <i class="fas fa-chevron-left me-1"></i> Anterior
                        </asp:LinkButton>
                        
                        <span class="page-indicator">
                            Página <asp:Label ID="lblPaginaActual" runat="server" Text="1"></asp:Label>
                        </span>

                        <asp:LinkButton ID="btnPaginaSiguiente" runat="server" CssClass="btn btn-outline-secondary btn-sm" OnClick="btnPaginaSiguiente_Click">
                            Siguiente <i class="fas fa-chevron-right ms-1"></i>
                        </asp:LinkButton>
                    </div>
                </ContentTemplate>
            </asp:UpdatePanel>
        </div>

        <%-- COLUMNA DERECHA: CARRITO (Sin Cambios Mayores) --%>
        <div class="col-lg-4">
            <asp:UpdatePanel ID="updCarrito" runat="server" UpdateMode="Conditional">
                <ContentTemplate>
                    <div class="cart-card d-flex flex-column h-100">
                        <div class="cart-header">
                            <i class="fas fa-shopping-cart"></i> Carrito de Compra
                        </div>

                        <%-- Bloque Cliente --%>
                        <div class="client-info-block">
                            <div>
                                <div class="client-name">
                                    <asp:Label ID="lblClienteNombre" runat="server"></asp:Label>
                                </div>
                                <div class="client-details">
                                    <asp:Label ID="lblClienteInfo" runat="server"></asp:Label>
                                </div>
                                <div class="mt-2">
                                    <asp:LinkButton ID="btnCambiarCliente" runat="server" CssClass="text-primary small me-2" OnClick="btnCambiarCliente_Click">
                                         <i class="fas fa-search me-1"></i>Buscar Registrado
                                    </asp:LinkButton>
                                    <span class="text-muted">|</span>
                                    <asp:LinkButton ID="btnClienteGuest" runat="server" CssClass="text-success small ms-2" OnClick="btnClienteGuest_Click">
                                         <i class="fas fa-user-plus me-1"></i>Invitado
                                    </asp:LinkButton>
                                </div>
                            </div>
                            <i class="fas fa-user-circle client-icon"></i>
                        </div>

                        <%-- Items Carrito --%>
                        <div class="cart-items-container">
                            <asp:Repeater ID="rptCarrito" runat="server" OnItemCommand="rptCarrito_ItemCommand">
                                <ItemTemplate>
                                    <div class="cart-item">
                                        <div class="cart-item-details">
                                            <span class="cart-item-name"><%# Eval("Nombre") %></span>
                                            <div class="cart-item-quantity-control">
                                                <asp:LinkButton ID="btnRestar" runat="server" CssClass="quantity-button" CommandName="Restar" CommandArgument='<%# Eval("ItemID") %>'><i class="fas fa-minus"></i></asp:LinkButton>
                                                <asp:TextBox ID="txtCantidad" runat="server" CssClass="quantity-input" Text='<%# Eval("Cantidad") %>' ReadOnly="true"></asp:TextBox>
                                                <asp:LinkButton ID="btnSumar" runat="server" CssClass="quantity-button" CommandName="Sumar" CommandArgument='<%# Eval("ItemID") %>'><i class="fas fa-plus"></i></asp:LinkButton>
                                            </div>
                                        </div>
                                        <div class="cart-item-price-delete">
                                            <span class="cart-item-price"><%# Eval("PrecioTotalFormateado") %></span>
                                            <asp:LinkButton ID="btnQuitar" runat="server" CssClass="btn-remove-item" CommandName="Quitar" CommandArgument='<%# Eval("ItemID") %>' ToolTip="Eliminar"><i class="fas fa-trash-alt"></i></asp:LinkButton>
                                        </div>
                                    </div>
                                </ItemTemplate>
                            </asp:Repeater>
                        </div>

                        <%-- Totales y Acciones --%>
                        <div class="cart-footer">
                            <div class="cart-summary">
                                <div class="summary-row"><span>Subtotal:</span><span><asp:Label ID="lblSubtotal" runat="server"></asp:Label></span></div>
                                <div class="summary-row"><span>IGV (18%):</span><span><asp:Label ID="lblIGV" runat="server"></asp:Label></span></div>
                                <div class="summary-row total"><span>TOTAL:</span><span><asp:Label ID="lblTotal" runat="server"></asp:Label></span></div>
                            </div>

                            <div class="payment-section">
                                <div class="mb-2">
                                    <label class="form-label mb-1">Método de Pago</label>
                                    <asp:DropDownList ID="ddlMetodoPago" runat="server" CssClass="form-select form-select-sm"></asp:DropDownList>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label mb-1">Tipo Comprobante</label>
                                    <asp:DropDownList ID="ddlTipoComprobante" runat="server" CssClass="form-select form-select-sm" AutoPostBack="true" OnSelectedIndexChanged="ddlTipoComprobante_SelectedIndexChanged"></asp:DropDownList>
                                </div>
                                <asp:Panel ID="pnlDatosFactura" runat="server" Visible="false" CssClass="mb-3 p-2 border rounded bg-light">
                                    <label class="form-label text-primary fw-bold small">RUC Cliente</label>
                                    <asp:TextBox ID="txtRucCliente" runat="server" CssClass="form-control form-control-sm" ReadOnly="true"></asp:TextBox>
                                </asp:Panel>
                                <div class="d-grid gap-2">
                                    <asp:LinkButton ID="btnGenerarComprobante" runat="server" CssClass="btn btn-success" OnClick="btnGenerarComprobante_Click">
                                        <i class="fas fa-check me-2"></i>Pagar
                                    </asp:LinkButton>
                                    <asp:LinkButton ID="btnCancelar" runat="server" CssClass="btn btn-danger" OnClick="btnCancelar_Click">
                                        Cancelar
                                    </asp:LinkButton>
                                </div>
                            </div>
                        </div>
                    </div>
                </ContentTemplate>
            </asp:UpdatePanel>
        </div>
    </div>

    <%-- MODALES (Mantenemos los mismos, solo asegúrate que estén al final) --%>
    <%-- Modal Buscar Cliente --%>
    <div class="modal fade" id="modalBuscarCliente" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Seleccionar Cliente</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <asp:UpdatePanel ID="updModalCliente" runat="server" UpdateMode="Conditional">
                        <ContentTemplate>
                            <div class="row g-2 mb-3">
                                <div class="col-9">
                                    <asp:TextBox ID="txtBuscarNombreCliente" runat="server" CssClass="form-control" placeholder="Nombre..."></asp:TextBox>
                                </div>
                                <div class="col-3">
                                    <asp:LinkButton ID="btnBuscarClienteModal" runat="server" CssClass="btn btn-primary w-100" OnClick="btnBuscarClienteModal_Click">Buscar</asp:LinkButton>
                                </div>
                            </div>
                            <div class="list-group" style="max-height: 250px; overflow-y: auto;">
                                <asp:Repeater ID="rptResultadosCliente" runat="server" OnItemCommand="rptResultadosCliente_ItemCommand">
                                    <ItemTemplate>
                                        <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                                            <div>
                                                <h6 class="mb-0"><%# Eval("nombre") %></h6>
                                                <small class="text-muted">Doc: <%# Eval("nroDocumento") %> | RUC: <%# Eval("ruc") ?? "No" %></small>
                                            </div>
                                            <asp:LinkButton ID="btnSeleccionar" runat="server" CssClass="btn btn-sm btn-outline-primary"
                                                CommandName="Seleccionar"
                                                CommandArgument='<%# Eval("personaId") + "|" + (Eval("ruc") ?? "0") + "|" + Eval("nombre") %>'>Seleccionar</asp:LinkButton>
                                        </div>
                                    </ItemTemplate>
                                </asp:Repeater>
                            </div>
                        </ContentTemplate>
                    </asp:UpdatePanel>
                </div>
            </div>
        </div>
    </div>

    <%-- Modal Guest --%>
    <div class="modal fade" id="modalGuest" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-light">
                    <h5 class="modal-title text-success">Cliente Invitado</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <asp:UpdatePanel ID="updModalGuest" runat="server" UpdateMode="Conditional">
                        <ContentTemplate>
                            <div class="mb-3">
                                <label>Nombre *</label>
                                <asp:TextBox ID="txtGuestNombre" runat="server" CssClass="form-control"></asp:TextBox>
                            </div>
                            <div class="row mb-3">
                                <div class="col-6"><label>DNI *</label><asp:TextBox ID="txtGuestDNI" runat="server" CssClass="form-control" TextMode="Number"></asp:TextBox></div>
                                <div class="col-6"><label>RUC (Opcional)</label><asp:TextBox ID="txtGuestRUC" runat="server" CssClass="form-control" TextMode="Number"></asp:TextBox></div>
                            </div>
                            <div class="text-end">
                                <asp:LinkButton ID="btnGuardarGuest" runat="server" CssClass="btn btn-success" OnClick="btnGuardarGuest_Click">Confirmar</asp:LinkButton>
                            </div>
                        </ContentTemplate>
                    </asp:UpdatePanel>
                </div>
            </div>
        </div>
    </div>

    <%-- Modal Exito --%>
    <div class="modal fade" id="modalExitoVenta" data-bs-backdrop="static" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content border-0 shadow-lg">
                <asp:UpdatePanel ID="updModalExito" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-header modal-success-header border-0 d-flex flex-column align-items-center pt-4 pb-5">
                            <h4 class="modal-title fw-bold mt-2">¡Venta Exitosa!</h4>
                        </div>
                        <div class="modal-body px-4 pb-4 position-relative pt-0">
                            <div class="success-icon-container"><i class="fas fa-check-circle success-icon"></i></div>
                            <div class="text-center mt-3">
                                <div class="receipt-info">
                                    <div class="text-uppercase text-muted small mb-1"><asp:Label ID="lblModalTipoDoc" runat="server"></asp:Label></div>
                                    <div class="receipt-number">
                                        <asp:Label ID="lblModalSerie" runat="server"></asp:Label>-<asp:Label ID="lblModalNumero" runat="server"></asp:Label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer border-0 justify-content-center pb-4">
                            <button type="button" class="btn btn-outline-secondary" onclick="window.location='Secretaria_PuntoVenta.aspx'">Cerrar</button>
                            <asp:LinkButton ID="btnIrReportes" runat="server" CssClass="btn btn-primary" OnClick="btnIrReportes_Click">Reportes</asp:LinkButton>
                        </div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script>
        $(document).ready(function () {
            var prm = Sys.WebForms.PageRequestManager.getInstance();
            if (prm != null) {
                prm.add_endRequest(function (sender, e) {
                    if (sender._postBackSettings.sourceElement && sender._postBackSettings.sourceElement.id.indexOf("btnSeleccionar") !== -1) { $('#modalBuscarCliente').modal('hide'); }
                    if (sender._postBackSettings.sourceElement && sender._postBackSettings.sourceElement.id.indexOf("btnGuardarGuest") !== -1) { $('#modalGuest').modal('hide'); }
                });
            }
        });
    </script>
</asp:Content>