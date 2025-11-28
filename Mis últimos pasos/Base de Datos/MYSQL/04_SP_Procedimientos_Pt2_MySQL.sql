USE petmedicalcare;

-- =============================================
-- 11. VERIFICAR HORARIO LABORAL EXISTENTE
-- =============================================
DROP PROCEDURE IF EXISTS sp_verificar_horario_laboral_existente;
DELIMITER $$
CREATE PROCEDURE sp_verificar_horario_laboral_existente(
    IN p_fecha DATE,
    IN p_veterinario_id INT,
    OUT p_horario_id INT
)
BEGIN
    SET p_horario_id = 0;

    SELECT HORARIO_LABORAL_ID 
    INTO p_horario_id
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = p_veterinario_id 
      AND FECHA = p_fecha
    LIMIT 1;
END$$
DELIMITER ;

-- =============================================
-- 12. GUARDAR HORARIO LABORAL (UPSERT)
-- =============================================
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

    SELECT COUNT(*) INTO v_existe
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = p_veterinario_id 
      AND FECHA = p_fecha;

    IF v_existe > 0 THEN
        UPDATE HORARIOS_LABORALES
        SET 
            HORA_INICIO = p_hora_inicio,
            HORA_FIN = p_hora_fin,
            ESTADO = 'DISPONIBLE',
            ACTIVO = p_activo
        WHERE VETERINARIO_ID = p_veterinario_id AND FECHA = p_fecha;
    ELSE
        INSERT INTO HORARIOS_LABORALES 
        (VETERINARIO_ID, FECHA, ESTADO, HORA_INICIO, HORA_FIN, ACTIVO)
        VALUES 
        (p_veterinario_id, p_fecha, 'DISPONIBLE', p_hora_inicio, p_hora_fin, p_activo);
    END IF;
END$$
DELIMITER ;

-- =============================================
-- 13. GENERAR HORARIO DISPONIBLE (Tablas Temporales)
-- =============================================
USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_generar_horario_disponible;

DELIMITER $$
CREATE PROCEDURE sp_generar_horario_disponible(
    IN p_veterinario_id INT,
    IN p_fecha DATE
)
BEGIN
    -- Variables para el horario REAL del veterinario
    DECLARE v_vet_inicio DATETIME;
    DECLARE v_vet_fin DATETIME;
    
    -- Variables para generar la grilla base (Horario Clínica 8:00 - 17:00)
    DECLARE v_clinica_apertura DATETIME;
    DECLARE v_clinica_cierre DATETIME;
    DECLARE v_slot_actual DATETIME;

    -- 1. Definir horario base de la clínica (Para que siempre genere filas)
    SET v_clinica_apertura = CAST(CONCAT(p_fecha, ' 08:00:00') AS DATETIME);
    SET v_clinica_cierre   = CAST(CONCAT(p_fecha, ' 17:00:00') AS DATETIME);

    -- 2. Tablas Temporales
    DROP TEMPORARY TABLE IF EXISTS TempSlotsGenerados;
    CREATE TEMPORARY TABLE TempSlotsGenerados ( SlotStart DATETIME PRIMARY KEY );

    DROP TEMPORARY TABLE IF EXISTS TempSlotsReservados;
    CREATE TEMPORARY TABLE TempSlotsReservados ( SlotStart DATETIME PRIMARY KEY );

    -- 3. Generar SIEMPRE los slots base (8am a 5pm)
    SET v_slot_actual = v_clinica_apertura;
    WHILE v_slot_actual < v_clinica_cierre DO
        INSERT INTO TempSlotsGenerados (SlotStart) VALUES (v_slot_actual);
        SET v_slot_actual = v_slot_actual + INTERVAL 1 HOUR;
    END WHILE;

    -- 4. Obtener horario ESPECÍFICO del veterinario (puede ser NULL si no trabaja)
    SELECT HORA_INICIO, HORA_FIN
    INTO v_vet_inicio, v_vet_fin
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = p_veterinario_id
      AND FECHA = p_fecha
      AND ESTADO = 'DISPONIBLE'
    LIMIT 1;

    -- 5. Obtener citas ocupadas
    INSERT INTO TempSlotsReservados (SlotStart)
    SELECT FECHA_HORA_INICIO
    FROM CITAS_ATENCION
    WHERE VETERINARIO_ID = p_veterinario_id
      AND DATE(FECHA_HORA_INICIO) = p_fecha
      AND ESTADO_CITA != 'CANCELADA'; 

    -- 6. Lógica final: Disponible (1) solo si cumple 3 condiciones:
    --    a) El vet tiene horario asignado (v_vet_inicio NOT NULL)
    --    b) La hora está dentro de su turno
    --    c) No hay cita reservada (tb.SlotStart IS NULL)
    SELECT
        ts.SlotStart AS Hora,
        CASE 
            WHEN v_vet_inicio IS NULL THEN 0  -- Vet no tiene turno hoy
            WHEN ts.SlotStart < v_vet_inicio OR ts.SlotStart >= v_vet_fin THEN 0 -- Fuera de su horario
            WHEN tb.SlotStart IS NOT NULL THEN 0 -- Ya tiene cita
            ELSE 1 -- ¡Libre!
        END AS Disponible
    FROM TempSlotsGenerados AS ts
    LEFT JOIN TempSlotsReservados AS tb ON ts.SlotStart = tb.SlotStart
    ORDER BY ts.SlotStart;

    -- Limpieza
    DROP TEMPORARY TABLE IF EXISTS TempSlotsGenerados;
    DROP TEMPORARY TABLE IF EXISTS TempSlotsReservados;
