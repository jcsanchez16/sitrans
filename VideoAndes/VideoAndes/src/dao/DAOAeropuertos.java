package dao;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import vos.Aeropuerto;
import vos.Vuelo;

public class DAOAeropuertos {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Aeropuerto> aeropuertos;
	
	private DAOVuelos vuelos;

	public DAOAeropuertos(String conectionData) {
		initConnectionData(conectionData);
		aeropuertos = new ArrayList<Aeropuerto>();
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

	public ArrayList<Aeropuerto> darAeropuertos() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Aeropuerto> aeropuertos = new ArrayList<Aeropuerto>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM AEROPUERTOS";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			String h = rs.toString();
			while (rs.next()) {
				String ciudad = rs.getString("CIUDAD");
				String iata = rs.getString("IATA");
				String nombre = rs.getString("NOMBRE");
				ArrayList<String> cri =new ArrayList<>();
				ArrayList<String> data =new ArrayList<>();
				cri.add("AEROPUERTO_SALIDA");
				data.add(iata);
				ArrayList<Vuelo> vuels = vuelos.buscarVuelosPorCriterio(cri,data);
				cri.clear();
				cri.add(0, "AEROPUERTO_LLEGADA");
				ArrayList<Vuelo> vuele = vuelos.buscarVuelosPorCriterio(cri,data);
				aeropuertos.add(new Aeropuerto(ciudad, nombre, iata,vuele,vuels));
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
		return aeropuertos;
	}

	public Aeropuerto buscarAeropuertoPK(String iata) throws Exception {
		PreparedStatement prepStmt = null;
		Aeropuerto aeropuertos = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM AEROPUERTOS WHERE IATA ='" + iata + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String ciudad = rs.getString("CIUDAD");
				String nombre = rs.getString("NOMBRE");
				ArrayList<String> cri =new ArrayList<>();
				ArrayList<String> data =new ArrayList<>();
				cri.add("AEROPUERTO_SALIDA");
				data.add(iata);
				ArrayList<Vuelo> vuels = vuelos.buscarVuelosPorCriterio(cri,data);
				cri.add(0, "AEROPUERTO_LLEGADA");
				ArrayList<Vuelo> vuele = vuelos.buscarVuelosPorCriterio(cri,data);
				aeropuertos=(new Aeropuerto(ciudad, nombre, iata,vuele,vuels));
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
		return aeropuertos;
	}

	
	

}
