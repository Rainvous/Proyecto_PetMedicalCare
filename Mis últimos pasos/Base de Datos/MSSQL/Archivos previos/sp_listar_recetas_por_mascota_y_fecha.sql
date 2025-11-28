USE petmedicalcare;
GO

IF OBJECT_ID('sp_listar_recetas_por_mascota_y_fecha', 'P') IS NOT NULL
    DROP PROCEDURE sp_listar_recetas_por_mascota_y_fecha;
GO

CREATE PROCEDURE sp_listar_recetas_por_mascota_y_fecha
    @p_mascota_id INT,
    @p_fecha VARCHAR(20) -- Recibe 'yyyy-MM-dd'
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @v_fecha_filtro DATE;
    
    -- Convertimos el texto a fecha (Equivalente a DATE(p_fecha))
    -- Se recomienda usar TRY_CAST si hay riesgo de formatos inválidos, 
    -- pero CAST funciona bien si el formato siempre es yyyy-MM-dd.
    SET @v_fecha_filtro = CAST(@p_fecha AS DATE);

    SELECT 
        r.RECETA_MEDICA_ID,
        r.CITA_ID,
        r.FECHA_EMISION,
        r.VIGENCIA_HASTA,
        r.DIAGNOSTICO,
        r.OBSERVACIONES,
        r.ACTIVO
    FROM RECETAS_MEDICAS AS r
    JOIN CITAS_ATENCION AS c ON r.CITA_ID = c.CITA_ID
    WHERE 
        c.MASCOTA_ID = @p_mascota_id
        -- Si r.FECHA_EMISION es DATETIME, el CAST asegura que ignoramos la hora para comparar
        AND CAST(r.FECHA_EMISION AS DATE) = @v_fecha_filtro
    ORDER BY r.FECHA_EMISION DESC;
END;
GO