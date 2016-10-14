import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import master.VuelAndesMaster;
import fachada.Master;
import vos.Reserva;

@Path("serviceRemitente")
public class ReminitenteServices 
{
	@Context
	private ServletContext context;

	private String getPath() 
	{
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	@POST
	@Path("{idRemitente: \\d+}/{idVuelo: \\d+}/{peso: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF9AsignarReseva(@PathParam("idRemitente") int idRemitente, @PathParam("idVuelo") int idVuelo,@PathParam("peso") int peso, String tipoIdentificacion, String fecha, String aerolinea) 
	{
		int reserva = 0;
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		try 
		{
			 reserva = fachada.registrarCarga(idVuelo, aerolinea, fecha, tipoIdentificacion, idRemitente, peso);
			 System.out.println(reserva);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(reserva).build();
		}
		return Response.status(200).entity(reserva).build();
	}
	

}
