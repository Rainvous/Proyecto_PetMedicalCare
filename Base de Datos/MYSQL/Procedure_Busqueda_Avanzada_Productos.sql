USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_productos_avanzada;

DELIMITER $$
CREATE PROCEDURE sp_buscar_productos_avanzada(
    IN p_nombre VARCHAR(120),
    IN p_rango_precio_id VARCHAR(10), -- 1=(0-50), 2=(51-150), 3=(151+), NULL/''=Sin filtro
    IN p_activo VARCHAR(10)           -- '1'=Activo, '0'=Inactivo, NULL/''=Ambos
)
BEGIN
    -- 1. Normalizar entradas (convertir '' en NULL)
    SET p_nombre = NULLIF(TRIM(p_nombre), '');
    SET p_rango_precio_id = NULLIF(TRIM(p_rango_precio_id), '');
    SET p_activo = NULLIF(TRIM(p_activo), '');

    -- 2. Búsqueda
    SELECT
        p.PRODUCTO_ID,
        p.TIPO_PRODUCTO_ID,
        p.NOMBRE,
        p.PRESENTACION,      -- Campo propio de productos
        p.PRECIO_UNITARIO,   -- Campo usado para el rango de precios
        p.STOCK,             -- Campo propio de productos
        p.ACTIVO,
        p.USUARIO_CREADOR,
        p.FECHA_CREACION,
        p.USUARIO_MODIFICADOR,
        p.FECHA_MODIFICACION
    FROM PRODUCTOS AS p
    JOIN TIPOS_PRODUCTO AS tp ON p.TIPO_PRODUCTO_ID = tp.TIPO_PRODUCTO_ID
    WHERE
        -- Filtro 1: Nombre (ignora si es NULL)
        (p_nombre IS NULL OR p.NOMBRE LIKE CONCAT('%', p_nombre, '%'))

        -- Filtro 2: Activo (ignora si es NULL)
        AND (p_activo IS NULL OR p.ACTIVO = p_activo)

        -- Filtro 3: Rango de Precio (ignora si es NULL)
        AND (
            p_rango_precio_id IS NULL
            OR (p_rango_precio_id = '1' AND p.PRECIO_UNITARIO BETWEEN 0 AND 50)
            OR (p_rango_precio_id = '2' AND p.PRECIO_UNITARIO BETWEEN 51 AND 150)
            OR (p_rango_precio_id = '3' AND p.PRECIO_UNITARIO >= 151)
        )
    ORDER BY
        p.PRECIO_UNITARIO ASC, p.NOMBRE ASC;
END$$
DELIMITER ;


-- Caso 1: Buscar todo (sin filtros)
CALL sp_buscar_productos_avanzada('', '', '');

-- Caso 2: Buscar solo por Rango 1 (0 - 50 soles)
-- Debería traer: Antibiótico (15.50), Vacuna (25.00), Collar (30.00)
CALL sp_buscar_productos_avanzada(NULL, '1', NULL);

-- Caso 3: Buscar solo por Rango 2 (51 - 150 soles)
-- Debería traer: Croquetas Premium (120.00)
CALL sp_buscar_productos_avanzada(NULL, '2', NULL);

-- Caso 4: Buscar solo Activos y con nombre "Vacuna"
CALL sp_buscar_productos_avanzada('Vacuna', NULL, '1');