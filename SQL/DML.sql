-- ===============================================
-- INSERTS DE PRUEBA PARA TODAS LAS TABLAS
-- ===============================================
use petmedicalcare;
-- ===============================================
-- DML DE PRUEBA (MySQL)
-- ===============================================
-- ===============================================
-- DML DE PRUEBA (MySQL)
-- ===============================================

USE petmedicalcare;

-- Opcional: si usas triggers de auditoría
SET @app_user := 'admin';

-- ---------------------------
-- 1) ROLES
-- ---------------------------
INSERT INTO ROLES (NOMBRE, ACTIVO) VALUES ('Administrador',1);
SET @rol_admin_id := LAST_INSERT_ID();

INSERT INTO ROLES (NOMBRE, ACTIVO) VALUES ('Usuario',1);
SET @rol_user_id := LAST_INSERT_ID();

INSERT INTO ROLES (NOMBRE, ACTIVO) VALUES ('Veterinario',1);
SET @rol_vet_id := LAST_INSERT_ID();

INSERT INTO ROLES (NOMBRE, ACTIVO) VALUES ('Caja',1);
SET @rol_caja_id := LAST_INSERT_ID();

-- ---------------------------
-- 2) USUARIOS
-- ---------------------------
INSERT INTO USUARIOS (USENAME, PASSWORD, CORREO, ACTIVO)
VALUES ('admin', 'admin123', 'admin@demo.com', 1);
SET @user_admin_id := LAST_INSERT_ID();

INSERT INTO USUARIOS (USENAME, PASSWORD, CORREO, ACTIVO)
VALUES ('vet01', 'vet123', 'vet01@demo.com', 1);
SET @user_vet_id := LAST_INSERT_ID();

-- ---------------------------
-- 3) ROLES_USUARIO (asignaciones)
-- ---------------------------
INSERT INTO ROLES_USUARIO (ROL_ID, USUARIO_ID, ACTIVO)
VALUES (@rol_admin_id, @user_admin_id, 1);

INSERT INTO ROLES_USUARIO (ROL_ID, USUARIO_ID, ACTIVO)
VALUES (@rol_vet_id, @user_vet_id, 1);

-- ---------------------------
-- 4) PERSONAS (relacionadas a USUARIOS)
-- ---------------------------
INSERT INTO PERSONAS (NOMBRE, DIRECCION, TELEFONO, SEXO, ACTIVO, TIPO_DOCUMENTO, NRO_DOCUMENTO, RUC, USUARIO_ID)
VALUES ('Juan Pérez', 'Av. Siempre Viva 123', '999111222', 'M', 1, 'DNI', 12345678, 201122334, @user_admin_id);
SET @persona_juan_id := LAST_INSERT_ID();

INSERT INTO PERSONAS (NOMBRE, DIRECCION, TELEFONO, SEXO, ACTIVO, TIPO_DOCUMENTO, NRO_DOCUMENTO, RUC, USUARIO_ID)
VALUES ('Ana Torres', 'Calle Falsa 456', '988777555', 'F', 1, 'DNI', 87654321, 201122335, @user_vet_id);
SET @persona_ana_id := LAST_INSERT_ID();

-- ---------------------------
-- 5) MASCOTAS (de personas)
-- ---------------------------
INSERT INTO MASCOTAS (NOMBRE, ESPECIE, SEXO, RAZA, COLOR, ACTIVO, PERSONA_ID)
VALUES ('Firulais', 'Perro', 'M', 'Labrador', 'Marrón', 1, @persona_juan_id);
SET @mascota_firu_id := LAST_INSERT_ID();

INSERT INTO MASCOTAS (NOMBRE, ESPECIE, SEXO, RAZA, COLOR, ACTIVO, PERSONA_ID)
VALUES ('Mishi', 'Gato', 'F', 'Siamés', 'Blanco', 1, @persona_ana_id);
SET @mascota_mishi_id := LAST_INSERT_ID();

-- ---------------------------
-- 6) VETERINARIOS (persona veterinaria)
-- ---------------------------
INSERT INTO VETERINARIOS (ESPECIALIZACION, FECHA_DE_CONTRATACION, ESTADO, ACTIVO, PERSONA_ID)
VALUES ('Cirugía', '2022-03-15', 'Activo', 1, @persona_ana_id);
SET @vet1_id := LAST_INSERT_ID();

-- ---------------------------
-- 7) TIPOS_SERVICIO
-- ---------------------------
INSERT INTO TIPOS_SERVICIO (NOMBRE, DESCRIPCION, ACTIVO)
VALUES ('Consulta', 'Consulta general', 1);
SET @tserv_consulta_id := LAST_INSERT_ID();

