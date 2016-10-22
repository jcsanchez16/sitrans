package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import master.VuelAndesMaster;
import vos.Aerolinea;
import vos.Aeropuerto;
import vos.Avion;
@Path("aeropuerto")
public class AeropuertoServices 
{
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	@GET
	@Path("/list")
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
	
	@DELETE
	@Path("CancelarVuelo")
	public void RF13CancelarReservaViajeroVuelo(@QueryParam("idViajero") int idViajero, @QueryParam("idReserva") int idReserva)
	{
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		try
		{
			fachada.cancelarVuelo(idViajero,idReserva);
		}
		catch (Exception e) 
		{
			System.out.println("no se pudo cancelar la reserva");
		}
		System.out.println("Se cancelo la reserva");
	}
}
