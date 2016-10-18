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
import vos.Cliente;
import vos.Vuelo;
import vos.VueloCarga;
import vos.VueloPasajeros;
import javafx.scene.control.TreeTableRow;

public class DAOVuelos {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Vuelo> vuelos;
	
	private DAOReserva reservas;

	public DAOVuelos(String conectionData) {
		initConnectionData(conectionData);
		vuelos = new ArrayList<Vuelo>();
		reservas = new DAOReserva(conectionData);
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
		 vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM VUELOS";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int codigo = Integer.parseInt(rs.getString("CODIGO"));
				int frecuencia = Integer.parseInt(rs.getString("FRECUENCIA_SEMANAL"));
				String Salida = rs.getString("AEROPUERTO_SALIDA");
				String Llegada = rs.getString("AEROPUERTO_LLEGADA");
				Date fSalida = Date.valueOf(rs.getString("FECHA_SALIDA"));
				Date fLlegada = Date.valueOf(rs.getString("FECHA_LLEGADA"));
				int avion = Integer.parseInt(rs.getString("AVION"));
				String aerolinea =rs.getString("AEROLINEA");
				String duracion = rs.getString("DURACION");
				int distancia = Integer.parseInt(rs.getString("DISTANCIA"));
				int realizado = Integer.parseInt(rs.getString("REALIZADO"));
				if(Integer.parseInt(rs.getString("TIPO"))==1)
				{
					Float carga = Float.parseFloat(rs.getString("PRECIO_DENSIDAD"));
					ArrayList clie =reservas.buscarReservaporvuelo(codigo, aerolinea);
					vuelos.add(new VueloCarga(codigo, frecuencia, fLlegada, fSalida, avion, Salida, Llegada,aerolinea, carga,realizado, distancia, duracion,clie));
				}
				else
				{
					Float ej = Float.parseFloat(rs.getString("PRECIO_EJECUTIVO"));
					Float ec = Float.parseFloat(rs.getString("PRECIO_ECONOMICO"));
					ArrayList clie =reservas.buscarReservaporvuelo(codigo, aerolinea);
					vuelos.add(new VueloPasajeros(codigo, frecuencia, fLlegada, fSalida, avion, Salida, Llegada,aerolinea, ej, ec,realizado, distancia, duracion,clie));
					
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
		return vuelos;
	}
	
	public Vuelo darVuelosPorPK(int codigo,String aerolinea) throws Exception {
		PreparedStatement prepStmt = null;
		Vuelo vuelos =null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM VUELOS WHERE CODIGO='"+codigo+"' and AEROLINEA ='"+aerolinea+"' ";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int frecuencia = Integer.parseInt(rs.getString("FRECUENCIA_SEMANAL"));
				String Salida = rs.getString("AEROPUERTO_SALIDA");
				String Llegada = rs.getString("AEROPUERTO_LLEGADA");				
				Date fSalida = Date.valueOf(rs.getString("FECHA_SALIDA"));
				Date fLlegada = Date.valueOf(rs.getString("FECHA_LLEGADA"));
				int avion = Integer.parseInt(rs.getString("AVION"));
				String duracion = rs.getString("DURACION");
				int distancia = Integer.parseInt(rs.getString("DISTANCIA"));
				int realizado = Integer.parseInt(rs.getString("REALIZADO"));
				if(Integer.parseInt(rs.getString("TIPO"))==1)
				{
					Float carga = Float.parseFloat(rs.getString("PRECIO_DENSIDAD"));
					vuelos=(new VueloCarga(codigo, frecuencia, fLlegada, fSalida, avion,Salida, Llegada,aerolinea, carga,realizado, distancia, duracion,null));
				}
				else
				{
					Float ej = Float.parseFloat(rs.getString("PRECIO_EJECUTIVO"));
					Float ec = Float.parseFloat(rs.getString("PRECIO_ECONOMICO"));
					vuelos=(new VueloPasajeros(codigo, frecuencia, fLlegada, fSalida, avion,Salida, Llegada,aerolinea, ej, ec,realizado, distancia, duracion,null));
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
		return vuelos;
	}
	

	public ArrayList<Vuelo> buscarVuelosPorCriterio(String cri, String data) throws Exception{
		PreparedStatement prepStmt = null;
		 vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM VUELOS WHERE "+cri+" ='"+data+"' ";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int codigo = Integer.parseInt(rs.getString("CODIGO"));
				int frecuencia = Integer.parseInt(rs.getString("FRECUENCIA_SEMANAL"));
				String Salida = rs.getString("AEROPUERTO_SALIDA");
				String Llegada = rs.getString("AEROPUERTO_LLEGADA");
				Date fSalida = Date.valueOf(rs.getString("FECHA_SALIDA"));
				Date fLlegada = Date.valueOf(rs.getString("FECHA_LLEGADA"));
				int avion = Integer.parseInt(rs.getString("AVION"));
				String aerolinea =rs.getString("AEROLINEA");
				String duracion = rs.getString("DURACION");
				int distancia = Integer.parseInt(rs.getString("DISTANCIA"));
				int realizado = Integer.parseInt(rs.getString("REALIZADO"));
				if(Integer.parseInt(rs.getString("TIPO"))==1)
				{
					Float carga = Float.parseFloat(rs.getString("PRECIO_DENSIDAD"));
					ArrayList clie =reservas.buscarReservaporvuelo(codigo, aerolinea);
					vuelos.add(new VueloCarga(codigo, frecuencia, fLlegada, fSalida, avion, Salida, Llegada,aerolinea, carga,realizado, distancia, duracion,clie));
				}
				else
				{
					Float ej = Float.parseFloat(rs.getString("PRECIO_EJECUTIVO"));
					Float ec = Float.parseFloat(rs.getString("PRECIO_ECONOMICO"));
					ArrayList clie =reservas.buscarReservaporvuelo(codigo, aerolinea);
					vuelos.add(new VueloPasajeros(codigo, frecuencia, fLlegada, fSalida, avion, Salida, Llegada,aerolinea, ej, ec,realizado, distancia, duracion,clie));
					
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
		return vuelos;
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
			//sql += vuelo.getId() + ",";
			//sql += vuelo.getCosto() + ")";
			
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
