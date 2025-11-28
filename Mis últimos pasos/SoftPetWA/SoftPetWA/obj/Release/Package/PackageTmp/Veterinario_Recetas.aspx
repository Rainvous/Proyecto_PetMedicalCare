<%@ Page Title="Gestión de Recetas" Language="C#" MasterPageFile="~/Veterinario.Master" AutoEventWireup="true"
    CodeBehind="Veterinario_Recetas.aspx.cs"
    Inherits="SoftPetWA.Veterinario_Recetas" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Gestión de Recetas Médicas
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/clientes.css" rel="stylesheet" />
    <style>
        .form-control[readonly], .form-select[disabled] { background-color: #e9ecef; cursor: not-allowed; opacity: 1; }
        .val-error { color: #dc3545; font-size: 0.85em; margin-top: 0.25rem; display: block; }
        .is-invalid { border-color: #dc3545 !important; }
        .recipe-card { border-left: 4px solid #0097a7; transition: all 0.2s; }
        .recipe-card:hover { transform: translateY(-2px); box-shadow: 0 5px 15px rgba(0,0,0,0.1); }
        .custom-pagination .page-item { margin: 0 2px; }
        .custom-pagination .page-link { border: none; border-radius: 50% !important; width: 35px; height: 35px; display: flex; align-items: center; justify-content: center; color: #6c757d; font-weight: 600; }
        .custom-pagination .page-item.active .page-link { background-color: #0097a7; color: white; box-shadow: 0 2px 5px rgba(0,151,167,0.3); }
        .hidden-id { display: none !important; }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    <asp:UpdatePanel ID="updPanelRecetas" runat="server" UpdateMode="Conditional">
        <ContentTemplate>
            <div class="d-flex justify-content-between align-items-center mb-3">
                <h3><i class="fas fa-file-prescription me-2"></i>Recetas Médicas</h3>
            </div>

            <%-- FILTROS --%>
            <div class="card shadow-sm mb-4 filter-bar">
                <div class="card-body">
                    <div class="row g-3 align-items-end">
                        <div class="col-md-3"><label class="form-label">Mascota</label><asp:TextBox ID="txtFiltroMascota" runat="server" CssClass="form-control" placeholder="Nombre..."></asp:TextBox></div>
                        <div class="col-md-3"><label class="form-label">Dueño</label><asp:TextBox ID="txtFiltroDuenio" runat="server" CssClass="form-control" placeholder="Nombre..."></asp:TextBox></div>
                        <div class="col-md-2"><label class="form-label">Fecha Emisión</label><asp:TextBox ID="txtFiltroFecha" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox></div>
                        <div class="col-md-2"><label class="form-label">Estado</label><asp:DropDownList ID="ddlFiltroEstado" runat="server" CssClass="form-select"><asp:ListItem Value="-1" Text="Todos" Selected="True"></asp:ListItem><asp:ListItem Value="1" Text="Vigente"></asp:ListItem><asp:ListItem Value="0" Text="Anulada"></asp:ListItem></asp:DropDownList></div>
                        <div class="col-md-2 d-flex">
                            <asp:LinkButton ID="btnBuscar" runat="server" CssClass="btn btn-primary me-2 w-100" OnClick="btnBuscar_Click"><i class='fas fa-search'></i></asp:LinkButton>
                            <asp:LinkButton ID="btnLimpiar" runat="server" CssClass="btn btn-outline-secondary w-100" OnClick="btnLimpiar_Click"><i class='fas fa-eraser'></i></asp:LinkButton>
                        </div>
                    </div>
                </div>
            </div>

            <div class="d-flex justify-content-end align-items-center mb-3">
                <asp:LinkButton ID="btnNuevaReceta" runat="server" CssClass="btn btn-success" OnClick="btnNuevaReceta_Click"><i class='fas fa-plus me-2'></i>Nueva Receta</asp:LinkButton>
            </div>

            <%-- LISTADO --%>
            <div id="divListaRecetas" runat="server" visible="false">
                <asp:Repeater ID="rptRecetas" runat="server" OnItemCommand="rptRecetas_ItemCommand">
                    <ItemTemplate>
                        <div class="card mb-3 shadow-sm recipe-card">
                            <div class="card-body py-3">
                                <div class="row align-items-center">
                                    <div class="col-md-2 text-center border-end">
                                        <i class="fas fa-notes-medical text-info fa-2x mb-2"></i>
                                        <div class="small fw-bold text-secondary"><%# Eval("FechaEmision") %></div>
                                    </div>
                                    <div class="col-md-6 ps-4">
                                        <h5 class="fw-bold mb-1 text-dark"><%# Eval("MascotaNombre") %></h5>
                                        <div class="text-muted small mb-1"><i class="fas fa-user-circle me-1"></i>Prop: <strong><%# Eval("PropietarioNombre") %></strong></div>
                                        <div class="text-secondary small fst-italic text-truncate"><i class="fas fa-stethoscope me-1"></i>Dx: <%# Eval("DiagnosticoResumen") %></div>
                                    </div>
                                    <div class="col-md-4 text-end">
                                        <div class="mb-2"><span class="badge rounded-pill <%# Eval("EstadoClase") %>"><%# Eval("EstadoTexto") %></span></div>
                                        <div>
                                            <button type="button" class="btn action-btn action-btn-view me-1" title="Ver"
                                                onclick='abrirModalJS(this, true)'
                                                data-id='<%# Eval("RecetaID") %>' data-citaid='<%# Eval("CitaID") %>' data-fechaemi='<%# Eval("FechaEmisionISO") %>'
                                                data-vigencia='<%# Eval("VigenciaISO") %>' data-diagnostico='<%# Eval("DiagnosticoCompleto") %>'
                                                data-observaciones='<%# Eval("Observaciones") %>' data-activo='<%# Eval("ActivoBool").ToString().ToLower() %>'>
                                                <i class="fas fa-eye"></i>
                                            </button>
                                            <button type="button" class="btn action-btn action-btn-edit me-1" title="Editar"
                                                onclick='abrirModalJS(this, false)'
                                                data-id='<%# Eval("RecetaID") %>' data-citaid='<%# Eval("CitaID") %>' data-fechaemi='<%# Eval("FechaEmisionISO") %>'
                                                data-vigencia='<%# Eval("VigenciaISO") %>' data-diagnostico='<%# Eval("DiagnosticoCompleto") %>'
                                                data-observaciones='<%# Eval("Observaciones") %>' data-activo='<%# Eval("ActivoBool").ToString().ToLower() %>'>
                                                <i class="fas fa-pencil-alt"></i>
                                            </button>
                                            <button type="button" class="btn action-btn action-btn-delete" title="Eliminar" onclick="abrirModalEliminar('<%# Eval("RecetaID") %>', '<%# Eval("MascotaNombre") %>')"><i class="fas fa-trash-alt"></i></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </ItemTemplate>
                </asp:Repeater>
                <div class="d-flex justify-content-between align-items-center mt-3 p-2 bg-white rounded border">
                    <div class="text-muted small ps-2">Mostrando <asp:Literal ID="litPaginacion" runat="server"></asp:Literal></div>
                    <nav class="custom-pagination">
                        <ul class="pagination mb-0">
                            <li class="page-item"><asp:LinkButton ID="lnkAnterior" runat="server" CssClass="page-link" OnClick="lnkPaginado_Click" CommandName="Anterior"><i class="fas fa-chevron-left"></i></asp:LinkButton></li>
                            <asp:Repeater ID="rptPaginador" runat="server" OnItemCommand="rptPaginador_ItemCommand">
                                <ItemTemplate><li class='<%# (bool)Eval("EsPaginaActual") ? "page-item active" : "page-item" %>'><asp:LinkButton ID="lnkPagina" runat="server" CssClass="page-link" CommandName="IrPagina" CommandArgument='<%# Eval("Pagina") %>'><%# Eval("Pagina") %></asp:LinkButton></li></ItemTemplate>
                            </asp:Repeater>
                            <li class="page-item"><asp:LinkButton ID="lnkSiguiente" runat="server" CssClass="page-link" OnClick="lnkPaginado_Click" CommandName="Siguiente"><i class="fas fa-chevron-right"></i></asp:LinkButton></li>
                        </ul>
                    </nav>
                </div>
            </div>

            <asp:Panel ID="pnlSinResultados" runat="server" Visible="false" CssClass="text-center py-5">
                <i class="fas fa-search mb-3 text-muted" style="font-size: 3rem; opacity: 0.3;"></i>
                <h5 class="text-secondary fw-bold">Gestión de Recetas</h5>
                <asp:Label ID="lblMensajeVacio" runat="server" CssClass="text-muted mb-0" Text="Utilice los filtros para buscar."></asp:Label>
            </asp:Panel>
        </ContentTemplate>
    </asp:UpdatePanel>

    <%-- MODAL 1: RECETA --%>
    <div class="modal fade" id="modalReceta" tabindex="-1" aria-hidden="true" data-bs-backdrop="static">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-light">
                    <h5 class="modal-title fw-bold" id="modalRecetaLabel">Información de Receta</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <asp:UpdatePanel ID="updModalReceta" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body p-4">
                            <asp:Literal ID="litModalError" runat="server"></asp:Literal>
                            <asp:HiddenField ID="hdRecetaID" runat="server" Value="0" />
                            
                            <div class="card bg-light border-0 mb-3"><div class="card-body pt-2 pb-3"><h6 class="text-primary fw-bold border-bottom pb-2 mb-3"><i class="fas fa-paw me-2"></i>Paciente y Cita</h6><div class="row g-3"><div class="col-md-4"><label class="form-label small fw-bold text-muted">Filtrar Citas (Fecha)</label><asp:TextBox ID="txtModalFiltroFechaCita" runat="server" CssClass="form-control" TextMode="Date" AutoPostBack="true" OnTextChanged="txtModalFiltroFechaCita_TextChanged"></asp:TextBox></div><div class="col-md-8"><label class="form-label small fw-bold text-muted">Seleccionar Cita *</label><asp:DropDownList ID="ddlModalCita" runat="server" CssClass="form-select"></asp:DropDownList><asp:RequiredFieldValidator ID="valCita" runat="server" ControlToValidate="ddlModalCita" InitialValue="0" ErrorMessage="Requerido." CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarReceta"></asp:RequiredFieldValidator></div></div></div></div>
                            <div class="row g-3">
                                <div class="col-md-6"><label class="form-label fw-bold">Fecha Emisión *</label><asp:TextBox ID="txtModalFechaEmision" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox><asp:RequiredFieldValidator ID="valFecEmi" runat="server" ControlToValidate="txtModalFechaEmision" ErrorMessage="Requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarReceta"></asp:RequiredFieldValidator></div>
                                <div class="col-md-6"><label class="form-label fw-bold">Vigencia Hasta</label><asp:TextBox ID="txtModalVigencia" runat="server" CssClass="form-control" TextMode="Date"></asp:TextBox></div>
                                <div class="col-12"><label class="form-label fw-bold">Diagnóstico *</label><asp:TextBox ID="txtModalDiagnostico" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="2" MaxLength="250"></asp:TextBox><asp:RequiredFieldValidator ID="valDiag" runat="server" ControlToValidate="txtModalDiagnostico" ErrorMessage="Requerido" CssClass="val-error" Display="Dynamic" ValidationGroup="GuardarReceta"></asp:RequiredFieldValidator></div>
                                <div class="col-12"><label class="form-label fw-bold">Observaciones</label><asp:TextBox ID="txtModalObservaciones" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="3" MaxLength="500"></asp:TextBox></div>
                                <div class="col-md-4"><label class="form-label fw-bold">Estado</label><asp:DropDownList ID="ddlModalEstado" runat="server" CssClass="form-select"><asp:ListItem Value="true" Text="Activo"></asp:ListItem><asp:ListItem Value="false" Text="Inactivo"></asp:ListItem></asp:DropDownList></div>
                                
                                <div class="col-md-8 text-end d-flex align-items-end justify-content-end">
                                    <%-- Botón oculto vía style para que JS lo pueda llamar --%>
                                    <asp:Panel ID="pnlBtnDetalles" runat="server" style="display:none;">
                                        <asp:LinkButton ID="btnGestionarDetalles" runat="server" CssClass="btn btn-outline-info" OnClick="btnGestionarDetalles_Click">
                                            <i class="fas fa-pills me-2"></i>Gestionar Medicamentos
                                        </asp:LinkButton>
                                    </asp:Panel>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer bg-light">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
                            <asp:Button ID="btnGuardarReceta" runat="server" Text="Guardar" CssClass="btn btn-primary px-4" OnClick="btnGuardarReceta_Click" ValidationGroup="GuardarReceta" OnClientClick="if(!Page_ClientValidate('GuardarReceta')){ applyBootstrapValidation(); return false; }" />
                        </div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

    <%-- MODAL 2: DETALLES --%>
    <div class="modal fade" id="modalDetalles" tabindex="-1" aria-hidden="true" data-bs-backdrop="static">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header bg-info text-white">
                    <h5 class="modal-title"><i class="fas fa-pills me-2"></i>Medicamentos Recetados</h5>
                    <button type="button" class="btn-close btn-close-white" onclick="cerrarDetallesYVolver()"></button>
                </div>
                <asp:UpdatePanel ID="updModalDetalles" runat="server" UpdateMode="Conditional">
                    <ContentTemplate>
                        <div class="modal-body">
                            <div id="divAgregarMedicamento" runat="server" class="card mb-3 border-0 bg-light">
                                <div class="card-body p-3">
                                    <h6 class="fw-bold text-secondary small mb-2">Agregar Nuevo Medicamento</h6>
                                    <div class="row g-2">
                                        <div class="col-md-3"><asp:TextBox ID="txtDetMedicina" runat="server" CssClass="form-control form-control-sm" placeholder="Medicamento"></asp:TextBox><asp:RequiredFieldValidator ID="valMed" runat="server" ControlToValidate="txtDetMedicina" ValidationGroup="AddDet" ErrorMessage="*" ForeColor="Red" Display="Dynamic"></asp:RequiredFieldValidator></div>
                                        <div class="col-md-2"><asp:TextBox ID="txtDetDosis" runat="server" CssClass="form-control form-control-sm" placeholder="Dosis (mg)"></asp:TextBox></div>
                                        <div class="col-md-2"><asp:TextBox ID="txtDetCantidad" runat="server" CssClass="form-control form-control-sm" placeholder="Cant."></asp:TextBox><asp:RequiredFieldValidator ID="valCant" runat="server" ControlToValidate="txtDetCantidad" ValidationGroup="AddDet" ErrorMessage="*" ForeColor="Red" Display="Dynamic"></asp:RequiredFieldValidator></div>
                                        <div class="col-md-3"><asp:TextBox ID="txtDetIndicacion" runat="server" CssClass="form-control form-control-sm" placeholder="Indicaciones"></asp:TextBox></div>
                                        <div class="col-md-2"><asp:LinkButton ID="btnAgregarDetalle" runat="server" CssClass="btn btn-sm btn-success w-100" ValidationGroup="AddDet" OnClick="btnAgregarDetalle_Click" OnClientClick="if(!Page_ClientValidate('AddDet')){ applyBootstrapValidation(); return false; }"><i class="fas fa-plus"></i> Agregar</asp:LinkButton></div>
                                    </div>
                                </div>
                            </div>
                            <div class="table-responsive">
                                <asp:GridView ID="gvDetalles" runat="server" CssClass="table table-hover table-bordered mb-0" AutoGenerateColumns="False" EmptyDataText="No hay medicamentos registrados." OnRowCommand="gvDetalles_RowCommand" GridLines="None">
                                    <Columns>
                                        <asp:BoundField DataField="descripcionMedicamento" HeaderText="Medicamento" />
                                        <asp:BoundField DataField="dosis" HeaderText="Dosis" />
                                        <asp:BoundField DataField="cantidad" HeaderText="Cant." ItemStyle-Width="80px" />
                                        <asp:BoundField DataField="indicacion" HeaderText="Indicación" />
                                        <asp:TemplateField HeaderText="Acción">
                                            <ItemTemplate><asp:LinkButton ID="btnEliminarDet" runat="server" CommandName="EliminarDetalle" CommandArgument='<%# Eval("detalleRecetaId") %>' CssClass="btn btn-sm btn-outline-danger border-0" OnClientClick="return confirm('¿Eliminar?');"><i class="fas fa-trash"></i></asp:LinkButton></ItemTemplate>
                                            <ItemStyle Width="50px" HorizontalAlign="Center" />
                                        </asp:TemplateField>
                                    </Columns>
                                    <HeaderStyle CssClass="table-light" />
                                </asp:GridView>
                            </div>
                        </div>
                        <div class="modal-footer pt-2 border-top-0"><button type="button" class="btn btn-secondary" onclick="cerrarDetallesYVolver()">Volver a Receta</button></div>
                    </ContentTemplate>
                </asp:UpdatePanel>
            </div>
        </div>
    </div>

    <%-- MODAL ELIMINAR --%>
    <div class="modal fade" id="modalConfirmarEliminar" tabindex="-1" aria-hidden="true"><div class="modal-dialog modal-dialog-centered"><div class="modal-content"><div class="modal-header border-0 pb-0"><h5 class="modal-title fw-bold text-danger">Confirmar Eliminación</h5><button type="button" class="btn-close" data-bs-dismiss="modal"></button></div><asp:UpdatePanel ID="updModalEliminar" runat="server" UpdateMode="Conditional"><ContentTemplate><div class="modal-body text-center py-4"><div class="mb-3 text-danger"><i class="fas fa-exclamation-triangle fa-3x"></i></div><asp:HiddenField ID="hdRecetaIDEliminar" runat="server" Value="0" /><p class="mb-1">¿Eliminar receta de <strong id="lblNombreRecetaEliminar"></strong>?</p></div><div class="modal-footer border-0 d-flex justify-content-center"><button type="button" class="btn btn-light px-4" data-bs-dismiss="modal">Cancelar</button><asp:Button ID="btnConfirmarEliminar" runat="server" Text="Sí, Eliminar" CssClass="btn btn-danger px-4" OnClick="btnConfirmarEliminar_Click" /></div></ContentTemplate></asp:UpdatePanel></div></div></div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script type="text/javascript">
        function abrirModalJS(btn, esSoloLectura) {
            var id = btn.getAttribute('data-id');
            var citaId = btn.getAttribute('data-citaid');
            var fechaEmi = btn.getAttribute('data-fechaemi');
            var vigencia = btn.getAttribute('data-vigencia');
            var diag = btn.getAttribute('data-diagnostico');
            var obs = btn.getAttribute('data-observaciones');
            var activo = btn.getAttribute('data-activo');

            $('#<%= hdRecetaID.ClientID %>').val(id);
            $('#<%= txtModalFechaEmision.ClientID %>').val(fechaEmi);
            $('#<%= txtModalVigencia.ClientID %>').val(vigencia);
            $('#<%= txtModalDiagnostico.ClientID %>').val(diag);
            $('#<%= txtModalObservaciones.ClientID %>').val(obs);
            $('#<%= ddlModalEstado.ClientID %>').val(activo);
            $('#<%= ddlModalCita.ClientID %>').val(citaId);

            configurarModoLectura(esSoloLectura);
            $('#modalRecetaLabel').text(esSoloLectura ? "Detalle de Receta" : "Modificar Receta");

            var pnl = document.getElementById('<%= pnlBtnDetalles.ClientID %>');
            if (pnl) pnl.style.display = (id != "0") ? 'block' : 'none';

            new bootstrap.Modal(document.getElementById('modalReceta')).show();
        }

        function configurarModoLectura(soloLectura) {
            $('#<%= txtModalFechaEmision.ClientID %>').prop('readonly', soloLectura);
            $('#<%= txtModalVigencia.ClientID %>').prop('readonly', soloLectura);
            $('#<%= txtModalDiagnostico.ClientID %>').prop('readonly', soloLectura);
            $('#<%= txtModalObservaciones.ClientID %>').prop('readonly', soloLectura);
            $('#<%= ddlModalEstado.ClientID %>').prop('disabled', soloLectura);
            $('#<%= txtModalFiltroFechaCita.ClientID %>').prop('disabled', true);
            $('#<%= ddlModalCita.ClientID %>').prop('disabled', true);
            var btn = document.getElementById('<%= btnGuardarReceta.ClientID %>');
            if (btn) btn.style.display = soloLectura ? 'none' : 'block';
        }

        function abrirModalEliminar(id, nombre) {
            $('#<%= hdRecetaIDEliminar.ClientID %>').val(id);
            $('#lblNombreRecetaEliminar').text(nombre);
            new bootstrap.Modal(document.getElementById('modalConfirmarEliminar')).show();
        }

        function abrirModalDetalles() { $('#modalReceta').modal('hide'); new bootstrap.Modal(document.getElementById('modalDetalles')).show(); }
        
        // Al cerrar detalles, volvemos a Receta.
        function cerrarDetallesYVolver() { 
            $('#modalDetalles').modal('hide'); 
            $('#modalReceta').modal('show'); 
        }

        function applyBootstrapValidation() { if (typeof (Page_Validators) != "undefined") { for (var i = 0; i < Page_Validators.length; i++) { var val = Page_Validators[i]; var ctrl = document.getElementById(val.controltovalidate); if (ctrl) { if (!val.isvalid) ctrl.classList.add("is-invalid"); else ctrl.classList.remove("is-invalid"); } } } }

        // NUEVO: SweetAlert con Opciones (Agregar Medicamento o Salir)
        function mostrarExitoConOpciones(mensaje, idReceta) {
            $('.modal').modal('hide');
            $('.modal-backdrop').remove();
            
            Swal.fire({
                icon: 'success',
                title: '¡Éxito!',
                text: mensaje + " ¿Desea asignar medicamentos ahora o continuar?",
                showDenyButton: true,
                showCancelButton: false,
                confirmButtonText: '💊 Agregar Medicamentos',
                denyButtonText: '🏠 Finalizar y Salir',
                confirmButtonColor: '#0097a7',
                denyButtonColor: '#6c757d'
            }).then((result) => {
                if (result.isConfirmed) {
                    // Reabrir el modal de Receta y disparar el click en "Gestionar Medicamentos"
                    // Nota: Como el servidor ya actualizó el UpdatePanel, el ID ya debería estar en el HiddenField y el botón visible
                    new bootstrap.Modal(document.getElementById('modalReceta')).show();
                    
                    // Simular click en el botón del servidor para cargar la grilla y cambiar de modal
                    var btnDetalles = document.getElementById('<%= btnGestionarDetalles.ClientID %>');
                    if (btnDetalles) btnDetalles.click();
                } else if (result.isDenied) {
                    // Recargar la página para limpiar todo
                    window.location.href = window.location.href;
                }
            });
        }

        // SweetAlert simple que redirige al cerrar (Para eliminar)
        function mostrarExitoRedirect(mensaje) {
            $('.modal').modal('hide');
            $('.modal-backdrop').remove();
            Swal.fire({
                icon: 'success',
                title: 'Éxito',
                text: mensaje,
                confirmButtonColor: '#0097a7'
            }).then((result) => {
                window.location.href = window.location.href;
            });
        }

        $(document).ready(function () {
            var prm = Sys.WebForms.PageRequestManager.getInstance();
            prm.add_endRequest(function (sender, e) {
                var elem = sender._postBackSettings.sourceElement;
                if (elem) {
                    // OJO: Ya no cerramos automáticamente al guardar receta, 
                    // porque ahora mostramos el SweetAlert con opciones.
                    // El cierre lo maneja la función JS 'mostrarExitoConOpciones'.

                    if (elem.id.includes("btnConfirmarEliminar")) {
                        $('#modalConfirmarEliminar').modal('hide');
                        $('.modal-backdrop').remove();
                    }
                }
            });
        });
    </script>
</asp:Content>