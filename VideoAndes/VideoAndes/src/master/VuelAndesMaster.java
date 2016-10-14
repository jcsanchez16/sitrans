package master;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import dao.DAOAerolineas;
import dao.DAOAeropuertos;
import dao.DAOAviones;
import dao.DAOCliente;
import dao.DAOReserva;
import dao.DAOVuelos;
import vos.Aerolinea;
import vos.Aeropuerto;
import vos.Avion;
import vos.AvionCarga;
import vos.AvionPasajeros;
import vos.Cliente;
import vos.Pasajero;
import vos.Remitente;
import vos.Vuelo;


public class VuelAndesMaster {

	private static VuelAndesMaster instacia;

	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private static String connectionDataPath;

	private DAOVuelos daoVuelos;
	
	private DAOAeropuertos daoAeropuertos;
	
	private DAOAerolineas daoAerolineas;
	
	private DAOAviones daoAviones;
	
	private DAOCliente daoClientes;
	
	private DAOReserva daoReservas;
	
	public static VuelAndesMaster darInstancia(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		instacia = instacia == null ? new VuelAndesMaster() : instacia;
		return instacia;
	}

	private VuelAndesMaster() {
		daoVuelos = new DAOVuelos(connectionDataPath);
		daoAerolineas = new DAOAerolineas(connectionDataPath);
		daoAeropuertos = new DAOAeropuertos(connectionDataPath);
		daoAviones = new DAOAviones(connectionDataPath);
		daoClientes = new DAOCliente(connectionDataPath);
		daoReservas = new DAOReserva(connectionDataPath);
		daoVuelos = new DAOVuelos(connectionDataPath);
		}

	public ArrayList<Vuelo> darVuelos() throws Exception {
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		return daoVuelos.darVuelos();
	}
	public Vuelo darVueloPK(int codigo, String fecha, String aerolinea)throws Exception
	{
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		return daoVuelos.darVuelosPorPK(codigo, fecha, aerolinea);
	}
	public String asignarAeronave(int idAvion, int idVuelo, String aerolinea, String fecha) throws Exception
	{
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		daoReservas = daoReservas== null ? new DAOReserva(connectionDataPath) : daoReservas;
		Avion avion = daoAviones.buscarAvionPK(idAvion);
		ArrayList<Cliente> clientes = daoReservas.buscarReservaporvuelo(idVuelo, aerolinea, fecha);
		Vuelo vuelo = daoVuelos.darVuelosPorPK(idVuelo, fecha, aerolinea);
		if(vuelo.isTipo()==Vuelo.PASAJEROS)
		{
			int ejecu = 0;
			int eco= 0 ;
			for (int i = 0; i < clientes.size(); i++) 
			{
			Pasajero este = (Pasajero)clientes.get(i);
				if(este.isEconomica()==Pasajero.ECONOMICO)
					eco++;
				else
					ejecu++;
			}
			if(avion.isTipo() == Avion.PASAJEROS)
			{
				if(((AvionPasajeros)avion).getAsientosEconomica() < eco || ((AvionPasajeros)avion).getAsientosEjecutivo() < ejecu)
				{
					return "El avion no tiene la capacidad necesaria";
				}
			}
			else return "El avion no es del tipo necesario";
				
			daoVuelos.asignarAvion(idVuelo, fecha, aerolinea, idAvion);
		}
		else 
		{
			float carga;
			for (int i = 0; i < clientes.size(); i++) 
			{
				carga +=((Remitente)clientes.get(i)).getDensidadCarga();
			}
			if(avion.isTipo() == Avion.CARGA)
			{
				if(((AvionCarga)avion).getCapacidadDensidad() < carga )
				{
					return "El avion no tiene la capacidad necesaria";
				}
			}
			else return "El avion no es del tipo necesario";
				
			daoVuelos.asignarAvion(idVuelo, fecha, aerolinea, idAvion);
		}

	}
	