END$$
DELIMITER ;

-- =============================================
-- 14. LISTAR CITAS POR FECHA (Simple)
-- =============================================
DROP PROCEDURE IF EXISTS sp_listar_citas_por_fecha;
DELIMITER $$
CREATE PROCEDURE sp_listar_citas_por_fecha(
    IN p_fecha VARCHAR(20),
    IN p_veterinario_id VARCHAR(20)
)
BEGIN
    DECLARE v_fecha_busqueda DATE;
    DECLARE v_vet_id_str VARCHAR(20);

    SET v_fecha_busqueda = COALESCE(NULLIF(TRIM(p_fecha), ''), CURDATE());
    SET v_vet_id_str = NULLIF(TRIM(p_veterinario_id), ''); 
    SET v_vet_id_str = NULLIF(v_vet_id_str, '0');       

    SELECT c.*
    FROM CITAS_ATENCION AS c
    JOIN VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    JOIN MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN PERSONAS AS p_cli ON m.PERSONA_ID = p_cli.PERSONA_ID
    WHERE
        DATE(c.FECHA_HORA_INICIO) = v_fecha_busqueda
        AND (v_vet_id_str IS NULL OR c.VETERINARIO_ID = v_vet_id_str)
    ORDER BY c.FECHA_HORA_INICIO ASC;
END$$
DELIMITER ;

-- =============================================
-- 15. LISTAR CITAS POR FECHA (Detallada V2)
-- =============================================
DROP PROCEDURE IF EXISTS sp_listar_citas_por_fecha_2;
DELIMITER $$
CREATE PROCEDURE sp_listar_citas_por_fecha_2(
    IN p_fecha VARCHAR(20),
    IN p_veterinario_id VARCHAR(20)
)
BEGIN
    DECLARE v_fecha_busqueda DATE;
    DECLARE v_vet_id_str VARCHAR(20);

    SET v_fecha_busqueda = COALESCE(NULLIF(TRIM(p_fecha), ''), CURDATE());
    SET v_vet_id_str = NULLIF(TRIM(p_veterinario_id), ''); 
    SET v_vet_id_str = NULLIF(v_vet_id_str, '0');        

    SELECT 
        c.CITA_ID, c.FECHA_REGISTRO, c.FECHA_HORA_INICIO, c.FECHA_HORA_FIN, c.ESTADO_CITA, c.PESO_MASCOTA, c.MONTO, c.OBSERVACION, c.ACTIVO AS CITA_ACTIVO,
        v.VETERINARIO_ID, v.ESPECIALIDAD AS VETERINARIO_ESPECIALIDAD, v.ESTADO AS VETERINARIO_ESTADO_LABORAL,
        p_vet.NOMBRE AS VETERINARIO_NOMBRE, p_vet.NRO_DOCUMENTO AS VETERINARIO_DNI, p_vet.TELEFONO AS VETERINARIO_TELEFONO,
        u_vet.CORREO AS VETERINARIO_CORREO,
        m.MASCOTA_ID, m.NOMBRE AS MASCOTA_NOMBRE, m.ESPECIE AS MASCOTA_ESPECIE, m.RAZA AS MASCOTA_RAZA, m.SEXO AS MASCOTA_SEXO, m.COLOR AS MASCOTA_COLOR, m.FECHA_DEFUNCION AS MASCOTA_FECHA_DEFUNCION,
        p_cli.PERSONA_ID AS CLIENTE_PERSONA_ID, p_cli.NOMBRE AS CLIENTE_NOMBRE, p_cli.DIRECCION AS CLIENTE_DIRECCION, p_cli.TELEFONO AS CLIENTE_TELEFONO, p_cli.NRO_DOCUMENTO AS CLIENTE_NRO_DOCUMENTO, p_cli.TIPO_DOCUMENTO AS CLIENTE_TIPO_DOC,
        u_cli.CORREO AS CLIENTE_CORREO, u_cli.USERNAME AS CLIENTE_USERNAME
    FROM CITAS_ATENCION AS c
    JOIN VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    LEFT JOIN USUARIOS AS u_vet ON p_vet.USUARIO_ID = u_vet.USUARIO_ID
    JOIN MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN PERSONAS AS p_cli ON m.PERSONA_ID = p_cli.PERSONA_ID
    LEFT JOIN USUARIOS AS u_cli ON p_cli.USUARIO_ID = u_cli.USUARIO_ID
    WHERE
        DATE(c.FECHA_HORA_INICIO) = v_fecha_busqueda
        AND (v_vet_id_str IS NULL OR c.VETERINARIO_ID = v_vet_id_str)
    ORDER BY c.FECHA_HORA_INICIO ASC;
