//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.RolClient;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class RolBOTest
//    {
//        private RolBO rolBO;

//        [TestInitialize]
//        public void Setup()
//        {
//            rolBO = new RolBO();
//        }

//        [TestMethod]
//        public void TestListarTodos()
//        {
//            // Listar todos los roles
//            List<rolDto> roles = rolBO.ListarTodos();

//            // Validaciones
//            Assert.IsNotNull(roles);
//            Assert.IsTrue(roles.Count > 0, "La lista de roles no debería estar vacía.");
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            // Crear DTO de rol
//            var nuevoRol = new rolDto
//            {
//                nombre = "ROL_PRUEBA",
//                activo = true
//            };

//            // Insertar
//            int resultado = rolBO.Insertar(nuevoRol);

//            // Validación
//            Console.WriteLine("ID del nuevo rol: " + resultado);
//            Assert.IsTrue(resultado > 0, "El rol debería haberse insertado correctamente.");
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener rol existente (ajusta el ID según tus datos)
//            int rolId = 2;
//            rolDto rolExistente = rolBO.ObtenerPorId(rolId);

//            Assert.IsNotNull(rolExistente, "El rol no debería ser nulo.");

//            // Cambios
//            rolExistente.nombre = "ROL_ACTUALIZADO";
//            rolExistente.activo = true;

//            // Modificar (parámetros sueltos, como en tu plantilla)
//            int resultado = rolBO.Modificar(
//                rolExistente.rolId,
//                rolExistente.nombre,
//                rolExistente.activo
//            );

//            Assert.IsTrue(resultado > 0, "El rol debería haberse modificado correctamente.");
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            // ID a eliminar (ajusta según tus datos)
//            int rolIdAEliminar = 3;

//            int resultado = rolBO.Eliminar(rolIdAEliminar);

//            Assert.IsTrue(resultado > 0, "El rol debería haberse eliminado correctamente.");
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // ID a consultar (ajusta según tus datos)
//            int rolId = 2;

//            rolDto rol = rolBO.ObtenerPorId(rolId);

//            Assert.IsNotNull(rol, "El rol no debería ser nulo.");
//            Assert.AreEqual(rolId, rol.rolId, "El ID del rol obtenido debería coincidir.");
//        }
//    }
//}
