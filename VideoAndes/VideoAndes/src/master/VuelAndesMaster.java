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
	public Vuelo darVueloPK(int codigo,  String aerolinea)throws Exception
	{
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		return daoVuelos.darVuelosPorPK(codigo, aerolinea);
	}
	public String asignarAeronave(int idAvion, int idVuelo, String aerolinea) throws Exception
	{
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		daoReservas = daoReservas== null ? new DAOReserva(connectionDataPath) : daoReservas;
		Avion avion = daoAviones.buscarAvionPK(idAvion);
		ArrayList<Cliente> clientes = daoReservas.buscarReservaporvuelo(idVuelo, aerolinea);
		Vuelo vuelo = daoVuelos.darVuelosPorPK(idVuelo, aerolinea);
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
				
			daoVuelos.asignarAvion(idVuelo, aerolinea, idAvion);
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
				
			daoVuelos.asignarAvion(idVuelo, aerolinea, idAvion);
		}

	}
	
	public String registrarViajero(int idVuelo, String aerolinea,  String tipoIdentificacion, int id, int asEje, int asEco) throws Exception
	{
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		daoReservas = daoReservas== null ? new DAOReserva(connectionDataPath) : daoReservas;
		Vuelo vuelo = daoVuelos.darVuelosPorPK(idVuelo, aerolinea);
		Date hoy = new Date();
		if(vuelo.getFechaSalida().before( hoy))
			return "El vuelo ya sucedio";
		Avion avion = daoAviones.buscarAvionPK(vuelo.getAvion());
		ArrayList<Cliente> clientes = daoReservas.buscarReservaporvuelo(idVuelo, aerolinea);
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
				return "El avion no tiene la capacidad necesaria";			
		}
			else 
			daoReservas.registrarPasajero(idVuelo, aerolinea, tipoIdentificacion, id);
	}
	
	public String registrarCarga(int idVuelo, String aerolinea, String tipoIdentificacion, int id, float carga) throws Exception
	{
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		daoReservas = daoReservas== null ? new DAOReserva(connectionDataPath) : daoReservas;
		Vuelo vuelo = daoVuelos.darVuelosPorPK(idVuelo, aerolinea);
		Date hoy = new Date();
		if(vuelo.getFechaSalida().before( hoy))
			return "El vuelo ya sucedio";
		Avion avion = daoAviones.buscarAvionPK(vuelo.getAvion());
		ArrayList<Cliente> clientes = daoReservas.buscarReservaporvuelo(idVuelo, aerolinea);
		if(vuelo.isTipo()==Vuelo.CARGA)
		{
			float car = 0;
			for (int i = 0; i < clientes.size(); i++) 
				carga +=((Remitente)clientes.get(i)).getDensidadCarga();
			car = ((AvionCarga)avion).getCapacidadDensidad()-car;

			if(  car< carga)
				return "El avion no tiene la capacidad necesaria";			}
			else 
			daoReservas.registrarPasajero(idVuelo, aerolinea, tipoIdentificacion, id);
	}
	
	public String registrarReserva2(String tipoId,int idViajero, String idAeroLlegada,String idAeroSalida, boolean economica, String fecha1) throws Exception 
	{
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		daoAviones = daoAviones == null ? new DAOAviones(connectionDataPath) : daoAviones;
		daoReservas = daoReservas== null ? new DAOReserva(connectionDataPath) : daoReservas;	
		ArrayList<String> cri =new ArrayList<>();
		cri.add("AEROPUERTO_SALIDA");
		cri.add("FECHA_SALIDA");
		cri.add("TIPO");
		ArrayList<String> data =new ArrayList<>();
		data.add(""+idAeroSalida);
		data.add(fecha1);
		data.add(""+0);
		ArrayList<String> respuesta = null;
		ArrayList<Vuelo> vuelos = daoVuelos.buscarVuelosPorCriterio(cri, data);
		Vuelo v = llegaA(vuelos, idAeroLlegada);
		if(v!=null )
		{
			Avion avion = daoAviones.buscarAvionPK(v.getAvion());
			ArrayList<Cliente> clientes = daoReservas.buscarReservaporvuelo(v.getCodigo(), v.getAerolinea());
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

			if((economica && eco>=1)||(!economica && ejecu>=1))		
				return v.getAerolinea()+v.getCodigo()+","+tipoId+idViajero;			
		}
		for (int i = 0; i < vuelos.size(); i++) 
		{	
			Vuelo vuel =vuelos.get(i);
			Avion avion = daoAviones.buscarAvionPK(vuel.getAvion());
			ArrayList<Cliente> clientes = daoReservas.buscarReservaporvuelo(vuel.getCodigo(), vuel.getAerolinea());
			int ejecu = 0;
			int eco= 0 ;
			for (int j = 0; j < clientes.size(); j++) 
			{
				Pasajero este = (Pasajero)clientes.get(j);
				if(este.isEconomica()==Pasajero.ECONOMICO)
					eco++;
				else
					ejecu++;
			}
			eco = ((AvionPasajeros)avion).getAsientosEconomica()-eco;
			ejecu = ((AvionPasajeros)avion).getAsientosEjecutivo() - ejecu;

			if((economica && eco>=1)||(!economica && ejecu>=1))		
				respuesta.add( vuel.getAerolinea()+vuel.getCodigo()+","+tipoId+idViajero+"/"+registrarReserva2(tipoId,idViajero, vuel.getLlegada(), idAeroSalida, economica, vuel.getFechaLlegada().toString()));
		}
		String mejor = null;
		int mejo = 10000000;
		for (int i = 0; i < respuesta.size(); i++) 
		{
			if(respuesta.get(i).split("/").length<mejo)
			{
				mejo =respuesta.get(i).split("/").length;
				mejor =respuesta.get(i);			
			}
		}
		return mejor;
	}
		
	public Vuelo llegaA(ArrayList<Vuelo>vuelos, String llegada)
	{
		boolean encontrado = false;
		for (int i = 0; i < vuelos.size() && !encontrado; i++) 
		{
			if(vuelos.get(i).getLlegada().equals(llegada))
			return vuelos.get(i);
		}
		return null;
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
