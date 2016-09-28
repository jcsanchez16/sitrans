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
import vos.Remitente;
import vos.Vuelo;
import javafx.scene.control.TreeTableRow;

public class DAOCliente {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Cliente> clientes;

	public DAOCliente(String conectionData) {
		initConnectionData(conectionData);
		clientes = new ArrayList<Cliente>();
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

	public ArrayList<Cliente> darClientes() throws Exception {
		PreparedStatement prepStmt = null;
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM CLIENTES";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();
			while (rs.next()) {
				int identificacion = Integer.parseInt(rs.getString("IDENTIFICACION"));
				String nombre= rs.getString("NOMBRE");
				String nacionalidad = rs.getString("NACIONALIDAD");
				String correo = rs.getString("CORREO");
				String tip = rs.getString("TIPO_IDENTIFICACION");
				if(Integer.parseInt(rs.getString("TIPO"))==1)
				{
					float densidad =Float.parseFloat(rs.getString("DENSIDAD_CARGA"));
					clientes.add(new Remitente(identificacion, nombre, nacionalidad, correo, tip, densidad));
				}
				else
				clientes.add(new Cliente(identificacion, nombre, nacionalidad, correo, tip));
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
		return clientes;
	}

	public Cliente buscarClientePK(int id, String tip) throws Exception {
		PreparedStatement prepStmt = null;
		Cliente cliente = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM VIDEOS WHERE IDENTIFICACION ='" + id + "' and TIPO_IDENTIFICACION ='"+tip+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String nombre= rs.getString("NOMBRE");
				String nacionalidad = rs.getString("NACIONALIDAD");
				String correo = rs.getString("CORREO");
				if(Integer.parseInt(rs.getString("TIPO"))==1)
				{
					float densidad =Float.parseFloat(rs.getString("DENSIDAD_CARGA"));
					cliente=(new Remitente(id, nombre, nacionalidad, correo, tip, densidad));
				}
				else
				cliente=(new Cliente(id, nombre, nacionalidad, correo, tip));
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
		return cliente;
	}

<<<<<<< HEAD:VideoAndes/VideoAndes/src/dao/DAOClientes.java
=======
	
	
	//----------------------Requerimientos-------------------------//
	

	
	
	

>>>>>>> db875ae6cc96870157965a9c91bdbfb2f751104d:VideoAndes/VideoAndes/src/dao/DAOCliente.java
}