INSERT INTO TIPOS_SERVICIO (NOMBRE, DESCRIPCION, ACTIVO)
VALUES ('Vacunación', 'Aplicación de vacunas', 1);
SET @tserv_vacuna_id := LAST_INSERT_ID();

-- ---------------------------
-- 8) SERVICIOS
-- ---------------------------
INSERT INTO SERVICIOS (NOMBRE, COSTO, ESTADO, DESCRIPCION, ACTIVO, TIPO_SERVICIO_ID)
VALUES ('Consulta básica', 50.00, 'Disponible', 'Consulta general veterinaria', 1, @tserv_consulta_id);
SET @serv_consulta_id := LAST_INSERT_ID();

INSERT INTO SERVICIOS (NOMBRE, COSTO, ESTADO, DESCRIPCION, ACTIVO, TIPO_SERVICIO_ID)
VALUES ('Vacuna Rabia', 80.00, 'Disponible', 'Vacuna contra la rabia', 1, @tserv_vacuna_id);
SET @serv_vacuna_id := LAST_INSERT_ID();

-- ---------------------------
-- 9) TIPOS_PRODUCTO
-- ---------------------------
INSERT INTO TIPOS_PRODUCTO (NOMBRE, DESCRIPCION, ACTIVO)
VALUES ('Medicamento', 'Medicamentos veterinarios', 1);
SET @tprod_medic_id := LAST_INSERT_ID();

INSERT INTO TIPOS_PRODUCTO (NOMBRE, DESCRIPCION, ACTIVO)
VALUES ('Accesorio', 'Accesorios para mascotas', 1);
SET @tprod_acces_id := LAST_INSERT_ID();

-- ---------------------------
-- 10) PRODUCTOS
-- (FECHA_CREACION/MODIFICACION son DATE en tu DDL; si tienes triggers, no hace falta enviarlas)
-- ---------------------------
INSERT INTO PRODUCTOS (NOMBRE, PRESENTACION, PRECIO_UNITARIO, ACTIVO, TIPO_PRODUCTO_ID)
VALUES ('Antibiótico X', 'Caja 10 tabletas', 25.00, 1, @tprod_medic_id);
SET @prod_antib_id := LAST_INSERT_ID();

INSERT INTO PRODUCTOS (NOMBRE, PRESENTACION, PRECIO_UNITARIO, ACTIVO, TIPO_PRODUCTO_ID)
VALUES ('Collar antipulgas', 'Talla M', 40.00, 1, @tprod_acces_id);
SET @prod_collar_id := LAST_INSERT_ID();

-- ---------------------------
-- 11) CITAS_ATENCION
-- ---------------------------
INSERT INTO CITAS_ATENCION (OBSERVACION, FECHA_HORA_INICIO, FECHA_REGISTRO, FECHA_HORA_FIN, MONTO, ACTIVO, PESO_MASCOTA, VETERINARIO_ID, MASCOTA_ID)
VALUES ('Control anual', NOW(), CURDATE(), DATE_ADD(NOW(), INTERVAL 45 MINUTE), 50.00, 1, 12.5, @vet1_id, @mascota_firu_id);
SET @cita1_id := LAST_INSERT_ID();

-- ---------------------------
-- 12) DETALLES_SERVICIO (detalle de la cita)
-- ---------------------------
INSERT INTO DETALLES_SERVICIO (DESCRIPCION, COSTO, ACTIVO, SERVICIO_ID, CITA_ID)
VALUES ('Consulta general realizada', 50.00, 1, @serv_consulta_id, @cita1_id);
SET @det_serv1_id := LAST_INSERT_ID();

-- ---------------------------
-- 13) RECETAS_MEDICA
-- ---------------------------
INSERT INTO RECETAS_MEDICA (DIAGNOSTICO, ACTIVO, CITA_ID)
VALUES ('Infección leve', 1, @cita1_id);
SET @receta1_id := LAST_INSERT_ID();

-- ---------------------------
-- 14) DETALLES_RECETA
-- ---------------------------
INSERT INTO DETALLES_RECETA (CANTIDAD, DESCRIPCION_MEDICAMENTO, INDICACION, RECETA_MEDICA_ID, ACTIVO)
VALUES (1, 'Antibiótico X', 'Tomar 1 tableta cada 12h por 5 días', @receta1_id, 1);
SET @det_receta1_id := LAST_INSERT_ID();

-- ---------------------------
-- 15) TIPOS_DOCUMENTO
-- ---------------------------
INSERT INTO TIPOS_DOCUMENTO (NOMBRE, ACTIVO)
VALUES ('Factura', 1);
SET @tdoc_factura_id := LAST_INSERT_ID();

