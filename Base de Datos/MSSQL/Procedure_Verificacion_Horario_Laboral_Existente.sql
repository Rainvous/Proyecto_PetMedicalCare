USE petmedicalcare;
GO

IF OBJECT_ID('sp_verificar_horario_laboral_existente', 'P') IS NOT NULL
    DROP PROCEDURE sp_verificar_horario_laboral_existente;
GO

CREATE PROCEDURE sp_verificar_horario_laboral_existente
    @p_fecha DATE,
    @p_veterinario_id INT,
    @p_horario_id INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Inicializar en 0
    SET @p_horario_id = 0;

    -- 2. Buscar ID
    SELECT TOP 1 @p_horario_id = HORARIO_LABORAL_ID
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = @p_veterinario_id 
      AND FECHA = @p_fecha;
END
GO


DECLARE @id_horario INT;
EXEC sp_verificar_horario_laboral_existente '2025-11-03', 1, @id_horario OUTPUT;
SELECT @id_horario AS Horario_ID_Encontrado;