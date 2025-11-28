USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_recetas_avanzada', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_recetas_avanzada;
GO

CREATE PROCEDURE sp_buscar_recetas_avanzada
    @p_nombre_mascota VARCHAR(100),
    @p_nombre_duenio VARCHAR(100),
    @p_fecha DATE,        -- NULL para ignorar
    @p_activo VARCHAR(10) -- '1', '0' o NULL
AS
BEGIN
    SET NOCOUNT ON;

    -- Limpieza de parámetros (NULLIF + TRIM)
    SET @p_nombre_mascota = NULLIF(LTRIM(RTRIM(@p_nombre_mascota)), '');
    SET @p_nombre_duenio = NULLIF(LTRIM(RTRIM(@p_nombre_duenio)), '');
    SET @p_activo = NULLIF(LTRIM(RTRIM(@p_activo)), '');

    SELECT 
        r.RECETA_MEDICA_ID,
        r.CITA_ID,
        r.FECHA_EMISION,
        r.VIGENCIA_HASTA,
        r.DIAGNOSTICO,
        r.OBSERVACIONES,
        r.ACTIVO,
        -- Datos Informativos
        m.NOMBRE AS MASCOTA_NOMBRE,
        CONCAT(m.ESPECIE, ' - ', m.RAZA) AS MASCOTA_DETALLE,
        p.NOMBRE AS DUENIO_NOMBRE,
        c.FECHA_HORA_INICIO AS FECHA_CITA
    FROM RECETAS_MEDICAS r
    JOIN CITAS_ATENCION c ON r.CITA_ID = c.CITA_ID
    JOIN MASCOTAS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN PERSONAS p ON m.PERSONA_ID = p.PERSONA_ID
    WHERE 
        (@p_nombre_mascota IS NULL OR m.NOMBRE LIKE CONCAT('%', @p_nombre_mascota, '%'))
        AND (@p_nombre_duenio IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre_duenio, '%'))
        AND (@p_fecha IS NULL OR CAST(r.FECHA_EMISION AS DATE) = @p_fecha)
        AND (@p_activo IS NULL OR r.ACTIVO = @p_activo)
    ORDER BY r.FECHA_EMISION DESC;
END;
GO