//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using SoftPetBussiness.RolUsuarioClient;
//using System;
//using System.Collections.Generic;

//namespace SoftPetBusiness.Tests
//{
//    [TestClass]
//    public class RolUsuarioBOTest
//    {
//        private RolUsuarioBO rolUsuarioBO;

//        [TestInitialize]
//        public void Setup()
//        {
//            rolUsuarioBO = new RolUsuarioBO();
//        }

//        [TestMethod]
//        public void TestListarTodos()
//        {
//            // Listar todas las relaciones rol-usuario
//            List<rolUsuarioDto> relaciones = rolUsuarioBO.ListarTodos();

//            // Validaciones
//            Assert.IsNotNull(relaciones);
//            Assert.IsTrue(relaciones.Count > 0, "La lista de relaciones Rol-Usuario no debería estar vacía.");
//        }

//        [TestMethod]
//        public void Insertar()
//        {
//            // Crear DTO
//            var nuevaRelacion = new rolUsuarioDto
//            {
//                rol = new rolDto { rolId = 1 },           // ajusta IDs existentes en tu BD
//                usuario = new usuarioDto { usuarioId = 1 },
//                activo = true
//            };

//            // Insertar
//            int resultado = rolUsuarioBO.Insertar(nuevaRelacion);

//            Console.WriteLine("ID de la nueva relación Rol-Usuario: " + resultado);
//            Assert.IsTrue(resultado > 0, "La relación Rol-Usuario debería haberse insertado correctamente.");
//        }

//        [TestMethod]
//        public void Modificar()
//        {
//            // Obtener relación existente (ajusta el ID según tus datos)
//            int rolUsuarioId = 2;
//            rolUsuarioDto existente = rolUsuarioBO.ObtenerPorId(rolUsuarioId);

//            Assert.IsNotNull(existente, "La relación Rol-Usuario no debería ser nula.");
//            Console.WriteLine("Id a modificar: " + rolUsuarioId);
//            Console.WriteLine("Relación existente antes de modificar: " + existente.rolUsuarioId);
//            // Cambiar estado
//            existente.activo = !existente.activo;

//            // Modificar (parámetros sueltos, como en tu plantilla)
//            int resultado = rolUsuarioBO.Modificar(
//                existente.rolUsuarioId,
//                existente.rol.rolId,
//                existente.usuario.usuarioId,
//                existente.activo
//            );

//            Assert.IsTrue(resultado > 0, "La relación Rol-Usuario debería haberse modificado correctamente.");
//        }

//        [TestMethod]
//        public void Eliminar()
//        {
//            // ID a eliminar (ajusta según tus datos)
//            int rolUsuarioIdAEliminar = 3;

//            int resultado = rolUsuarioBO.Eliminar(rolUsuarioIdAEliminar);

//            Assert.IsTrue(resultado > 0, "La relación Rol-Usuario debería haberse eliminado correctamente.");
//        }

//        [TestMethod]
//        public void ObtenerPorId()
//        {
//            // ID a consultar (ajusta según tus datos)
//            int rolUsuarioId = 2;

//            rolUsuarioDto relacion = rolUsuarioBO.ObtenerPorId(rolUsuarioId);

//            Assert.IsNotNull(relacion, "La relación Rol-Usuario no debería ser nula.");
//            Assert.AreEqual(rolUsuarioId, relacion.rolUsuarioId, "El ID de la relación obtenida debería coincidir.");
//        }
//    }
//}
