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
import vos.Pasajero;
import vos.Remitente;
import vos.Vuelo;

public class DAOCliente {

	private Connection conexion;

	private String user;

	private String password;

	private String url;

	private String driver;
	
	private ArrayList<Cliente> clientes;
	
	private DAOReserva reservas;
	
	private DAOVuelos vuel;

	public DAOCliente(String conectionData) {
		initConnectionData(conectionData);
		clientes = new ArrayList<Cliente>();
		reservas = new DAOReserva(conectionData);
		vuel = new DAOVuelos(conectionData);
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
				ArrayList<String> vuelos = reservas.buscarReservaPorCliente(identificacion, tip);
				int tipo=Integer.parseInt(rs.getString("TIPO"));				
				if(tipo==1)
				{
					float densidad =Float.parseFloat(rs.getString("DENSIDAD_CARGA"));
					clientes.add(new Remitente(identificacion, nombre, nacionalidad, correo, tip, densidad,vuelos,tipo, 0, 0));
				}
				else
				{
					int eco = Integer.parseInt(rs.getString("ECONOMICO"));
					clientes.add(new Pasajero(identificacion, nombre, nacionalidad, correo, tip, eco,vuelos,tipo, 0, 0));
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
		return clientes;
	}

	public Cliente buscarClientePK(int id, String tip) throws Exception {
		PreparedStatement prepStmt = null;
		Cliente cliente = null;

		try {
			establecerConexion();
			String sql = "SELECT * FROM CLIENTES WHERE IDENTIFICACION ='" + id + "' and TIPO_IDENTIFICACION ='"+tip+"'";
			prepStmt = conexion.prepareStatement(sql);
			ResultSet rs = prepStmt.executeQuery();

			while (rs.next()) {
				String nombre= rs.getString("NOMBRE");
				String nacionalidad = rs.getString("NACIONALIDAD");
				String correo = rs.getString("CORREO");
				ArrayList<String> vuelos = reservas.buscarReservaPorCliente(id, tip);
				int tipo = Integer.parseInt(rs.getString("TIPO"));
				if(tipo ==1)
				{
					float densidad =Float.parseFloat(rs.getString("DENSIDAD_CARGA"));
					cliente=(new Remitente(id, nombre, nacionalidad, correo, tip, densidad,vuelos,tipo,0,0));
				}
				else
				{
					int ti = Integer.parseInt(rs.getString("ECONOMICO"));
					cliente=(new Pasajero(id, nombre, nacionalidad, correo, tip,ti,vuelos,tipo,0,0));					
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
		return cliente;
	}

	public void registrarCarga(String tipoIdentificacion, int id, Float carga) throws SQLException 
	{
		PreparedStatement prepStmt = null;
		try 
		{
			String c = (carga+"");
			String[] car = c.split(".");
			establecerConexion();
			String sql = "UPDATE CLIENTES SET DENSIDAD_CARGA= '"+c.charAt(0)+","+c.charAt(2)+"' WHERE IDENTIFICACION ='" + id + "' and TIPO_IDENTIFICACION ='"+tipoIdentificacion+"'";
			prepStmt = conexion.prepareStatement(sql);
			prepStmt.execute();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} 
		finally {
			if (prepStmt != null) 
			{
				try 
				{
					prepStmt.close();
				} 
				catch (SQLException exception) 
				{
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
		}
	}

	public void registrarTipo(String tipoIdentificacion, int id,boolean tipoSilla) throws SQLException 
	{
		PreparedStatement prepStmt = null;
		int tip = tipoSilla == false? 1:0;
		try 
		{
			establecerConexion();
			String sql = "UPDATE CLIENTES SET ECONOMICO= '"+tip+"' WHERE IDENTIFICACION ='" + id + "' and TIPO_IDENTIFICACION ='"+tipoIdentificacion+"'";
			prepStmt = conexion.prepareStatement(sql);
			prepStmt.execute();
		} 
		catch (SQLException e) 
		{
			System.err.println("SQLException in executing:");
			e.printStackTrace();
			throw e;
		} 
		finally {
			if (prepStmt != null) 
			{
				try 
				{
					prepStmt.close();
				} 
				catch (SQLException exception) 
				{
					System.err.println("SQLException in closing Stmt:");
					exception.printStackTrace();
					throw exception;
				}
			}
			if (this.conexion != null)
				closeConnection(this.conexion);
			
		}
	}
	public ArrayList<Cliente> contarTiempos(ArrayList<Cliente> clientes) throws Exception
	{
		for (int i = 0; i < clientes.size(); i++) 
		{			
			ArrayList<String> vuelos =clientes.get(i).getVuelos();
			int millas = 0;
			double horas=0;
			double minutos=0;
			for (int j = 0; j < vuelos.size(); j++) 
			{
				int g=Integer.parseInt(vuelos.get(j).split(";")[1]);
				String k =vuelos.get(j).split(";")[0];
				Vuelo este = vuel.darVuelosPorPK(g, k);
				millas+=este.getDistancia();
				String[] h = este.getDuracion().split(":");
				horas+=Integer.parseInt(h[0]);
				minutos+=Integer.parseInt(h[1]);
			}
			minutos= minutos/60;
			horas+=minutos;
			clientes.get(i).setMillas(millas);
			clientes.get(i).setTiempo(horas);
		}
		return clientes;
	}
}
