USE petmedicalcare;
GO

IF OBJECT_ID('sp_listar_solo_clientes', 'P') IS NOT NULL
    DROP PROCEDURE sp_listar_solo_clientes;
GO

CREATE PROCEDURE sp_listar_solo_clientes
AS
BEGIN
    SET NOCOUNT ON;

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
        -- Usamos NOT EXISTS para excluir empleados
        NOT EXISTS (
            SELECT 1
            FROM ROLES_USUARIO AS RU
            JOIN ROLES AS R ON RU.ROL_ID = R.ROL_ID
            WHERE
                RU.USUARIO_ID = P.USUARIO_ID
                AND R.NOMBRE IN ('ADMIN', 'VET', 'RECEPCION')
        );
END
GO


EXEC sp_listar_solo_clientes;