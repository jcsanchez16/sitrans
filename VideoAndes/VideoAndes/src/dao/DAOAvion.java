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
import vos.Vuelo;
import javafx.scene.control.TreeTableRow;

public class DAOAvion {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Avion> aviones;

	public DAOAvion(String conectionData) {
		initConnectionData(conectionData);
		aviones = new ArrayList<Avion>();
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
				int codigo = Integer.parseInt(rs.getString("CODIGO"));
				int frecuencia = Integer.parseInt(rs.getString("FRECUENCIA_SEMANAL"));
				int Salida = Integer.parseInt(rs.getString("AEROPUERTO_SALIDA"));
				int Llegada = Integer.parseInt(rs.getString("AEROPUERTO_LLEGADA"));
				Date fSalida = Date.valueOf(rs.getString("FECHA_SALIDA"));
				Date fLlegada = Date.valueOf(rs.getString("FECHA_LLEGADA"));
				String duracion = rs.getString("DURACION");
				int distancia = Integer.parseInt(rs.getString("DISTANCIA"));
				//vuelos.add(new Vuelo(id, precio, fLlegada, fSalida, null, null, null));
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
	 * RF7 - ASOCIAR AERONAVE A VIAJE
	 * Registra la información de cuál aeronave va a realizar efectivamente un viaje
	 * (vuelo en una fecha específica). Esta información es necesaria para toda la gestión
	 * de reservas que se quiere realizar. Esta operación es realizada por un usuario de Aerolínea.
	 * Es aceptada siempre y cuando las características y capacidades de la aeronave no entren en conflicto
	 * con las reservas que se hayan realizado sobre ese viaje.
	 * 
	 * Da los aviones que realizan un vuelo en cierta fecha especifica.
	 * 
	 * @param fecha
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Avion> darAvionesQueRealizaVueloEnFechaExpecifica(Date fecha) throws Exception{
		
		PreparedStatement prepStmt = null;
		ArrayList<Avion> aviones = new ArrayList<Avion>();

		try {
			establecerConexion();
			
			//Teniendo en cuenta unicamente aviones tenemos:
			
//			String sql = "SELECT avion.* FROM VUELOS vuelo "
//					+"INNER JOIN RESERVAS reserva ON vuelo.codigo = reserva.ID_VUELO "
//					+"INNER JOIN AVIONES avion ON vuelo.AVION = avion.NSERIE "
//					+ "WHERE FECHA_SALIDA = '" + fecha.toString() + "'"; //Tener en cuenta que el toString ya lo devuelve en el formato deseado
			
			//Sin embargo, para crear los aviones necesitamos la aerolinea tambien, entonces:
			
			String sql = "SELECT avion.*,aero.* FROM VUELOS vuelo "
					+"INNER JOIN AVIONES avion ON vuelo.AVION = avion.NSERIE "
					+"INNER JOIN RESERVAS reserva ON vuelo.codigo = reserva.ID_VUELO "
					+"INNER JOIN AEROLINEAS aero ON aero.OACI = reserva.AEROLINEA "
					+ "WHERE FECHA_SALIDA = '" + fecha.toString() + "'"; //Tener en cuenta que el toString ya lo devuelve en el formato deseado
			
			
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				int nSerie = Integer.parseInt(rs.getString("NSERIE"));
				int modelo = Integer.parseInt(rs.getString("MODELO"));
				int anhoFabricacion = Integer.parseInt(rs.getString("AÑO_FABRICACION"));
				String marca = rs.getString("MARCA");
				String paisRadicacion = rs.getString("PAIS_RADICACION");
				String nombre = rs.getString("NOMBRE");
				String OACI = rs.getString("OACI");
				String codigo = rs.getString("CODIGO");
				
				aviones.add(new Avion(nSerie, modelo, anhoFabricacion, marca, new Aerolinea(paisRadicacion, nombre, OACI, codigo)));
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
