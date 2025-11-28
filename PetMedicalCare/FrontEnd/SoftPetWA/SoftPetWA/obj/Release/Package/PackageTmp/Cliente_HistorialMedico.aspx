<%@ Page Title="Historial Médico" Language="C#" MasterPageFile="~/Cliente.Master" AutoEventWireup="true"
    CodeBehind="Cliente_HistorialMedico.aspx.cs"
    Inherits="SoftPetWA.Cliente_HistorialMedico" %>

<asp:Content ID="Content1" ContentPlaceHolderID="cphTitulo" runat="server">
    Historial Médico
</asp:Content>

<asp:Content ID="Content3" ContentPlaceHolderID="cphHead" runat="server">
    <%-- ESTILOS INCRUSTADOS PARA GARANTIZAR EL DISEÑO --%>
    <style>
        /* --- Tarjeta Izquierda (Perfil) --- */
        .card-perfil {
            background: #fff;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0,0,0,0.08);
            padding: 30px 20px;
            text-align: center;
            border: 1px solid #eee;
        }

        /* FORZAR TAMAÑO DE IMAGEN */
        .avatar-container {
            width: 130px;
            height: 130px;
            margin: 0 auto 15px;
            border-radius: 50%;
            overflow: hidden;
            border: 5px solid #f8f9fa;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            display: flex;
            align-items: center;
            justify-content: center;
            background: #fff;
        }

            .avatar-container img {
                width: 100%;
                height: 100%;
                object-fit: cover; /* Clave para que no se deforme */
            }

        .perfil-nombre {
            font-size: 1.6rem;
            font-weight: 800;
            color: #333;
            margin-bottom: 5px;
        }

        .perfil-raza {
            color: #777;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85rem;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 10px;
            margin-top: 25px;
            text-align: left;
        }

        .stat-item {
            background: #f8f9fa;
            padding: 10px 15px;
            border-radius: 10px;
            border: 1px solid #e9ecef;
        }

        .stat-label {
            font-size: 0.75rem;
            color: #999;
            font-weight: 700;
            display: block;
            text-transform: uppercase;
        }

        .stat-value {
            font-size: 1rem;
            color: #333;
            font-weight: 700;
        }

        /* --- Columna Derecha (Línea de Tiempo) --- */
        .timeline-wrapper {
            padding-left: 20px;
            border-left: 3px solid #e9ecef;
            margin-left: 10px;
        }

        .card-historia {
            background: #fff;
            border-radius: 12px;
            padding: 20px;
            margin-bottom: 25px;
            position: relative;
            box-shadow: 0 3px 10px rgba(0,0,0,0.05);
            border: 1px solid #eee;
            transition: transform 0.2s;
        }

            .card-historia:hover {
                transform: translateY(-3px);
                box-shadow: 0 8px 20px rgba(0,0,0,0.1);
            }

        /* Punto (Dot) */
        .timeline-dot {
            position: absolute;
            left: -29px; /* Ajustado a la línea */
            top: 25px;
            width: 16px;
            height: 16px;
            border-radius: 50%;
            background: #fff;
            border: 4px solid #ccc;
        }

        /* Colores de estado */
        .borde-verde {
            border-left: 5px solid #28a745;
        }

        .dot-verde {
            border-color: #28a745;
        }

        .borde-azul {
            border-left: 5px solid #0d6efd;
        }

        .dot-azul {
            border-color: #0d6efd;
        }

        .borde-rojo {
            border-left: 5px solid #dc3545;
        }

        .dot-rojo {
            border-color: #dc3545;
        }

        /* Cabecera de Cita */
        .cita-header {
            display: flex;
            justify-content: space-between;
            margin-bottom: 15px;
            border-bottom: 1px dashed #eee;
            padding-bottom: 10px;
        }

        .cita-fecha {
            font-weight: 700;
            font-size: 0.85rem;
            color: #888;
        }

        .cita-titulo {
            font-size: 1.2rem;
            font-weight: 800;
            color: #333;
            margin: 0;
        }

        .cita-doc {
            font-size: 0.9rem;
            color: #666;
        }

        .badge-estado {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 0.75rem;
            font-weight: 700;
            text-transform: uppercase;
        }

        .bg-verde {
            background: #d4edda;
            color: #155724;
        }

        .bg-azul {
            background: #cce5ff;
            color: #004085;
        }

        .bg-rojo {
            background: #f8d7da;
            color: #721c24;
        }

        .info-bloque {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 8px;
            margin-top: 10px;
            font-size: 0.95rem;
            color: #555;
        }

        .info-label {
            font-weight: 700;
            font-size: 0.7rem;
            color: #aaa;
            display: block;
            margin-bottom: 3px;
        }
    </style>
</asp:Content>

