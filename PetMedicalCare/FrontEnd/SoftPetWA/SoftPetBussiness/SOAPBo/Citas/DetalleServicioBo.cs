using SoftPetBussiness.DetalleServicioClient; // Ajusta al namespace real de tu Service Reference
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class DetalleServicioBO
    {
        private DetallesServicioClient clienteSOAP; // Ajusta el nombre si tu proxy se llama distinto

        public DetalleServicioBO()
        {
            this.clienteSOAP = new DetallesServicioClient();
        }
        //detalleServicioDto det= new detalleServicioDto();
        //det.cita.citaId=citaId;
        //det.servicio.servicioId=servicioId;
        //det.descripcion=descripcion;
        //det.costo=costo;
        //det.activo=activo;
        // ===================================================
        //  INSERTAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  insertar_detalleservicio(int citaId, int servicioId,
        //                           String descripcion, double costo,
        //                           boolean activo)
        // ===================================================
        public int Insertar(
            int citaId,
            int servicioId,
            string descripcion,
            double costo,
            bool activo)
        {
            return this.clienteSOAP.insertar_detalleservicio(
                citaId,
                servicioId,
                descripcion,
                costo,
                activo
            );



        }

        // ===================================================
        //  MODIFICAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  modificar_detalleservicio(int detalleServicioId, int citaId,
        //                            int servicioId, String descripcion,
        //                            double costo, boolean activo)
        // ===================================================
        public int Modificar(
            int detalleServicioId,
            int citaId,
            int servicioId,
            string descripcion,
            double costo,
            bool activo)
        {
            return this.clienteSOAP.modificar_detalleservicio(
                detalleServicioId,
                citaId,
                servicioId,
                descripcion,
                costo,
                activo
            );
        }

        // =========================
        //        ELIMINAR
        // =========================
        public int Eliminar(int detalleServicioId)
        {
            return this.clienteSOAP.eliminar_detalleservicio(detalleServicioId);
        }

        // =========================
        //      OBTENER POR ID
        // =========================
        public detalleServicioDto ObtenerPorId(int detalleServicioId)
        {
            return this.clienteSOAP.obtener_por_id(detalleServicioId);
        }

        // =========================
        //       LISTAR TODOS
        // =========================
        public List<detalleServicioDto> ListarTodos()
        {
            return this.clienteSOAP
                .listar_todos()
                .ToList<detalleServicioDto>();
        }
        // En SoftPetBusiness/DetalleServicioBO.cs

        public List<detalleServicioDto> ListarPorIdCita(int idCita)
        {
            // Conectamos con el método específico de Java que ya creaste
            // Nota: Asegúrate que en tu Service Reference (Cliente SOAP) el método se llame así.
            // A veces Java genera nombres como "listar_por_id_cita" o "ListarPorIDCita".
            return this.clienteSOAP.listar_por_id_cita(idCita).ToList<detalleServicioDto>();
        }
    }
}
