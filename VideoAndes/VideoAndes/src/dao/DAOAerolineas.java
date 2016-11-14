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

import vos.Aerolinea;
import vos.Avion;
import vos.Vuelo;

public class DAOAerolineas {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Aerolinea> aerolineas;
	
	private DAOAviones aviones;
	private DAOVuelos vuelos;

	public DAOAerolineas(String conectionData) {
		initConnectionData(conectionData);
		aerolineas = new ArrayList<Aerolinea>();
		aviones = new DAOAviones(conectionData);
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

	public ArrayList<Aerolinea> darAerolineas() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Aerolinea> aerolineas = new ArrayList<Aerolinea>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM AEROLINEAS";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) 	
			{
				String OACI = rs.getString("OACI");
				String pais = rs.getString("PAIS_RADICACION");
				String nombre = rs.getString("NOMBRE");
				String codigo = rs.getString("CODIGO");
				ArrayList<Avion> avi= aviones.buscarAvionesPorAero(OACI);
				ArrayList<String> cri =new ArrayList<>();
				ArrayList<String> data =new ArrayList<>();
				cri.add("AEROLINEA");
				data.add(OACI);
				ArrayList<Vuelo> vuel = vuelos.buscarVuelosPorCriterio(cri,data);
				aerolineas.add(new Aerolinea(pais, nombre, OACI, codigo, avi,vuel));
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
		return aerolineas;
	}

	public Aerolinea buscarAerolineasPK(String OACI) throws Exception {
		PreparedStatement prepStmt = null;
		Aerolinea aerolineas = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM AEROLINEAS WHERE OACI ='" +OACI + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) 
			{
				String pais = rs.getString("PAIS_RADICACION");
				String nombre = rs.getString("NOMBRE");
				String codigo = rs.getString("CODIGO");
				ArrayList<Avion> avi= aviones.buscarAvionesPorAero(OACI);
				ArrayList<String> cri =new ArrayList<>();
				ArrayList<String> data =new ArrayList<>();
				cri.add("AEROLINEA");
				data.add(OACI);
				ArrayList<Vuelo> vuel = vuelos.buscarVuelosPorCriterio(cri,data);
				aerolineas=(new Aerolinea(pais, nombre, OACI, codigo, avi,vuel));
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
		return aerolineas;
	}

	
	
	

}