	public String registrarViajero(int idVuelo, String aerolinea, String fecha, String tipoIdentificacion, int id, int asEje, int asEco) throws Exception
	{
		Date hoy = new Date();
		if(java.sql.Date.valueOf(fecha).before( hoy))
			return "El vuelo ya sucedio";
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		daoReservas = daoReservas== null ? new DAOReserva(connectionDataPath) : daoReservas;
		Vuelo vuelo = daoVuelos.darVuelosPorPK(idVuelo, fecha, aerolinea);
		Avion avion = daoAviones.buscarAvionPK(vuelo.getAvion());
		ArrayList<Cliente> clientes = daoReservas.buscarReservaporvuelo(idVuelo, aerolinea, fecha);
		if(vuelo.isTipo()==Vuelo.PASAJEROS)
		{
			int ejecu = 0;
			int eco= 0 ;
			for (int i = 0; i < clientes.size(); i++) 
			{
				Pasajero este = (Pasajero)clientes.get(i);
				if(este.isEconomica()==Pasajero.ECONOMICO)
					eco++;
				else
					ejecu++;
			}
			eco = ((AvionPasajeros)avion).getAsientosEconomica()-eco;
			ejecu = ((AvionPasajeros)avion).getAsientosEjecutivo() - ejecu;

			if(  eco < asEco ||   ejecu< asEje)
				return "El avion no tiene la capacidad necesaria";			}
			else 
			daoReservas.registrarPasajero(idVuelo, fecha, aerolinea, tipoIdentificacion, id);
	}
	
	public String registrarViajero(int idVuelo, String aerolinea, String fecha, String tipoIdentificacion, int id, float carga) throws Exception
	{
		Date hoy = new Date();
		if(java.sql.Date.valueOf(fecha).before( hoy))
			return "El vuelo ya sucedio";
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		daoReservas = daoReservas== null ? new DAOReserva(connectionDataPath) : daoReservas;
		Vuelo vuelo = daoVuelos.darVuelosPorPK(idVuelo, fecha, aerolinea);
		Avion avion = daoAviones.buscarAvionPK(vuelo.getAvion());
		ArrayList<Cliente> clientes = daoReservas.buscarReservaporvuelo(idVuelo, aerolinea, fecha);
		if(vuelo.isTipo()==Vuelo.CARGA)
		{
			float car = 0;
			for (int i = 0; i < clientes.size(); i++) 
				carga +=((Remitente)clientes.get(i)).getDensidadCarga();
			car = ((AvionCarga)avion).getCapacidadDensidad()-car;

			if(  car< carga)
				return "El avion no tiene la capacidad necesaria";			}
			else 
			daoReservas.registrarPasajero(idVuelo, fecha, aerolinea, tipoIdentificacion, id);
	}
	
	
	
	
	
	
	
	
	
	public ArrayList<Aerolinea> darAerolineas() throws Exception {
		daoAerolineas = daoAerolineas == null ? new DAOAerolineas(connectionDataPath) : daoAerolineas;
		return daoAerolineas.darAerolineas();
	}
	public Aerolinea buscarAerolineaPK(String c )throws Exception
	{
		daoAerolineas = daoAerolineas == null ? new DAOAerolineas(connectionDataPath) : daoAerolineas;
		return daoAerolineas.buscarAerolineasPK(c);
	}
	
	
	
	
	
	
	
	public ArrayList<Aeropuerto> darAerpuertos() throws Exception {
		daoAeropuertos = daoAeropuertos == null ? new DAOAeropuertos(connectionDataPath) : daoAeropuertos;
		return daoAeropuertos.darAeropuertos();
	}
	public Aeropuerto buscarAeropuertoPK(String c )throws Exception
	{
		daoAeropuertos = daoAeropuertos == null ? new DAOAeropuertos(connectionDataPath) : daoAeropuertos;
		return daoAeropuertos.buscarAeropuertoPK(c);
	}
	
	
	
	
	
	
	
	public ArrayList<Avion> darAviones() throws Exception {
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		return daoAviones.darAviones();
	}
	public Avion buscarAvionPK(int c )throws Exception
	{
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		return daoAviones.buscarAvionPK(c);
	}







	public ArrayList<Cliente> darCLientes() throws Exception {
		daoClientes = daoClientes == null ? new DAOCliente(connectionDataPath) : daoClientes;
		return daoClientes.darClientes();
	}
	public Cliente buscaClientePK(int id, String tip )throws Exception
	{
		daoClientes = daoClientes == null ? new DAOCliente(connectionDataPath) : daoClientes;
		return daoClientes.buscarClientePK(id, tip);
	}
}
