//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.SoftPetPersonasWS;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class PersonaBOTest
//    {
//        private PersonaBO personaBO;

//        [TestInitialize]
//        public void Setup()
//        {
//            personaBO = new PersonaBO();
//        }

//        [TestMethod]
//        public void TestListarTodos()
//        {
//            // Listar todas las personas
//            List<personaDto> personas = personaBO.ListarTodos();

//            // Validaciones
//            Assert.IsNotNull(personas);
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            // Crear DTO de persona
//            var nuevaPersona = new personaDto
//            {
//                usuario = new usuarioDto { usuarioId = 1 },
//                nombre = "Juan Pérez",
//                direccion = "Av. Siempre Viva 123",
//                telefono = "999888777",
//                sexo = sexo.F,      // Si el proxy expone enum, usar .ToString() del enum correspondiente
//                nroDocumento = 12345678,
//                ruc = 0,                  // Ajusta si tu caso requiere RUC distinto de 0
//                tipoDocumento = "DNI",
//                activo = true
//            };

//            // Insertar
//            //int resultado = personaBO.Insertar(nuevaPersona);
//            int resultado = personaBO.Insertar(
//                nuevaPersona.usuario.usuarioId,
//                nuevaPersona.nombre,
//                nuevaPersona.direccion,
//                nuevaPersona.telefono,
//                nuevaPersona.sexo.ToString(),   // si el proxy es string, queda igual
//                nuevaPersona.nroDocumento,
//                nuevaPersona.ruc,
//                nuevaPersona.tipoDocumento,
//                nuevaPersona.activo
//            );

//            // Validación
//            Console.WriteLine("ID de la nueva persona: " + resultado);
//            Assert.IsTrue(resultado > 0);
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener existente (ajusta el ID de prueba según tus datos)
//            int personaId = 2;
//            personaDto personaExistente = personaBO.ObtenerPorId(personaId);

//            // Asegurar que existe
//            Assert.IsNotNull(personaExistente);

//            // Cambios
//            personaExistente.nombre = "Juan P. Actualizado";
//            personaExistente.telefono = "988777666";

//            // Modificar con parámetros sueltos (siguiendo tu plantilla)
//            int resultado = personaBO.Modificar(
//                personaExistente.personaId,
//                personaExistente.usuario.usuarioId,
//                personaExistente.nombre,
//                personaExistente.direccion,
//                personaExistente.telefono,
//                personaExistente.sexo.ToString(),   // si el proxy es string, queda igual
//                personaExistente.nroDocumento,
//                personaExistente.ruc,
//                personaExistente.tipoDocumento,
//                personaExistente.activo
//            );

//            // Validación
//            Assert.IsTrue(resultado > 0);
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            //// ID a eliminar (ajusta según tus datos)
//            //int personaIdAEliminar = 3;

//            //int resultado = personaBO.Eliminar(personaIdAEliminar);

//            //Assert.IsTrue(resultado > 0);
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // ID a consultar (ajusta según tus datos)
//            int personaId = 2;

//            personaDto persona = personaBO.ObtenerPorId(personaId);

//            Assert.IsNotNull(persona);
//            Assert.AreEqual(personaId, persona.personaId);
//        }
//    }
//}
