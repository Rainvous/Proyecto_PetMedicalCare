USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_mascotas_avanzada', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_mascotas_avanzada;
GO

CREATE PROCEDURE sp_buscar_mascotas_avanzada
    @p_nombre_mascota VARCHAR(80),
    @p_raza VARCHAR(60),
    @p_especie VARCHAR(40),
    @p_nombre_propietario VARCHAR(120)
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Normalizar entradas
    SET @p_nombre_mascota     = NULLIF(TRIM(@p_nombre_mascota), '');
    SET @p_raza               = NULLIF(TRIM(@p_raza), '');
    SET @p_especie            = NULLIF(TRIM(@p_especie), '');
    SET @p_nombre_propietario = NULLIF(TRIM(@p_nombre_propietario), '');

    -- 2. Búsqueda
    SELECT
        m.MASCOTA_ID,
        m.PERSONA_ID,
        m.NOMBRE,
        m.ESPECIE,
        m.SEXO,
        m.RAZA,
        m.COLOR,
        m.FECHA_DEFUNCION,
        m.ACTIVO
    FROM MASCOTAS AS m
    JOIN PERSONAS AS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE
        -- Filtros con CONCAT (Soportado en SQL Server moderno)
        (@p_nombre_mascota IS NULL OR m.NOMBRE LIKE CONCAT('%', @p_nombre_mascota, '%'))
        AND (@p_raza IS NULL OR m.RAZA LIKE CONCAT('%', @p_raza, '%'))
        AND (@p_especie IS NULL OR m.ESPECIE LIKE CONCAT('%', @p_especie, '%'))
        AND (@p_nombre_propietario IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre_propietario, '%'))
        
    ORDER BY
        p.NOMBRE ASC, m.NOMBRE ASC;
END
GO


EXEC sp_buscar_mascotas_avanzada NULL, NULL, 'Perro', 'Carlos Torres';