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

import com.csvreader.CsvReader;

import vos.Aeropuerto;
import vos.Vuelo;
import vos.VueloCarga;
import vos.VueloPasajeros;

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
					ArrayList clie =reservas.buscarReservaporvuelo(codigo, aerolinea);
					vuelos=(new VueloCarga(codigo, frecuencia, fLlegada, fSalida, avion,Salida, Llegada,aerolinea, carga,realizado, distancia, duracion,null));
				}
				else
				{
					Float ej = Float.parseFloat(rs.getString("PRECIO_EJECUTIVO"));
					Float ec = Float.parseFloat(rs.getString("PRECIO_ECONOMICO"));
					ArrayList clie =reservas.buscarReservaporvuelo(codigo, aerolinea);
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
	

	public ArrayList<Vuelo> buscarVuelosPorCriterio(ArrayList<String> cri, ArrayList<String> data) throws Exception{
		PreparedStatement prepStmt = null;
		 vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql = "SELECT * FROM VUELOS WHERE "+cri.get(0)+" ='"+data.get(0)+"' ";
			for (int i = 1; i < cri.size(); i++) 
			{
				sql +=" AND "+cri.get(i)+" ='"+data.get(i)+"' ";
			}
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

	public void asignarAvion(int idVuelo, String aerolinea, int idAvion) throws SQLException 
	{
		PreparedStatement prepStmt = null;
		try 
		{
			establecerConexion();
			String sql = "UPDATE VUELOS SET AVION= '"+idAvion+"' WHERE CODIGO='"+idVuelo+"' and AEROLINEA ='"+aerolinea+"' ";
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

	public void cancelarVuelo(int idv, String aero) throws Exception 
	{
		PreparedStatement prepStmt = null;
		try {
			establecerConexion();
			String sql = "DELETE FROM VUELOS WHERE CODIGO = "+idv+" AND AEROLINEA = '"+aero+"'";
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

	public ArrayList<Vuelo> buscarVuelosPorCriterio2(ArrayList<String> cri,
			ArrayList<String> data, String fechaF, String fechaI, String order,
			String tipoOrder, String group, String pGroup) throws Exception 
	{
		PreparedStatement prepStmt = null;
		 vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql="";
			if(group!= null)
				sql += "SELECT "+group+", "+pGroup+" AS GROUP FROM VUELOS WHERE (("+cri.get(0)+" ='"+data.get(0)+"' AND (FECHA_SALIDA BETWEEN '"+fechaI+"' AND '"+fechaF+"')) OR ("+cri.get(1)+" ='"+data.get(1)+"' AND (FECHA_LLEGADA BETWEEN '"+fechaI+"' AND '"+fechaF+"'))) ";
			else
				sql += "SELECT * FROM VUELOS WHERE (("+cri.get(0)+" ='"+data.get(0)+"' AND (FECHA_SALIDA BETWEEN '"+fechaI+"' AND '"+fechaF+"')) OR ("+cri.get(1)+" ='"+data.get(1)+"' AND (FECHA_LLEGADA BETWEEN '"+fechaI+"' AND '"+fechaF+"'))) ";
			for (int i = 2; i < cri.size(); i++) 
			{
				if(data.get(i)!= null)
				sql +=" AND "+cri.get(i)+" ='"+data.get(i)+"' ";
			}
			if(group != null)
				sql+=" GROUP BY "+group;
			if(order != null)
				sql+= " ORDER BY " +order+" "+tipoOrder;
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
	public ArrayList<Vuelo> buscarVuelosPorCriterio3(ArrayList<String> cri,
			ArrayList<String> data, String fechaF, String fechaI, String order,
			String tipoOrder, String group, String pGroup,String aeroline) throws Exception 
	{
		PreparedStatement prepStmt = null;
		 vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql="";
			if(group!= null)
				sql += "SELECT "+group+", "+pGroup+" AS GROUP FROM VUELOS WHERE (("+cri.get(0)+" ='"+data.get(0)+"' AND (FECHA_SALIDA BETWEEN '"+fechaI+"' AND '"+fechaF+"')) OR ("+cri.get(1)+" ='"+data.get(1)+"' AND (FECHA_LLEGADA BETWEEN '"+fechaI+"' AND '"+fechaF+"'))) ";
			else
				sql += "SELECT * FROM VUELOS WHERE (("+cri.get(0)+" ='"+data.get(0)+"' AND (FECHA_SALIDA BETWEEN '"+fechaI+"' AND '"+fechaF+"')) OR ("+cri.get(1)+" ='"+data.get(1)+"' AND (FECHA_LLEGADA BETWEEN '"+fechaI+"' AND '"+fechaF+"'))) ";
			for (int i = 2; i < cri.size(); i++) 
			{
				if(data.get(i)!= null)
				sql +=" AND "+cri.get(i)+" ='"+data.get(i)+"' ";
			}
			sql+="AND AEROLINEA <> '"+aeroline+"'";
			if(group != null)
				sql+=" GROUP BY "+group;
			if(order != null)
				sql+= " ORDER BY " +order+" "+tipoOrder;
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

	public void cargar(String dir, int num) throws Exception 
	{
		PreparedStatement prepStmt = null;
        int i = 0;
		try {
             
            CsvReader dat = new CsvReader("C:/Users/jc.sanchez16/comas.csv");
            dat.readHeaders();
            establecerConexion();
            while (dat.readRecord()) 
            {
            	if(i>=num)
            	{
	                String CODIGO = dat.get(0);
	                String FRECUENCIA_SEMANAL = dat.get(1);
	                String FECHA_SALIDA = dat.get(2);
	                String FECHA_LLEGADA = dat.get(3);
	                String AEROPUERTO_SALIDA = dat.get(4).substring(0,3);
	                String AEROPUERTO_LLEGADA = dat.get(5).substring(0,3);
	                String AVION = dat.get(6);
	                String AEROLINEA = dat.get(7);
	                String TIPO = dat.get(8);
	                String PRECIO_EJECUTIVO = dat.get(9);      
	                String PRECIO_ECONOMICO = dat.get(10);
	                String PRECIO_DENSIDAD = dat.get(11);
	                String DISTANCIA = dat.get(12); 
	                String DURACION = dat.get(13);
	                String REALIZADO = dat.get(14);      
	                String NACIONAL = dat.get(15);
	                String HORA_SALIDA = dat.get(16);
	                String HORA_LLEGADA = dat.get(17);   
	                String sql ="INSERT INTO VUELOS (CODIGO, FRECUENCIA_SEMANAL, FECHA_SALIDA, FECHA_LLEGADA, AEROPUERTO_SALIDA, AEROPUERTO_LLEGADA, AVION, AEROLINEA, TIPO, PRECIO_EJECUTIVO, PRECIO_ECONOMICO, PRECIO_DENSIDAD, DISTANCIA, DURACION, REALIZADO, NACIONAL, HORA_SALIDA, HORA_LLEGADA) VALUES "
	                		+ "('"+CODIGO+"', '"+FRECUENCIA_SEMANAL+"', '"+FECHA_SALIDA+"', '"+FECHA_LLEGADA+"', '"+AEROPUERTO_SALIDA+"', '"+AEROPUERTO_LLEGADA+"', '"+AVION+"', '"+AEROLINEA+"', '"+TIPO+"', '"+PRECIO_EJECUTIVO+"', '"+PRECIO_ECONOMICO+"', '"+PRECIO_DENSIDAD+"', '"+DISTANCIA+"', '"+DURACION+"', '"+REALIZADO+"', '"+NACIONAL+"', '"+HORA_SALIDA+"', '"+HORA_LLEGADA+"')";
	                prepStmt = conexion.prepareStatement(sql);
	    			prepStmt.executeUpdate();
            	}
    			i++;
            }
             
            dat.close();
            
             
        }
		catch (Exception e) 
        {
        	System.err.println("SQLException in executing:");
        	cargar(dir, i);
		e.printStackTrace();
		throw e;
        } 
		finally {
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

	public ArrayList<Vuelo> vuelosUsuario(ArrayList<String> vuelos2,
			String fechaI, String fechaF, int tipoVuelo,
			String order, String tipoOrder) throws Exception {
		PreparedStatement prepStmt = null;
		 vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql="";
			String cod=vuelos2.get(0).split(";")[1];
			String ae =vuelos2.get(0).split(";")[0];
			sql += "SELECT * FROM VUELOS WHERE ((CODIGO = '"+cod+"' AND AEROLINEA = '"+ae+"') ";
			for (int i = 1; i < vuelos2.size(); i++) 
			{
				cod=vuelos2.get(i).split(";")[1];
				ae=vuelos2.get(i).split(";")[0];
				sql+=" OR (CODIGO = '"+cod+"' AND AEROLINEA = '"+ae+"') ";
			}
			sql+=") AND FECHA_SALIDA > '"+fechaI+"' AND FECHA_LLEGADA < '"+fechaF+"' AND TIPO = '"+tipoVuelo+"' ";
			if(order != null)
				sql+= " ORDER BY " +order+" "+tipoOrder;
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
	public ArrayList<Vuelo> buscarVuelosPorCiudades( String fechaF, String fechaI, ArrayList<Aeropuerto> aeropuertos) throws Exception 
	{
		PreparedStatement prepStmt = null;
		 vuelos = new ArrayList<Vuelo>();

		try {
			establecerConexion();
			String sql= "SELECT * FROM VUELOS WHERE (AEROPUERTO_SALIDA = '"+aeropuertos.get(0).getIATA()+"'";
			for (int i = 1; i < aeropuertos.size(); i++) 
			{
				sql +=" OR AEROPUERTO_SALIDA = '"+aeropuertos.get(i).getIATA()+"'";
			}
			sql+=") AND (AEROPUERTO_LLEGADA = '"+aeropuertos.get(0).getIATA()+"'";
			for (int i = 1; i < aeropuertos.size(); i++) 
			{
				sql +=" OR AEROPUERTO_LLEGADA  = '"+aeropuertos.get(i).getIATA()+"'";
			}
			sql+= " ) AND FECHA_SALIDA > '"+fechaI+"' AND FECHA_LLEGADA < '"+fechaF+"'";
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
}
