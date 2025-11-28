USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_productos_avanzada', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_productos_avanzada;
GO

CREATE PROCEDURE sp_buscar_productos_avanzada
    @p_nombre VARCHAR(120),
    @p_rango_precio_id VARCHAR(10), 
    @p_activo VARCHAR(10)
AS
BEGIN
    SET NOCOUNT ON;

    SET @p_nombre = NULLIF(LTRIM(RTRIM(@p_nombre)), '');
    SET @p_rango_precio_id = NULLIF(LTRIM(RTRIM(@p_rango_precio_id)), '');
    SET @p_activo = NULLIF(LTRIM(RTRIM(@p_activo)), '');

    SELECT 
        p.PRODUCTO_ID,
        p.TIPO_PRODUCTO_ID,
        tp.NOMBRE as NOMBRE_TIPO,
        tp.DESCRIPCION as DESC_TIPO,
        p.NOMBRE,
        p.PRESENTACION,
        p.PRECIO_UNITARIO,
        p.STOCK,
        p.ACTIVO
    FROM PRODUCTOS AS p
    INNER JOIN TIPOS_PRODUCTO AS tp ON p.TIPO_PRODUCTO_ID = tp.TIPO_PRODUCTO_ID
    WHERE 
        (@p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))
        AND (@p_activo IS NULL OR p.ACTIVO = @p_activo)
        AND (
            @p_rango_precio_id IS NULL
            OR (@p_rango_precio_id = '1' AND p.PRECIO_UNITARIO BETWEEN 0 AND 50)
            OR (@p_rango_precio_id = '2' AND p.PRECIO_UNITARIO BETWEEN 51 AND 150)
            OR (@p_rango_precio_id = '3' AND p.PRECIO_UNITARIO > 150)
        )
    ORDER BY p.NOMBRE ASC;
END;
GO