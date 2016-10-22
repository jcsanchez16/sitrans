package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import master.VuelAndesMaster;
import vos.Aerolinea;
import vos.Avion;
@Path("aerolinea")
public class AerolineaServices 
{
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	@GET
	@Path("/list")
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
	
	@POST
	@Path("{idAeronave: \\d+}/{codigo:[A-Z]{3}[0-9]{3}}")
	public Response RF7asignarAeronave(@PathParam("idAeronave") int idAeronave, @PathParam("codigo") String vuelo) 
	{
		int idVuelo =  Integer.parseInt(vuelo.substring(3,5));
		String aero = vuelo.substring(0, 2);
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Avion> aeronaves = null;
		try 
		{
			fachada.asignarAeronave(idAeronave,idVuelo,aero);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(aeronaves).build();
		}
		return Response.status(200).entity(aeronaves).build();
	}
	}
