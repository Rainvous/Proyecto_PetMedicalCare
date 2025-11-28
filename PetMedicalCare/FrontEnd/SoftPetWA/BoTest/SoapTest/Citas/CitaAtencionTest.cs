using Microsoft.VisualStudio.TestTools.UnitTesting;
using SoftPetBusiness;
using SoftPetBussiness.CitaAtencionClient;
using System;
using System.Collections.Generic;
using System.Threading;

namespace SoftPetBusiness.Tests
{
    [TestClass]
    public class CitaAtencionBOTest
    {
        private CitaAtencionBO citaBO;

        [TestInitialize]
        public void Setup()
        {
            citaBO = new CitaAtencionBO();
        }

        [TestMethod]
        public void TestListarProgramdas()
        {
            //// Obtener la lista de todas las citas
            //List<citaAtencionDto> citas = citaBO.ListarTodos();

            //// Verificar que la lista no esté vacía
            //Assert.IsNotNull(citas);
            //Assert.IsTrue(citas.Count > 0, "La lista de citas no debería estar vacía.");
            List<citaProgramadaDto> citaProgramadaDtos= citaBO.ListarCitasProgramadaEseDia(1, "2025-11-17");
            for(int i=0 ;i<citaProgramadaDtos.Count;i++)
            {
                Console.WriteLine("FechaHora: "+citaProgramadaDtos[i].fecha +" Disponible: "+citaProgramadaDtos[i].estaProgramada);
            }
            Assert.IsTrue(citaProgramadaDtos.Count > 0, "La lista de citas programadas no debería estar vacía.");
        }

        [TestMethod]
        public void Insertar()
        {
            // Fechas de la cita: hoy + 1 día, duración 30 minutos
            //DateTime fechaHoraInicio = DateTime.Now.AddDays(1);
            //DateTime fechaHoraFin = fechaHoraInicio.AddMinutes(30);

            //// Insertar la cita usando la sobrecarga con DateTime
            //int resultado = citaBO.Insertar(
            //    veterinarioId: 1,
            //    mascotaId: 1,
            //    fechaHoraInicio: fechaHoraInicio,
            //    fechaHoraFin: fechaHoraFin,
            //    pesoMascota: 5.5,
            //    monto: 80.0,
            //    estado: "PROGRAMADA", // Debe coincidir con el enum del backend
            //    observacion: "Cita de prueba automatizada",
            //    activo: true
            //);

            //Console.WriteLine("ID de la nueva cita: " + resultado);
            //Assert.IsTrue(resultado > 0, "La cita debería haberse insertado correctamente.");
        }

        [TestMethod]
        public void Modificar()
        {
            //// ID de cita existente (ajusta según tus datos reales)
            //int citaIdExistente = 1;

            //// Obtener la cita actual
            //citaAtencionDto citaExistente = citaBO.ObtenerPorId(citaIdExistente);
            //Assert.IsNotNull(citaExistente, $"No se encontró la cita con ID {citaIdExistente}.");

            //// Nuevos valores modificados
            //string nuevaFechaInicioStr = DateTime.Now.AddDays(1).ToString("yyyyMMddHHmmss");
            //string nuevaFechaFinStr = DateTime.Now.AddDays(1).AddMinutes(30).ToString("yyyyMMddHHmmss");
            //string nuevaObservacion = "Observación actualizada desde test (vía método completo)";

            //// Usar el método Modificar con TODOS los parámetros
            //int resultado = citaBO.Modificar(
            //    citaId: citaExistente.citaId,
            //    veterinarioId: citaExistente.veterinario.veterinarioId,
            //    mascotaId: citaExistente.mascota.mascotaId,
            //    fechaHoraInicio: nuevaFechaInicioStr,
            //    fechaHoraFin: nuevaFechaFinStr,
            //    pesoMascota: citaExistente.pesoMascota,
            //    monto: citaExistente.monto,
            //    estado: citaExistente.estado.ToString(), // String exacto del enum
            //    observacion: nuevaObservacion,
            //    activo: citaExistente.activo
            //);

            //Assert.IsTrue(resultado > 0, "La cita debería haberse modificado correctamente usando la firma completa.");
        }


        [TestMethod]
        public void ObtenerPorId()
        {
            // Definir el ID de la cita a obtener (ajusta según tus datos)
            int citaId = 1;

            // Obtener la cita
            citaAtencionDto cita = citaBO.ObtenerPorId(citaId);

            // Verificar que la cita no sea nula y que el ID coincida
            Assert.IsNotNull(cita, "La cita no debería ser nula.");
            Assert.AreEqual(citaId, cita.citaId, "El ID de la cita obtenida debería coincidir.");
        }

        [TestMethod]
        public void ListasBusquedaAvanzada()
        {
            // Buscar citas por fecha usando la sobrecarga con DateTime
            //DateTime fechaBusqueda = new DateTime(2025, 11, 14);
            //Console.WriteLine("Buscando citas para la fecha: " + fechaBusqueda.ToString("yyyy-MM-dd"));

            //List<citaAtencionDto> resultados = citaBO.ListasBusquedaAvanzada(fechaBusqueda);

            //// Verificar que la respuesta no sea nula
            //Assert.IsNotNull(resultados, "El resultado de la búsqueda avanzada no debería ser nulo.");

            //// No necesariamente habrá resultados, pero al menos no debe lanzar error
            //Console.WriteLine("Cantidad de citas encontradas para la fecha {0}: {1}",
            //    fechaBusqueda.ToString("yyyyMMdd"), resultados.Count);
        }
    }
}
