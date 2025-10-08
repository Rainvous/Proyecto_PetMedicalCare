package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.ServicioDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

public class ServicioDaoImpl extends DaoBaseImpl implements ServicioDao {

    private ServicioDto servicio;

    public ServicioDaoImpl() {
        super("SERVICIOS");
        this.retornarLlavePrimaria = true;
        this.servicio = null;
        this.usuario = "user_backend";
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("SERVICIO_ID", true, true));
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("COSTO", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("TIPO_SERVICIO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        // NOTA: la auditoria se maneja con un trigger
        this.statement.setString(1, this.servicio.getNombre());
        this.statement.setDouble(2, this.servicio.getCosto());
        this.statement.setString(3, this.servicio.getEstado());
        this.statement.setString(4, this.servicio.getDescripcion());
        this.statement.setInt(5, this.servicio.getActivo() ? 1 : 0);
        this.statement.setInt(6, this.servicio.getTipoServicio().getTipoServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.servicio.getNombre());
        this.statement.setDouble(2, this.servicio.getCosto());
        this.statement.setString(3, this.servicio.getEstado());
        this.statement.setString(4, this.servicio.getDescripcion());
        this.statement.setInt(5, this.servicio.getActivo() ? 1 : 0);
        this.statement.setInt(6, this.servicio.getTipoServicio().getTipoServicioId());

        this.statement.setInt(7, this.servicio.getServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaEliminacion() throws SQLException {
        this.statement.setInt(1, this.servicio.getServicioId());
    }

    @Override
    protected void incluirValorDeParametrosParaObtenerPorId() throws SQLException {
        this.statement.setInt(1, this.servicio.getServicioId());
    }

    @Override
    protected void instanciarObjetoDelResultSet() throws SQLException {
        this.servicio = new ServicioDto();
        this.servicio.setServicioId(this.resultSet.getInt("SERVICIO_ID"));
        this.servicio.setNombre(this.resultSet.getString("NOMBRE"));
        this.servicio.setCosto(this.resultSet.getDouble("COSTO"));
        this.servicio.setEstado(this.resultSet.getString("ESTADO"));
        this.servicio.setDescripcion(this.resultSet.getString("DESCRIPCION"));
        this.servicio.setActivo(this.resultSet.getInt("ACTIVO") == 1);
        this.servicio.setTipoServicio(new TipoServicioDaoImpl().
                obtenerPorId(this.resultSet.getInt("TIPO_SERVICIO_ID")));
    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.servicio = null;
    }

    @Override
    protected void agregarObjetoALaLista(List lista) throws SQLException {
        this.instanciarObjetoDelResultSet();
        lista.add(this.servicio);
    }

    @Override
    public ServicioDto obtenerPorId(Integer idDto) {
        this.servicio = new ServicioDto();
        this.servicio.setServicioId(idDto);
        super.obtenerPorId();
        return this.servicio;
    }

    @Override
    public Integer insertar(ServicioDto entity) {
        this.servicio = entity;
        return super.insertar();
    }

    @Override
    public ArrayList<ServicioDto> listarTodos() {
        return (ArrayList<ServicioDto>) super.listarTodos();
    }

    @Override
    public Integer modificar(ServicioDto entity) {
        this.servicio = entity;
        return super.modificar();
    }

    @Override
    public Integer eliminar(ServicioDto entity) {
        this.servicio = entity;
        return super.eliminar();
    }

    /// PROCEDURES Y SELECT'
    /// @param NombreTipo
    /// @return s
    
    @Override
    public ArrayList<ServicioDto> ListarPorTipoServicio(String NombreTipo) {
        ServicioDto productoAux = new ServicioDto();
        TipoServicioDto tipo = new TipoServicioDto();
        tipo.setNombre(NombreTipo);
        productoAux.setTipoServicio(tipo);
        String sql = GenerarSQLSelectPorTipoServicio();
        return (ArrayList<ServicioDto>) super.listarTodos(sql, this::incluirValorDeParametrosParaListarPorTipo, NombreTipo);
    }

    private void incluirValorDeParametrosParaListarPorTipo(Object objetoParametro) {
        ServicioDto parametro = (ServicioDto) objetoParametro;
        String nombre = "%";
        nombre = nombre.concat(parametro.getTipoServicio().getNombre());
        nombre = nombre.concat("%");

        try {
            this.statement.setString(1, nombre);
        } catch (SQLException ex) {
            System.err.println("No se pudo incluirValores de parametro den el Statement-> " + this.statement);
            System.getLogger(ProductoDaoImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private String GenerarSQLSelectPorTipoServicio() {
        String sql = "SELECT * ";
        sql = sql.concat("FROM SERVICIOS s ");
        sql = sql.concat("JOIN TIPOS_SERVICIO ts ON ts.tipo_servicio_id=s.tipo_servicio_id ");
        sql = sql.concat("WHERE ts.nombre LIKE ?");
        return sql;
    }

    private String GenerarSQLSelectPorNombre() {
        String sql = "SELECT * ";
        sql = sql.concat("FROM PRODUCTOS p ");
        sql = sql.concat("WHERE p.nombre LIKE ?");
        return sql;
    }

    private void incluirValorDeParametrosPorNombre(Object objetoParametro) {
        ServicioDto parametro = (ServicioDto) objetoParametro;
        String nombre = "%";
        nombre = nombre.concat(parametro.getTipoServicio().getNombre());
        nombre = nombre.concat("%");

        try {
            this.statement.setString(1, nombre);
        } catch (SQLException ex) {
            System.err.println("No se pudo incluirValores de parametro den el Statement-> " + this.statement);
            System.getLogger(ProductoDaoImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public ArrayList<ServicioDto> ListarPorNombre(String Nombre) {
        ServicioDto Aux = new ServicioDto();
        Aux.setNombre(Nombre);

        String sql = GenerarSQLSelectPorNombre();
        return (ArrayList<ServicioDto>) super.listarTodos(sql, this::incluirValorDeParametrosPorNombre, Aux);
    }
}
