//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.TipoServicioClient;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class TipoServicioBOTest
//    {
//        private TipoServicioBO tipoServicioBO;

//        [TestInitialize]
//        public void Setup()
//        {
//            tipoServicioBO = new TipoServicioBO();
//        }

//        [TestMethod]
//        public void TestListarTodos()
//        {
//            // Obtener la lista de todos los tipos de servicio
//            List<tipoServicioDto> tipoServicios = tipoServicioBO.ListarTodos();

//            // Verificar que la lista no esté vacía
//            Assert.IsNotNull(tipoServicios);
//            Assert.IsTrue(tipoServicios.Count > 0, "La lista de tipos de servicio no debería estar vacía.");
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            // Crear un nuevo tipo de servicio
//            tipoServicioDto nuevoTipoServicio = new tipoServicioDto
//            {
//                nombre = "Limpieza general",
//                descripcion = "Servicio de limpieza general para oficinas",
//                activo = true
//            };

//            // Insertar el tipo de servicio
//            int resultado = tipoServicioBO.Insertar(nuevoTipoServicio);

//            // Verificar que el ID del nuevo tipo de servicio sea mayor que 0 (se insertó correctamente)
//            Console.WriteLine("ID del nuevo tipo de servicio: " + resultado);
//            Assert.IsTrue(resultado > 0, "El tipo de servicio debería haberse insertado correctamente.");
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener un tipo de servicio existente
//            tipoServicioDto tipoServicioExistente = tipoServicioBO.ObtenerPorId(2);
//            tipoServicioExistente.nombre = "Limpieza profunda";

//            // Modificar el tipo de servicio
//            int resultado = tipoServicioBO.Modificar(
//                tipoServicioExistente.tipoServicioId,
//                tipoServicioExistente.nombre,
//                tipoServicioExistente.descripcion,
//                tipoServicioExistente.activo
//            );

//            // Verificar que la modificación fue exitosa
//            Assert.IsTrue(resultado > 0, "El tipo de servicio debería haberse modificado correctamente.");
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            // Definir el ID del tipo de servicio a eliminar
//            int tipoServicioIdAEliminar = 3;

//            // Eliminar el tipo de servicio
//            int resultado = tipoServicioBO.Eliminar(tipoServicioIdAEliminar);

//            // Verificar que la eliminación fue exitosa
//            Assert.IsTrue(resultado > 0, "El tipo de servicio debería haberse eliminado correctamente.");
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // Definir el ID del tipo de servicio a obtener
//            int tipoServicioId = 2;

//            // Obtener el tipo de servicio
//            tipoServicioDto tipoServicio = tipoServicioBO.ObtenerPorId(tipoServicioId);

//            // Verificar que el tipo de servicio no sea nulo y que el ID coincida
//            Assert.IsNotNull(tipoServicio, "El tipo de servicio no debería ser nulo.");
//            Assert.AreEqual(tipoServicioId, tipoServicio.tipoServicioId, "El ID del tipo de servicio obtenido debería coincidir.");
//        }
//    }
//}
