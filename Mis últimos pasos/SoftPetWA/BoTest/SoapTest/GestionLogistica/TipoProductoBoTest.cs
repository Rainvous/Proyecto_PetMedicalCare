//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.TipoProductoClient;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class TipoProductoBOTest
//    {
//        private TipoProductoBO tipoProductoBO;

//        [TestInitialize]
//        public void Setup()
//        {
//            tipoProductoBO = new TipoProductoBO();
//        }

//        [TestMethod]
//        public void TestListarTodos()
//        {
//            // Obtener la lista de todos los tipos de productos
//            List<tipoProductoDto> tipoProductos = tipoProductoBO.ListarTodos();

//            // Verificar que la lista no esté vacía
//            Assert.IsNotNull(tipoProductos);
//            Assert.IsTrue(tipoProductos.Count > 0, "La lista de tipos de productos no debería estar vacía.");
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            // Crear un nuevo tipo de producto
//            tipoProductoDto nuevoTipoProducto = new tipoProductoDto
//            {
//                nombre = "Producto tipo A",
//                descripcion = "Descripción del producto tipo A",
//                activo = true
//            };

//            // Insertar el tipo de producto
//            int resultado = tipoProductoBO.Insertar(nuevoTipoProducto);

//            // Verificar que el ID del nuevo tipo de producto sea mayor que 0 (se insertó correctamente)
//            Console.WriteLine("ID del nuevo tipo de producto: " + resultado);
//            Assert.IsTrue(resultado > 0, "El tipo de producto debería haberse insertado correctamente.");
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener un tipo de producto existente
//            tipoProductoDto tipoProductoExistente = tipoProductoBO.ObtenerPorId(2);
//            tipoProductoExistente.nombre = "Producto tipo A modificado";

//            // Modificar el tipo de producto
//            int resultado = tipoProductoBO.Modificar(
//                tipoProductoExistente.tipoProductoId,
//                tipoProductoExistente.nombre,
//                tipoProductoExistente.descripcion,
//                tipoProductoExistente.activo
//            );

//            // Verificar que la modificación fue exitosa
//            Assert.IsTrue(resultado > 0, "El tipo de producto debería haberse modificado correctamente.");
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            // Definir el ID del tipo de producto a eliminar
//            int tipoProductoIdAEliminar = 1;

//            // Eliminar el tipo de producto
//            int resultado = tipoProductoBO.Eliminar(tipoProductoIdAEliminar);

//            // Verificar que la eliminación fue exitosa
//            Assert.IsTrue(resultado > 0, "El tipo de producto debería haberse eliminado correctamente.");
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // Definir el ID del tipo de producto a obtener
//            int tipoProductoId = 2;

//            // Obtener el tipo de producto
//            tipoProductoDto tipoProducto = tipoProductoBO.ObtenerPorId(tipoProductoId);

//            // Verificar que el tipo de producto no sea nulo y que el ID coincida
//            Assert.IsNotNull(tipoProducto, "El tipo de producto no debería ser nulo.");
//            Assert.AreEqual(tipoProductoId, tipoProducto.tipoProductoId, "El ID del tipo de producto obtenido debería coincidir.");
//        }
//    }
//}
