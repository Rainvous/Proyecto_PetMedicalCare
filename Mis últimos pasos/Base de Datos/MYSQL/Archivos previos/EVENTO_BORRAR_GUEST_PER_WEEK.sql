SELECT * FROM petmedicalcare.USUARIOS;
SELECT * FROM petmedicalcare.ROLES_USUARIO;
SELECT * FROM petmedicalcare.PERSONAS;
SELECT * FROM petmedicalcare.DETALLES_DOCUMENTO_DE_PAGO;
USE petmedicalcare;

USE petmedicalcare;

USE petmedicalcare;

-- 1) Asegúrate de que el scheduler esté encendido (solo una vez en el servidor)

-- 2) Creamos el evento mensual
DELIMITER //

CREATE EVENT ev_purge_guest_users
ON SCHEDULE EVERY 1 MONTH
STARTS '2025-12-01 00:00:00'
DO
BEGIN
    -- Tabla temporal con usuarios/personas GUEST
    CREATE TEMPORARY TABLE tmp_guest_users (
        USUARIO_ID INT PRIMARY KEY,
        PERSONA_ID INT
    );

    INSERT INTO tmp_guest_users (USUARIO_ID, PERSONA_ID)
    SELECT DISTINCT u.USUARIO_ID, p.PERSONA_ID
    FROM PERSONAS p
    JOIN USUARIOS u       ON p.USUARIO_ID = u.USUARIO_ID
    JOIN ROLES_USUARIO ru ON u.USUARIO_ID = ru.USUARIO_ID
    JOIN ROLES r          ON ru.ROL_ID    = r.ROL_ID
    WHERE r.NOMBRE = 'GUEST';

    -- 1) Poner PERSONA_ID en NULL en los comprobantes de esos GUEST
    UPDATE DOCUMENTOS_DE_PAGO dp
    JOIN tmp_guest_users g ON dp.PERSONA_ID = g.PERSONA_ID
    SET dp.PERSONA_ID = NULL;

    -- 2) Borrar las asignaciones de rol
    DELETE ru
    FROM ROLES_USUARIO ru
    JOIN tmp_guest_users g ON ru.USUARIO_ID = g.USUARIO_ID;

    -- 3) Borrar personas
    DELETE p
    FROM PERSONAS p
    JOIN tmp_guest_users g ON p.PERSONA_ID = g.PERSONA_ID;

    -- 4) Borrar usuarios
    DELETE u
    FROM USUARIOS u
    JOIN tmp_guest_users g ON u.USUARIO_ID = g.USUARIO_ID;

    DROP TEMPORARY TABLE tmp_guest_users;
END//

DELIMITER ;


