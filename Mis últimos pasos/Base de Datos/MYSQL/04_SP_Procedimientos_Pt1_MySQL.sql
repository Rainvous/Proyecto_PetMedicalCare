USE petmedicalcare;

-- =============================================
-- 1. BUSCAR CLIENTES (Avanzada)
-- =============================================
DROP PROCEDURE IF EXISTS sp_buscar_clientes_avanzada;
DELIMITER $$
CREATE PROCEDURE sp_buscar_clientes_avanzada(
    IN p_nombre VARCHAR(120),
    IN p_nro_documento VARCHAR(20),
    IN p_ruc VARCHAR(11),
    IN p_activo TINYINT
)
BEGIN
    SELECT 
        p.PERSONA_ID,
        p.NOMBRE,
        p.DIRECCION,
        p.TELEFONO,
        p.SEXO,
        p.NRO_DOCUMENTO,
        p.RUC,
        p.TIPO_DOCUMENTO,
        p.ACTIVO as ACTIVO_PERS,
        u.USUARIO_ID,
        u.USERNAME,
        u.CORREO
    FROM PERSONAS p
    INNER JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID
    WHERE 
        (p_nombre IS NULL OR p_nombre = '' OR p.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_nro_documento IS NULL OR p_nro_documento = '' OR p.NRO_DOCUMENTO LIKE CONCAT('%', p_nro_documento, '%'))
        AND (p_ruc IS NULL OR p_ruc = '' OR p.RUC LIKE CONCAT('%', p_ruc, '%'))
        AND (p_activo IS NULL OR p.ACTIVO = p_activo)
        -- Excluir Staff (ADMIN, VET, RECEPCION)
        AND NOT EXISTS (
            SELECT 1
            FROM ROLES_USUARIO AS RU
            JOIN ROLES AS R ON RU.ROL_ID = R.ROL_ID
            WHERE RU.USUARIO_ID = p.USUARIO_ID
            AND R.NOMBRE IN ('ADMIN', 'VET', 'RECEPCION', 'GUEST')
        )
    ORDER BY p.NOMBRE ASC;
END$$
DELIMITER ;

-- =============================================
-- 2. BUSCAR PERSONAS (General)
-- =============================================
DROP PROCEDURE IF EXISTS sp_buscar_personas_avanzada;
DELIMITER $$
CREATE PROCEDURE sp_buscar_personas_avanzada(
    IN p_nombre         VARCHAR(120),
    IN p_nro_documento  VARCHAR(20),
    IN p_ruc            VARCHAR(11),
    IN p_telefono       VARCHAR(20),
    IN p_activo         VARCHAR(2)
)
BEGIN
    SET p_nombre        = NULLIF(TRIM(p_nombre), '');
    SET p_telefono      = NULLIF(TRIM(p_telefono), '');
    SET p_nro_documento = TRIM(p_nro_documento);
    SET p_ruc           = TRIM(p_ruc);
    SET p_activo        = NULLIF(TRIM(p_activo), '');

    IF p_nro_documento IS NULL OR p_nro_documento = '' OR p_nro_documento = '0' THEN SET p_nro_documento = NULL; END IF;
    IF p_ruc IS NULL OR p_ruc = '' OR p_ruc = '0' THEN SET p_ruc = NULL; END IF;

    SELECT
        PERSONA_ID, NOMBRE, DIRECCION, TELEFONO, SEXO, ACTIVO, TIPO_DOCUMENTO, NRO_DOCUMENTO, RUC, USUARIO_ID
    FROM PERSONAS
    WHERE
        (p_nombre        IS NULL OR NOMBRE        LIKE CONCAT('%', p_nombre, '%'))
        AND (p_nro_documento IS NULL OR NRO_DOCUMENTO LIKE CONCAT('%', p_nro_documento, '%'))
        AND (p_ruc           IS NULL OR RUC           LIKE CONCAT('%', p_ruc, '%'))
        AND (p_telefono      IS NULL OR TELEFONO      LIKE CONCAT('%', p_telefono, '%'))
        AND (p_activo        IS NULL OR ACTIVO = p_activo)
    ORDER BY NOMBRE ASC, PERSONA_ID ASC;
END$$
DELIMITER ;

