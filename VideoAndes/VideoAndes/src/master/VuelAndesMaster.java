package master;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOVuelos;
import vos.Vuelo;


public class VuelAndesMaster {

	private static VuelAndesMaster instacia;

	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	private static String connectionDataPath;

	private DAOVuelos daoVuelos;

	public static VuelAndesMaster darInstancia(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		instacia = instacia == null ? new VuelAndesMaster() : instacia;
		return instacia;
	}

	private VuelAndesMaster() {
		daoVuelos = new DAOVuelos(connectionDataPath);
	}

	public ArrayList<Vuelo> darVuelos() throws Exception {
		daoVuelos = daoVuelos == null ? new DAOVuelos(connectionDataPath) : daoVuelos;
		return daoVuelos.darVuelos();
	}

}
