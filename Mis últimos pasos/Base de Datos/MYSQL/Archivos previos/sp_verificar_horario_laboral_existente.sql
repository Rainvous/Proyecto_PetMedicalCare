USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_verificar_horario_laboral_existente;

DELIMITER $$
CREATE PROCEDURE sp_verificar_horario_laboral_existente(
    IN p_fecha DATE,
    IN p_veterinario_id INT,
    OUT p_horario_id INT
)
BEGIN
    -- 1. Inicializamos el valor de retorno en 0 (Asumimos que NO existe)
    SET p_horario_id = 0;

    -- 2. Buscamos el ID. 
    -- Si existe, MySQL guardará el valor en p_horario_id.
    -- Si no existe, la consulta no devuelve nada y p_horario_id se queda en 0.
    SELECT HORARIO_LABORAL_ID 
    INTO p_horario_id
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = p_veterinario_id 
      AND FECHA = p_fecha
    LIMIT 1; -- Por seguridad, tomamos solo 1
END$$
DELIMITER ;


-- CASO 1: Horario que SÍ existe (Ana Pérez el 2025-11-03)
-- Debería devolver el ID del horario (ej. 1, 2, 5...)
SET @resultado_existe = 0;
CALL sp_verificar_horario_laboral_existente(
    '2025-11-03', 
    (SELECT VETERINARIO_ID FROM VETERINARIOS V JOIN PERSONAS P ON P.PERSONA_ID=V.PERSONA_ID WHERE P.NOMBRE='Ana Pérez'),
    @resultado_existe
);
SELECT @resultado_existe AS 'ID_Encontrado';


-- CASO 2: Horario que NO existe (Cualquier fecha futura o vet sin horario)
-- Debería devolver 0
SET @resultado_no_existe = 0;
CALL sp_verificar_horario_laboral_existente(
    '2025-12-31', 
    1, 
    @resultado_no_existe
);
SELECT @resultado_no_existe AS 'ID_No_Encontrado';