USE petmedicalcare;
GO

IF OBJECT_ID('sp_listar_citas_por_mascota_y_fecha', 'P') IS NOT NULL
    DROP PROCEDURE sp_listar_citas_por_mascota_y_fecha;
GO

CREATE PROCEDURE sp_listar_citas_por_mascota_y_fecha
    @p_mascota_id INT,
    @p_fecha VARCHAR(20)
AS
BEGIN
    SET NOCOUNT ON;

    -- Convertimos el texto a fecha
    DECLARE @v_fecha_filtro DATE;
    
    -- TRY_CAST devuelve NULL si la fecha no es válida, evitando errores
    SET @v_fecha_filtro = TRY_CAST(@p_fecha AS DATE);

    SELECT 
        c.CITA_ID,
        c.VETERINARIO_ID,
        c.MASCOTA_ID,
        c.FECHA_REGISTRO,
        c.FECHA_HORA_INICIO,
        c.FECHA_HORA_FIN,
        c.PESO_MASCOTA,
        c.MONTO,
        c.ESTADO_CITA,
        c.OBSERVACION,
        c.ACTIVO

    FROM CITAS_ATENCION AS c
    JOIN MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    
    WHERE 
        c.MASCOTA_ID = @p_mascota_id
        AND CAST(c.FECHA_HORA_INICIO AS DATE) = @v_fecha_filtro
    
    ORDER BY c.FECHA_HORA_INICIO ASC;
END
GO

EXEC sp_listar_citas_por_mascota_y_fecha 1, '2025-10-28';