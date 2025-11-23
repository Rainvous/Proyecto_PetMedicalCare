USE petmedicalcare;
GO

IF OBJECT_ID('sp_buscar_productos_avanzada', 'P') IS NOT NULL
    DROP PROCEDURE sp_buscar_productos_avanzada;
GO

CREATE PROCEDURE sp_buscar_productos_avanzada
    @p_nombre VARCHAR(120),
    @p_rango_precio_id VARCHAR(10), -- 1=(0-50), 2=(51-150), 3=(151+), NULL/''=Sin filtro
    @p_activo VARCHAR(10)           -- '1'=Activo, '0'=Inactivo, NULL/''=Ambos
AS
BEGIN
    SET NOCOUNT ON; -- Buena práctica en SQL Server para evitar mensajes de "n filas afectadas"

    -- 1. Normalizar entradas
    SET @p_nombre = NULLIF(TRIM(@p_nombre), '');
    SET @p_rango_precio_id = NULLIF(TRIM(@p_rango_precio_id), '');
    SET @p_activo = NULLIF(TRIM(@p_activo), '');

    -- 2. Búsqueda
    SELECT
        p.PRODUCTO_ID,
        p.TIPO_PRODUCTO_ID,
        p.NOMBRE,
        p.PRESENTACION,
        p.PRECIO_UNITARIO,
        p.STOCK,
        p.ACTIVO,
        p.USUARIO_CREADOR,
        p.FECHA_CREACION,
        p.USUARIO_MODIFICADOR,
        p.FECHA_MODIFICACION
    FROM PRODUCTOS AS p
    JOIN TIPOS_PRODUCTO AS tp ON p.TIPO_PRODUCTO_ID = tp.TIPO_PRODUCTO_ID
    WHERE
        -- Filtro 1: Nombre
        (@p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', @p_nombre, '%'))

        -- Filtro 2: Activo
        -- Nota: SQL Server convierte implícitamente el string '1' a BIT 1
        AND (@p_activo IS NULL OR p.ACTIVO = @p_activo)

        -- Filtro 3: Rango de Precio
        AND (
            @p_rango_precio_id IS NULL
            OR (@p_rango_precio_id = '1' AND p.PRECIO_UNITARIO BETWEEN 0 AND 50)
            OR (@p_rango_precio_id = '2' AND p.PRECIO_UNITARIO BETWEEN 51 AND 150)
            OR (@p_rango_precio_id = '3' AND p.PRECIO_UNITARIO >= 151)
        )
    ORDER BY
        p.PRECIO_UNITARIO ASC, p.NOMBRE ASC;
END
GO

-- Caso 1: Buscar todo
EXEC sp_buscar_productos_avanzada '', '', '';

-- Caso 2: Rango 1
EXEC sp_buscar_productos_avanzada NULL, '1', NULL;

-- Caso 4: Nombre 'Vacuna' y Activos
EXEC sp_buscar_productos_avanzada 'Vacuna', NULL, '1';