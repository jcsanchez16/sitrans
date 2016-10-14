import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import master.VuelAndesMaster;

public class ViajerosServices 
{
	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	@POST
	@Path("{idViajero: \\d+}/{idVuelo: \\d+}/{sillasEconomicas: \\d+}/{sillasEjecutivas: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF8AsignarReseva(@PathParam("idViajero") int idViajero, @PathParam("idVuelo") int idVuelo,@PathParam("sillasEconomicas") int sillasEconomicas
			,@PathParam("sillasEjecutivas") int sillasEjecutivas, String aerolinea, String fecha, String tipoIdentificacion) 
	{
		int reserva = 0;
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		try 
		{
			 reserva = fachada.registrarViajero(idVuelo, aerolinea, fecha, tipoIdentificacion, idViajero, sillasEjecutivas, sillasEconomicas);
			 System.out.println(reserva);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(reserva).build();
		}
		return Response.status(200).entity(reserva).build();
	}


}