<asp:Content ID="Content2" ContentPlaceHolderID="cphContenido" runat="server">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h3 class="fw-bold text-dark"><i class="fas fa-file-medical-alt me-2 text-primary"></i>Historial Médico</h3>
        <asp:LinkButton ID="btnImprimir" runat="server" CssClass="btn btn-light border shadow-sm" OnClick="btnImprimir_Click">
             <i class="fas fa-print me-2"></i>Imprimir
        </asp:LinkButton>
    </div>

    <div class="row">
        <%-- IZQUIERDA: PERFIL --%>
        <div class="col-lg-4 mb-4">
            <div class="card-perfil">
                <div class="avatar-container">
                    <asp:Image ID="imgAvatarMascotaHist" runat="server" />
                </div>
                <h3 class="perfil-nombre">
                    <asp:Label ID="lblNombreMascotaHist" runat="server"></asp:Label></h3>
                <div class="perfil-raza">
                    <asp:Label ID="lblRazaMascotaHist" runat="server"></asp:Label>
                </div>

                <div class="stats-grid">
                    <div class="stat-item">
                        <span class="stat-label">Edad</span>
                        <span class="stat-value">
                            <asp:Label ID="lblEdadHist" runat="server"></asp:Label></span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Sexo</span>
                        <span class="stat-value">
                            <asp:Label ID="lblSexoHist" runat="server"></asp:Label></span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Peso</span>
                        <span class="stat-value">
                            <asp:Label ID="lblPesoHist" runat="server"></asp:Label></span>
                    </div>
                    <div class="stat-item">
                        <span class="stat-label">Color</span>
                        <span class="stat-value">
                            <asp:Label ID="lblColorHist" runat="server"></asp:Label></span>
                    </div>
                    <div class="stat-item" style="grid-column: span 2;">
                        <span class="stat-label">Total Historial</span>
                        <span class="stat-value">
                            <asp:Label ID="lblVisitasHist" runat="server"></asp:Label></span>
                    </div>
                </div>

                <div class="d-grid gap-2 mt-4">
                    <asp:LinkButton ID="btnAgendarCita" runat="server" CssClass="btn btn-primary fw-bold py-2" OnClick="btnAgendarCita_Click">
                         <i class="fas fa-plus-circle me-2"></i>Agendar Cita
                    </asp:LinkButton>
                    <asp:LinkButton ID="btnCambiarMascota" runat="server" CssClass="btn btn-outline-secondary py-2" OnClick="btnCambiarMascota_Click">
                          Cambiar Mascota
                    </asp:LinkButton>
                </div>
            </div>
        </div>

        <%-- DERECHA: HISTORIAL --%>
        <div class="col-lg-8">
            <div class="timeline-wrapper">
                <asp:Repeater ID="rptHistorialCliente" runat="server" OnItemCommand="rptHistorialCliente_ItemCommand">
                    <ItemTemplate>
                        <%-- Tarjeta --%>
                        <div class="card-historia <%# Eval("ClaseDot").ToString().Replace("dot-atendida", "borde-verde").Replace("dot-programada", "borde-azul").Replace("dot-cancelada", "borde-rojo") %>">

                            <%-- Punto (Dot) con mapeo manual de colores para asegurar que funcione --%>
                            <div class="timeline-dot <%# Eval("ClaseDot").ToString().Replace("dot-atendida", "dot-verde").Replace("dot-programada", "dot-azul").Replace("dot-cancelada", "dot-rojo") %>"></div>

                            <div class="cita-header">
                                <div>
                                    <div class="cita-fecha"><i class="far fa-clock me-1"></i><%# Eval("FechaHora") %></div>
                                    <h4 class="cita-titulo mt-1"><%# Eval("Titulo") %></h4>
                                    <div class="cita-doc"><i class="fas fa-user-md me-1"></i><%# Eval("Doctor") %></div>
                                </div>
                                <div>
                                    <span class="badge-estado <%# Eval("ClaseBadge").ToString().Replace("bg-soft-success", "bg-verde").Replace("bg-soft-primary", "bg-azul").Replace("bg-soft-danger", "bg-rojo") %>">
                                        <%# Eval("BadgeTexto") %>
                                    </span>
                                </div>
                            </div>

                            <%-- Diagnóstico --%>
                            <asp:Panel ID="pnlDiag" runat="server" Visible='<%# !string.IsNullOrEmpty(Eval("Diagnostico").ToString()) && Eval("Diagnostico").ToString() != "Pendiente de atención" && Eval("Diagnostico").ToString() != "Cita Cancelada" %>'>
                                <div class="info-bloque" style="border-left: 3px solid #ffc107;">
                                    <span class="info-label text-warning">DIAGNÓSTICO</span>
                                    <%# Eval("Diagnostico") %>
                                </div>
                            </asp:Panel>

                            <%-- Observaciones --%>
                            <div class="info-bloque">
                                <span class="info-label">OBSERVACIONES</span>
                                <%# Eval("Observaciones") %>
                            </div>

                            <%-- Peso --%>
                            <asp:Panel ID="pnlPeso" runat="server" Visible='<%# Eval("Peso").ToString() != "-" %>'>
                                <div class="mt-3 pt-2 border-top">
                                    <small class="text-muted fw-bold">PESO REGISTRADO:</small>
                                    <span class="fw-bold text-dark"><%# Eval("Peso") %></span>
                                </div>
                            </asp:Panel>

                        </div>
                    </ItemTemplate>

                    <FooterTemplate>
                        <asp:Panel ID="pnlVacio" runat="server" Visible='<%# rptHistorialCliente.Items.Count == 0 %>'>
                            <div class="text-center py-5 text-muted">
                                <i class="fas fa-folder-open fa-3x mb-3 opacity-50"></i>
                                <h5>No hay historial registrado</h5>
                            </div>
                        </asp:Panel>
                    </FooterTemplate>
                </asp:Repeater>
            </div>
        </div>
    </div>

</asp:Content>
