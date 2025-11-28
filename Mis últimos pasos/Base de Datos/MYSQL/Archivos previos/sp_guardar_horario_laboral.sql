USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_guardar_horario_laboral;

DELIMITER $$
CREATE PROCEDURE sp_guardar_horario_laboral(
    IN p_veterinario_id INT,
    IN p_fecha DATE,
    IN p_hora_inicio DATETIME,
    IN p_hora_fin DATETIME,
    IN p_activo TINYINT
)
BEGIN
    DECLARE v_existe INT;

    -- 1. Verificamos si ya existe un horario para ese veterinario en esa fecha exacta
    SELECT COUNT(*) INTO v_existe
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = p_veterinario_id 
      AND FECHA = p_fecha;

    -- 2. Lógica UPSERT
    IF v_existe > 0 THEN
        -- Si existe, SOBREESCRIBIMOS el horario de ese día
        UPDATE HORARIOS_LABORALES
        SET 
            HORA_INICIO = p_hora_inicio,
            HORA_FIN = p_hora_fin,
            ESTADO = 'DISPONIBLE',
            ACTIVO = p_activo
        WHERE VETERINARIO_ID = p_veterinario_id AND FECHA = p_fecha;
    ELSE
        -- Si no existe, CREAMOS uno nuevo
        INSERT INTO HORARIOS_LABORALES 
        (VETERINARIO_ID, FECHA, ESTADO, HORA_INICIO, HORA_FIN, ACTIVO)
        VALUES 
        (p_veterinario_id, p_fecha, 'DISPONIBLE', p_hora_inicio, p_hora_fin, p_activo);
    END IF;
END$$
DELIMITER ;