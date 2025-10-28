<%@ Page Title="Consulta Médica" Language="C#" MasterPageFile="~/Veterinario.Master" AutoEventWireup="true" CodeBehind="Veterinario_Consulta.aspx.cs" Inherits="SoftPetWA.Veterinario_Consulta" %>

<asp:Content ID="ContentTitulo" ContentPlaceHolderID="cphTitulo" runat="server">
    Consulta Médica
</asp:Content>

<asp:Content ID="ContentHead" ContentPlaceHolderID="cphHead" runat="server">
    <%-- Enlaza los estilos específicos de las vistas --%>
    <link href="Content/GestionVeterinarios.css" rel="stylesheet" /> 
</asp:Content>

<asp:Content ID="ContentMain" ContentPlaceHolderID="cphContenido" runat="server">
    <%-- Cabecera con Botón Volver (simplificada, título podría ir en Master) --%>
    <div class="d-flex align-items-center mb-4">
         <asp:LinkButton ID="btnVolverAgenda" runat="server" CssClass="btn btn-outline-secondary me-3" PostBackUrl="~/Veterinario_Agenda.aspx" ToolTip="Volver a la Agenda">
             <i class="fas fa-arrow-left"></i>
         </asp:LinkButton>
         <h1 class="h3 mb-0 text-gray-800">Registro de Consulta Médica</h1>
         <%-- Los iconos de acción ya están en la navbar del Master --%>
    </div>

    <%-- Contenido Principal (Row con 2 columnas) --%>
    <div class="row">
        <%-- Columna Izquierda: Info Paciente --%>
        <div class="col-lg-4 mb-4 mb-lg-0"> <%-- Margen inferior en móvil --%>
            <div class="paciente-sidebar card shadow"> <%-- Added card shadow --%>
                <div class="card-body text-center"> <%-- Added card-body --%>
                    <div id="pacienteAvatarLgDiv" class="paciente-avatar-lg avatar-lg-gray mb-3" runat="server"> 
                         <asp:Literal ID="litAvatarIconConsulta" runat="server"><i class="fas fa-question-circle"></i></asp:Literal> 
                    </div>
                    <h3 class="card-title"><asp:Literal ID="litNombrePacienteConsulta" runat="server" Text="No Seleccionado"></asp:Literal></h3>
                    <p class="card-text text-muted mb-4"><asp:Literal ID="litRazaPacienteConsulta" runat="server" Text="-"></asp:Literal></p>
                    
                    <%-- Cajas de Info (Quitamos fondo gris, usamos texto directo) --%>
                    <div class="info-box border rounded p-3 mb-2 text-start"> <span class="info-label d-block text-uppercase small"><i class="fas fa-user me-2"></i> Propietario</span><span class="info-value"><asp:Literal ID="litPropietarioConsulta" runat="server" Text="-"></asp:Literal></span></div>
                    <div class="info-box border rounded p-3 mb-2 text-start"> <span class="info-label d-block text-uppercase small"><i class="fas fa-birthday-cake me-2"></i> Edad</span><span class="info-value"><asp:Literal ID="litEdadConsulta" runat="server" Text="-"></asp:Literal></span></div>
                    <div class="info-box border rounded p-3 mb-2 text-start"> <span class="info-label d-block text-uppercase small"><i class="fas fa-venus-mars me-2"></i> Sexo</span><span class="info-value"><asp:Literal ID="litSexoConsulta" runat="server" Text="-"></asp:Literal></span></div>
                    <div class="info-box border rounded p-3 mb-2 text-start"> <span class="info-label d-block text-uppercase small"><i class="fas fa-weight me-2"></i> Peso Anterior</span><span class="info-value"><asp:Literal ID="litPesoAntConsulta" runat="server" Text="-"></asp:Literal></span></div>
                    <div class="info-box border rounded p-3 mb-3 text-start"> <span class="info-label d-block text-uppercase small"><i class="fas fa-phone-alt me-2"></i> Contacto</span><span class="info-value"><asp:Literal ID="litContactoConsulta" runat="server" Text="-"></asp:Literal></span></div>

                    <asp:Button ID="btnVerHistorialCompleto" runat="server" Text="Ver Historial Completo" CssClass="btn btn-outline-primary w-100" OnClick="btnVerHistorialCompleto_Click" />
                </div>
            </div>
        </div>

        <%-- Columna Derecha: Formulario Consulta --%>
        <div class="col-lg-8">
             <%-- Sección 1: Info Cita --%>
             <div class="card shadow consulta-section"> 
                 <div class="card-header consulta-section-header"><i class="fas fa-info-circle"></i><h5>Información de la Cita</h5></div>
                 <div class="card-body consulta-section-body">
                     <div class="alert alert-primary info-cita-bar"><i class="fas fa-calendar-check me-1"></i> Cita programada: <strong><asp:Literal ID="litFechaHoraCitaConsulta" runat="server" Text="-"></asp:Literal></strong> - <asp:Literal ID="litTipoCitaConsulta" runat="server" Text="-"></asp:Literal></div>
                     <div class="row vitals-grid g-3"> <%-- Added g-3 for spacing --%>
                         <div class="col-md-4"><div class="form-group"><label class="form-label">Peso Actual (kg)</label><asp:TextBox ID="txtPesoActual" runat="server" CssClass="form-control" placeholder="Ej: 29.5"></asp:TextBox></div></div>
                         <div class="col-md-4"><div class="form-group"><label class="form-label">Temperatura (°C)</label><asp:TextBox ID="txtTemperatura" runat="server" CssClass="form-control" placeholder="Ej: 38.5"></asp:TextBox></div></div>
                         <div class="col-md-4"><div class="form-group"><label class="form-label">Frecuencia Cardíaca</label><asp:TextBox ID="txtFrecuenciaCardiaca" runat="server" CssClass="form-control" placeholder="lat/min"></asp:TextBox></div></div>
                     </div>
                 </div>
             </div>
             <%-- Sección 2: Motivo --%>
             <div class="card shadow consulta-section">
                 <div class="card-header consulta-section-header"><i class="fas fa-question-circle"></i><h5>Motivo de Consulta</h5></div>
                 <div class="card-body consulta-section-body">
                     <div class="form-group mb-3"><label class="form-label">Motivo de Consulta</label><asp:TextBox ID="txtMotivoConsulta" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="3" placeholder="Describa el motivo principal de la consulta..."></asp:TextBox></div>
                     <div class="form-group"><label class="form-label">Observaciones Clínicas</label><asp:TextBox ID="txtObservacionesClinicas" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="4" placeholder="Describa los hallazgos del examen físico, síntomas observados, etc..."></asp:TextBox></div>
                 </div>
             </div>
              <%-- Sección 3: Diagnóstico --%>
             <div class="card shadow consulta-section">
                 <div class="card-header consulta-section-header"><i class="fas fa-diagnoses"></i><h5>Diagnóstico</h5></div>
                 <div class="card-body consulta-section-body">
                     <div class="form-group"><label class="form-label">Diagnóstico Clínico</label><asp:TextBox ID="txtDiagnosticoClinico" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="4" placeholder="Ingrese el diagnóstico basado en el examen clínico y observaciones..."></asp:TextBox></div>
                     <div class="alert alert-warning mt-3"><i class="fas fa-exclamation-triangle me-1"></i> El diagnóstico será parte del historial médico permanente de la mascota</div>
                 </div>
             </div>
             <%-- Sección 4: Tratamiento y Servicios --%>
             <div class="card shadow consulta-section">
                 <div class="card-header consulta-section-header"><i class="fas fa-pills"></i><h5>Tratamiento y Servicios Aplicados</h5></div>
                 <div class="card-body consulta-section-body">
                    <div class="form-group mb-4"><label class="form-label">Plan de Tratamiento</label><asp:TextBox ID="txtPlanTratamiento" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="3" placeholder="Describa el tratamiento recomendado, medicamentos, cuidados especiales..."></asp:TextBox></div>
                    <label class="form-label">Servicios Aplicados en esta Consulta</label>
                    <div class="servicios-checkbox-list">
                        <div class="row g-3"> <%-- Added g-3 --%>
                            <div class="col-md-6">
                                <div class="servicio-item card card-body"><asp:CheckBox ID="chkConsultaGeneral" runat="server" CssClass="form-check-input"/><label class="form-check-label" for="<%= chkConsultaGeneral.ClientID %>"> Consulta General<span class="servicio-precio">S/ 80.00</span></label></div>
                                <div class="servicio-item card card-body"><asp:CheckBox ID="chkDesparasitacion" runat="server" CssClass="form-check-input"/><label class="form-check-label" for="<%= chkDesparasitacion.ClientID %>">Desparasitación<span class="servicio-precio">S/ 35.00</span></label></div>
                                <div class="servicio-item card card-body"><asp:CheckBox ID="chkRadiografia" runat="server" CssClass="form-check-input"/><label class="form-check-label" for="<%= chkRadiografia.ClientID %>">Radiografía<span class="servicio-precio">S/ 120.00</span></label></div>
                            </div>
                            <div class="col-md-6">
                                <div class="servicio-item card card-body"><asp:CheckBox ID="chkVacunacion" runat="server" CssClass="form-check-input"/><label class="form-check-label" for="<%= chkVacunacion.ClientID %>">Vacunación<span class="servicio-precio">S/ 50.00</span></label></div>
                                <div class="servicio-item card card-body"><asp:CheckBox ID="chkAnalisisSangre" runat="server" CssClass="form-check-input"/><label class="form-check-label" for="<%= chkAnalisisSangre.ClientID %>">Análisis de Sangre<span class="servicio-precio">S/ 180.00</span></label></div>
                                 <div class="servicio-item card card-body"><asp:CheckBox ID="chkLimpiezaDental" runat="server" CssClass="form-check-input"/><label class="form-check-label" for="<%= chkLimpiezaDental.ClientID %>">Limpieza Dental<span class="servicio-precio">S/ 250.00</span></label></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
             <%-- Sección 5: Receta Médica --%>
              <div class="card shadow consulta-section">
                 <div class="card-header consulta-section-header"><i class="fas fa-file-medical"></i><h5>Receta Médica</h5></div>
                 <div class="card-body consulta-section-body">
                     <div class="alert alert-info"><i class="fas fa-info-circle me-1"></i> Si requiere prescribir medicamentos, puede generar la receta médica firmada digitalmente</div>
                     <div class="form-group my-3"><label class="form-label">Indicaciones Generales para el Propietario</label><asp:TextBox ID="txtIndicacionesGenerales" runat="server" CssClass="form-control" TextMode="MultiLine" Rows="3" placeholder="Recomendaciones de cuidado, alimentación, reposo, próxima cita, etc..."></asp:TextBox></div>
                    <asp:LinkButton ID="btnGenerarReceta" runat="server" CssClass="btn btn-generar-receta w-100" OnClick="btnGenerarReceta_Click"><i class='fas fa-signature'></i> Generar Receta Médica</asp:LinkButton>
                </div>
            </div>
             <%-- Sección 6: Resumen --%>
             <div class="card shadow consulta-section">
                 <div class="card-header consulta-section-header"><i class="fas fa-clipboard-check"></i><h5>Resumen de la Consulta</h5></div>
                 <div class="card-body consulta-section-body">
                    <div class="alert alert-success resumen-consulta-bar"><i class="fas fa-spinner fa-spin me-1"></i> Consulta en progreso - Veterinario: <strong><asp:Literal ID="litVetResumen" runat="server" Text="Dr. Rony"></asp:Literal></strong> • Fecha: <asp:Literal ID="litFechaResumen" runat="server" Text="28/10/2025 11:01 AM"></asp:Literal></div>
                     <div class="monto-servicios-bar d-flex justify-content-between align-items-center p-3 border rounded"><span><i class="fas fa-dollar-sign me-1"></i> MONTO DE SERVICIOS</span><span class="monto-valor fw-bold"><asp:Literal ID="litMontoServicios" runat="server" Text="S/ 0.00"></asp:Literal></span></div>
                </div>
            </div>
            <%-- Botones Finales --%>
            <div class="consulta-actions mt-4 pt-4 border-top d-flex justify-content-between">
                 <asp:Button ID="btnCancelarConsulta" runat="server" Text="Cancelar" CssClass="btn btn-secondary" OnClick="btnCancelarConsulta_Click" />
                 <div>
                      <asp:LinkButton ID="btnGuardarBorrador" runat="server" CssClass="btn btn-guardar-borrador" OnClick="btnGuardarBorrador_Click"><i class='fas fa-save'></i> Guardar Borrador</asp:LinkButton>
                      <asp:LinkButton ID="btnCompletarConsulta" runat="server" CssClass="btn btn-completar-consulta ms-2" OnClick="btnCompletarConsulta_Click"><i class='fas fa-check-circle'></i> Completar Consulta</asp:LinkButton>
                 </div>
            </div>
        </div> <%-- Fin Columna Derecha --%>
    </div> <%-- Fin Row Principal --%>
</asp:Content>

<asp:Content ID="ContentScripts" ContentPlaceHolderID="cphScripts" runat="server">
    <%-- SCRIPT PARA CHECKBOXES DE SERVICIOS --%>
    <script type="text/javascript">
        $(document).ready(function () {
            function updateCheckboxStyle(checkbox) {
                var $item = $(checkbox).closest('.servicio-item');
                if ($(checkbox).is(':checked')) { $item.addClass('selected'); }
                else { $item.removeClass('selected'); }
            }
            var checkboxes = $('.servicios-checkbox-list .form-check-input');
            checkboxes.each(function () { updateCheckboxStyle(this); });
            checkboxes.on('change', function () { updateCheckboxStyle(this); /* calcularMontoTotal(); */ });
            // Click en el item completo (ahora es card-body)
            $('.servicios-checkbox-list .servicio-item').on('click', function (e) {
                if (!$(e.target).is('input[type="checkbox"]') && !$(e.target).is('label')) {
                    var $checkbox = $(this).find('.form-check-input');
                    $checkbox.prop('checked', !$checkbox.prop('checked')).trigger('change');
                }
            });
        });
    </script>
</asp:Content>