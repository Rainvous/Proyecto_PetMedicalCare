USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_veterinarios_avanzada', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_veterinarios_avanzada;
GO

CREATE PROCEDURE sp_buscar_veterinarios_avanzada
    @p_especialidad VARCHAR(50),
    @p_nombre VARCHAR(120),
    @p_nro_documento VARCHAR(20),
    @p_activo TINYINT -- 1 (Activo), 0 (Inactivo), NULL (Todos)
AS
BEGIN
    SET NOCOUNT ON;

    -- Normalización
    SET @p_especialidad = NULLIF(TRIM(@p_especialidad), '');
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_nro_documento = NULLIF(TRIM(@p_nro_documento), '');

    SELECT 
        v.VETERINARIO_ID,
        v.FECHA_DE_CONTRATACION,
        v.ESTADO as ESTADO_VET,
        v.ESPECIALIDAD,
        v.ACTIVO as ACTIVO_VET,
        -- Datos Persona
        p.PERSONA_ID,
        p.NOMBRE,
        p.DIRECCION,
        p.TELEFONO,
        p.SEXO,
        p.NRO_DOCUMENTO,
        p.RUC,
        p.TIPO_DOCUMENTO,
        p.ACTIVO as ACTIVO_PERS,
        -- Datos Usuario
        u.USUARIO_ID,
        u.USERNAME,
        u.CORREO
    FROM VETERINARIOS v
    JOIN PERSONAS p ON v.PERSONA_ID = p.PERSONA_ID
    JOIN USUARIOS u ON p.USUARIO_ID = u.USUARIO_ID
    WHERE 
        (@p_especialidad IS NULL OR v.ESPECIALIDAD LIKE CONCAT('%', @p_especialidad, '%'))
        AND (@p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_nro_documento IS NULL OR p.NRO_DOCUMENTO LIKE CONCAT('%', @p_nro_documento, '%'))
        -- Filtro Estado
        AND (@p_activo IS NULL OR v.ACTIVO = @p_activo)
    ORDER BY p.NOMBRE ASC;
END
GO