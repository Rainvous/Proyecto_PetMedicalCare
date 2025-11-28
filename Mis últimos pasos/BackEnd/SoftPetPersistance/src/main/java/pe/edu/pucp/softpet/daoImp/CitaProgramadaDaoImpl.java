package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.softpet.dto.citas.CitaProgramadaDto;

public class CitaProgramadaDaoImpl extends DaoImplBaseProcedures {

    private CitaProgramadaDto citaProg;

    public CitaProgramadaDaoImpl() {
        citaProg = null;
    }

    private void InstanciarObjetoCitaProgramada() throws SQLException {
        citaProg = new CitaProgramadaDto();
        citaProg.setFecha(this.resultSet.getString(1));
        citaProg.setEstaProgramada(this.resultSet.getBoolean(2));
    }

    private void AgregarObjetoCitaALaLista(Object objetoParametros) {
        List<CitaProgramadaDto> lista = (List<CitaProgramadaDto>) objetoParametros;
        try {
            this.InstanciarObjetoCitaProgramada();
            lista.add(this.citaProg);
        } catch (SQLException ex) {
            System.err.println("No se instancio bien el objeto");
        }
    }

    public ArrayList<CitaProgramadaDto> ListarProgramadas(int idVeterinario, Date fechaDeCitas) {
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
        parametrosEntrada.put(1, idVeterinario);
        parametrosEntrada.put(2, fechaDeCitas);
        String sql = "sp_generar_horario_disponible";

        return (ArrayList<CitaProgramadaDto>) this.ejecutarProcedimientoLectura(sql, parametrosEntrada, this::AgregarObjetoCitaALaLista);
    }
}
