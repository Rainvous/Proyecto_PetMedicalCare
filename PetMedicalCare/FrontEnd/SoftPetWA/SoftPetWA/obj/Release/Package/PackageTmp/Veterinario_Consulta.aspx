<%@ Page Title="Consulta Médica" Language="C#" MasterPageFile="~/Veterinario.Master" AutoEventWireup="true" CodeBehind="Veterinario_Consulta.aspx.cs" Inherits="SoftPetWA.Veterinario_Consulta" %>

<asp:Content ID="ContentTitulo" ContentPlaceHolderID="cphTitulo" runat="server">
    Consulta Médica
</asp:Content>

<asp:Content ID="ContentHead" ContentPlaceHolderID="cphHead" runat="server">
    <%-- ESTILOS CSS COMPLETOS E INCRUSTADOS --%>
    <link href="Content/GestionVeterinarios.css" rel="stylesheet" /> 
    <style>
        :root {
            --primary-color: #00bcd4;
            --primary-dark: #008ba3;
            --soft-bg: #f8f9fc;
            --text-dark: #363b4d;
            --text-muted: #858796;
        }

        /* --- Sidebar Paciente --- */
        .patient-card {
            border: none; border-radius: 15px; background: white;
            box-shadow: 0 5px 20px rgba(0,0,0,0.05); overflow: hidden; text-align: center;
            height: auto; /* Ajuste automático de altura */
        }
        .patient-header-bg {
            background: linear-gradient(135deg, #00bcd4 0%, #4dd0e1 100%);
            height: 100px; margin-bottom: -50px;
        }
        .patient-avatar-container {
            width: 100px; height: 100px; margin: 0 auto 15px;
            background-color: white; border-radius: 50%;
            display: flex; align-items: center; justify-content: center;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1); border: 4px solid white;
            font-size: 2.5rem; color: var(--text-muted); position: relative;
        }
        .patient-avatar-container.dog { color: #4e73df; }
        .patient-avatar-container.cat { color: #f6c23e; }

        .patient-info-list { padding: 20px 25px; text-align: left; }
        .patient-info-item {
            display: flex; align-items: center; margin-bottom: 15px;
            padding-bottom: 15px; border-bottom: 1px solid #f1f3f9;
        }
        .patient-info-item:last-child { border-bottom: none; margin-bottom: 0; padding-bottom: 0; }
        
        .patient-info-icon {
            width: 35px; height: 35px; background-color: #e0f7fa; color: var(--primary-dark);
            border-radius: 8px; display: flex; align-items: center; justify-content: center;
            margin-right: 15px; font-size: 0.9rem;
        }
        .patient-label { font-size: 0.75rem; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.5px; font-weight: 700; display: block; }
        .patient-value { font-size: 0.95rem; color: var(--text-dark); font-weight: 600; }

        /* --- Tarjetas de Sección (Formulario) --- */
        .section-card {
            border: none; border-radius: 12px; box-shadow: 0 3px 15px rgba(0,0,0,0.03);
            margin-bottom: 25px; transition: transform 0.2s;
        }
        .section-header {
            background-color: white; border-bottom: 1px solid #f1f3f9;
            padding: 15px 20px; border-radius: 12px 12px 0 0; display: flex; align-items: center;
        }
        .section-header i { color: var(--primary-color); margin-right: 10px; font-size: 1.1rem; }
        .section-header h5 { margin: 0; font-size: 1rem; font-weight: 700; color: var(--text-dark); }
        
        .form-label { font-weight: 600; font-size: 0.85rem; color: #5a5c69; margin-bottom: 8px; }
        .form-control { border-radius: 8px; border: 1px solid #d1d3e2; padding: 10px 15px; font-size: 0.9rem; }

        /* Header Cita con Fecha */
        .cita-info-header {
            background-color: #e3f2fd; color: #1565c0; padding: 15px;
            border-radius: 8px; margin-bottom: 20px; display: flex;
            justify-content: space-between; align-items: center; border-left: 5px solid #1565c0;
        }

        /* --- Botones de Acción --- */
        .action-bar-container {
            background-color: #fff; padding: 20px 30px; border-radius: 15px;
            box-shadow: 0 5px 25px rgba(0,0,0,0.04); display: flex;
            justify-content: space-between; align-items: center; margin-top: 40px; margin-bottom: 40px;
        }
        
        /* Cancelar */
        .btn-cancel-modern {
            background-color: white; color: #858796; border: 2px solid #e3e6f0;
            border-radius: 10px; padding: 10px 25px; font-weight: 600; transition: all 0.3s ease;
        }
        .btn-cancel-modern:hover {
            background-color: #fff; color: #e74a3b; border-color: #e74a3b;
            box-shadow: 0 5px 15px rgba(231, 74, 59, 0.15); transform: translateY(-2px);
        }

        /* Finalizar */
        .btn-finalize-modern {
            background: linear-gradient(135deg, #2e59d9 0%, #224abe 100%); color: white;
            border: none; border-radius: 10px; padding: 12px 35px; font-weight: 700; letter-spacing: 0.5px;
            box-shadow: 0 4px 15px rgba(46, 89, 217, 0.4); transition: all 0.3s ease; text-decoration: none;
        }
        .btn-finalize-modern:hover {
            background: linear-gradient(135deg, #224abe 0%, #1a3a9c 100%);
            color: white; box-shadow: 0 6px 20px rgba(46, 89, 217, 0.6); transform: translateY(-2px) scale(1.02);
        }

        /* Eliminar Servicio */
        .btn-delete-service { color: #e74a3b; cursor: pointer; transition: color 0.2s; }
        .btn-delete-service:hover { color: #c0392b; transform: scale(1.2); }

        /* Modales */
        .servicio-modal-item { border: 1px solid #eee; border-radius: 8px; padding: 10px; margin-bottom: 10px; cursor: pointer; }
        .servicio-modal-item:hover { background-color: #f8f9fa; }
        .servicio-modal-item.selected { background-color: #e0f7fa; border-color: #00bcd4; }
        
        .modal-success-icon { font-size: 4rem; color: #28a745; margin-bottom: 15px; }
    </style>
</asp:Content>

<asp:Content ID="ContentMain" ContentPlaceHolderID="cphContenido" runat="server">
    
    <asp:HiddenField ID="hfMontoTotal" runat="server" Value="0.00" />

    <%-- Encabezado --%>
    <div class="d-flex align-items-center justify-content-between mb-4">
        <div class="d-flex align-items-center">
             <asp:LinkButton ID="btnVolverAgenda" runat="server" CssClass="btn btn-light btn-circle me-3 shadow-sm" PostBackUrl="~/Veterinario_Agenda.aspx" ToolTip="Volver">
                 <i class="fas fa-arrow-left text-gray-600"></i>
             </asp:LinkButton>
             <div>
                 <h1 class="h3 mb-0 text-gray-800 fw-bold">Registro Clínico</h1>
                 <span class="text-muted small">Complete la información de la consulta médica</span>
             </div>
        </div>
        <div class="d-none d-sm-block text-end">
            <span class="badge bg-primary fs-6 px-3 py-2 rounded-pill shadow-sm mb-1">
                <i class="fas fa-user-md me-1"></i> <asp:Literal ID="litVetResumen" runat="server" Text="Dr. Veterinario"></asp:Literal>
            </span>
        </div>
    </div>

    <div class="row">
        <%-- PERFIL DEL PACIENTE (IZQUIERDA) --%>
        <div class="col-lg-4 mb-4">
            <div class="patient-card">
                <div class="patient-header-bg"></div>
                <div id="pacienteAvatarLgDiv" class="patient-avatar-container" runat="server"> 
                     <asp:Literal ID="litAvatarIconConsulta" runat="server"><i class="fas fa-paw"></i></asp:Literal> 
                </div>
                <div class="px-3 pb-3">
                    <h3 class="fw-bold text-dark mb-1"><asp:Literal ID="litNombrePacienteConsulta" runat="server" Text="Paciente"></asp:Literal></h3>
                    <p class="text-primary mb-3"><asp:Literal ID="litRazaPacienteConsulta" runat="server" Text="Especie • Raza"></asp:Literal></p>
                </div>
                <div class="patient-info-list">
                    <div class="patient-info-item">
                        <div class="patient-info-icon"><i class="fas fa-user"></i></div>
                        <div><span class="patient-label">Propietario</span><div class="patient-value"><asp:Literal ID="litPropietarioConsulta" runat="server" Text="-"></asp:Literal></div></div>
                    </div>
                    <div class="patient-info-item">
                        <div class="patient-info-icon"><i class="fas fa-phone-alt"></i></div>
                        <div><span class="patient-label">Contacto</span><div class="patient-value"><asp:Literal ID="litContactoConsulta" runat="server" Text="-"></asp:Literal></div></div>
                    </div>
                    <div class="patient-info-item">
                        <div class="patient-info-icon"><i class="fas fa-venus-mars"></i></div>
                        <div><span class="patient-label">Sexo</span><div class="patient-value"><asp:Literal ID="litSexoConsulta" runat="server" Text="-"></asp:Literal></div></div>
                    </div>
                </div>
            </div>
        </div>

        <%-- FORMULARIO MÉDICO (DERECHA) --%>
        <div class="col-lg-8">
             
             <%-- Header Cita --%>
             <div class="cita-info-header shadow-sm">
                 <div>
                     <i class="fas fa-calendar-alt me-2"></i>
                     <span class="fw-bold">Fecha de Cita:</span> 
                     <asp:Literal ID="litFechaHoraCitaConsulta" runat="server" Text="Cargando fecha..."></asp:Literal>
                 </div>
                 <div class="badge bg-white text-primary border px-3 py-2">
                     <asp:Literal ID="litTipoCitaConsulta" runat="server" Text="Consulta General"></asp:Literal>
                 </div>
             </div>

             <%-- Anamnesis --%>
             <div class="section-card bg-white">
                 <div class="section-header"><i class="fas fa-notes-medical"></i><h5>Anamnesis</h5></div>
                 <div class="p-4">
                     <div class="form-group">
                         <label class="form-label">Observaciones Clínicas</label>
                         <asp:TextBox ID="txtObservacionesClinicas" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="3" placeholder="Hallazgos durante la evaluación física..."></asp:TextBox>
                     </div>
                 </div>
             </div>

              <%-- Diagnóstico --%>
             <div class="section-card bg-white">
                 <div class="section-header"><i class="fas fa-stethoscope"></i><h5>Diagnóstico</h5></div>
                 <div class="p-4">
                     <div class="form-group">
                         <label class="form-label">Diagnóstico Presuntivo / Definitivo</label>
                         <asp:TextBox ID="txtDiagnosticoClinico" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="2" placeholder="Escriba el diagnóstico..."></asp:TextBox>
                     </div>
                 </div>
             </div>

             <%-- Tratamiento y Servicios --%>
             <asp:UpdatePanel ID="updServicios" runat="server" UpdateMode="Conditional">
                <ContentTemplate>
                     <div class="section-card bg-white">
                         <div class="section-header"><i class="fas fa-syringe"></i><h5>Tratamiento y Servicios</h5></div>
                         <div class="p-4">
                            <div class="form-group mb-4">
                                <label class="form-label">Plan de Tratamiento</label>
                                <asp:TextBox ID="txtPlanTratamiento" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="2" placeholder="Describa el procedimiento o plan a seguir..."></asp:TextBox>
                            </div>
                            
                            <div class="d-flex justify-content-between align-items-center mb-3 border-bottom pb-2">
                                <label class="form-label mb-0 text-primary fw-bold">Servicios Aplicados</label>
                                <asp:LinkButton ID="btnGestionarServicios" runat="server" CssClass="btn btn-sm btn-outline-info rounded-pill px-3" OnClick="btnGestionarServicios_Click">
                                     <i class="fas fa-plus me-1"></i> Insertar Servicio
                                </asp:LinkButton>
                            </div>

                             <%-- GRID DE SERVICIOS --%>
                             <asp:GridView ID="gvServiciosSeleccionados" runat="server" AutoGenerateColumns="False" 
                                 CssClass="table table-sm align-middle" GridLines="Horizontal" ShowHeader="false" BorderStyle="None"
                                 OnRowCommand="gvServiciosSeleccionados_RowCommand">
                                <Columns>
                                    <asp:TemplateField>
                                        <ItemTemplate>
                                            <i class="fas fa-check-circle text-success me-2"></i> <%# Eval("Nombre") %>
                                            
                                            <%-- Etiqueta de Guardado (Visible si NO es nuevo) --%>
                                            <asp:Panel ID="pnlGuardado" runat="server" Visible='<%# !(bool)Eval("EsNuevo") %>' style="display:inline;">
                                                <span class="badge bg-light text-muted ms-2" style="font-size:0.65rem;">Guardado</span>
                                            </asp:Panel>
                                        </ItemTemplate>
                                    </asp:TemplateField>
                                    <asp:BoundField DataField="PrecioFormateado" ItemStyle-CssClass="text-end fw-bold text-dark" />
                                    
                                    <%-- BOTÓN ELIMINAR: Solo visible si EsNuevo es TRUE --%>
                                    <asp:TemplateField ItemStyle-Width="40px" ItemStyle-HorizontalAlign="Center">
                                        <ItemTemplate>
                                            <asp:LinkButton ID="btnEliminarServicio" runat="server" CssClass="btn-delete-service"
                                                CommandName="EliminarServicio" 
                                                CommandArgument='<%# Eval("ServicioID") %>' 
                                                ToolTip="Quitar servicio"
                                                Visible='<%# Eval("EsNuevo") %>'>
                                                <i class="fas fa-trash-alt"></i>
                                            </asp:LinkButton>
                                        </ItemTemplate>
                                    </asp:TemplateField>
                                </Columns>
                                <EmptyDataTemplate>
                                     <div class="text-center py-3 bg-light rounded text-muted small">
                                         <i class="fas fa-info-circle me-1"></i> No se han añadido servicios.
                                     </div>
                                </EmptyDataTemplate>
                            </asp:GridView>
                             
                             <div class="d-flex justify-content-end mt-2">
                                 <div class="bg-light px-3 py-2 rounded">
                                     <small class="text-uppercase text-muted fw-bold me-2">Total Servicios:</small>
                                     <span class="fw-bold text-primary fs-5" id="spanMontoTotal">S/ 0.00</span>
                                 </div>
                             </div>

                        </div>
                    </div>
                </ContentTemplate>
            </asp:UpdatePanel>

             <%-- Receta Médica --%>
            <asp:UpdatePanel ID="updReceta" runat="server">
                 <ContentTemplate>
                     <div class="section-card bg-white">
                         <div class="section-header"><i class="fas fa-prescription-bottle-alt"></i><h5>Receta Médica</h5></div>
                         <div class="p-4">
                             <div class="bg-soft-primary p-3 rounded mb-3" style="background-color: #f0f8ff;">
                                 <div class="row g-2">
                                     <div class="col-md-5"><asp:TextBox ID="txtRecDesc" runat="server" CssClass="form-control form-control-sm" placeholder="Medicamento"></asp:TextBox></div>
                                     <div class="col-md-3"><asp:TextBox ID="txtRecDosis" runat="server" CssClass="form-control form-control-sm" placeholder="Dosis"></asp:TextBox></div>
                                     <div class="col-md-2"><asp:TextBox ID="txtRecFrec" runat="server" CssClass="form-control form-control-sm" placeholder="Frecuencia"></asp:TextBox></div>
                                     <div class="col-md-2"><asp:TextBox ID="txtRecDuracion" runat="server" CssClass="form-control form-control-sm" placeholder="Duración"></asp:TextBox></div>
                                     <div class="col-md-8"><asp:TextBox ID="txtRecIndicacion" runat="server" CssClass="form-control form-control-sm" placeholder="Indicación adicional"></asp:TextBox></div>
                                     <div class="col-md-2"><asp:TextBox ID="txtRecCant" runat="server" CssClass="form-control form-control-sm" placeholder="Cant." TextMode="Number"></asp:TextBox></div>
                                     <div class="col-md-2"><asp:Button ID="btnAgregarMedicamento" runat="server" Text="+" CssClass="btn btn-add-med btn-sm w-100 fw-bold" OnClick="btnAgregarMedicamento_Click" /></div>
                                 </div>
                            </div>
                             <div class="table-responsive mb-3">
                                 <asp:GridView ID="gvReceta" runat="server" AutoGenerateColumns="False" CssClass="table table-bordered table-hover tabla-medicamentos mb-0" OnRowDeleting="gvReceta_RowDeleting" GridLines="None">
                                      <Columns>
                                         <asp:BoundField DataField="Descripcion" HeaderText="Medicamento" />
                                         <asp:BoundField DataField="Dosis" HeaderText="Dosis" />
                                         <asp:BoundField DataField="Frecuencia" HeaderText="Frecuencia" />
                                         <asp:BoundField DataField="Duracion" HeaderText="Días" />
                                         <asp:BoundField DataField="Cantidad" HeaderText="#" />
                                         <asp:CommandField ShowDeleteButton="True" ButtonType="Link" DeleteText="<i class='fas fa-times text-danger'></i>" HeaderText="" ItemStyle-HorizontalAlign="Center" />
                                     </Columns>
                                     <EmptyDataTemplate><div class="text-center text-muted py-2 small">Sin medicamentos recetados.</div></EmptyDataTemplate>
                                 </asp:GridView>
                             </div>
                             <div class="form-group mb-0">
                                 <label class="form-label small text-uppercase text-muted">Indicaciones Generales</label>
                                 <asp:TextBox ID="txtIndicacionesGenerales" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="2"></asp:TextBox>
                             </div>
                        </div>
                    </div>
                </ContentTemplate>
             </asp:UpdatePanel>

            <%-- BOTONES DE ACCIÓN --%>
            <asp:UpdatePanel ID="updBotones" runat="server" UpdateMode="Conditional">
                <ContentTemplate>
                     <div class="action-bar-container">
                         <%-- Botón Cancelar --%>
                         <asp:Button ID="btnCancelarConsulta" runat="server" Text="Cancelar" CssClass="btn-cancel-modern" OnClick="btnCancelarConsulta_Click" />
                         
                         <div>
                              <%-- BOTÓN DE GUARDAR ELIMINADO, SOLO QUEDA FINALIZAR --%>
                              <asp:LinkButton ID="btnCompletarConsulta" runat="server" CssClass="btn-finalize-modern" OnClick="btnCompletarConsulta_Click">
                                  <i class='fas fa-check-circle me-2'></i> Finalizar Consulta
                              </asp:LinkButton>
                         </div>
                     </div>
                    <asp:Label ID="lblError" runat="server" CssClass="alert alert-danger d-block text-center fw-bold" Visible="false"></asp:Label>
                </ContentTemplate>
            </asp:UpdatePanel>
        </div> 
    </div> 

    <%-- MODAL SERVICIOS --%>
    <div class="modal fade" id="modalServicios" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content border-0 shadow-lg">
                <div class="modal-header bg-light border-0">
                    <h5 class="modal-title fw-bold text-dark">Agregar Servicios</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body p-4">
                    <asp:UpdatePanel ID="updModalServicios" runat="server" UpdateMode="Conditional">
                        <ContentTemplate>
                            <div class="input-group mb-3 shadow-sm">
                                <span class="input-group-text bg-white border-end-0 ps-3"><i class="fas fa-search text-muted"></i></span>
                                <asp:TextBox ID="txtBuscarServicio" runat="server" CssClass="form-control border-start-0 py-2" placeholder="Buscar servicio..." AutoPostBack="true" OnTextChanged="txtBuscarServicio_TextChanged"></asp:TextBox>
                            </div>
                            <asp:Panel ID="pnlMensajeInicial" runat="server" Visible="false" CssClass="text-center py-5">
                                <i class="fas fa-search fa-3x mb-3 text-light-gray" style="color:#e0e0e0;"></i>
                                <p class="text-muted">Escribe para buscar procedimientos...</p>
                            </asp:Panel>
                            <div class="servicios-list-modal" style="max-height: 350px; overflow-y: auto;">
                                <asp:Repeater ID="rptServiciosModal" runat="server">
                                    <ItemTemplate>
                                        <div class="servicio-modal-item d-flex align-items-center justify-content-between p-3">
                                            <div class="form-check flex-grow-1">
                                                <asp:HiddenField ID="hdServicioID" runat="server" Value='<%# Eval("ServicioID") %>' />
                                                <asp:CheckBox ID="chkServicio" runat="server" CssClass="form-check-input" 
                                                    data-precio='<%# Eval("Precio") %>' 
                                                    Checked='<%# Eval("Seleccionado") %>' />
                                                <label class="form-check-label ms-3 cursor-pointer" for="<%# ((CheckBox)Container.FindControl("chkServicio")).ClientID %>">
                                                    <strong class="d-block text-dark"><%# Eval("Nombre") %></strong>
                                                    <span class="text-muted small"><%# Eval("Descripcion") %></span>
                                                </label>
                                             </div>
                                            <span class="badge bg-soft-primary text-primary fs-6"><%# Eval("PrecioFormateado") %></span>
                                         </div>
                                    </ItemTemplate>
                                </asp:Repeater>
                             </div>
                        </ContentTemplate>
                    </asp:UpdatePanel>
                </div>
                <div class="modal-footer border-0 bg-light">
                     <button type="button" class="btn btn-link text-muted text-decoration-none" data-bs-dismiss="modal">Cancelar</button>
                    <asp:Button ID="btnGuardarServiciosModal" runat="server" Text="Confirmar Selección" CssClass="btn btn-primary px-4 rounded-pill" OnClick="btnGuardarServiciosModal_Click" />
                </div>
            </div>
        </div>
    </div>
    
    <%-- Modal Exito --%>
    <div class="modal fade" id="modalExito" data-bs-backdrop="static" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content text-center p-5 border-0 shadow-lg" style="border-radius: 20px;">
                <div class="modal-body">
                    <div class="modal-success-icon mb-4"><i class="fas fa-check-circle animated bounceIn"></i></div>
                    <h3 class="fw-bold mb-2">¡Consulta Registrada!</h3>
                     <p class="text-muted mb-4">El historial clínico ha sido actualizado correctamente.</p>
                    <a href="Veterinario_Agenda.aspx" class="btn btn-success w-100 py-3 rounded-pill fw-bold shadow">Volver a la Agenda</a>
                </div>
            </div>
        </div>
    </div>
</asp:Content>

<asp:Content ID="ContentScripts" ContentPlaceHolderID="cphScripts" runat="server">
   <script type="text/javascript">
       $(document).ready(function () {
           // Lógica visual para selección en modal
           $(document).on('click', '.servicio-modal-item', function (e) {
               if (e.target.type !== 'checkbox' && e.target.tagName !== 'LABEL') {
                   var $chk = $(this).find('input[type="checkbox"]');
                   $chk.prop('checked', !$chk.prop('checked'));
               }
               updateSelectedStyles();
           });
           function updateSelectedStyles() {
               $('.servicio-modal-item').each(function () {
                   if ($(this).find('input[type="checkbox"]').is(':checked')) {
                       $(this).addClass('selected shadow-sm');
                   } else {
                       $(this).removeClass('selected shadow-sm');
                   }
               });
           }
       });
   </script>
</asp:Content>