USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_mascotas_avanzada', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_mascotas_avanzada;
GO

CREATE PROCEDURE sp_buscar_mascotas_avanzada
    @p_nombre VARCHAR(100),
    @p_especie VARCHAR(40),
    @p_nombre_duenio VARCHAR(120),
    @p_activo TINYINT -- 1: Activo, 0: Inactivo, NULL: Todos
AS
BEGIN
    SET NOCOUNT ON;

    -- Normalización opcional (convierte vacíos a NULL para simplificar el WHERE)
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_especie = NULLIF(TRIM(@p_especie), '');
    SET @p_nombre_duenio = NULLIF(TRIM(@p_nombre_duenio), '');

    SELECT 
        m.MASCOTA_ID,
        m.NOMBRE,
        m.ESPECIE,
        m.SEXO,
        m.RAZA,
        m.COLOR,
        m.FECHA_DEFUNCION,
        m.ACTIVO,
        -- Datos del Dueño
        p.PERSONA_ID,
        p.NOMBRE as NOMBRE_DUENIO,
        p.TELEFONO as TELEFONO_DUENIO
    FROM MASCOTAS m
    INNER JOIN PERSONAS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (@p_nombre IS NULL OR m.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_especie IS NULL OR m.ESPECIE = @p_especie)
        AND (@p_nombre_duenio IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre_duenio, '%'))
        -- Filtro Estado
        AND (@p_activo IS NULL OR m.ACTIVO = @p_activo)
    ORDER BY m.NOMBRE ASC;
END
GO