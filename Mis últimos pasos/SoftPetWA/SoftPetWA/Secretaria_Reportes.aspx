<%@ Page Title="Reportes" Language="C#" MasterPageFile="~/Secretaria.Master" AutoEventWireup="true"
    CodeBehind="Secretaria_Reportes.aspx.cs" Inherits="SoftPetWA.Secretaria_Reportes" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Comprobantes
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/reportes.css" rel="stylesheet" />
    <link href="Content/cargando.css" rel="stylesheet" />
    
    <%-- 1. AGREGAMOS EL CDN DE SWEETALERT2 --%>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <script type="text/javascript">
        // Función para mostrar la carga
        function mostrarCarga() {
            var overlay = document.getElementById('loadingOverlay');
            if (overlay) overlay.style.display = 'flex';
        }

        // Función para ocultar la carga (Forzamos el display none)
        function ocultarCarga() {
            var overlay = document.getElementById('loadingOverlay');
            if (overlay) overlay.style.display = 'none';
        }

        function mostrarCargaPDF() {
            mostrarCarga();
            setTimeout(ocultarCarga, 5000);
        }

        // --- FUNCIONES DEL MODAL DE CORREO ---
        var modalEmailBootstrap; // Variable global para la instancia del modal

        function abrirModalEmail() {
            var el = document.getElementById('modalEmail');
            // Creamos la instancia y la guardamos
            modalEmailBootstrap = new bootstrap.Modal(el);
            modalEmailBootstrap.show();
        }

        function cerrarModalEmail() {
            // Intentamos obtener la instancia existente o crear una para cerrarla
            var el = document.getElementById('modalEmail');
            var modal = bootstrap.Modal.getInstance(el);
            if (modal) {
                modal.hide();
            } else {
                // Fallback por si acaso
                var btnClose = document.getElementById('btnCerrarModal');
                if (btnClose) btnClose.click();
            }

            // IMPORTANTE: Eliminar manualmente el backdrop si se queda pegado
            var backdrops = document.getElementsByClassName('modal-backdrop');
            while (backdrops.length > 0) {
                backdrops[0].parentNode.removeChild(backdrops[0]);
            }
            document.body.classList.remove('modal-open');
        }

        // --- NUEVA FUNCIÓN PARA MENSAJE DE ÉXITO ---
        function mostrarMensajeExito(mensaje) {
            // 1. Aseguramos que se oculte el spinner de carga
            ocultarCarga();

            // 2. Cerramos el modal de email si está abierto
            cerrarModalEmail();

            // 3. Mostramos la alerta bonita
            Swal.fire({
                icon: 'success',
                title: '¡Envío Exitoso!',
                text: mensaje,
                confirmButtonColor: '#20c997', // Tu color "Teal"
                confirmButtonText: 'Genial'
            });
        }

        // --- NUEVA FUNCIÓN PARA MENSAJE DE ERROR ---
        function mostrarMensajeError(mensaje) {
            ocultarCarga();
            Swal.fire({
                icon: 'error',
                title: 'Ocurrió un problema',
                text: mensaje,
                confirmButtonColor: '#d33'
            });
        }
    </script>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <%-- OVERLAY DE CARGA (Usando tu CSS) --%>
    <div id="loadingOverlay" class="loading-overlay" style="display:none;">
        <div class="loading-content">
            <div class="pet-spinner">
                <i class="fas fa-paw"></i>
            </div>
            <div class="loading-text">Procesando solicitud...</div>
        </div>
    </div>

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3><i class="fas fa-file-invoice-dollar me-2 text-primary"></i>Comprobantes de Pago</h3>
    </div>

    <asp:UpdatePanel ID="updReportes" runat="server">
        <ContentTemplate>

            <%-- BARRA DE FILTROS --%>
            <div class="card shadow-sm border-0 mb-4">
                <div class="card-body">
                    <div class="row align-items-end g-3">
                        
                        <%-- Columna 1: Fecha de Emisión --%>
                        <div class="col-md-6">
                            <label class="form-label fw-bold text-muted small">FECHA DE EMISIÓN</label>
                            <div class="input-group">
                                <span class="input-group-text bg-white"><i class="far fa-calendar-alt"></i></span>
                                <asp:TextBox ID="txtFechaBusqueda" runat="server" TextMode="Date" CssClass="form-control"></asp:TextBox>
                            </div>
                        </div>

                        <%-- Columna 2: Botón Buscar --%>
                        <div class="col-md-6">
                            <div class="d-grid">
                                <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary" OnClick="btnBuscar_Click" OnClientClick="mostrarCarga();">
                                    <i class="fas fa-search me-2"></i>Buscar Comprobantes
                                </asp:LinkButton>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%-- LISTA DE COMPROBANTES --%>
            <asp:Panel ID="pnlComprobantes" runat="server">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h5 class="text-muted">Resultados de la búsqueda</h5>
                </div>

                <div class="report-list-container">
                    <asp:Repeater ID="rptComprobantes" runat="server" 
                        OnItemCommand="rptComprobantes_ItemCommand" 
                        OnItemDataBound="rptComprobantes_ItemDataBound">
                        <ItemTemplate>
                            <div class="report-item">
                                <%-- Info --%>
                                <div class="report-info-group">
                                    <div class="report-icon-box">
                                        <i class="<%# Eval("Icono") %>"></i>
                                    </div>
                                    <div class="report-details">
                                        <h5><%# Eval("TipoDoc") %> - <%# Eval("SerieNumero") %></h5>
                                        <div class="report-meta">
                                            <span><i class="fas fa-calendar-alt"></i> <%# Eval("FechaEmision") %></span>
                                            <span><i class="fas fa-user"></i> <%# Eval("ClienteNombre") %></span>
                                        </div>
                                    </div>
                                </div>

                                <%-- Acciones --%>
                                <div class="report-actions text-end">
                                    <div class="report-amount mb-2"><%# Eval("Total") %></div>
                                    
                                    <div class="btn-group">
                                        <%-- Botón Ver PDF --%>
                                        <%-- Enviamos ID|TIPO --%>
                                        <asp:LinkButton ID="btnPdf" runat="server" CssClass="btn btn-sm btn-outline-danger me-2" 
                                            CommandName="VerPDF" 
                                            OnClientClick="mostrarCargaPDF();"
                                            CommandArgument='<%# Eval("DocumentoID") + "|" + Eval("TipoDoc") %>'>
                                            <i class="fas fa-file-pdf"></i> PDF
                                        </asp:LinkButton>

                                        <%-- Botón Enviar Email --%>
                                        <%-- Enviamos ID_DOC|TIPO|ID_PERSONA --%>
                                        <asp:LinkButton ID="btnEmail" runat="server" CssClass="btn btn-sm btn-outline-success" 
                                            CommandName="PreparaEmail" 
                                            OnClientClick="mostrarCarga();"
                                            CommandArgument='<%# Eval("DocumentoID") + "|" + Eval("TipoDoc") + "|" + Eval("PersonaID") %>'>
                                            <i class="fas fa-envelope"></i> Enviar
                                        </asp:LinkButton>
                                    </div>
                                </div>
                            </div>
                        </ItemTemplate>
                    </asp:Repeater>
                </div>
                
                <div runat="server" id="divNoResultados" visible="false" class="text-center mt-5 text-muted">
                    <i class="fas fa-folder-open fa-3x mb-3"></i>
                    <p>No se encontraron comprobantes para esta fecha.</p>
                </div>
            </asp:Panel>

            <%-- MODAL DE ENVÍO DE CORREO --%>
            <div class="modal fade" id="modalEmail" tabindex="-1" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header bg-success text-white">
                            <h5 class="modal-title"><i class="fas fa-paper-plane me-2"></i>Enviar Comprobante</h5>
                            <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close" id="btnCerrarModal"></button>
                        </div>
                        <div class="modal-body">
                            <p>Confirme el correo electrónico del destinatario:</p>
                            
                            <div class="mb-3">
                                <label class="form-label">Correo Electrónico</label>
                                <asp:TextBox ID="txtEmailDestino" runat="server" CssClass="form-control" placeholder="ejemplo@correo.com"></asp:TextBox>
                                <asp:RequiredFieldValidator ID="rfvEmail" runat="server" ControlToValidate="txtEmailDestino" 
                                    ErrorMessage="El correo es obligatorio" CssClass="text-danger small" ValidationGroup="GrupoEmail" Display="Dynamic"></asp:RequiredFieldValidator>
                                <asp:RegularExpressionValidator ID="revEmail" runat="server" ControlToValidate="txtEmailDestino"
                                    ErrorMessage="Formato de correo inválido" CssClass="text-danger small" ValidationGroup="GrupoEmail" Display="Dynamic"
                                    ValidationExpression="\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*"></asp:RegularExpressionValidator>
                            </div>

                            <%-- Campos ocultos para mantener el estado --%>
                            <asp:HiddenField ID="hfIdDocModal" runat="server" />
                            <asp:HiddenField ID="hfTipoDocModal" runat="server" />

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <asp:LinkButton ID="btnEnviarCorreoConfirmado" runat="server" CssClass="btn btn-success" 
                                OnClick="btnEnviarCorreoConfirmado_Click" ValidationGroup="GrupoEmail" OnClientClick="mostrarCarga();">
                                <i class="fas fa-check me-2"></i>Enviar
                            </asp:LinkButton>
                        </div>
                    </div>
                </div>
            </div>

        </ContentTemplate>
    </asp:UpdatePanel>

</asp:Content>