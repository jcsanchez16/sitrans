package dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import vos.Aerolinea;
import vos.Avion;
import vos.AvionCarga;
import vos.AvionPasajeros;
import vos.Vuelo;
import javafx.scene.control.TreeTableRow;

public class DAOAviones {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Avion> aviones;
	
	private DAOVuelos vuelos;

	public DAOAviones(String conectionData) {
		initConnectionData(conectionData);
		aviones = new ArrayList<Avion>();
		vuelos = new DAOVuelos(conectionData);
	}

	private void initConnectionData(String conectionData) {
		try {
			File arch = new File(conectionData);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void establecerConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		conexion = DriverManager.getConnection(url, user, password);
	}

	public void closeConnection(Connection connection) throws SQLException {
		try {
			connection.close();
			connection = null;
		} catch (SQLException exception) {
			System.err.println("SQLException in closing Connection:");
			exception.printStackTrace();
			throw exception;
		}
	}

	public ArrayList<Avion> darAviones() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Avion> aviones = new ArrayList<Avion>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM AVIONES";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int nserie = Integer.parseInt(rs.getString("NSERIE"));
				String aerolinea = rs.getString("AEROLINEA");
				String marca = rs.getString("MARCA");
				int modelo = Integer.parseInt(rs.getString("MODELO"));
				int ano = Integer.parseInt(rs.getString("AÑO_FABRICACION"));
				ArrayList<String> cri =new ArrayList<>();
				ArrayList<String> data =new ArrayList<>();
				cri.add("AVION");
				data.add(nserie+"");
				ArrayList<Vuelo> vuel = vuelos.buscarVuelosPorCriterio(cri,data);
				if(Integer.parseInt(rs.getString("TIPO"))==1)
				{
					float carga = Float.parseFloat(rs.getString("CAPACIDAD_DENSIDAD"));
					aviones.add(new AvionCarga(nserie, modelo, ano, marca, carga, aerolinea,vuel));
				}
				else
				{
					int asientosEjecutivo= Integer.parseInt(rs.getString("ASIENTOS_EJECUTIVO"));
					int asientosEconomica= Integer.parseInt(rs.getString("ASIENTOS_ECONOMICA"));
					aviones.add(new AvionPasajeros(nserie, modelo, ano, marca, asientosEconomica, asientosEjecutivo, aerolinea,vuel));
				}
			}

		} catch (SQLException e) {
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} finally {
			if (prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException exception) {
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
		}
		return aviones;
	}

	public Avion buscarAvionPK(int serie) throws Exception {
		PreparedStatement prepStmt = null;
		Avion aviones = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM AVIONES WHERE NSERIE='"+ serie+"'"; 
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String aerolinea = rs.getString("AEROLINEA");
				String marca = rs.getString("MARCA");
				int modelo = Integer.parseInt(rs.getString("MODELO"));
				int ano = Integer.parseInt(rs.getString("AÑO_FABRICACION"));
				ArrayList<String> cri =new ArrayList<>();
				ArrayList<String> data =new ArrayList<>();
				cri.add("AVION");
				data.add(serie+"");
				ArrayList<Vuelo> vuel = vuelos.buscarVuelosPorCriterio(cri,data);
				if(Integer.parseInt(rs.getString("TIPO"))==1)
				{
					float carga = Float.parseFloat(rs.getString("CAPACIDAD_DENSIDAD"));
					aviones=(new AvionCarga(serie, modelo, ano, marca, carga, aerolinea,vuel));
				}
				else
				{
					int asientosEjecutivo= Integer.parseInt(rs.getString("ASIENTOS_EJECUTIVO"));
					int asientosEconomica= Integer.parseInt(rs.getString("ASIENTOS_ECONOMICA"));
					aviones=(new AvionPasajeros(serie, modelo, ano, marca, asientosEconomica, asientosEjecutivo, aerolinea,vuel));
				}
			}

		} catch (SQLException e) {
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} finally {
			if (prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException exception) {
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
		}
		return aviones;
	}

	public ArrayList<Avion> buscarAvionesPorAero(String oACI) throws Exception{
		PreparedStatement prepStmt = null;
		ArrayList<Avion> aviones = new ArrayList<Avion>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM AVIONES WHERE AEROLINEA='"+ oACI+"'"; 
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				int nserie = Integer.parseInt(rs.getString("NSERIE"));
				String marca = rs.getString("MARCA");
				int modelo = Integer.parseInt(rs.getString("MODELO"));
				int ano = Integer.parseInt(rs.getString("AÑO_FABRICACION"));
				ArrayList<String> cri =new ArrayList<>();
				ArrayList<String> data =new ArrayList<>();
				cri.add("AVION");
				data.add(nserie+"");
				ArrayList<Vuelo> vuel = vuelos.buscarVuelosPorCriterio(cri,data);
				if(Integer.parseInt(rs.getString("TIPO"))==1)
				{
					float carga = Float.parseFloat(rs.getString("CAPACIDAD_DENSIDAD"));
					aviones.add(new AvionCarga(nserie, modelo, ano, marca, carga, oACI,vuel));
				}
				else
				{
					int asientosEjecutivo= Integer.parseInt(rs.getString("ASIENTOS_EJECUTIVO"));
					int asientosEconomica= Integer.parseInt(rs.getString("ASIENTOS_ECONOMICA"));
					aviones.add(new AvionPasajeros(nserie, modelo, ano, marca, asientosEconomica, asientosEjecutivo, oACI,vuel));
				}
			}

		} catch (SQLException e) {
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} finally {
			if (prepStmt != null) {
				try {
					prepStmt.close();
				} catch (SQLException exception) {
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
		}
		return aviones;
	}

	
	

}
