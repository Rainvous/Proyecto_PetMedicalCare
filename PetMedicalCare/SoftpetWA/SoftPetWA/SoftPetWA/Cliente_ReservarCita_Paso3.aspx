<%-- Nombre del archivo: Cliente_ReservarCita_Paso3.aspx --%>
<%@ Page Title="Reservar Cita - Fecha y Hora" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true" 
    CodeBehind="Cliente_ReservarCita_Paso3.aspx.cs" 
    Inherits="SoftPetWA.Cliente_ReservarCita_Paso3" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Reservar Cita - Fecha y Hora
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <link href="Content/reservarcita.css" rel="stylesheet" />
    <link href="Content/reservarcita-calendario.css" rel="stylesheet" />
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">
    
    <%-- 1. Botón Volver --%>
    <asp:LinkButton ID="btnVolver" runat="server" CssClass="btn btn-link mb-3 text-decoration-none text-muted" OnClick="btnAnterior_Click">
        <i class="fas fa-arrow-left me-2"></i>Reservar Nueva Cita
    </asp:LinkButton>

    <%-- 2. Contenedor del Stepper (En su propia tarjeta) --%>
    <div class="card shadow-sm border-0 mb-4">
        <div class="card-body p-4">
            <div class="stepper-wrapper">
                <div class="step-item active">
                    <div class="step-circle">1</div><span class="step-label">Mascota</span>
                </div>
                <div class="step-item active">
                    <div class="step-circle">2</div><span class="step-label">Servicio</span>
                </div>
                <div class="step-item active">
                    <div class="step-circle">3</div><span class="step-label">Fecha y Hora</span>
                </div>
                <div class="step-item">
                    <div class="step-circle">4</div><span class="step-label">Confirmación</span>
                </div>
            </div>
        </div>
    </div>

    <%-- 3. Contenedor del Contenido Principal (En su propia tarjeta) --%>
    <div class="card shadow-sm border-0">
        <div class="card-body p-4">
            
            <%-- SECCIÓN DE SELECCIÓN DE FECHA Y HORA --%>
            <div class="mb-4">
                <h4><i class="fas fa-calendar-alt me-2 text-primary"></i>Selecciona Fecha y Hora</h4>
            </div>

            <div class="row">
                <%-- Columna Izquierda: Veterinario y Calendario --%>
                <div class="col-md-7">
                    <div class="mb-3">
                        <label for="<%= ddlVeterinario.ClientID %>" class="form-label fw-bold">Veterinario</label>
                        <asp:DropDownList ID="ddlVeterinario" runat="server" CssClass="form-select" AutoPostBack="true"></asp:DropDownList>
                    </div>

                    <div class="calendar-container">
                        <div class="calendar-header">
                            <span class="month-year"><asp:Label ID="lblMesAnio" runat="server"></asp:Label></span>
                            <div class="calendar-nav">
                                <asp:LinkButton ID="btnPrevMonth" runat="server" CssClass="btn btn-sm btn-outline-secondary me-1"><i class="fas fa-chevron-left"></i></asp:LinkButton>
                                <asp:LinkButton ID="btnNextMonth" runat="server" CssClass="btn btn-sm btn-outline-secondary"><i class="fas fa-chevron-right"></i></asp:LinkButton>
                            </div>
                        </div>
                        <div class="calendar-grid">
                            <div class="day-header">Dom</div>
                            <div class="day-header">Lun</div>
                            <div class="day-header">Mar</div>
                            <div class="day-header">Mié</div>
                            <div class="day-header">Jue</div>
                            <div class="day-header">Vie</div>
                            <div class="day-header">Sáb</div>
                            <div class="date-cell other-month">29</div>
                            <div class="date-cell other-month">30</div>
                            <div class="date-cell">1</div>
                            <div class="date-cell">2</div>
                            <div class="date-cell">3</div>
                            <div class="date-cell">4</div>
                            <div class="date-cell">5</div>
                            <div class="date-cell">6</div>
                            <div class="date-cell">7</div>
                            <div class="date-cell">8</div>
                            <div class="date-cell">9</div>
                            <div class="date-cell">10</div>
                            <div class="date-cell">11</div>
                            <div class="date-cell">12</div>
                            <div class="date-cell">13</div>
                            <div class="date-cell">14</div>
                            <div class="date-cell selected" data-date="15">15</div>
                            <div class="date-cell" data-date="16">16</div>
                            <div class="date-cell" data-date="17">17</div>
                            <div class="date-cell" data-date="18">18</div>
                            <div class="date-cell" data-date="19">19</div>
                            <div class="date-cell" data-date="20">20</div>
                            <div class="date-cell" data-date="21">21</div>
                            <div class="date-cell" data-date="22">22</div>
                            <div class="date-cell" data-date="23">23</div>
                            <div class="date-cell" data-date="24">24</div>
                            <div class="date-cell" data-date="25">25</div>
                            <div class="date-cell" data-date="26">26</div>
                            <div class="date-cell" data-date="27">27</div>
                            <div class="date-cell" data-date="28">28</div>
                            <div class="date-cell" data-date="29">29</div>
                            <div class="date-cell" data-date="30">30</div>
                            <div class="date-cell" data-date="31">31</div>
                            <div class="date-cell other-month">1</div>
                            <div class="date-cell other-month">2</div>
                        </div>
                    </div>
                </div>

                <%-- Columna Derecha: Horarios Disponibles --%>
                <div class="col-md-5">
                    <div class="time-slots-container">
                        <h6 class="time-slots-header">
                            <asp:Label ID="lblHorariosDisponibles" runat="server"></asp:Label>
                        </h6>
                        <div>
                            <asp:Repeater ID="rptHorarios" runat="server" OnItemCommand="rptHorarios_ItemCommand">
                                <ItemTemplate>
                                    <asp:LinkButton ID="btnHora" runat="server"
                                        CssClass='<%# Convert.ToBoolean(Eval("Disponible")) ? (Eval("Hora").ToString() == "11:00 AM" ? "time-slot-btn selected" : "time-slot-btn") : "time-slot-btn disabled" %>'
                                        CommandName="SelectTime"
                                        CommandArgument='<%# Eval("Hora") %>'
                                        Enabled='<%# Convert.ToBoolean(Eval("Disponible")) %>'>
                                        <%# Eval("Hora") %>
                                    </asp:LinkButton>
                                </ItemTemplate>
                            </asp:Repeater>
                        </div>
                        <div class="alert alert-primary mt-3" role="alert">
                            <i class="fas fa-info-circle me-2"></i>Los horarios mostrados son los disponibles para el veterinario seleccionado
                        </div>
                    </div>
                </div>
            </div>

            <%-- BOTONES DE NAVEGACIÓN --%>
            <div class="d-flex justify-content-between mt-4">
                <asp:LinkButton ID="btnAnterior" runat="server" CssClass="btn btn-outline-secondary" OnClick="btnAnterior_Click">
                    <i class="fas fa-arrow-left me-2"></i>Anterior
                </asp:LinkButton>
                <asp:LinkButton ID="btnSiguiente" runat="server" CssClass="btn btn-primary" OnClick="btnSiguiente_Click">
                    Siguiente<i class="fas fa-arrow-right ms-2"></i>
                </asp:LinkButton>
            </div>

        </div>
    </div>

</asp:Content>

<asp:Content ID="Content4" ContentPlaceHolderID="cphScripts" runat="server">
    <script type="text/javascript">
        $(document).ready(function () {
            $('.date-cell:not(.other-month)').click(function () {
                $('.date-cell').removeClass('selected');
                $(this).addClass('selected');  
                var day = $(this).data('date');
                $('#<%= lblHorariosDisponibles.ClientID %>').text('Horarios Disponibles - ' + day + ' de Octubre');
                $('.time-slot-btn').removeClass('selected disabled');
                if (day == 16) {
                    $('.time-slot-btn:contains("09:00 AM")').addClass('disabled').removeClass('selected');
                }
            });

            $('.time-slot-btn:not(.disabled)').click(function (e) {
                e.preventDefault(); // Previene el PostBack del LinkButton por ahora
                $('.time-slot-btn').removeClass('selected'); // Quita selección de todos
                $(this).addClass('selected'); // Marca el clicado
            });
        });
    </script>
</asp:Content>