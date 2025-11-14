package pe.edu.pucp.softpet.daoImp;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.mascotas.MascotaDto;
import pe.edu.pucp.softpet.dao.MascotaDao;
import pe.edu.pucp.softpet.dto.personas.PersonaDto;

public class MascotaDaoImpl extends DaoBaseImpl implements MascotaDao {

    private MascotaDto mascota;

    public MascotaDaoImpl() {
        super("MASCOTAS");
        this.mascota = null;
        this.retornarLlavePrimaria = true;
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("MASCOTA_ID", true, true));
        this.listaColumnas.add(new Columna("PERSONA_ID", false, false));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("ESPECIE", false, false));
        this.listaColumnas.add(new Columna("SEXO", false, false));
        this.listaColumnas.add(new Columna("RAZA", false, false));
        this.listaColumnas.add(new Columna("COLOR", false, false));
        this.listaColumnas.add(new Columna("FECHA_DEFUNCION", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        this.statement.setInt(1, this.mascota.getPersona().getPersonaId());
        this.statement.setString(2, this.mascota.getNombre());
        this.statement.setString(3, this.mascota.getEspecie());
        this.statement.setString(4, this.mascota.getSexo());
        this.statement.setString(5, this.mascota.getRaza());
        this.statement.setString(6, this.mascota.getColor());
        this.statement.setDate(7, (Date) this.mascota.getFechaDefuncion());
        this.statement.setInt(8, this.mascota.getActivo() ? 1 : 0);
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setInt(1, this.mascota.getPersona().getPersonaId());
        this.statement.setString(2, this.mascota.getNombre());
        this.statement.setString(3, this.mascota.getEspecie());
        this.statement.setString(4, this.mascota.getSexo());
        this.statement.setString(5, this.mascota.getRaza());
        this.statement.setString(6, this.mascota.getColor());
        this.statement.setDate(7, (Date) this.mascota.getFechaDefuncion());
        this.statement.setInt(8, this.mascota.getActivo() ? 1 : 0);
        
        this.statement.setInt(9, this.mascota.getMascotaId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.mascota.getMascotaId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.mascota.getMascotaId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.mascota = new MascotaDto();
        this.mascota.setMascotaId(this.resultSet.getInt("MASCOTA_ID"));
        PersonaDto per= new PersonaDto();
        per.setPersonaId(this.resultSet.getInt("PERSONA_ID"));
        this.mascota.setPersona(per);
        this.mascota.setNombre(this.resultSet.getString("NOMBRE"));
        this.mascota.setEspecie(this.resultSet.getString("ESPECIE"));
        this.mascota.setSexo(this.resultSet.getString("SEXO"));
        this.mascota.setRaza(this.resultSet.getString("RAZA"));
        this.mascota.setColor(this.resultSet.getString("COLOR"));
        this.mascota.setFechaDefuncion(this.resultSet.getDate("FECHA_DEFUNCION"));
        this.mascota.setActivo(this.resultSet.getInt("ACTIVO") == 1);
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.mascota = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.mascota);
    }

    @Override
    public Integer insertar(MascotaDto mascota) {
        this.mascota = mascota;
        return super.insertar();
    }

    @Override
    public MascotaDto obtenerPorId(Integer mascotaId) {
        this.mascota = new MascotaDto();
        this.mascota.setMascotaId(mascotaId);
        super.obtenerPorId();
        return this.mascota;
    }

    @Override
    public ArrayList<MascotaDto> listarTodos() {
        return (ArrayList<MascotaDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(MascotaDto mascota) {
        this.mascota = mascota;
        return super.modificar();
    }

    @Override
    public Integer eliminar(MascotaDto mascota) {
        this.mascota = mascota;
        return super.eliminar();
    }
    
    public ArrayList<MascotaDto> ListasBusquedaAvanzada(MascotaDto mascota){
        Map<Integer, Object> parametrosEntrada = new HashMap<>();
       
        parametrosEntrada.put(1,mascota.getNombre());
        parametrosEntrada.put(2,mascota.getRaza());
        parametrosEntrada.put(3,mascota.getEspecie());
        parametrosEntrada.put(4,mascota.getPersona().getNombre());
        
        return (ArrayList<MascotaDto>)super.ejecutarProcedimientoLectura("sp_buscar_mascotas_avanzada", parametrosEntrada);
    }
    
    
}
