package fachada;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOVuelos;
import vos.Vuelo;


public class VuelAndesFachada {

	private static VuelAndesFachada instacia;

	private static final String CONNECTION_DATA_FILE_NAME_LOCAL = "/conexionLocal.properties";

	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private static String connectionDataPath;

	private DAOVuelos daoVuelos;

	public static VuelAndesFachada darInstancia(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		instacia = instacia == null ? new VuelAndesFachada() : instacia;
		return instacia;
	}

	private VuelAndesFachada() {
		daoVuelos = new DAOVuelos(connectionDataPath);
	}

	public ArrayList<Vuelo> darVuelos() throws Exception {
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		return daoVuelos.darVuelos();
	}

	public ArrayList<Vuelo> darVideosConError() throws Exception {
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		return daoVuelos.darVideosConError();
	}

	public ArrayList<Vuelo> buscarVideosPorName(String name) throws Exception {
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		return daoVuelos.buscarVideosPorName(name);
	}

	public ArrayList<Vuelo> buscarVideosPorNameYId(String name, int id) throws Exception {
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		return daoVuelos.buscarVideosPorNameYId(name, id);
	}

}
