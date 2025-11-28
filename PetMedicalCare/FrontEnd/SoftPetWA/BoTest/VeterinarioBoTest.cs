//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.SoftPetVeterinariosWS;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class VeterinarioBOTest
//    {
//        private VeterinarioBO veterinarioBO;

//        [TestInitialize]
//        public void Setup()
//        {
//            veterinarioBO = new VeterinarioBO();
//        }

//        [TestMethod]
//        public void TestListarTodos()
//        {
//            // Obtener la lista de todos los veterinarios
//            List<veterinarioDto> veterinarios = veterinarioBO.ListarTodos();

//            // Verificar que la lista no esté vacía
//            Assert.IsNotNull(veterinarios);
//            Assert.IsTrue(veterinarios.Count > 0, "La lista de veterinarios no debería estar vacía.");
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            //// Crear un nuevo veterinario
//            //veterinarioDto nuevoVeterinario = new veterinarioDto
//            //{
//            //    persona = new personaDto { personaId = 1 },
//            //    estado = estadoVeterinario.ACTIVO,
//            //    especialidad = "Cirujano Veterinario",
//            //    activo = true
//            //};

//            // Insertar el veterinario
//            int resultado = veterinarioBO.Insertar(
//                2,
//                DateTime.Now.ToString("yyyy-MM-dd"), //OJO LA FECHA ASI SIEMPRE
//                "ACTIVO",
//                "Cirujano Veterinario",
//                true
//            );

//            // Verificar que el ID del nuevo veterinario sea mayor que 0 (se insertó correctamente)
//            Console.WriteLine("ID del nuevo veterinario: " + resultado);
//            Assert.IsTrue(resultado > 0, "El veterinario debería haberse insertado correctamente.");
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener un veterinario existente
//            veterinarioDto veterinarioExistente = veterinarioBO.ObtenerPorId(2);
//            veterinarioExistente.especialidad = "Cardiólogo Veterinario";
//            Console.WriteLine("Fecha de contratación actual: " + veterinarioExistente.fechaContratacion);

//            // Modificar el veterinario
//            int resultado = veterinarioBO.Modificar(
//                veterinarioExistente.veterinarioId,
//                veterinarioExistente.persona.personaId,
//                DateTime.Now.ToString("yyyy-MM-dd"), // NO USAR veterinarioExistente.fechaContratacion.ToString()
//                veterinarioExistente.estado.ToString(),
//                veterinarioExistente.especialidad,
//                veterinarioExistente.activo
//            );


//            // Verificar que la modificación fue exitosa
//            Assert.IsTrue(resultado > 0, "El veterinario debería haberse modificado correctamente.");
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            // Definir el ID del veterinario a eliminar
//            int veterinarioIdAEliminar = 3;

//            // Eliminar el veterinario
//            int resultado = veterinarioBO.Eliminar(veterinarioIdAEliminar);

//            // Verificar que la eliminación fue exitosa
//            Assert.IsTrue(resultado > 0, "El veterinario debería haberse eliminado correctamente.");
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // Definir el ID del veterinario a obtener
//            int veterinarioId = 2;

//            // Obtener el veterinario
//            veterinarioDto veterinario = veterinarioBO.ObtenerPorId(veterinarioId);

//            // Verificar que el veterinario no sea nulo y que el ID coincida
//            Assert.IsNotNull(veterinario, "El veterinario no debería ser nulo.");
//            Assert.AreEqual(veterinarioId, veterinario.veterinarioId, "El ID del veterinario obtenido debería coincidir.");
//        }
//    }
//}
