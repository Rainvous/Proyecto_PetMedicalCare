USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_servicios_avanzada_v3', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_servicios_avanzada_v3;
GO

CREATE PROCEDURE sp_buscar_servicios_avanzada_v3
    @p_nombre VARCHAR(100),
    @p_rango_precio_id VARCHAR(10), 
    @p_activo VARCHAR(10),
    @p_tipo_servicio_id INT -- Nuevo parámetro para las pestañas
AS
BEGIN
    SET NOCOUNT ON;

    SET @p_nombre = NULLIF(LTRIM(RTRIM(@p_nombre)), '');
    SET @p_rango_precio_id = NULLIF(LTRIM(RTRIM(@p_rango_precio_id)), '');
    SET @p_activo = NULLIF(LTRIM(RTRIM(@p_activo)), '');
    
    IF @p_tipo_servicio_id = 0 
    BEGIN
        SET @p_tipo_servicio_id = NULL; 
    END

    SELECT
        s.SERVICIO_ID,
        s.TIPO_SERVICIO_ID,
        s.NOMBRE,
        s.DESCRIPCION,
        s.COSTO,
        s.ESTADO,
        s.ACTIVO,
        -- Datos del Tipo para el DTO
        ts.NOMBRE as NOMBRE_TIPO_SERVICIO,
        ts.DESCRIPCION as DESCRIPCION_TIPO_SERVICIO,
        ts.ACTIVO as ACTIVO_TIPO_SERVICIO
    FROM SERVICIOS AS s
    JOIN TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        (@p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_activo IS NULL OR s.ACTIVO = @p_activo)
        -- Filtro de Pestaña
        AND (@p_tipo_servicio_id IS NULL OR s.TIPO_SERVICIO_ID = @p_tipo_servicio_id)
        AND (
            @p_rango_precio_id IS NULL
            OR (@p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (@p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (@p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY s.NOMBRE ASC;
END;
GO