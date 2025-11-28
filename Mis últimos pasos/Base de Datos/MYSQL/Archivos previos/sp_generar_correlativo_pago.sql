USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_generar_correlativo_pago;

DELIMITER $$

CREATE PROCEDURE sp_generar_correlativo_pago(
    IN p_tipo_codigo CHAR(1),        -- 'B' o 'F'
    OUT p_serie_generada VARCHAR(10),
    OUT p_numero_generado VARCHAR(12)
)
main_block: BEGIN

    DECLARE v_tipo_documento VARCHAR(50);
    DECLARE v_ultima_serie VARCHAR(10);
    DECLARE v_ultimo_numero VARCHAR(12);
    DECLARE v_num_entero BIGINT;
    DECLARE v_serie_entero INT;

    -- 1. Mapear el código
    IF p_tipo_codigo = 'B' THEN
        SET v_tipo_documento = 'BOLETA';
    ELSEIF p_tipo_codigo = 'F' THEN
        SET v_tipo_documento = 'FACTURA';
    ELSE
        SET p_serie_generada = NULL;
        SET p_numero_generado = NULL;
        LEAVE main_block;
    END IF;

    -- 2. Obtener el ÚLTIMO documento (¡FILTRANDO BASURA!)
    SELECT SERIE, NUMERO 
    INTO v_ultima_serie, v_ultimo_numero
    FROM DOCUMENTOS_DE_PAGO
    WHERE TIPO_DOCUMENTO = v_tipo_documento
      -- [IMPORTANTE] Esta línea hace la magia:
      -- Solo mira series que empiecen con la letra que pediste ('B%' o 'F%')
      -- Ignorará 'X008', 'A123', 'PRUEBA', etc.
      AND SERIE LIKE CONCAT(p_tipo_codigo, '%') 
    ORDER BY SERIE DESC, NUMERO DESC
    LIMIT 1;

    -- 3. Lógica de Generación
    IF v_ultima_serie IS NULL THEN
        -- Si no encontró ninguna serie 'B...' (aunque haya 'X...')
        -- Empieza de cero correctamente.
        SET p_serie_generada = CONCAT(p_tipo_codigo, '001');
        SET p_numero_generado = '00000001';
    ELSE
        -- Si encontró una serie válida (ej: B001), continúa la secuencia
        SET v_num_entero = CAST(v_ultimo_numero AS UNSIGNED) + 1;

        IF v_num_entero > 99999999 THEN
            SET v_serie_entero = CAST(SUBSTRING(v_ultima_serie, 2) AS UNSIGNED) + 1;
            SET p_serie_generada = CONCAT(p_tipo_codigo, LPAD(v_serie_entero, 3, '0'));
            SET p_numero_generado = '00000001';
        ELSE
            SET p_serie_generada = v_ultima_serie;
            SET p_numero_generado = LPAD(v_num_entero, 8, '0');
        END IF;
    END IF;

END main_block$$

DELIMITER ;


USE petmedicalcare;

-- ==========================================================================
-- ESCENARIO 1: PRUEBA DE FILTRO (IGNORAR DATOS BASURA)
-- ==========================================================================
-- 1. Insertamos "Ruido": Una boleta con serie rara 'X008' y número alto.
INSERT INTO DOCUMENTOS_DE_PAGO (METODO_DE_PAGO_ID, TIPO_DOCUMENTO, SERIE, NUMERO, FECHA_EMISION, ESTADO, SUBTOTAL, IGV_TOTAL, TOTAL, ACTIVO)
VALUES (1, 'BOLETA', 'X008', '00005000', CURDATE(), 'PAGADO', 10, 1.8, 11.8, 1);

-- 2. Insertamos la "Real": Una boleta válida B001 en el número 20.
INSERT INTO DOCUMENTOS_DE_PAGO (METODO_DE_PAGO_ID, TIPO_DOCUMENTO, SERIE, NUMERO, FECHA_EMISION, ESTADO, SUBTOTAL, IGV_TOTAL, TOTAL, ACTIVO)
VALUES (1, 'BOLETA', 'B001', '00000020', CURDATE(), 'PAGADO', 10, 1.8, 11.8, 1);

-- 3. EJECUTAMOS EL PROCEDIMIENTO (Pedimos siguiente Boleta 'B')
CALL sp_generar_correlativo_pago('B', @serie_res, @num_res);

-- 4. VERIFICACIÓN
-- Resultado Esperado: B001 - 00000021
-- (Si fallara el filtro, devolvería X008 - 00005001)
SELECT 'Escenario 1' AS Test, @serie_res AS Serie, @num_res AS Numero, 
       CASE WHEN @serie_res = 'B001' AND @num_res = '00000021' THEN '✅ ÉXITO' ELSE '❌ FALLÓ' END AS Resultado;

-- Limpieza Escenario 1
DELETE FROM DOCUMENTOS_DE_PAGO WHERE SERIE IN ('X008', 'B001') AND NUMERO IN ('00005000', '00000020');


-- ==========================================================================
-- ESCENARIO 2: PRUEBA DE DESBORDAMIENTO (CAMBIO DE SERIE)
-- ==========================================================================
-- 1. Insertamos una Factura al límite: F001 - 99999999
INSERT INTO DOCUMENTOS_DE_PAGO (METODO_DE_PAGO_ID, TIPO_DOCUMENTO, SERIE, NUMERO, FECHA_EMISION, ESTADO, SUBTOTAL, IGV_TOTAL, TOTAL, ACTIVO)
VALUES (1, 'FACTURA', 'F001', '99999999', CURDATE(), 'PAGADO', 100, 18, 118, 1);

-- 2. EJECUTAMOS EL PROCEDIMIENTO (Pedimos siguiente Factura 'F')
CALL sp_generar_correlativo_pago('F', @serie_res, @num_res);

-- 3. VERIFICACIÓN
-- Resultado Esperado: F002 - 00000001
SELECT 'Escenario 2' AS Test, @serie_res AS Serie, @num_res AS Numero, 
       CASE WHEN @serie_res = 'F002' AND @num_res = '00000001' THEN '✅ ÉXITO' ELSE '❌ FALLÓ' END AS Resultado;

-- Limpieza Escenario 2
DELETE FROM DOCUMENTOS_DE_PAGO WHERE SERIE = 'F001' AND NUMERO = '99999999';


-- ==========================================================================
-- ESCENARIO 3: PRUEBA DE TIPO INCORRECTO
-- ==========================================================================
-- 1. Pedimos un tipo que no existe, ejemplo 'Z'
CALL sp_generar_correlativo_pago('Z', @serie_res, @num_res);

-- 2. VERIFICACIÓN
-- Resultado Esperado: NULL - NULL
SELECT 'Escenario 3' AS Test, @serie_res AS Serie, @num_res AS Numero,
       CASE WHEN @serie_res IS NULL THEN '✅ ÉXITO' ELSE '❌ FALLÓ' END AS Resultado;

-- Borramos la boleta que usamos para probar el límite 99999999
-- DELETE FROM DOCUMENTOS_DE_PAGO 
-- WHERE SERIE = 'B001' AND NUMERO = '99999999';



-- 1. Ejecutas el procedimiento (MySQL guarda el resultado en las variables @Serie y @Numero)
CALL sp_generar_correlativo_pago('F', @Serie, @Numero);

-- 2. ¡ESTO ES LO QUE TE FALTA!
-- Haces un SELECT a las variables para ver su contenido en una tabla
SELECT @Serie AS Serie_Generada, @Numero AS Numero_Generado;

       