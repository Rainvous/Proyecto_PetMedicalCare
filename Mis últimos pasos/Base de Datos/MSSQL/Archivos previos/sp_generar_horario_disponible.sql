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
    DECLARE @v_hora_inicio_real DATETIME; 
    DECLARE @v_hora_fin_real DATETIME;    
    
    DECLARE @v_slot_actual DATETIME;
    DECLARE @v_fin_dia_laboral DATETIME;
    
    -- Variables auxiliares para conversión segura de texto
    DECLARE @fechaStr VARCHAR(20);
    DECLARE @horaInicioStr VARCHAR(20);
    DECLARE @horaFinStr VARCHAR(20);
    
    -- --- CONFIGURACIÓN DE HORARIO BASE (GRILLA VISUAL) ---
    DECLARE @v_default_start TIME = '08:00:00'; 
    DECLARE @v_default_end TIME = '17:00:00';   
    -- ---------------------------------------------------

    -- 2. Limpieza de Tablas Temporales
    IF OBJECT_ID('tempdb..#TempSlotsGenerados') IS NOT NULL DROP TABLE #TempSlotsGenerados;
    CREATE TABLE #TempSlotsGenerados ( SlotStart DATETIME PRIMARY KEY );

    IF OBJECT_ID('tempdb..#TempSlotsReservados') IS NOT NULL DROP TABLE #TempSlotsReservados;
    CREATE TABLE #TempSlotsReservados ( SlotStart DATETIME PRIMARY KEY );

    -- 3. Generar TODOS los slots del día (CORREGIDO)
    -- Usamos CONVERT con estilo para evitar el error de "Conversion failed"
    -- Estilo 23 = yyyy-mm-dd
    -- Estilo 108 = hh:mm:ss
    SET @fechaStr = CONVERT(VARCHAR(20), @p_fecha, 23); 
    SET @horaInicioStr = CONVERT(VARCHAR(20), @v_default_start, 108);
    SET @horaFinStr = CONVERT(VARCHAR(20), @v_default_end, 108);

    -- Concatenamos de forma segura: '2025-11-03' + ' ' + '08:00:00'
    SET @v_slot_actual = CAST(@fechaStr + ' ' + @horaInicioStr AS DATETIME);
    SET @v_fin_dia_laboral = CAST(@fechaStr + ' ' + @horaFinStr AS DATETIME);

    WHILE @v_slot_actual < @v_fin_dia_laboral
    BEGIN
        INSERT INTO #TempSlotsGenerados (SlotStart) VALUES (@v_slot_actual);
        SET @v_slot_actual = DATEADD(HOUR, 1, @v_slot_actual);
    END

    -- 4. Obtener el horario laboral REAL del veterinario
    SELECT TOP 1 
        @v_hora_inicio_real = HORA_INICIO, 
        @v_hora_fin_real = HORA_FIN
    FROM HORARIOS_LABORALES
    WHERE VETERINARIO_ID = @p_veterinario_id
      AND FECHA = @p_fecha
      AND ESTADO = 'DISPONIBLE';

    -- 5. Obtener las citas ya reservadas
    INSERT INTO #TempSlotsReservados (SlotStart)
    SELECT FECHA_HORA_INICIO
    FROM CITAS_ATENCION
    WHERE VETERINARIO_ID = @p_veterinario_id
      AND CAST(FECHA_HORA_INICIO AS DATE) = @p_fecha
      AND ESTADO_CITA != 'CANCELADA';

    -- 6. Resultado Final
    SELECT
        ts.SlotStart AS Hora,
        CASE 
            WHEN 
                @v_hora_inicio_real IS NOT NULL 
                AND ts.SlotStart >= @v_hora_inicio_real 
                AND ts.SlotStart < @v_hora_fin_real
                AND tb.SlotStart IS NULL 
            THEN 1 
            ELSE 0 
        END AS Disponible
    FROM #TempSlotsGenerados AS ts
    LEFT JOIN #TempSlotsReservados AS tb ON ts.SlotStart = tb.SlotStart
    ORDER BY ts.SlotStart;

    -- 7. Limpieza
    DROP TABLE #TempSlotsGenerados;
    DROP TABLE #TempSlotsReservados;
END
GO