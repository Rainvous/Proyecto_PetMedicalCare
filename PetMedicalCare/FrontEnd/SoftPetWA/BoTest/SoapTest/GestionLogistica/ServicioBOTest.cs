//using Microsoft.VisualStudio.TestTools.UnitTesting;

//using SoftPetBussiness.ServicioClient;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class ServicioBOTest
//    {
//        private ServicioBO servicioBO;
//        private int id;

//        [TestInitialize]
//        public void Setup()
//        {
//            servicioBO = new ServicioBO();
//        }
//        [TestMethod]
//        public void TestListarTodos()
//        {
//            List<servicioDto> servicios = servicioBO.ListarTodos();
//            Assert.IsNotNull(servicios);
//        }
//        [TestMethod]
//        public void Insertar()
//        {
//            servicioDto nuevoServicio = new servicioDto
//            {
//                tipoServicio = new tipoServicioDto { tipoServicioId = 1 },
//                nombre = "Corte de pelo",
//                descripcion = "Corte de pelo para perros",
//                costo = 25.0,
//                estado = "Disponible",
//                activo = true
//            };
//            int resultado = servicioBO.Insertar(nuevoServicio);
//            Console.WriteLine("ID del nuevo servicio: " + resultado);
//            Assert.IsTrue(resultado > 0);
//        }
//        [TestMethod]
//        public void Modificar()
//        {
//            servicioDto servicioExistente = servicioBO.ObtenerPorId(2);
//            servicioExistente.nombre = "Corte de pelo actualizado";
//            int resultado = servicioBO.Modificar(
//                servicioExistente.servicioId,
//                servicioExistente.tipoServicio.tipoServicioId,
//                servicioExistente.nombre,
//                servicioExistente.descripcion,
//                servicioExistente.costo,
//                servicioExistente.estado,
//                servicioExistente.activo
//            );
//            Assert.IsTrue(resultado > 0);
//        }
//        [TestMethod]
//        public void Eliminar()
//        {
//            int servicioIdAEliminar = 3; // ID del servicio a eliminar
//            int resultado = servicioBO.Eliminar(servicioIdAEliminar);
//            Assert.IsTrue(resultado > 0);
//        }
//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            int servicioId = 2; // ID del servicio a obtener
//            servicioDto servicio = servicioBO.ObtenerPorId(servicioId);
//            Assert.IsNotNull(servicio);
//            Assert.AreEqual(servicioId, servicio.servicioId);
//        }
//    }
//}
