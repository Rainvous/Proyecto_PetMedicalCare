USE petmedicalcare;
DROP PROCEDURE IF EXISTS sp_listar_solo_clientes;

DELIMITER $$
CREATE PROCEDURE sp_listar_solo_clientes()
BEGIN
    SELECT
        P.PERSONA_ID,
        P.USUARIO_ID,
        P.NOMBRE,
        P.DIRECCION,
        P.TELEFONO,
        P.SEXO,
        P.NRO_DOCUMENTO,
        P.RUC,
        P.TIPO_DOCUMENTO,
        P.ACTIVO
    FROM PERSONAS AS P
    WHERE
        -- Usamos NOT EXISTS para excluir eficientemente a los empleados.
        -- Buscamos a la persona que NO tenga un rol de staff.
        NOT EXISTS (
            SELECT 1
            FROM ROLES_USUARIO AS RU
            JOIN ROLES AS R ON RU.ROL_ID = R.ROL_ID
            WHERE
                RU.USUARIO_ID = P.USUARIO_ID
                AND R.NOMBRE IN ('ADMIN', 'VET', 'RECEPCION')
        );
END$$
DELIMITER ;


CALL sp_listar_solo_clientes();