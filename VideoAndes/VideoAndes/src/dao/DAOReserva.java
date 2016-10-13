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

import vos.Cliente;
import vos.Vuelo;
import javafx.scene.control.TreeTableRow;

public class DAOReserva {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Vuelo> vuelos;
	
	private DAOCliente cliente;

	public DAOReserva(String conectionData) {
		initConnectionData(conectionData);
		vuelos = new ArrayList<Vuelo>();
		cliente = new DAOCliente(conectionData);
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

	public ArrayList darReservas() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM VUELOS";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int codigo = Integer.parseInt(rs.getString("CODIGO"));
				int frecuencia = Integer.parseInt(rs.getString("FRECUENCIA_SEMANAL"));
				int Salida = Integer.parseInt(rs.getString("AEROPUERTO_SALIDA"));
				int Llegada = Integer.parseInt(rs.getString("AEROPUERTO_LLEGADA"));
				Date fSalida = Date.valueOf(rs.getString("FECHA_SALIDA"));
				Date fLlegada = Date.valueOf(rs.getString("FECHA_LLEGADA"));
				String duracion = rs.getString("DURACION");
				int distancia = Integer.parseInt(rs.getString("DISTANCIA"));
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

	public ArrayList darVideosConError() throws Exception {
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

	public ArrayList<Cliente> buscarReservaporvuelo(int vuelo, String aerolinea, String Fecha) throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Cliente> c = new ArrayList<Cliente>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM RESERVAS WHERE ID_VUELO ='" + vuelo + "' and FECHA_VUELO ='" + Fecha + "' and AEROLINEA ='" + aerolinea + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String tip = rs.getString("TIPO_IDENTIFICACION");
				int id = Integer.parseInt(rs.getString("ID_CLIENTE"));
				c.add(cliente.buscarClientePK(id, tip));
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
		return c;
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
	
	
}
