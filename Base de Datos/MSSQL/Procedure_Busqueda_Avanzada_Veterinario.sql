USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_veterinarios_avanzada', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_veterinarios_avanzada;
GO

CREATE PROCEDURE sp_buscar_veterinarios_avanzada
    @p_especialidad VARCHAR(50),
    @p_nombre VARCHAR(120),
    @p_telefono VARCHAR(20),
    @p_nro_documento VARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Normalizar entradas
    SET @p_especialidad  = NULLIF(TRIM(@p_especialidad), '');
    SET @p_nombre        = NULLIF(TRIM(@p_nombre), '');
    SET @p_telefono      = NULLIF(TRIM(@p_telefono), '');
    SET @p_nro_documento = NULLIF(TRIM(@p_nro_documento), '');

    -- 2. Búsqueda
    SELECT 
        v.VETERINARIO_ID,
        v.PERSONA_ID,
        v.FECHA_DE_CONTRATACION,
        v.ESTADO,
        v.ESPECIALIDAD,
        v.ACTIVO
        -- Nota: Si necesitas columnas de PERSONA (Nombre, DNI), agrégalas aquí:
        -- , p.NOMBRE, p.NRO_DOCUMENTO 
    FROM VETERINARIOS AS v
    JOIN PERSONAS AS p ON v.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (@p_especialidad IS NULL OR v.ESPECIALIDAD LIKE CONCAT('%', @p_especialidad, '%'))
        AND (@p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_telefono IS NULL OR p.TELEFONO LIKE CONCAT('%', @p_telefono, '%'))
        AND (@p_nro_documento IS NULL OR p.NRO_DOCUMENTO LIKE CONCAT('%', @p_nro_documento, '%'))
        
    ORDER BY p.NOMBRE ASC;
END
GO



-- Caso 1: Buscar todo
EXEC sp_buscar_veterinarios_avanzada '', '', '', '';

-- Caso 5: Combinada
EXEC sp_buscar_veterinarios_avanzada 'Dermatología', 'García', NULL, NULL;