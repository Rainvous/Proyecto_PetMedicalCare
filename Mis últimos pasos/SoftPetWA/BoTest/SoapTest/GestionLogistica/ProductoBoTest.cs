//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.ProductoClient;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class ProductoBOTest
//    {
//        private ProductoBO productoBO;
//        private int id;

//        [TestInitialize]
//        public void Setup()
//        {
//            productoBO = new ProductoBO();
//        }

//        [TestMethod]
//        public void TestListarGenericos()
//        {
//            // Obtener la lista de todos los productos
//            List<productoDto> productos = productoBO.ListarTodos();

//            // Verificar que la lista no esté vacía
//            Assert.IsNotNull(productos);
//            Assert.IsTrue(productos.Count > 0, "La lista de productos no debería estar vacía.");

//            List<productoDto> productosActivos = productoBO.ListarActivos();
//            Assert.IsNotNull(productosActivos,"No se listo bien los activos");
//            Assert.IsTrue(productosActivos.Count > 0, "La lista de productos activos no debería estar vacía.");

//            List<productoDto> productosPorTipo = productoBO.ListarPorTipo("MEDICAMENTO");
//            Assert.IsNotNull(productosPorTipo, "No se listo bien los por tipo");
//            Assert.IsTrue(productosPorTipo.Count > 0, "La lista de productos por tipo no debería estar vacía.");
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            // Crear un nuevo producto
//            productoDto nuevoProducto = new productoDto
//            {
//                tipoProducto = new tipoProductoDto { tipoProductoId = 1 },
//                nombre = "Shampoo",
//                presentacion = "Botella de 500ml",
//                precioUnitario = 15.5,
//                stock = 100,
//                activo = true
//            };

//            // Insertar el producto
//            int resultado = productoBO.Insertar(nuevoProducto);

//            // Verificar que el ID del nuevo producto sea mayor que 0 (se insertó correctamente)
//            Console.WriteLine("ID del nuevo producto: " + resultado);
//            Assert.IsTrue(resultado > 0, "El producto debería haberse insertado correctamente.");
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener un producto existente
//            productoDto productoExistente = productoBO.ObtenerPorId(2);
//            productoExistente.nombre = "Shampoo actualizado";

//            // Modificar el producto
//            int resultado = productoBO.Modificar(
//                productoExistente.productoId,
//                productoExistente.tipoProducto.tipoProductoId,
//                productoExistente.nombre,
//                productoExistente.presentacion,
//                productoExistente.precioUnitario,
//                productoExistente.stock,
//                productoExistente.activo
//            );

//            // Verificar que la modificación fue exitosa
//            Assert.IsTrue(resultado > 0, "El producto debería haberse modificado correctamente.");
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            // Definir el ID del producto a eliminar
//            int productoIdAEliminar = 1;

//            // Eliminar el producto
//            int resultado = productoBO.Eliminar(productoIdAEliminar);

//            // Verificar que la eliminación fue exitosa
//            Assert.IsTrue(resultado > 0, "El producto debería haberse eliminado correctamente.");
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // Definir el ID del producto a obtener
//            int productoId = 2;

//            // Obtener el producto
//            productoDto producto = productoBO.ObtenerPorId(productoId);

//            // Verificar que el producto no sea nulo y que el ID coincida
//            Assert.IsNotNull(producto, "El producto no debería ser nulo.");
//            Assert.AreEqual(productoId, producto.productoId, "El ID del producto obtenido debería coincidir.");
//        }
//    }
//}
