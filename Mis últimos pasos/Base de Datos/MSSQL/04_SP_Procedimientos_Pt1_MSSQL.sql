USE petmedicalcare;
GO

-- =============================================
-- 1. BUSCAR CLIENTES (Avanzada)
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_buscar_clientes_avanzada
    @p_nombre VARCHAR(120) = NULL,
    @p_nro_documento VARCHAR(20) = NULL,
    @p_ruc VARCHAR(11) = NULL,
    @p_activo BIT = NULL
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        p.PERSONA_ID, p.NOMBRE, p.DIRECCION, p.TELEFONO, p.SEXO, p.NRO_DOCUMENTO, p.RUC, p.TIPO_DOCUMENTO, p.ACTIVO as ACTIVO_PERS,
        u.USUARIO_ID, u.USERNAME, u.CORREO
    FROM dbo.PERSONAS p
    INNER JOIN dbo.USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID
    WHERE 
        (@p_nombre IS NULL OR @p_nombre = '' OR p.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_nro_documento IS NULL OR @p_nro_documento = '' OR p.NRO_DOCUMENTO LIKE CONCAT('%', @p_nro_documento, '%'))
        AND (@p_ruc IS NULL OR @p_ruc = '' OR p.RUC LIKE CONCAT('%', @p_ruc, '%'))
        AND (@p_activo IS NULL OR p.ACTIVO = @p_activo)
        -- Excluir Staff
        AND NOT EXISTS (
            SELECT 1 FROM dbo.ROLES_USUARIO AS RU
            JOIN dbo.ROLES AS R ON RU.ROL_ID = R.ROL_ID
            WHERE RU.USUARIO_ID = p.USUARIO_ID
            AND R.NOMBRE IN ('ADMIN', 'VET', 'RECEPCION', 'GUEST')
        )
    ORDER BY p.NOMBRE ASC;
END
GO

-- =============================================
-- 2. BUSCAR PERSONAS (General)
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_buscar_personas_avanzada
    @p_nombre         VARCHAR(120) = NULL,
    @p_nro_documento  VARCHAR(20) = NULL,
    @p_ruc            VARCHAR(11) = NULL,
    @p_telefono       VARCHAR(20) = NULL,
    @p_activo         VARCHAR(2) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    
    SET @p_nombre        = NULLIF(TRIM(@p_nombre), '');
    SET @p_telefono      = NULLIF(TRIM(@p_telefono), '');
    SET @p_nro_documento = TRIM(@p_nro_documento);
    SET @p_ruc           = TRIM(@p_ruc);
    SET @p_activo        = NULLIF(TRIM(@p_activo), '');

    IF @p_nro_documento IS NULL OR @p_nro_documento = '' OR @p_nro_documento = '0' SET @p_nro_documento = NULL;
    IF @p_ruc IS NULL OR @p_ruc = '' OR @p_ruc = '0' SET @p_ruc = NULL;

    SELECT
        PERSONA_ID, NOMBRE, DIRECCION, TELEFONO, SEXO, ACTIVO, TIPO_DOCUMENTO, NRO_DOCUMENTO, RUC, USUARIO_ID
    FROM dbo.PERSONAS
    WHERE
        (@p_nombre        IS NULL OR NOMBRE        LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_nro_documento IS NULL OR NRO_DOCUMENTO LIKE CONCAT('%', @p_nro_documento, '%'))
        AND (@p_ruc           IS NULL OR RUC           LIKE CONCAT('%', @p_ruc, '%'))
        AND (@p_telefono      IS NULL OR TELEFONO      LIKE CONCAT('%', @p_telefono, '%'))
        AND (@p_activo        IS NULL OR ACTIVO = CAST(@p_activo AS BIT))
    ORDER BY NOMBRE ASC, PERSONA_ID ASC;
END
GO

