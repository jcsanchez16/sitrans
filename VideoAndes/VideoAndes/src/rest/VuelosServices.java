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
	@GET
	@Path("/Aerolineas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetAerolineas() {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Aerolinea> a = null;
		try {
			a = master.darAerolineas();
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@GET
	@Path("/Aeropuertos")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetAeropuertos() {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Aeropuerto> a = null;
		try {
			a = master.darAerpuertos();
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@GET
	@Path("/Clientes")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetClientesT() {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Cliente> clientes = null;
		try {
			clientes = master.darCLientes();
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(clientes).build();
	}
	@GET
	@Path("/Aviones")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetAviones() {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Avion> a = null;
		try {
			a = master.darAviones();
		} catch (Exception e) {
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(a).build();
	}
	@GET
	@Path("/Reservas")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response GetVideosGET() {
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
