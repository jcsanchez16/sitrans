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
import vos.Vuelo;

@Path("api")
public class VuelosServices {

	// GET SERVICES:

	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}

	@GET
	@Path("/vuelos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetVideosGET() {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		System.out.println("holi mundo");
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

	@GET
	@Path("/GetVideosGETError")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetVideosGETError() {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Vuelo> vuelos = null;
		try {
			vuelos = master.darVideosConError();
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(vuelos).build();
	}

	@GET
	@Path("/GetVideosByNameGET")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetVideosByNameGET(@QueryParam("name") String name) {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Vuelo> vuelos = null;
		try {
			vuelos = master.buscarVideosPorName(name);
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(vuelos).build();
	}

	// POST SERVICES:

	@POST
	@Path("/GetVideosPOST")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetVideosPOST() {
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

	@POST
	@Path("/GetVideosByNameAndIdPOST")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetVideosByNameAndIdPOST(@FormParam("name") String name, @FormParam("id") String id) {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Vuelo> vuelos = null;
		try {
			vuelos = master.buscarVideosPorNameYId(name, Integer.parseInt(id));
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	

	
	//----------------------Requerimientos-------------------------//
	
	

}
