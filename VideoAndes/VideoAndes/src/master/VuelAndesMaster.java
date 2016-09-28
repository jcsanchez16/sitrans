package master;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOAerolineas;
import dao.DAOAeropuertos;
import dao.DAOAviones;
import dao.DAOClientes;
import dao.DAOReservas;
import dao.DAOVuelos;
import vos.Aerolinea;
import vos.Aeropuerto;
import vos.Avion;
import vos.Cliente;
import vos.Vuelo;


public class VuelAndesMaster {

	private static VuelAndesMaster instacia;

	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private static String connectionDataPath;

	private DAOVuelos daoVuelos;
	
	private DAOAeropuertos daoAeropuertos;
	
	private DAOAerolineas daoAerolineas;
	
	private DAOAviones daoAviones;
	
	private DAOClientes daoClientes;
	
	private DAOReservas daoReservas;
	
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
		daoClientes = new DAOClientes(connectionDataPath);
		daoReservas = new DAOReservas(connectionDataPath);
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
		daoClientes = daoClientes == null ? new DAOClientes(connectionDataPath) : daoClientes;
		return daoClientes.darClientes();
	}
	public Cliente buscaClientePK(int id, String tip )throws Exception
	{
		daoClientes = daoClientes == null ? new DAOClientes(connectionDataPath) : daoClientes;
		return daoClientes.buscarClientePK(id, tip);
	}
}
