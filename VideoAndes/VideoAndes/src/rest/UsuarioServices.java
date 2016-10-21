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

import vos.Cliente;
import master.VuelAndesMaster;

@Path("usuario")
public class UsuarioServices 
{
	@Context
	private ServletContext context;

	private String getPath() 
	{
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	@GET
	@Path("/list")
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
	@POST
	@Path("{tipo: [a-zA-Z][a-zA-Z]*}/{idRemitente: \\d+}/{codigo: [a-zA-Z][a-zA-Z]\\d+}/{peso: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF9AsignarReseva(@PathParam("idRemitente") int idRemitente, @PathParam("codigo") String vuelo,@PathParam("peso") int peso,@PathParam("tipo") String tipoIdentificacion) 
	{
		String reserva = null;
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		int idVuelo =  Integer.parseInt(vuelo.substring(3, 4));
		String aero = vuelo.substring(0, 2);
		try 
		{
			 reserva = fachada.registrarCarga(idVuelo, aero, tipoIdentificacion, idRemitente, peso);
			 System.out.println(reserva);
		} 
		catch (Exception e) 
		{
			return Response.status(500).entity(reserva).build();
		}
		return Response.status(200).entity(reserva).build();
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
			 reserva = fachada.registrarViajero(idViajero,idVuelo,sillasEconomicas,sillasEjecutivas);
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