INSERT INTO TIPOS_DOCUMENTO (NOMBRE, ACTIVO)
VALUES ('Boleta', 1);
SET @tdoc_boleta_id := LAST_INSERT_ID();

-- ---------------------------
-- 16) DOCUMENTOS_DE_PAGO
-- (ejemplo para la persona Juan, con IGV 18%)
-- ---------------------------
INSERT INTO DOCUMENTOS_DE_PAGO
(SERIE, NUMERO, TASA_IGV, FECHA_EMISION, METODO_DE_PAGO, ESTADO, SUBTOTAL_SIN_IGV, IGV_TOTAL, TOTAL, ACTIVO, TIPO_DOCUMENTO_ID, PERSONA_ID)
VALUES
('F001', '000001', 0.18, CURDATE(), 'Efectivo', 'Emitido', 50.00, 9.00, 59.00, 1, @tdoc_factura_id, @persona_juan_id);
SET @doc_pago1_id := LAST_INSERT_ID();

-- ---------------------------
-- 17) DETALLES_DOCUMENTO_DE_PAGO
-- (una línea por servicio y otra por producto)
-- ---------------------------
-- Línea por servicio "Consulta básica"
INSERT INTO DETALLES_DOCUMENTO_DE_PAGO
(NRO_ITEM, DESCRIPCION, CANTIDAD, PRECIO_UNITARIO_SIN_IGV, VALOR_VENTA, IGV_ITEM, IMPORTE_TOTAL,
 DOCUMENTO_DE_PAGO_ID, SERVICIO_ID, PRODUCTO_ID)
VALUES
(1, 'Consulta básica', 1, 50.00, 50.00, 9.00, 59.00, @doc_pago1_id, @serv_consulta_id, NULL);

-- Otra factura para un producto (opcional)
INSERT INTO DOCUMENTOS_DE_PAGO
(SERIE, NUMERO, TASA_IGV, FECHA_EMISION, METODO_DE_PAGO, ESTADO, SUBTOTAL_SIN_IGV, IGV_TOTAL, TOTAL, ACTIVO, TIPO_DOCUMENTO_ID, PERSONA_ID)
VALUES
('B001', '000001', 0.18, CURDATE(), 'Tarjeta', 'Emitido', 40.00, 7.20, 47.20, 1, @tdoc_boleta_id, @persona_juan_id);
SET @doc_pago2_id := LAST_INSERT_ID();

INSERT INTO DETALLES_DOCUMENTO_DE_PAGO
(NRO_ITEM, DESCRIPCION, CANTIDAD, PRECIO_UNITARIO_SIN_IGV, VALOR_VENTA, IGV_ITEM, IMPORTE_TOTAL,
 DOCUMENTO_DE_PAGO_ID, SERVICIO_ID, PRODUCTO_ID)
VALUES
(1, 'Collar antipulgas', 1, 40.00, 40.00, 7.20, 47.20, @doc_pago2_id, NULL, @prod_collar_id);

-- ===============================================
-- Verificaciones rápidas (opcionales)
-- ===============================================
-- SELECT * FROM ROLES;
-- SELECT * FROM USUARIOS;
-- SELECT u.USENAME, r.NOMBRE AS Rol
-- FROM USUARIOS u JOIN ROLES_USUARIO ru ON u.USUARIO_ID=ru.USUARIO_ID
-- JOIN ROLES r ON ru.ROL_ID=r.ROL_ID;

-- SELECT p.NOMBRE AS Dueno, m.NOMBRE AS Mascota FROM PERSONAS p JOIN MASCOTAS m ON p.PERSONA_ID=m.PERSONA_ID;

-- SELECT c.CITA_ID, m.NOMBRE AS Mascota, v.ESPECIALIZACION, c.MONTO
-- FROM CITAS_ATENCION c JOIN MASCOTAS m ON c.MASCOTA_ID=m.MASCOTA_ID
-- JOIN VETERINARIOS v ON c.VETERINARIO_ID=v.VETERINARIO_ID;

-- SELECT d.SERIE, d.NUMERO, dd.NRO_ITEM, dd.DESCRIPCION, dd.IMPORTE_TOTAL
-- FROM DOCUMENTOS_DE_PAGO d JOIN DETALLES_DOCUMENTO_DE_PAGO dd
--   ON d.DOCUMENTO_DE_PAGO_ID=dd.DOCUMENTO_DE_PAGO_ID
-- ORDER BY d.DOCUMENTO_DE_PAGO_ID, dd.NRO_ITEM;

