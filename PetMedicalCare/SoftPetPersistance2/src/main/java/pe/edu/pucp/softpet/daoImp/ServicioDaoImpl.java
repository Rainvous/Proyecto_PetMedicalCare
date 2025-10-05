/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.softpet.daoImp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softpet.dao.ServicioDao;
import pe.edu.pucp.softpet.daoImp.util.Columna;
import pe.edu.pucp.softpet.dto.servicios.ServicioDto;
import pe.edu.pucp.softpet.dto.servicios.TipoServicioDto;

/**
 *
 * @author User
 */
public class ServicioDaoImpl extends DAOImplBase implements ServicioDao {

    private ServicioDto servicio;

    public ServicioDaoImpl() {
        super("SERVICIOS");
        this.retornarLlavePrimaria = true;
        this.servicio = null;
        this.usuario = "user_backend";
    }

    @Override
    protected void configurarListaDeColumnas() {
        this.listaColumnas.add(new Columna("SERVICIO_ID", true, true));  // PK, autogenerado
        this.listaColumnas.add(new Columna("NOMBRE", false, false));
        this.listaColumnas.add(new Columna("COSTO", false, false));
        this.listaColumnas.add(new Columna("ESTADO", false, false));
        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
        this.listaColumnas.add(new Columna("ACTIVO", false, false));
        this.listaColumnas.add(new Columna("TIPO_SERVICIO_ID", false, false));
    }

    @Override
    protected void incluirValorDeParametrosParaInsercion() throws SQLException {
        //NOTA NO ES NECESARIO AGREGAR LA AUDITORIA
        this.statement.setString(1, this.servicio.getNombre());
        this.statement.setDouble(2, this.servicio.getCosto());
        this.statement.setString(3, this.servicio.getEstado());
        this.statement.setString(3, this.servicio.getDescripcion());
//        if (this.servicio.getActivo() == null) {
//            System.out.println("nos");
//        }
        this.statement.setInt(4, this.servicio.getActivo() ? 1 : 0);

        int idTiposervicio = this.servicio.getTipoServicio().getTipoServicioId();
        this.statement.setInt(5, idTiposervicio);
        //System.out.println(statement);

    }

    @Override
    protected void incluirValorDeParametrosParaModificacion() throws SQLException {
        this.statement.setString(1, this.servicio.getNombre());
        this.statement.setDouble(2, this.servicio.getCosto());
        this.statement.setString(3, this.servicio.getEstado());
        this.statement.setString(3, this.servicio.getDescripcion());
        if (this.servicio.getActivo() == null) {
            System.out.println("nos");
        }
        this.statement.setInt(4, this.servicio.getActivo() ? 1 : 0);

        int idTiposervicio = this.servicio.getTipoServicio().getTipoServicioId();
        this.statement.setInt(5, idTiposervicio);

        this.statement.setInt(6, this.servicio.getServicioId());
    }

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
        TipoServicioDto tipoProducto = new TipoServicioDto();
        tipoProducto.setTipoServicioId(this.resultSet.getInt("TIPO_SERVICIO_ID"));

        this.servicio.setTipoServicio(tipoProducto);

    }

    @Override
    protected void limpiarObjetoDelResultSet() {
        this.servicio = null;
    }
//        this.listaColumnas.add(new Columna("SERVICIO_ID", true, true));  // PK, autogenerado
//        this.listaColumnas.add(new Columna("NOMBRE", false, false));
//        this.listaColumnas.add(new Columna("COSTO", false, false));
//        this.listaColumnas.add(new Columna("ESTADO", false, false));
//        this.listaColumnas.add(new Columna("DESCRIPCION", false, false));
//        this.listaColumnas.add(new Columna("ACTIVO", false, false));
//        this.listaColumnas.add(new Columna("TIPO_SERVICIO_ID", false, false));

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

}
