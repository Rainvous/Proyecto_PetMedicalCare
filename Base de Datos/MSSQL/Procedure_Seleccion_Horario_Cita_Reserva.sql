USE petmedicalcare;
GO

IF OBJECT_ID('sp_generar_horario_disponible', 'P') IS NOT NULL
    DROP PROCEDURE sp_generar_horario_disponible;
GO

CREATE PROCEDURE sp_generar_horario_disponible
    @p_veterinario_id INT,
    @p_fecha DATE
AS
BEGIN
    SET NOCOUNT ON;

    -- 1. Declarar variables
    DECLARE @v_hora_inicio DATETIME;
    DECLARE @v_hora_fin DATETIME;
    DECLARE @v_slot_actual DATETIME;

    -- 2. Manejo de Tablas Temporales (Limpieza previa)
    IF OBJECT_ID('tempdb..#TempSlotsGenerados') IS NOT NULL DROP TABLE #TempSlotsGenerados;
    CREATE TABLE #TempSlotsGenerados ( SlotStart DATETIME PRIMARY KEY );

    IF OBJECT_ID('tempdb..#TempSlotsReservados') IS NOT NULL DROP TABLE #TempSlotsReservados;
    CREATE TABLE #TempSlotsReservados ( SlotStart DATETIME PRIMARY KEY );

    -- 3. Obtener el horario laboral
    SELECT TOP 1 
        @v_hora_inicio = HORA_INICIO, 
        @v_hora_fin = HORA_FIN
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = @p_veterinario_id
      AND FECHA = @p_fecha
      AND ESTADO = 'DISPONIBLE';

    -- 4. Generar slots (Si hay horario)
    IF @v_hora_inicio IS NOT NULL AND @v_hora_fin IS NOT NULL
    BEGIN
        SET @v_slot_actual = @v_hora_inicio;

        WHILE @v_slot_actual < @v_hora_fin
        BEGIN
            INSERT INTO #TempSlotsGenerados (SlotStart) VALUES (@v_slot_actual);
            -- Avanzar 1 hora
            SET @v_slot_actual = DATEADD(HOUR, 1, @v_slot_actual);
        END
    END

    -- 5. Obtener citas reservadas
    INSERT INTO #TempSlotsReservados (SlotStart)
    SELECT FECHA_HORA_INICIO
    FROM CITAS_ATENCION
    WHERE VETERINARIO_ID = @p_veterinario_id
      AND CAST(FECHA_HORA_INICIO AS DATE) = @p_fecha
      AND ESTADO_CITA != 'CANCELADA';

    -- 6. Resultado Final
    SELECT
        ts.SlotStart AS Hora,
        -- Simulación de booleano (1=True, 0=False)
        CASE WHEN tb.SlotStart IS NULL THEN 1 ELSE 0 END AS Disponible
    FROM #TempSlotsGenerados AS ts
    LEFT JOIN #TempSlotsReservados AS tb ON ts.SlotStart = tb.SlotStart
    ORDER BY ts.SlotStart;

    -- 7. Limpieza final (Opcional, SQL Server las borra al terminar la sesión)
    DROP TABLE #TempSlotsGenerados;
    DROP TABLE #TempSlotsReservados;
END
GO



-- Asegúrate de tener un horario insertado para probar
EXEC sp_generar_horario_disponible 1, '2025-11-03';