-- =============================================
-- 3. BUSCAR VETERINARIOS
-- =============================================
DROP PROCEDURE IF EXISTS sp_buscar_veterinarios_avanzada;
DELIMITER $$
CREATE PROCEDURE sp_buscar_veterinarios_avanzada(
    IN p_especialidad VARCHAR(50),
    IN p_nombre VARCHAR(120),
    IN p_nro_documento VARCHAR(20),
    IN p_activo TINYINT
)
BEGIN
    SELECT 
        v.VETERINARIO_ID, v.FECHA_DE_CONTRATACION, v.ESTADO as ESTADO_VET, v.ESPECIALIDAD, v.ACTIVO as ACTIVO_VET,
        p.PERSONA_ID, p.NOMBRE, p.DIRECCION, p.TELEFONO, p.SEXO, p.NRO_DOCUMENTO, p.RUC, p.TIPO_DOCUMENTO, p.ACTIVO as ACTIVO_PERS,
        u.USUARIO_ID, u.USERNAME, u.CORREO
    FROM VETERINARIOS v
    JOIN PERSONAS p ON v.PERSONA_ID = p.PERSONA_ID
    JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID
    WHERE 
        (p_especialidad IS NULL OR p_especialidad = '' OR v.ESPECIALIDAD LIKE CONCAT('%', p_especialidad, '%'))
        AND (p_nombre IS NULL OR p_nombre = '' OR p.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_nro_documento IS NULL OR p_nro_documento = '' OR p.NRO_DOCUMENTO LIKE CONCAT('%', p_nro_documento, '%'))
        AND (p_activo IS NULL OR v.ACTIVO = p_activo)
    ORDER BY p.NOMBRE ASC;
END$$
DELIMITER ;

-- =============================================
-- 4. BUSCAR MASCOTAS
-- =============================================
DROP PROCEDURE IF EXISTS sp_buscar_mascotas_avanzada;
DELIMITER $$
CREATE PROCEDURE sp_buscar_mascotas_avanzada(
    IN p_nombre VARCHAR(100),
    IN p_especie VARCHAR(40),
    IN p_nombre_duenio VARCHAR(120),
    IN p_activo TINYINT
)
BEGIN
    SELECT 
        m.MASCOTA_ID, m.NOMBRE, m.ESPECIE, m.SEXO, m.RAZA, m.COLOR, m.FECHA_DEFUNCION, m.ACTIVO,
        p.PERSONA_ID, p.NOMBRE as NOMBRE_DUENIO, p.TELEFONO as TELEFONO_DUENIO
    FROM MASCOTAS m
    INNER JOIN PERSONAS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (p_nombre IS NULL OR p_nombre = '' OR m.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_especie IS NULL OR p_especie = '' OR m.ESPECIE = p_especie)
        AND (p_nombre_duenio IS NULL OR p_nombre_duenio = '' OR p.NOMBRE LIKE CONCAT('%', p_nombre_duenio, '%'))
        AND (p_activo IS NULL OR m.ACTIVO = p_activo)
    ORDER BY m.NOMBRE ASC;
END$$
DELIMITER ;

-- =============================================
-- 5. BUSCAR PRODUCTOS
-- =============================================
USE petmedicalcare;

