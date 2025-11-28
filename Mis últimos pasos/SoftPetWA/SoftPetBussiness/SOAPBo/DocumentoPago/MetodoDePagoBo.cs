using SoftPetBussiness.MetodoDePagoClient; // Ajusta al namespace de tu Service Reference
using System;
using System.Collections.Generic;
using System.Linq;

namespace SoftPetBusiness
{
    public class MetodoDePagoBO
    {
        private MetodosDePagoClient clienteSOAP;

        public MetodoDePagoBO()
        {
            this.clienteSOAP = new MetodosDePagoClient();
        }
        //metodoDePagoDto met= new metodoDePagoDto();
        //met.activo = activo;
        //met.nombre = nombre;

        // ===================================================
        //  INSERTAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  insertar_MetodoDePago(String nombre, boolean activo)
        // ===================================================
        public int Insertar(string nombre, bool activo)
        {
            return this.clienteSOAP.insertar_MetodoDePago(
                nombre,
                activo
            );

        }

        // ===================================================
        //  MODIFICAR (MISMA FIRMA QUE EL SERVICIO EN JAVA)
        //  Java:
        //  modificar_MetodoDePago(int metodoDePagoId, String nombre,
        //                         boolean activo)
        // ===================================================
        public int Modificar(int metodoDePagoId, string nombre, bool activo)
        {
            return this.clienteSOAP.modificar_MetodoDePago(
                metodoDePagoId,
                nombre,
                activo
            );
        }

        // =========================
        //        ELIMINAR
        // =========================
        public int Eliminar(int metodoDePagoId)
        {
            return this.clienteSOAP.eliminar_modificar_MetodoDePago(metodoDePagoId);
        }

        // =========================
        //      OBTENER POR ID
        // =========================
        public metodoDePagoDto ObtenerPorId(int metodoDePagoId)
        {
            return this.clienteSOAP.obtener_por_id(metodoDePagoId);
        }

        // =========================
        //       LISTAR TODOS
        // =========================
        public List<metodoDePagoDto> ListarTodos()
        {
            return this.clienteSOAP
                .listar_todos()
                .ToList<metodoDePagoDto>();
        }
    }
}
