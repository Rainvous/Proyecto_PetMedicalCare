USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_servicios_avanzada_2', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_servicios_avanzada_2;
GO

CREATE PROCEDURE sp_buscar_servicios_avanzada_2
    @p_nombre VARCHAR(100),
    @p_rango_precio_id VARCHAR(10), -- 1=(0-50), 2=(51-150), 3=(151+), NULL/''=Sin filtro
    @p_activo VARCHAR(10)           -- '1'=Activo, '0'=Inactivo, NULL/''=Ambos
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Normalizar entradas
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_rango_precio_id = NULLIF(TRIM(@p_rango_precio_id), '');
    SET @p_activo = NULLIF(TRIM(@p_activo), '');

    -- 2. Búsqueda
    SELECT
        s.SERVICIO_ID,
        s.TIPO_SERVICIO_ID,
        s.NOMBRE,
        s.DESCRIPCION,
        s.COSTO,
        s.ESTADO,
        s.ACTIVO,
        s.USUARIO_CREADOR,
        s.FECHA_CREACION,
        s.USUARIO_MODIFICADOR,
        s.FECHA_MODIFICACION,
        ts.TIPO_SERVICIO_ID as TIPO_SERVICIO_ID_TS, -- Alias ajustado para evitar conflicto
        ts.NOMBRE as NOMBRE_TIPO_SERVICIO,
        ts.DESCRIPCION as DESCRIPCION_TIPO_SERVICIO,
        ts.ACTIVO as ACTIVO_TIPO_SERVICIO
    FROM SERVICIOS AS s
    JOIN TIPOS_SERVICIO AS ts ON s.TIPO_SERVICIO_ID = ts.TIPO_SERVICIO_ID
    WHERE
        -- Filtro 1: Nombre
        (@p_nombre IS NULL OR s.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))

        -- Filtro 2: Activo
        AND (@p_activo IS NULL OR s.ACTIVO = @p_activo)

        -- Filtro 3: Rango de Precio
        AND (
            @p_rango_precio_id IS NULL
            OR (@p_rango_precio_id = '1' AND s.COSTO BETWEEN 0 AND 50)
            OR (@p_rango_precio_id = '2' AND s.COSTO BETWEEN 51 AND 150)
            OR (@p_rango_precio_id = '3' AND s.COSTO >= 151)
        )
    ORDER BY
        s.COSTO ASC, s.NOMBRE ASC;
END
GO

EXEC sp_buscar_servicios_avanzada_2 'Consulta', '2', NULL;