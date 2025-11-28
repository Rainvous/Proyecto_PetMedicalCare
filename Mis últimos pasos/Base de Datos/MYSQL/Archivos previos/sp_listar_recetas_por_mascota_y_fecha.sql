USE petmedicalcare;

DROP PROCEDURE IF EXISTS sp_listar_recetas_por_mascota_y_fecha;



DELIMITER $$

CREATE PROCEDURE sp_listar_recetas_por_mascota_y_fecha(

    IN p_mascota_id INT,

    IN p_fecha VARCHAR(20) -- Recibe 'yyyy-MM-dd'

)

BEGIN

    DECLARE v_fecha_filtro DATE;

    SET v_fecha_filtro = DATE(p_fecha);



    SELECT 

        r.RECETA_MEDICA_ID,

        r.CITA_ID,

        r.FECHA_EMISION,

        r.VIGENCIA_HASTA,

        r.DIAGNOSTICO,

        r.OBSERVACIONES,

        r.ACTIVO

    FROM RECETAS_MEDICAS AS r

    JOIN CITAS_ATENCION AS c ON r.CITA_ID = c.CITA_ID

    WHERE 

        c.MASCOTA_ID = p_mascota_id

        AND r.FECHA_EMISION = v_fecha_filtro

    ORDER BY r.FECHA_EMISION DESC;

END$$

DELIMITER ;