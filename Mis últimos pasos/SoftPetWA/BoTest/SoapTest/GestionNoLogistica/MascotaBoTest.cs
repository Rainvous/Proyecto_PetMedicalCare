

//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.MascotaClient;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class MascotaBOTest
//    {
//        private MascotaBO mascotaBO;

//        [TestInitialize]
//        public void Setup()
//        {
//            mascotaBO = new MascotaBO();
//        }

//        [TestMethod]
//        public void TestListarTodos()
//        {
//            //// Listar todas las mascotas
//            //List<mascotaDto> mascotas = mascotaBO.ListarTodos();

//            //// Validaciones
//            //Assert.IsNotNull(mascotas);
//            //Assert.IsTrue(mascotas.Count > 0, "La lista de mascotas no debería estar vacía.");
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            // Crear nueva mascota
//            var nuevaMascota = new mascotaDto
//            {
//                persona = new personaDto { personaId = 1 },
//                nombre = "pepe",
//                especie = "Canino",
//                sexo = "M",
//                raza = "Bulldog Francés",
//                color = "Blanco con ojeras",
//                // fechaDefuncion = Nullable,   // No fallecida
//                activo = true
//            };

//            // Insertar
//            //int resultado = mascotaBO.Insertar(nuevaMascota,"");
//            int resultado = mascotaBO.Insertar(
//                nuevaMascota.persona.personaId,
//                nuevaMascota.nombre,
//                nuevaMascota.especie,
//                nuevaMascota.sexo,
//                nuevaMascota.raza,
//                nuevaMascota.color,
//                "",
//                nuevaMascota.activo
//            );

//            Console.WriteLine("ID de la nueva mascota: " + resultado);
//            Assert.IsTrue(resultado > 0, "La mascota debería haberse insertado correctamente.");
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener una mascota existente (ajusta el ID según tus datos)
//            int mascotaId = 4;
//            mascotaDto mascotaExistente = mascotaBO.ObtenerPorId(mascotaId);
//            Assert.IsNotNull(mascotaExistente, "La mascota existente no debe ser nula.");

//            // Modificar algunos campos
//            mascotaExistente.color = "Marrón claro";
//            mascotaExistente.raza = "Golden Retriever";

//            // Ejecutar modificación
//            int resultado = mascotaBO.Modificar(
//                mascotaExistente.mascotaId,
//                mascotaExistente.persona.personaId,
//                "Pepe lobo",
//                mascotaExistente.especie,
//                mascotaExistente.sexo,
//                mascotaExistente.raza,
//                mascotaExistente.color,
//                "",
//                mascotaExistente.activo
//            );

//            Assert.IsTrue(resultado > 0, "La mascota debería haberse modificado correctamente.");
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            // ID de mascota a eliminar (ajusta según tus datos)
//            int mascotaIdAEliminar = 3;

//            int resultado = mascotaBO.Eliminar(mascotaIdAEliminar);

//            Assert.IsTrue(resultado > 0, "La mascota debería haberse eliminado correctamente.");
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // ID de la mascota a obtener (ajusta según tus datos)
//            int mascotaId = 2;

//            mascotaDto mascota = mascotaBO.ObtenerPorId(mascotaId);

//            Assert.IsNotNull(mascota, "La mascota no debería ser nula.");
//            Assert.AreEqual(mascotaId, mascota.mascotaId, "El ID de la mascota obtenida debería coincidir.");
//        }
//    }
//}