-- =============================================
-- 3. BUSCAR VETERINARIOS
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_buscar_veterinarios_avanzada
    @p_especialidad VARCHAR(50) = NULL,
    @p_nombre VARCHAR(120) = NULL,
    @p_nro_documento VARCHAR(20) = NULL,
    @p_activo BIT = NULL
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        v.VETERINARIO_ID, v.FECHA_DE_CONTRATACION, v.ESTADO as ESTADO_VET, v.ESPECIALIDAD, v.ACTIVO as ACTIVO_VET,
        p.PERSONA_ID, p.NOMBRE, p.DIRECCION, p.TELEFONO, p.SEXO, p.NRO_DOCUMENTO, p.RUC, p.TIPO_DOCUMENTO, p.ACTIVO as ACTIVO_PERS,
        u.USUARIO_ID, u.USERNAME, u.CORREO
    FROM dbo.VETERINARIOS v
    JOIN dbo.PERSONAS p ON v.PERSONA_ID = p.PERSONA_ID
    JOIN dbo.USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID
    WHERE 
        (@p_especialidad IS NULL OR @p_especialidad = '' OR v.ESPECIALIDAD LIKE CONCAT('%', @p_especialidad, '%'))
        AND (@p_nombre IS NULL OR @p_nombre = '' OR p.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_nro_documento IS NULL OR @p_nro_documento = '' OR p.NRO_DOCUMENTO LIKE CONCAT('%', @p_nro_documento, '%'))
        AND (@p_activo IS NULL OR v.ACTIVO = @p_activo)
    ORDER BY p.NOMBRE ASC;
END
GO

-- =============================================
-- 4. BUSCAR MASCOTAS
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_buscar_mascotas_avanzada
    @p_nombre VARCHAR(100) = NULL,
    @p_especie VARCHAR(40) = NULL,
    @p_nombre_duenio VARCHAR(120) = NULL,
    @p_activo BIT = NULL
AS
BEGIN
    SET NOCOUNT ON;

    SELECT 
        m.MASCOTA_ID, m.NOMBRE, m.ESPECIE, m.SEXO, m.RAZA, m.COLOR, m.FECHA_DEFUNCION, m.ACTIVO,
        p.PERSONA_ID, p.NOMBRE as NOMBRE_DUENIO, p.TELEFONO as TELEFONO_DUENIO
    FROM dbo.MASCOTAS m
    INNER JOIN dbo.PERSONAS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (@p_nombre IS NULL OR @p_nombre = '' OR m.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_especie IS NULL OR @p_especie = '' OR m.ESPECIE = @p_especie)
        AND (@p_nombre_duenio IS NULL OR @p_nombre_duenio = '' OR p.NOMBRE LIKE CONCAT('%', @p_nombre_duenio, '%'))
        AND (@p_activo IS NULL OR m.ACTIVO = @p_activo)
    ORDER BY m.NOMBRE ASC;
END
GO

-- =============================================
-- 5. BUSCAR PRODUCTOS
-- =============================================
USE petmedicalcare;
GO

CREATE OR ALTER PROCEDURE dbo.sp_buscar_productos_avanzada
    @p_nombre VARCHAR(120) = NULL,
    @p_rango_precio_id VARCHAR(10) = NULL,
    @p_activo VARCHAR(10) = NULL,
    @p_tipo_producto_id INT = 0 -- Nuevo parámetro (Default 0)
AS
BEGIN
    SET NOCOUNT ON;
    
    -- Normalización
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_rango_precio_id = NULLIF(TRIM(@p_rango_precio_id), '');
    SET @p_activo = NULLIF(TRIM(@p_activo), '');
    
    -- Lógica 0 = Todos
    IF @p_tipo_producto_id = 0 SET @p_tipo_producto_id = NULL;

    SELECT 
        p.PRODUCTO_ID, p.TIPO_PRODUCTO_ID, tp.NOMBRE as NOMBRE_TIPO, tp.DESCRIPCION as DESC_TIPO,
        p.NOMBRE, p.PRESENTACION, p.PRECIO_UNITARIO, p.STOCK, p.ACTIVO
    FROM dbo.PRODUCTOS AS p
    INNER JOIN dbo.TIPOS_PRODUCTO AS tp ON p.TIPO_PRODUCTO_ID = tp.TIPO_PRODUCTO_ID
    WHERE 
        (@p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_activo IS NULL OR p.ACTIVO = CAST(@p_activo AS BIT))
        AND (@p_tipo_producto_id IS NULL OR p.TIPO_PRODUCTO_ID = @p_tipo_producto_id)
        AND (
            @p_rango_precio_id IS NULL
            OR (@p_rango_precio_id = '1' AND p.PRECIO_UNITARIO BETWEEN 0 AND 50)
            OR (@p_rango_precio_id = '2' AND p.PRECIO_UNITARIO BETWEEN 51 AND 150)
            OR (@p_rango_precio_id = '3' AND p.PRECIO_UNITARIO > 150)
        )
    ORDER BY p.NOMBRE ASC;
