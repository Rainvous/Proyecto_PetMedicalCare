use petmedicalcare;
DELIMITER $$

CREATE PROCEDURE sp_buscar_personas_avanzada(
    IN p_nombre        VARCHAR(100),
    IN p_nro_documento INT,
    IN p_ruc           INT,
    IN p_telefono      VARCHAR(100)
)
BEGIN
    -- Primero declara todas las variables
    DECLARE v_doc_pat VARCHAR(32);
    DECLARE v_ruc_pat VARCHAR(32);

    -- Luego haz los SET o IF
    SET p_nombre   = NULLIF(TRIM(p_nombre), '');
    SET p_telefono = NULLIF(TRIM(p_telefono), '');

    IF p_nro_documento IS NULL OR p_nro_documento = 0 THEN
        SET v_doc_pat = NULL;
    ELSE
        SET v_doc_pat = CONCAT('%', CAST(p_nro_documento AS CHAR), '%');
    END IF;

    IF p_ruc IS NULL OR p_ruc = 0 THEN
        SET v_ruc_pat = NULL;
    ELSE
        SET v_ruc_pat = CONCAT('%', CAST(p_ruc AS CHAR), '%');
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
        (p_nombre   IS NULL OR NOMBRE LIKE CONCAT('%', p_nombre, '%'))
        AND (v_doc_pat IS NULL OR CAST(NRO_DOCUMENTO AS CHAR) LIKE v_doc_pat)
        AND (v_ruc_pat IS NULL OR CAST(RUC AS CHAR) LIKE v_ruc_pat)
        AND (p_telefono IS NULL OR TELEFONO LIKE CONCAT('%', p_telefono, '%'))
    ORDER BY NOMBRE ASC, PERSONA_ID ASC;
END$$

DELIMITER ;

-- Contiene en nro_documento (p. ej., 12345678, 91234, 2349, etc.)
CALL sp_buscar_personas_avanzada(NULL, 234, 0, NULL);

-- Contiene en RUC (p. ej., 20481234..., 301234..., etc.)
CALL sp_buscar_personas_avanzada('', 0, 2011, '');

-- Combinar: nombre parcial y documento conteniendo '789'
CALL sp_buscar_personas_avanzada('ana', 0, 0, '');

-- Sin filtros (retorna todo)
CALL sp_buscar_personas_avanzada('', 0, 0, '');
