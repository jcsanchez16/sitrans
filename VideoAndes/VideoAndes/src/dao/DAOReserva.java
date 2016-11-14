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

import vos.Cliente;

public class DAOReserva {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private DAOVuelos vuelos;
	
	private DAOCliente cliente;
	
	private String conectionData;

	public DAOReserva(String conectionDat) {
		initConnectionData(conectionDat);
		conectionData = conectionDat;
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

	
	public ArrayList<Cliente> buscarReservaporvuelo(int vuelo, String aerolinea) throws Exception {
		
		PreparedStatement prepStmt = null;
		ArrayList<Cliente> c = new ArrayList<Cliente>();
		cliente = new DAOCliente(conectionData);

		try {
			establecerConexion();
			String sql = "SELECT * FROM RESERVAS WHERE ID_VUELO ='" + vuelo + "' and AEROLINEA ='" + aerolinea + "'";
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
	public ArrayList<String> buscarReservaPorCliente(int identificacion,String tip) throws Exception 
	{
		PreparedStatement prepStmt = null;
		ArrayList<String> v = new ArrayList<String>();
		vuelos = new DAOVuelos(conectionData);

		try {
			establecerConexion();
			String sql = "SELECT * FROM RESERVAS WHERE ID_CLIENTE ='" + identificacion + "' and TIPO_IDENTIFICACION ='" + tip + "'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String aero = rs.getString("AEROLINEA");
				int id = Integer.parseInt(rs.getString("ID_VUELO"));
				v.add(aero+";"+id);
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
		return v;
	}

	public void cancelarReserva(String aero, int idv, String tipo, int idc) throws SQLException 
	{
		PreparedStatement prepStmt = null;
		try {
			establecerConexion();
			String sql = "DELETE FROM RESERVAS WHERE ID_VUELO = "+idv+" AND ID_CLIENTE = '"+idc+"' AND TIPO_IDENTIFICACION = '"+tipo+"' AND AEROLINEA = '"+aero+"'";
			prepStmt = conexion.prepareStatement(sql);
			prepStmt.execute();

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
	}

	public void registrar(int idVuelo, String aerolinea,String tipoIdentificacion, int id) throws SQLException 
	{
		PreparedStatement prepStmt = null;
		try {
			establecerConexion();
			String sql = "INSERT INTO RESERVAS(ID_VUELO, ID_CLIENTE, TIPO_IDENTIFICACION, AEROLINEA) VALUES ('"+idVuelo+"', '"+id+"', '"+tipoIdentificacion+"', '"+aerolinea+"')";
			prepStmt = conexion.prepareStatement(sql);
			prepStmt.execute();
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
	}

	public void cancelarReservas(int idv, String aero) throws Exception 
	{
		PreparedStatement prepStmt = null;
		try {
			establecerConexion();
			String sql = "DELETE FROM RESERVAS WHERE ID_VUELO = "+idv+" AND AEROLINEA = '"+aero+"'";
			prepStmt = conexion.prepareStatement(sql);
			prepStmt.execute();

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
	}

}