DROP PROCEDURE IF EXISTS sp_buscar_productos_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_productos_avanzada(
    IN p_nombre VARCHAR(120),
    IN p_rango_precio_id VARCHAR(10), 
    IN p_activo VARCHAR(10),
    IN p_tipo_producto_id INT -- Nuevo parámetro
)
BEGIN
    -- Normalización
    SET p_nombre = NULLIF(TRIM(p_nombre), '');
    SET p_rango_precio_id = NULLIF(TRIM(p_rango_precio_id), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');
    
    -- Lógica: 0 equivale a "Todos" (NULL para ignorar filtro)
    IF p_tipo_producto_id = 0 THEN 
        SET p_tipo_producto_id = NULL; 
    END IF;

    SELECT 
        p.PRODUCTO_ID,
        p.TIPO_PRODUCTO_ID,
        tp.NOMBRE as NOMBRE_TIPO,
        tp.DESCRIPCION as DESC_TIPO,
        p.NOMBRE,
        p.PRESENTACION,
        p.PRECIO_UNITARIO,
        p.STOCK,
        p.ACTIVO
    FROM PRODUCTOS AS p
    INNER JOIN TIPOS_PRODUCTO AS tp ON p.TIPO_PRODUCTO_ID = tp.TIPO_PRODUCTO_ID
    WHERE 
        -- Filtro Nombre
        (p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        -- Filtro Activo
        AND (p_activo IS NULL OR p.ACTIVO = p_activo)
        -- Filtro Tipo Producto (0 trajo NULL, por tanto ignora; ID trajo valor, por tanto filtra)
        AND (p_tipo_producto_id IS NULL OR p.TIPO_PRODUCTO_ID = p_tipo_producto_id)
        -- Filtro Rango Precio
        AND (
            p_rango_precio_id IS NULL
            OR (p_rango_precio_id = '1' AND p.PRECIO_UNITARIO BETWEEN 0 AND 50)
            OR (p_rango_precio_id = '2' AND p.PRECIO_UNITARIO BETWEEN 51 AND 150)
            OR (p_rango_precio_id = '3' AND p.PRECIO_UNITARIO > 150)
        )
    ORDER BY p.NOMBRE ASC;
END$$
DELIMITER ;

-- =============================================
-- 6. BUSCAR SERVICIOS (Versión 1)
-- =============================================
DROP PROCEDURE IF EXISTS sp_buscar_servicios_avanzada;
DELIMITER $$
CREATE PROCEDURE sp_buscar_servicios_avanzada(
    IN p_nombre VARCHAR(100),
    IN p_rango_precio_id VARCHAR(10),
    IN p_activo VARCHAR(10)
)
BEGIN
    SET p_nombre = NULLIF(TRIM(p_nombre), '');
    SET p_rango_precio_id = NULLIF(TRIM(p_rango_precio_id), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');

    SELECT
		s.SERVICIO_ID, s.TIPO_SERVICIO_ID, s.NOMBRE, s.DESCRIPCION, s.COSTO, s.ESTADO, s.ACTIVO,
        s.USUARIO_CREADOR, s.FECHA_CREACION, s.USUARIO_MODIFICADOR, s.FECHA_MODIFICACION
    FROM SERVICIOS AS s
    JOIN TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        (p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_activo IS NULL OR s.ACTIVO = p_activo)
        AND (
            p_rango_precio_id IS NULL
            OR (p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY s.COSTO ASC, s.NOMBRE ASC;
END$$
DELIMITER ;

-- =============================================
-- 7. BUSCAR SERVICIOS (Versión 2 - con datos tipo)
-- =============================================
DROP PROCEDURE IF EXISTS sp_buscar_servicios_avanzada_2;
DELIMITER $$
CREATE PROCEDURE sp_buscar_servicios_avanzada_2(
    IN p_nombre VARCHAR(100),
    IN p_rango_precio_id VARCHAR(10),
    IN p_activo VARCHAR(10)
)
BEGIN
    SET p_nombre = NULLIF(TRIM(p_nombre), '');
    SET p_rango_precio_id = NULLIF(TRIM(p_rango_precio_id), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');

    SELECT
		s.SERVICIO_ID, s.TIPO_SERVICIO_ID, s.NOMBRE, s.DESCRIPCION, s.COSTO, s.ESTADO, s.ACTIVO,
        s.USUARIO_CREADOR, s.FECHA_CREACION, s.USUARIO_MODIFICADOR, s.FECHA_MODIFICACION,
        ts.TIPO_SERVICIO_ID as TIPO_SERVICIO_ID_T, -- Alias para evitar conflicto
		ts.NOMBRE as NOMBRE_TIPO_SERVICIO,
        ts.DESCRIPCION as DESCRIPCION_TIPO_SERVICIO,
        ts.ACTIVO as ACTIVO_TIPO_SERVICIO
    FROM SERVICIOS AS s
    JOIN TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        (p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_activo IS NULL OR s.ACTIVO = p_activo)
        AND (
            p_rango_precio_id IS NULL
            OR (p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY s.COSTO ASC, s.NOMBRE ASC;
END$$
DELIMITER ;

-- =============================================
-- 8. BUSCAR SERVICIOS (Versión 3 - Filtro por Tipo ID)
-- =============================================
DROP PROCEDURE IF EXISTS sp_buscar_servicios_avanzada_v3;
DELIMITER $$
CREATE PROCEDURE sp_buscar_servicios_avanzada_v3(
    IN p_nombre VARCHAR(100),
    IN p_rango_precio_id VARCHAR(10), 
    IN p_activo VARCHAR(10),
    IN p_tipo_servicio_id INT
)
BEGIN
    SET p_nombre = NULLIF(TRIM(p_nombre), '');
    SET p_rango_precio_id = NULLIF(TRIM(p_rango_precio_id), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');
    IF p_tipo_servicio_id = 0 THEN SET p_tipo_servicio_id = NULL; END IF;

    SELECT
        s.SERVICIO_ID, s.TIPO_SERVICIO_ID, s.NOMBRE, s.DESCRIPCION, s.COSTO, s.ESTADO, s.ACTIVO,
        ts.NOMBRE as NOMBRE_TIPO_SERVICIO,
        ts.DESCRIPCION as DESCRIPCION_TIPO_SERVICIO,
        ts.ACTIVO as ACTIVO_TIPO_SERVICIO
    FROM SERVICIOS AS s
    JOIN TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        (p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (p_activo IS NULL OR s.ACTIVO = p_activo)
        AND (p_tipo_servicio_id IS NULL OR s.TIPO_SERVICIO_ID = p_tipo_servicio_id)
        AND (
            p_rango_precio_id IS NULL
            OR (p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY s.NOMBRE ASC;
END$$
DELIMITER ;

-- =============================================
-- 9. BUSCAR RECETAS
-- =============================================
DROP PROCEDURE IF EXISTS sp_buscar_recetas_avanzada;
DELIMITER $$
CREATE PROCEDURE sp_buscar_recetas_avanzada(
    IN p_nombre_mascota VARCHAR(100),
    IN p_nombre_duenio VARCHAR(100),
    IN p_fecha DATE,
    IN p_activo VARCHAR(10)
)
BEGIN
    SET p_nombre_mascota = NULLIF(TRIM(p_nombre_mascota), '');
    SET p_nombre_duenio = NULLIF(TRIM(p_nombre_duenio), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');

    SELECT 
        r.RECETA_MEDICA_ID, r.CITA_ID, r.FECHA_EMISION, r.VIGENCIA_HASTA, r.DIAGNOSTICO, r.OBSERVACIONES, r.ACTIVO,
        m.NOMBRE AS MASCOTA_NOMBRE,
        CONCAT(m.ESPECIE, ' - ', m.RAZA) AS MASCOTA_DETALLE,
        p.NOMBRE AS DUENIO_NOMBRE,
        c.FECHA_HORA_INICIO AS FECHA_CITA
    FROM RECETAS_MEDICAS r
    JOIN CITAS_ATENCION c ON r.CITA_ID = c.CITA_ID
    JOIN MASCOTAS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN PERSONAS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (p_nombre_mascota IS NULL OR m.NOMBRE LIKE CONCAT('%', p_nombre_mascota, '%'))
        AND (p_nombre_duenio IS NULL OR p.NOMBRE LIKE CONCAT('%', p_nombre_duenio, '%'))
        AND (p_fecha IS NULL OR r.FECHA_EMISION = p_fecha)
        AND (p_activo IS NULL OR r.ACTIVO = p_activo)
    ORDER BY r.FECHA_EMISION DESC;
END$$
DELIMITER ;

-- =============================================
-- 10. GENERAR CORRELATIVO (BOLETA/FACTURA)
-- =============================================
DROP PROCEDURE IF EXISTS sp_generar_correlativo_pago;
DELIMITER $$
CREATE PROCEDURE sp_generar_correlativo_pago(
    IN p_tipo_codigo CHAR(1),
    OUT p_serie_generada VARCHAR(10),
    OUT p_numero_generado VARCHAR(12)
)
main_block: BEGIN
    DECLARE v_tipo_documento VARCHAR(50);
    DECLARE v_ultima_serie VARCHAR(10);
    DECLARE v_ultimo_numero VARCHAR(12);
    DECLARE v_num_entero BIGINT;
    DECLARE v_serie_entero INT;

    IF p_tipo_codigo = 'B' THEN
        SET v_tipo_documento = 'BOLETA';
    ELSEIF p_tipo_codigo = 'F' THEN
        SET v_tipo_documento = 'FACTURA';
    ELSE
        SET p_serie_generada = NULL;
        SET p_numero_generado = NULL;
        LEAVE main_block;
    END IF;

    SELECT SERIE, NUMERO 
    INTO v_ultima_serie, v_ultimo_numero
    FROM DOCUMENTOS_DE_PAGO
    WHERE TIPO_DOCUMENTO = v_tipo_documento
      AND SERIE LIKE CONCAT(p_tipo_codigo, '%') 
    ORDER BY SERIE DESC, NUMERO DESC
    LIMIT 1;

    IF v_ultima_serie IS NULL THEN
        SET p_serie_generada = CONCAT(p_tipo_codigo, '001');
        SET p_numero_generado = '00000001';
    ELSE
        SET v_num_entero = CAST(v_ultimo_numero AS UNSIGNED) + 1;

        IF v_num_entero > 99999999 THEN
            SET v_serie_entero = CAST(SUBSTRING(v_ultima_serie, 2) AS UNSIGNED) + 1;
            SET p_serie_generada = CONCAT(p_tipo_codigo, LPAD(v_serie_entero, 3, '0'));
            SET p_numero_generado = '00000001';
        ELSE
            SET p_serie_generada = v_ultima_serie;
            SET p_numero_generado = LPAD(v_num_entero, 8, '0');
        END IF;
    END IF;
END$$
DELIMITER ;