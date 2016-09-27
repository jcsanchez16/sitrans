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

import vos.Vuelo;
import javafx.scene.control.TreeTableRow;

public class DAOVuelos {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Vuelo> vuelos;

	public DAOVuelos(String conectionData) {
		initConnectionData(conectionData);
		vuelos = new ArrayList<Vuelo>();
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

	public ArrayList<Vuelo> darVuelos() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM VUELOS";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int precio = Integer.parseInt(rs.getString("PRECIO"));
				int id = Integer.parseInt(rs.getString("ID_VUELO"));
				int aeropuertoSalida = Integer.parseInt(rs.getString("AEROPUERTO_SALIDA"));
				int aeropuertoLlegada = Integer.parseInt(rs.getString("AEROPUERTO_LLEGADA"));
				String fs=rs.getString("FECHA_SALIDA").split(" ")[0];
				Date fSalida = Date.valueOf(fs) ;
				Date fLlegada = Date.valueOf(rs.getString("FECHA_LLEGADA").split(" ")[0]) ;
				vuelos.add(new Vuelo(id, precio, fLlegada, fSalida, null, null, null));
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
		return vuelos;
	}

	public ArrayList<Vuelo> darVideosConError() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Vuelo> videos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM VIDEOSSS"; // intencionalmente se
													// escribe mal VIDEOS para
													// que lance error.
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("NAME");
				int id = Integer.parseInt(rs.getString("ID"));
				int duration = Integer.parseInt(rs.getString("DURATION"));
				//videos.add(new Vuelo(id, costo, llegada, salida, avion, asalida, allegada));
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
		return videos;
	}

	public ArrayList<Vuelo> buscarVideosPorName(String name) throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Vuelo> videos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM VIDEOS WHERE NAME ='" + name + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String name2 = rs.getString("NAME");
				int id = Integer.parseInt(rs.getString("ID"));
				int duration = Integer.parseInt(rs.getString("DURATION"));
				//videos.add(new Vuelo(id, name, duration));
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
		return videos;
	}

	public ArrayList<Vuelo> buscarVideosPorNameYId(String name, int id) throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Vuelo> videos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM VIDEOS WHERE NAME ='" + name + "' and ID = " + id;
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String name2 = rs.getString("NAME");
				int id2 = Integer.parseInt(rs.getString("ID"));
				int duration = Integer.parseInt(rs.getString("DURATION"));
				//videos.add(new Vuelo(id, name, duration));
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
		return videos;
	}
	
	//----------------------Requerimientos-------------------------//
	
	/**
	 * Metodo para registrar un vuelo a la base de datos.
	 * @param vuelo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public Vuelo registrarVuelo(Vuelo vuelo) throws SQLException, Exception {
		
		PreparedStatement prepStmt = null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			
			String sql = "INSERT INTO ARRIBOS VALUES (";
			sql += vuelo.getId() + ",";
			sql += vuelo.getCosto() + ")";
			
			System.out.println("SQL stmt:" + sql);
			
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
//				String name2 = rs.se;
//				int id2 = Integer.parseInt(rs.getString("ID"));
//				int duration = Integer.parseInt(rs.getString("DURATION"));
				//videos.add(new Vuelo(id, name, duration));
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
		return vuelo;

				
	}

}
