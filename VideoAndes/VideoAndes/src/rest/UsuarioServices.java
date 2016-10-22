package rest;

import java.sql.Date;
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
	@Path("{tipo: [a-zA-Z][a-zA-Z]*}/{idRemitente: \\d+}/{codigo:[A-Z]{3}[0-9]{3}}/{peso: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RF9AsignarReseva(@PathParam("idRemitente") int idRemitente, @PathParam("codigo") String vuelo,@PathParam("peso") int peso,@PathParam("tipo") String tipoIdentificacion) 
	{
		String reserva = null;
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		int idVuelo =  Integer.parseInt(vuelo.substring(3, 5));
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
	public Response RF8AsignarReseva(@PathParam("idViajero") int idViajero, @PathParam("idVuelo") String idVuelo,@PathParam("silla") int silla, String tipoIdentificacion) 
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
	
	//RF12
		@POST 
		@Path("RegistarReserva2")
		@Produces({ MediaType.APPLICATION_JSON })
		public Response RF12RegistrarReserva2(@QueryParam("idViajero") int idViajero, @QueryParam("fecha") String fecha, @QueryParam("idAeroSalida") String idAeroSalida,
				@QueryParam("idAeroLlegada") String idAeroLlegada, @QueryParam("idTipoSilla") int idTipoSilla,@QueryParam("tipoIdentificacion") String idTipo )
		{
			VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
			String reserva = null;
			String general = idTipo+idViajero;
			boolean economica =idTipoSilla==0? true:false;
			try
			{
				 reserva = fachada.registrarReserva2(idTipo,idViajero,idAeroLlegada,idAeroSalida,economica,fecha); 
				 String[] r = reserva.split("/");
				 for (int i = 0; i < r.length; i++) 
				 {
					 String[] r1 = r[i].split(",");
					 general+= r1[0];		
					 RF8AsignarReseva(idViajero, r1[0], idTipoSilla, idTipo);
				 }
			}
			catch (Exception e) 
			{
				return Response.status(500).entity(general+reserva).build();
			}
			return Response.status(200).entity(general+reserva).build();
		}
		
		//RF13
		@DELETE
		@Path("CancelarReservaViajeroVuelo")
		public void RF13CancelarReservaViajeroVuelo(@QueryParam("idViajero") int idViajero, @QueryParam("idReserva") int idReserva)
		{
			VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
			try
			{
				fachada.cancelarReservaViajeroVuelo(idViajero,idReserva);
			}
			catch (Exception e) 
			{
				System.out.println("no se pudo cancelar la reserva");
			}
			System.out.println("Se cancelo la reserva");
		}
		
		//RF14
			@DELETE
			@Path("CancelarReservaViajero")
			public void RF14CancelarReservaViajero(@QueryParam("idViajero") int idViajero, @QueryParam("idReserva") int idReserva)
			{
				VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
				try
				{
					fachada.cancelarReservaViajero(idViajero,idReserva);
				}
				catch (Exception e) 
				{
					System.out.println("no se pudo cancelar la reserva");
				}
				System.out.println("Se cancelo la reserva");
			}
	

}