END
GO

-- =============================================
-- 6. BUSCAR SERVICIOS (Versión 1)
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_buscar_servicios_avanzada
    @p_nombre VARCHAR(100) = NULL,
    @p_rango_precio_id VARCHAR(10) = NULL,
    @p_activo VARCHAR(10) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_rango_precio_id = NULLIF(TRIM(@p_rango_precio_id), '');
    SET @p_activo = NULLIF(TRIM(@p_activo), '');

    SELECT
		s.SERVICIO_ID, s.TIPO_SERVICIO_ID, s.NOMBRE, s.DESCRIPCION, s.COSTO, s.ESTADO, s.ACTIVO,
        s.USUARIO_CREADOR, s.FECHA_CREACION, s.USUARIO_MODIFICADOR, s.FECHA_MODIFICACION
    FROM dbo.SERVICIOS AS s
    JOIN dbo.TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        (@p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_activo IS NULL OR s.ACTIVO = CAST(@p_activo AS BIT))
        AND (
            @p_rango_precio_id IS NULL
            OR (@p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (@p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (@p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY s.COSTO ASC, s.NOMBRE ASC;
END
GO

-- =============================================
-- 7. BUSCAR SERVICIOS (Versión 2)
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_buscar_servicios_avanzada_2
    @p_nombre VARCHAR(100) = NULL,
    @p_rango_precio_id VARCHAR(10) = NULL,
    @p_activo VARCHAR(10) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_rango_precio_id = NULLIF(TRIM(@p_rango_precio_id), '');
    SET @p_activo = NULLIF(TRIM(@p_activo), '');

    SELECT
		s.SERVICIO_ID, s.TIPO_SERVICIO_ID, s.NOMBRE, s.DESCRIPCION, s.COSTO, s.ESTADO, s.ACTIVO,
        s.USUARIO_CREADOR, s.FECHA_CREACION, s.USUARIO_MODIFICADOR, s.FECHA_MODIFICACION,
        ts.TIPO_SERVICIO_ID as TIPO_SERVICIO_ID_T,
		ts.NOMBRE as NOMBRE_TIPO_SERVICIO,
        ts.DESCRIPCION as DESCRIPCION_TIPO_SERVICIO,
        ts.ACTIVO as ACTIVO_TIPO_SERVICIO
    FROM dbo.SERVICIOS AS s
    JOIN dbo.TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        (@p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_activo IS NULL OR s.ACTIVO = CAST(@p_activo AS BIT))
        AND (
            @p_rango_precio_id IS NULL
            OR (@p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (@p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (@p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY s.COSTO ASC, s.NOMBRE ASC;
END
GO

-- =============================================
-- 8. BUSCAR SERVICIOS (Versión 3)
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_buscar_servicios_avanzada_v3
    @p_nombre VARCHAR(100) = NULL,
    @p_rango_precio_id VARCHAR(10) = NULL, 
    @p_activo VARCHAR(10) = NULL,
    @p_tipo_servicio_id INT = NULL
AS
BEGIN
    SET NOCOUNT ON;
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_rango_precio_id = NULLIF(TRIM(@p_rango_precio_id), '');
    SET @p_activo = NULLIF(TRIM(@p_activo), '');
    IF @p_tipo_servicio_id = 0 SET @p_tipo_servicio_id = NULL;

    SELECT
        s.SERVICIO_ID, s.TIPO_SERVICIO_ID, s.NOMBRE, s.DESCRIPCION, s.COSTO, s.ESTADO, s.ACTIVO,
        ts.NOMBRE as NOMBRE_TIPO_SERVICIO,
        ts.DESCRIPCION as DESCRIPCION_TIPO_SERVICIO,
        ts.ACTIVO as ACTIVO_TIPO_SERVICIO
    FROM dbo.SERVICIOS AS s
    JOIN dbo.TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        (@p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_activo IS NULL OR s.ACTIVO = CAST(@p_activo AS BIT))
        AND (@p_tipo_servicio_id IS NULL OR s.TIPO_SERVICIO_ID = @p_tipo_servicio_id)
        AND (
            @p_rango_precio_id IS NULL
            OR (@p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (@p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (@p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY s.NOMBRE ASC;
END
GO

-- =============================================
-- 9. BUSCAR RECETAS
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_buscar_recetas_avanzada
    @p_nombre_mascota VARCHAR(100) = NULL,
    @p_nombre_duenio VARCHAR(100) = NULL,
    @p_fecha DATE = NULL,
    @p_activo VARCHAR(10) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    SET @p_nombre_mascota = NULLIF(TRIM(@p_nombre_mascota), '');
    SET @p_nombre_duenio = NULLIF(TRIM(@p_nombre_duenio), '');
    SET @p_activo = NULLIF(TRIM(@p_activo), '');

    SELECT 
        r.RECETA_MEDICA_ID, r.CITA_ID, r.FECHA_EMISION, r.VIGENCIA_HASTA, r.DIAGNOSTICO, r.OBSERVACIONES, r.ACTIVO,
        m.NOMBRE AS MASCOTA_NOMBRE,
        CONCAT(m.ESPECIE, ' - ', m.RAZA) AS MASCOTA_DETALLE,
        p.NOMBRE AS DUENIO_NOMBRE,
        c.FECHA_HORA_INICIO AS FECHA_CITA
    FROM dbo.RECETAS_MEDICAS r
    JOIN dbo.CITAS_ATENCION c ON r.CITA_ID = c.CITA_ID
    JOIN dbo.MASCOTAS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN dbo.PERSONAS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (@p_nombre_mascota IS NULL OR m.NOMBRE LIKE CONCAT('%', @p_nombre_mascota, '%'))
        AND (@p_nombre_duenio IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre_duenio, '%'))
        AND (@p_fecha IS NULL OR r.FECHA_EMISION = @p_fecha)
        AND (@p_activo IS NULL OR r.ACTIVO = CAST(@p_activo AS BIT))
    ORDER BY r.FECHA_EMISION DESC;
END
GO

-- =============================================
-- 10. GENERAR CORRELATIVO
-- =============================================
CREATE OR ALTER PROCEDURE dbo.sp_generar_correlativo_pago
    @p_tipo_codigo CHAR(1),
    @p_serie_generada VARCHAR(10) OUTPUT,
    @p_numero_generado VARCHAR(12) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @v_tipo_documento VARCHAR(50);
    DECLARE @v_ultima_serie VARCHAR(10);
    DECLARE @v_ultimo_numero VARCHAR(12);
    DECLARE @v_num_entero BIGINT;
    DECLARE @v_serie_entero INT;

    IF @p_tipo_codigo = 'B' SET @v_tipo_documento = 'BOLETA';
    ELSE IF @p_tipo_codigo = 'F' SET @v_tipo_documento = 'FACTURA';
    ELSE
    BEGIN
        SET @p_serie_generada = NULL;
        SET @p_numero_generado = NULL;
        RETURN;
    END

    -- Obtener el último (TOP 1 equivalente a LIMIT 1)
    SELECT TOP 1 
        @v_ultima_serie = SERIE, 
        @v_ultimo_numero = NUMERO
    FROM dbo.DOCUMENTOS_DE_PAGO
    WHERE TIPO_DOCUMENTO = @v_tipo_documento
      AND SERIE LIKE CONCAT(@p_tipo_codigo, '%') 
    ORDER BY SERIE DESC, NUMERO DESC;

    IF @v_ultima_serie IS NULL
    BEGIN
        SET @p_serie_generada = CONCAT(@p_tipo_codigo, '001');
        SET @p_numero_generado = '00000001';
    END
    ELSE
    BEGIN
        SET @v_num_entero = CAST(@v_ultimo_numero AS BIGINT) + 1;

        IF @v_num_entero > 99999999
        BEGIN
            -- Logic for LPAD in MSSQL: RIGHT('000' + val, 3)
            SET @v_serie_entero = CAST(SUBSTRING(@v_ultima_serie, 2, LEN(@v_ultima_serie)) AS INT) + 1;
            SET @p_serie_generada = CONCAT(@p_tipo_codigo, RIGHT('000' + CAST(@v_serie_entero AS VARCHAR(10)), 3));
            SET @p_numero_generado = '00000001';
        END
        ELSE
        BEGIN
            SET @p_serie_generada = @v_ultima_serie;
            SET @p_numero_generado = RIGHT('00000000' + CAST(@v_num_entero AS VARCHAR(12)), 8);
        END
    END
END
GO