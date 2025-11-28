USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_buscar_personas_avanzada;

DELIMITER $$
CREATE PROCEDURE `sp_buscar_personas_avanzada`(
    IN p_nombre         VARCHAR(120),
    IN p_nro_documento  VARCHAR(20),
    IN p_ruc            VARCHAR(11),
    IN p_telefono       VARCHAR(20),
    IN p_activo         VARCHAR(2)  -- <-- NUEVO PARÁMETRO ('1', '0', o NULL/'')
)
BEGIN
    -- Normaliza entradas: '' => NULL
    SET p_nombre        = NULLIF(TRIM(p_nombre), '');
    SET p_telefono      = NULLIF(TRIM(p_telefono), '');
    SET p_nro_documento = TRIM(p_nro_documento);
    SET p_ruc           = TRIM(p_ruc);
    SET p_activo        = NULLIF(TRIM(p_activo), ''); -- <-- NUEVA NORMALIZACIÓN

    -- Lógica especial para '0' en DNI/RUC
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
        AND (p_activo        IS NULL OR ACTIVO = p_activo) -- <-- NUEVA CONDICIÓN
    ORDER BY NOMBRE ASC, PERSONA_ID ASC;
END$$
DELIMITER ;


-- Buscar solo Activos ('1')
CALL sp_buscar_personas_avanzada(NULL, NULL, NULL, NULL, '1');
-- Caso 2: Buscar solo Inactivos ('0')
CALL sp_buscar_personas_avanzada(NULL, NULL, NULL, NULL, '0');
-- Caso 3: Búsqueda combinada (Activos que se llamen 'Carlos')
CALL sp_buscar_personas_avanzada('Carlos', NULL, NULL, NULL, '1');
-- Caso 4: Sin filtros (retorna todo)
CALL sp_buscar_personas_avanzada('', '', '', '', '');