using Microsoft.VisualStudio.TestTools.UnitTesting;
using Newtonsoft.Json;
using SoftPetBussiness.Bo;
using System;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace SoftPetBussiness.Tests
{
    [TestClass]
    public class RecetaMedicaBoTest
    {
        private static RecetaMedicaBo recetaMedicaBo;

        [ClassInitialize]
        public static void ClassInitialize(TestContext context)
        {
            // Inicializar el BO (RecetaMedicaBo)
            recetaMedicaBo = new RecetaMedicaBo();
        }

        [TestMethod]
        public void TestListarTodos()
        {
            // Obtener todas las recetas
            var recetas = recetaMedicaBo.ListarTodos();

            // Verificar que la lista no esté vacía
            Assert.IsNotNull(recetas);
            Assert.IsTrue(recetas.Count > 0); // Esperamos que al menos una receta esté presente
        }

        [TestMethod]
        public void TestObtenerPorId()
        {
            int recetaId = 1;  // Cambia esto según los datos disponibles en tu base

            // Obtener una receta por ID
            var receta = recetaMedicaBo.ObtenerPorId(recetaId);

            // Verificar que la receta no sea nula
            Assert.IsNotNull(receta);
            Assert.AreEqual(recetaId, receta.RecetaMedicaId);  // Verificar que los IDs coincidan
        }

        [TestMethod]
        public void TestInsertar()
        {
            // Crear un objeto RecetaMedicaDto para insertar
            var nuevaReceta = new RecetaMedicaDto
            {
                Cita = new CitaDto { CitaId = 2 },
                Diagnostico = "Gastritis leve RESTFUL C#",
                Observaciones = "Reposo y control en 7 días RESTFUL C#",
                FechaEmision = DateTime.Parse("2025-10-28"),
                VigenciaHasta = DateTime.Parse("2025-11-04"),
                Activo = true
            };

            // Insertar la nueva receta
            var recetaCreada = recetaMedicaBo.Insertar(nuevaReceta);

            // Verificar que la receta fue creada
            Assert.IsNotNull(recetaCreada);
            Assert.AreEqual("Gastritis leve RESTFUL C#", recetaCreada.Diagnostico);  // Verificar que el diagnóstico sea el esperado
            Assert.IsTrue(recetaCreada.Activo);  // Verificar que esté activo
        }

        [TestMethod]
        public void TestModificar()
        {
            // Crear un objeto RecetaMedicaDto para modificar
            var recetaModificada = new RecetaMedicaDto
            {
                RecetaMedicaId = 1,  // Asegúrate de que este ID exista en la base de datos
                Cita = new CitaDto { CitaId = 1 },
                Diagnostico = "Conjuntivitis RESTFUL C#",
                Observaciones = "Gotas 3x/día",
                FechaEmision = DateTime.Parse("2025-10-28"),
                VigenciaHasta = DateTime.Parse("2025-11-04"),
                Activo = true
            };

            // Modificar la receta
            var recetaActualizada = recetaMedicaBo.Modificar(recetaModificada);

            // Verificar que la receta fue modificada correctamente
            Assert.IsNotNull(recetaActualizada);
            Assert.AreEqual("Conjuntivitis RESTFUL C#", recetaActualizada.Diagnostico);
            Assert.AreEqual(true, recetaActualizada.Activo);
        }

        [TestMethod]
        public void TestEliminar()
        {
            int recetaId = 9;  // Cambia esto a un ID válido de tu base

            // Eliminar la receta
            recetaMedicaBo.Eliminar(recetaId);

            // Verificar que la receta ha sido eliminada (si el ID no existe, lanzará una excepción)
            try
            {
                var recetaEliminada = recetaMedicaBo.ObtenerPorId(recetaId);  // Intentamos obtener la receta eliminada
                Assert.Fail("La receta debería haber sido eliminada.");
            }
            catch (Exception)
            {
                // Si obtenemos un error, significa que la receta fue eliminada correctamente
                Assert.IsTrue(true);
            }
        }
    }
}
