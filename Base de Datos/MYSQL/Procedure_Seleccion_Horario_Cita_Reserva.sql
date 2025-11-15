USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_generar_horario_disponible;

DELIMITER $$
CREATE PROCEDURE sp_generar_horario_disponible(
    IN p_veterinario_id INT,
    IN p_fecha DATE
)
BEGIN
    -- 1. Declarar variables para el rango laboral del día
    DECLARE v_hora_inicio DATETIME;
    DECLARE v_hora_fin DATETIME;
    DECLARE v_slot_actual DATETIME;

    -- 2. Crear Tablas Temporales
    -- Tabla 1: Para guardar TODOS los slots generados (8:00, 9:00, 10:00...)
    DROP TEMPORARY TABLE IF EXISTS TempSlotsGenerados;
    CREATE TEMPORARY TABLE TempSlotsGenerados (
        SlotStart DATETIME PRIMARY KEY
    );

    -- Tabla 2: Para guardar las citas YA RESERVADAS (9:00, 14:00...)
    DROP TEMPORARY TABLE IF EXISTS TempSlotsReservados;
    CREATE TEMPORARY TABLE TempSlotsReservados (
        SlotStart DATETIME PRIMARY KEY
    );

    -- 3. Obtener el horario laboral del veterinario para ese día
    -- (Asumimos que HORA_INICIO y HORA_FIN ya son DATETIMES completos)
    SELECT HORA_INICIO, HORA_FIN
    INTO v_hora_inicio, v_hora_fin
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = p_veterinario_id
      AND FECHA = p_fecha
      AND ESTADO = 'DISPONIBLE'
    LIMIT 1;

    -- 4. Si se encontró un horario, generar los slots de 1 hora
    IF v_hora_inicio IS NOT NULL AND v_hora_fin IS NOT NULL THEN
        SET v_slot_actual = v_hora_inicio;

        -- Bucle: Inserta un slot por cada hora desde el inicio hasta el fin
        WHILE v_slot_actual < v_hora_fin DO
            INSERT INTO TempSlotsGenerados (SlotStart) VALUES (v_slot_actual);
            -- Avanza al siguiente slot de 1 hora
            SET v_slot_actual = v_slot_actual + INTERVAL 1 HOUR;
        END WHILE;
    END IF;

    -- 5. Obtener las citas ya reservadas (que no estén canceladas)
    INSERT INTO TempSlotsReservados (SlotStart)
    SELECT FECHA_HORA_INICIO
    FROM CITAS_ATENCION
    WHERE VETERINARIO_ID = p_veterinario_id
      AND DATE(FECHA_HORA_INICIO) = p_fecha
      -- Ignoramos citas 'CANCELADA'
      AND ESTADO_CITA != 'CANCELADA'; 

    -- 6. Generar el resultado final (La lógica que pediste)
    -- Comparamos la lista de TODOS los slots (Generados)
    -- con la lista de slots RESERVADOS
    SELECT
        ts.SlotStart AS Hora,
        -- (tb.SlotStart IS NULL) devuelve 1 (true) si no hay reserva,
        -- y 0 (false) si SÍ hay una reserva (porque el JOIN encontró algo).
        (tb.SlotStart IS NULL) AS Disponible
    FROM TempSlotsGenerados AS ts
    LEFT JOIN TempSlotsReservados AS tb ON ts.SlotStart = tb.SlotStart
    ORDER BY ts.SlotStart;

    -- 7. Limpiar
    DROP TEMPORARY TABLE IF EXISTS TempSlotsGenerados;
    DROP TEMPORARY TABLE IF EXISTS TempSlotsReservados;

END$$
DELIMITER ;


-- Primero, necesitamos insertar un horario laboral (ya que DML_MEJORADO.sql inserta uno para el 27/10).

-- 1. Damos de alta un horario para la Vet 'Ana Pérez' para el 03-NOV
-- Ella trabaja de 9:00 AM a 5:00 PM (17:00)
INSERT INTO HORARIOS_LABORALES (VETERINARIO_ID, FECHA, ESTADO, HORA_INICIO, HORA_FIN, ACTIVO)
VALUES (
    (SELECT V.VETERINARIO_ID FROM VETERINARIOS V JOIN PERSONAS P ON P.PERSONA_ID=V.PERSONA_ID WHERE P.NOMBRE='Ana Pérez'),
    '2025-11-03', 
    'DISPONIBLE',
    '2025-11-03 09:00:00',
    '2025-11-03 17:00:00',
    1
);

-- Ahora, llamamos al procedimiento. (Recuerda que en tu DML, Ana Pérez SÍ tiene una cita el 2025-11-03 a las 14:00:00 con 'Paco').
-- 2. Llamamos al procedimiento para Ana Pérez ese día
CALL sp_generar_horario_disponible(
    (SELECT V.VETERINARIO_ID FROM VETERINARIOS V JOIN PERSONAS P ON P.PERSONA_ID=V.PERSONA_ID WHERE P.NOMBRE='Ana Pérez'),
    '2025-11-03'
);