END$$
DELIMITER ;

-- =============================================
-- 16. LISTAR CITAS POR MASCOTA Y FECHA
-- =============================================
DROP PROCEDURE IF EXISTS sp_listar_citas_por_mascota_y_fecha;
DELIMITER $$
CREATE PROCEDURE sp_listar_citas_por_mascota_y_fecha(
    IN p_mascota_id INT,
    IN p_fecha VARCHAR(20)
)
BEGIN
    DECLARE v_fecha_filtro DATE;
    SET v_fecha_filtro = DATE(p_fecha);

    SELECT 
        c.CITA_ID, c.VETERINARIO_ID, c.MASCOTA_ID, c.FECHA_REGISTRO, c.FECHA_HORA_INICIO, c.FECHA_HORA_FIN, c.PESO_MASCOTA, c.MONTO, c.ESTADO_CITA, c.OBSERVACION, c.ACTIVO
    FROM CITAS_ATENCION AS c
    JOIN MASCOTAS AS m ON c.MASCOTA_ID = m.MASCOTA_ID
    JOIN VETERINARIOS AS v ON c.VETERINARIO_ID = v.VETERINARIO_ID
    JOIN PERSONAS AS p_vet ON v.PERSONA_ID = p_vet.PERSONA_ID
    WHERE 
        c.MASCOTA_ID = p_mascota_id
        AND DATE(c.FECHA_HORA_INICIO) = v_fecha_filtro
    ORDER BY c.FECHA_HORA_INICIO ASC;
END$$
DELIMITER ;

-- =============================================
-- 17. LISTAR RECETAS POR MASCOTA Y FECHA
-- =============================================
DROP PROCEDURE IF EXISTS sp_listar_recetas_por_mascota_y_fecha;
DELIMITER $$
CREATE PROCEDURE sp_listar_recetas_por_mascota_y_fecha(
    IN p_mascota_id INT,
    IN p_fecha VARCHAR(20)
)
BEGIN
    DECLARE v_fecha_filtro DATE;
    SET v_fecha_filtro = DATE(p_fecha);

    SELECT 
        r.RECETA_MEDICA_ID, r.CITA_ID, r.FECHA_EMISION, r.VIGENCIA_HASTA, r.DIAGNOSTICO, r.OBSERVACIONES, r.ACTIVO
    FROM RECETAS_MEDICAS AS r
    JOIN CITAS_ATENCION AS c ON r.CITA_ID = c.CITA_ID
    WHERE 
        c.MASCOTA_ID = p_mascota_id
        AND r.FECHA_EMISION = v_fecha_filtro
    ORDER BY r.FECHA_EMISION DESC;
END$$
DELIMITER ;

-- =============================================
-- 18. VERIFICAR RELACION MASCOTA (Para Borrado)
-- =============================================
DROP PROCEDURE IF EXISTS sp_verificar_relacion_mascota;
DELIMITER $$
CREATE PROCEDURE sp_verificar_relacion_mascota(
    IN p_mascota_id INT,
    OUT p_valorRetornado INT
)
BEGIN
    SET p_valorRetornado = 0;

    SELECT 1 INTO p_valorRetornado
    FROM CITAS_ATENCION
    WHERE MASCOTA_ID = p_mascota_id
    LIMIT 1;
END$$
DELIMITER ;

-- =============================================
-- 19. VERIFICAR RELACION PERSONA (Para Borrado)
-- =============================================
DROP PROCEDURE IF EXISTS sp_verificar_relacion_persona;
DELIMITER $$
CREATE PROCEDURE sp_verificar_relacion_persona(
    IN p_persona_id INT,
    OUT p_valorRetornado INT
)
BEGIN
    SET p_valorRetornado = 0;

    SELECT 1 INTO p_valorRetornado FROM MASCOTAS WHERE PERSONA_ID = p_persona_id LIMIT 1;

    IF p_valorRetornado = 0 THEN
        SELECT 1 INTO p_valorRetornado FROM VETERINARIOS WHERE PERSONA_ID = p_persona_id LIMIT 1;
    END IF;

    IF p_valorRetornado = 0 THEN
        SELECT 1 INTO p_valorRetornado FROM DOCUMENTOS_DE_PAGO WHERE PERSONA_ID = p_persona_id LIMIT 1;
    END IF;
END$$
DELIMITER ;

-- =============================================
-- 20. VERIFICAR RELACION PRODUCTO (Para Borrado)
-- =============================================
DROP PROCEDURE IF EXISTS sp_verificar_relacion_producto;
DELIMITER $$
CREATE PROCEDURE sp_verificar_relacion_producto(
    IN p_producto_id INT,
    OUT p_valorRetornado INT
)
BEGIN
    SET p_valorRetornado = 0;

    SELECT 1 INTO p_valorRetornado
    FROM DETALLES_DOCUMENTO_DE_PAGO
    WHERE PRODUCTO_ID = p_producto_id
    LIMIT 1;
END$$
DELIMITER ;