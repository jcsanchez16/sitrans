package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import master.VuelAndesMaster;
import vos.Aerolinea;
import vos.Aeropuerto;
import vos.Avion;
import vos.Cliente;
import vos.Vuelo;
import vos.VueloPasajeros;

@Path("vuelo")
public class VuelosServices {

	// GET SERVICES:

	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}

	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetVideos() {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Vuelo> vuelos = null;
		try {
			vuelos = master.darVuelos();
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	
	
	
	
	
	
	

	
}
