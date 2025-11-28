USE petmedicalcare;
GO

IF OBJECT_ID('sp_guardar_horario_laboral', 'P') IS NOT NULL
    DROP PROCEDURE sp_guardar_horario_laboral;
GO

CREATE PROCEDURE sp_guardar_horario_laboral
    @p_veterinario_id INT,
    @p_fecha DATE,
    @p_hora_inicio DATETIME,
    @p_hora_fin DATETIME,
    @p_activo TINYINT
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @v_existe INT;

    -- 1. Verificamos si ya existe un horario para ese veterinario en esa fecha exacta
    SELECT @v_existe = COUNT(*)
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = @p_veterinario_id 
      AND FECHA = @p_fecha;

    -- 2. Lógica UPSERT
    IF @v_existe > 0
    BEGIN
        -- Si existe, SOBREESCRIBIMOS el horario de ese día
        UPDATE HORARIOS_LABORALES
        SET 
            HORA_INICIO = @p_hora_inicio,
            HORA_FIN = @p_hora_fin,
            ESTADO = 'DISPONIBLE',
            ACTIVO = @p_activo
        WHERE VETERINARIO_ID = @p_veterinario_id AND FECHA = @p_fecha;
    END
    ELSE
    BEGIN
        -- Si no existe, CREAMOS uno nuevo
        INSERT INTO HORARIOS_LABORALES 
        (VETERINARIO_ID, FECHA, ESTADO, HORA_INICIO, HORA_FIN, ACTIVO)
        VALUES 
        (@p_veterinario_id, @p_fecha, 'DISPONIBLE', @p_hora_inicio, @p_hora_fin, @p_activo);
    END
END;
GO