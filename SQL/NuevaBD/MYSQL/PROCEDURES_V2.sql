-- ===============================================
-- BUSCAR PERSONASS AVANZADOS
-- ===============================================
use tu_esquema;
DROP PROCEDURE IF EXISTS sp_buscar_personas_avanzada;
DELIMITER $$
CREATE PROCEDURE `sp_buscar_personas_avanzada`(
    IN p_nombre         VARCHAR(120),
    IN p_nro_documento  VARCHAR(20),
    IN p_ruc            VARCHAR(11),
    IN p_telefono       VARCHAR(20)
)
BEGIN
    -- Normaliza entradas: '' y '0' => NULL (sin filtro), trim a todo
    SET p_nombre        = NULLIF(TRIM(p_nombre), '');
    SET p_telefono      = NULLIF(TRIM(p_telefono), '');
    SET p_nro_documento = TRIM(p_nro_documento);
    SET p_ruc           = TRIM(p_ruc);

    IF p_nro_documento IS NULL OR p_nro_documento = '' OR p_nro_documento = '0' THEN
        SET p_nro_documento = NULL;
    END IF;

    IF p_ruc IS NULL OR p_ruc = '' OR p_ruc = '0' THEN
        SET p_ruc = NULL;
    END IF;

    SELECT
        PERSONA_ID,
        NOMBRE,
        DIRECCION,
        TELEFONO,
        SEXO,
        ACTIVO,
        TIPO_DOCUMENTO,
        NRO_DOCUMENTO,
        RUC,
        USUARIO_ID
    FROM PERSONAS
    WHERE
        (p_nombre        IS NULL OR NOMBRE        LIKE CONCAT('%', p_nombre, '%'))
        AND (p_nro_documento IS NULL OR NRO_DOCUMENTO LIKE CONCAT('%', p_nro_documento, '%'))
        AND (p_ruc           IS NULL OR RUC           LIKE CONCAT('%', p_ruc, '%'))
        AND (p_telefono      IS NULL OR TELEFONO      LIKE CONCAT('%', p_telefono, '%'))
    ORDER BY NOMBRE ASC, PERSONA_ID ASC;
END$$

DELIMITER ;
-- ===============================================
-- PRUEBAS
-- ===============================================
-- Contiene en nro_documento (ej.: '234', '91234', '2349', etc.)
CALL sp_buscar_personas_avanzada(NULL, '234', NULL, NULL);

-- Contiene en RUC (ej.: '2011', '2048', etc.)
CALL sp_buscar_personas_avanzada('', NULL, '2011', '');

-- Combinar: nombre parcial y documento conteniendo '789'
CALL sp_buscar_personas_avanzada('ana', '789', NULL, '');

-- Sin filtros (retorna todo)
CALL sp_buscar_personas_avanzada('', '0', '0', '');
