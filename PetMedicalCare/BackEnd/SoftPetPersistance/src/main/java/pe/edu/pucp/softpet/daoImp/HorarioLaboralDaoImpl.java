package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.HorarioLaboralDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.personas.HorarioLaboralDto;
import pe.edu.pucp.softpet.dto.personas.VeterinarioDto;
import pe.edu.pucp.softpet.dto.util.enums.EstadoLaboral;

public class HorarioLaboralDaoImpl extends DaoBaseImpl implements HorarioLaboralDao {

    private HorarioLaboralDto horario;

    public HorarioLaboralDaoImpl() {
        super("HORARIOS_LABORALES");
        this.horario = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("HORARIO_LABORAL_ID", true, true));
        this.listaColumnas.add(new Columna("VETERINARIO_ID", false, false));
        this.listaColumnas.add(new Columna("FECHA", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("HORA_INICIO", false, false));
        this.listaColumnas.add(new Columna("HORA_FIN", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.horario.getVeterinario().getVeterinarioId());
        this.statement.setDate(2, this.horario.getFecha());
        this.statement.setString(3, this.horario.getEstado().toString());
        this.statement.setDate(4, this.horario.getHoraInicio());
        this.statement.setDate(5, this.horario.getHoraFin());
        this.statement.setInt(6, this.horario.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.horario.getVeterinario().getVeterinarioId());
        this.statement.setDate(2, this.horario.getFecha());
        this.statement.setString(3, this.horario.getEstado().toString());
        this.statement.setDate(4, this.horario.getHoraInicio());
        this.statement.setDate(5, this.horario.getHoraFin());
        this.statement.setInt(6, this.horario.getActivo() ? 1 : 0);

        this.statement.setInt(7, this.horario.getHorarioLaboralId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.horario.getHorarioLaboralId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.horario.getHorarioLaboralId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.horario = new HorarioLaboralDto();
        this.horario.setHorarioLaboralId(this.resultSet.getInt("HORARIO_LABORAL_ID"));
        VeterinarioDto vet = new VeterinarioDto();
        vet.setVeterinarioId(this.resultSet.getInt("VETERINARIO_ID"));
        this.horario.setVeterinario(vet);
        this.horario.setFecha(this.resultSet.getDate("FECHA"));
        this.horario.setEstado(EstadoLaboral.valueOf(this.resultSet.getString("ESTADO")));
        this.horario.setHoraInicio(this.resultSet.getDate("HORA_INICIO"));
        this.horario.setHoraFin(this.resultSet.getDate("HORA_FIN"));
        this.horario.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

//    protected String sacarEstado(String data) {
//        if (data.equals(EstadoLaboral.DISPONIBLE.toString()) || data.equals("disponible")) {
//            return EstadoLaboral.DISPONIBLE.toString();
//        } else {
//            return EstadoLaboral.NO_DISPONIBLE.toString();
//        }
//    }
    @Override
    protected void limpiarObjetoDelResultSet() {
        this.horario = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.horario);
    }

    @Override
    public Integer insertar(HorarioLaboralDto entity) {
        this.horario = entity;
        return super.insertar();
    }

    @Override
    public HorarioLaboralDto obtenerPorId(Integer id) {
        this.horario = new HorarioLaboralDto();
        this.horario.setHorarioLaboralId(id);
        super.obtenerPorId();
        return this.horario;
    }

    @Override
    public ArrayList<HorarioLaboralDto> listarTodos() {
        return (ArrayList<HorarioLaboralDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(HorarioLaboralDto entity) {
        this.horario = entity;
        return super.modificar();
    }

    @Override
    public Integer eliminar(HorarioLaboralDto entity) {
        this.horario = entity;
        return super.eliminar();
    }
}
