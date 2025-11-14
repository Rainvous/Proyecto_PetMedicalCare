USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_verificar_relacion_producto;

DELIMITER $$
CREATE PROCEDURE sp_verificar_relacion_producto(
    IN p_producto_id INT,
    OUT p_valorRetornado INT
)
BEGIN
    -- 1. Establece el valor por defecto (0 = Sin relación)
    SET p_valorRetornado = 0;

    -- 2. Busca si existe al menos 1 registro en la tabla de ventas
    SELECT 1 INTO p_valorRetornado
    FROM DETALLES_DOCUMENTO_DE_PAGO
    WHERE PRODUCTO_ID = p_producto_id
    LIMIT 1; -- La forma más rápida de verificar existencia
END$$
DELIMITER ;


-- --- PRUEBA 1: Producto CON relaciones (se ha vendido) ---
-- 'Comida premium' SÍ está en DETALLES_DOCUMENTO_DE_PAGO
SET @id_producto_usado = (SELECT PRODUCTO_ID FROM PRODUCTOS WHERE NOMBRE = 'Comida premium' LIMIT 1);

CALL sp_verificar_relacion_producto(@id_producto_usado, @resultado);

SELECT @resultado AS 'Producto_Comida_Premium';
-- Resultado esperado: 1


-- --- PRUEBA 2: Producto SIN relaciones (no se ha vendido) ---
-- 'Collar isabelino' se insertó en PRODUCTOS, 
-- pero NO se usó en ningún documento de pago
SET @id_producto_no_usado = (SELECT PRODUCTO_ID FROM PRODUCTOS WHERE NOMBRE = 'Collar isabelino' LIMIT 1);

CALL sp_verificar_relacion_producto(@id_producto_no_usado, @resultado);

SELECT @resultado AS 'Producto_Collar_Isabelino';
-- Resultado esperado: 0