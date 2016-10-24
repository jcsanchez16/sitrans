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
	@GET
	@Path("prueba")
	@Produces({ MediaType.APPLICATION_JSON })
	public java.util.Date Get() {
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		java.util.Date g = new java.util.Date();
		return g;
	}
	@POST
	@Path("{tipo: [a-zA-Z][a-zA-Z]*}/{idRemitente: \\d+}/{codigo:[A-Z]{3}[0-9]{3}}/{peso: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String RF9AsignarReseva(@PathParam("idRemitente") int idRemitente, @PathParam("codigo") String vuelo,@PathParam("peso") int peso,@PathParam("tipo") String tipoIdentificacion) 
	{
		String reserva = null;
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		int idVuelo =  Integer.parseInt(vuelo.substring(3, 5));
		String aero = vuelo.substring(0, 2);
		try 
		{
			 reserva = fachada.registrar(idVuelo, aero, tipoIdentificacion, idRemitente, peso,false);
			 System.out.println(reserva);
		} 
		catch (Exception e) 
		{
			return reserva;
		}
		return reserva;
	}
	
	@POST
	@Path("{idViajero: \\d+}/{idVuelo: \\d+}/{sillasEconomicas: \\d+}/{sillasEjecutivas: \\d+}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String R8Registrar (@PathParam("idViajero") int idViajero, @PathParam("idVuelo") String idVuelo,@PathParam("silla") int silla, String tipoIdentificacion) 
	{
		String reserva = null;
		int vuel= Integer.parseInt(idVuelo.split(";")[1]);
		String aerolinea = idVuelo.split(";")[0];
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		boolean economica =silla==0? true:false;
		try 
		{
			reserva = fachada.registrar(vuel, aerolinea, tipoIdentificacion, idViajero,0, economica);
			System.out.println(reserva);
		} 
		catch (Exception e) 
		{
			return reserva;
		}
		return reserva;
	}
	
	//RF12
		@POST 
		@Path("RegistarReserva2")
		@Produces({ MediaType.APPLICATION_JSON })
		public String RF12RegistrarReserva2(@QueryParam("idViajero") int idViajero, @QueryParam("fecha") String fecha, @QueryParam("idAeroSalida") String idAeroSalida,
				@QueryParam("idAeroLlegada") String idAeroLlegada, @QueryParam("idTipoSilla") int idTipoSilla,@QueryParam("tipoIdentificacion") String idTipo )
		{
			VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
			String reserva = null;
			String general = idTipo+";"+idViajero;
			boolean economica =idTipoSilla==0? true:false;
			try
			{
				 String res = fachada.registrarReserva2(idTipo,idViajero,idAeroLlegada,idAeroSalida,economica,fecha,0,null); 
				 String[] r = res.split("/");
				 reserva =R8Registrar(idViajero, r[0], idTipoSilla, idTipo);
				 general +=","+r[0];
				 
				 for (int i = 1; i < r.length; i++) 
				 {
					 general += ","+ r[i];
					reserva += "/"+R8Registrar(idViajero, r[i], idTipoSilla, idTipo);
				 }
			}
			catch (Exception e) 
			{
				return general+reserva;
			}
			return general+reserva;
		}
		
		//RF13
		@DELETE
		@Path("CancelarReservaViajeroVuelo")
		public String RF13CancelarReservaViajeroVuelo(@QueryParam("idReserva") String idReserva)
		{
			VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
			String g= null;
			try
			{
				g =fachada.cancelarReservaViajeroVuelo(idReserva);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return g;
		}
		
		//RF14
			@DELETE
			@Path("CancelarReservaViajero")
			public String RF14CancelarReservaViajero( @QueryParam("idReserva") String idReserva)
			{
				VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
				
				try
				{
					String[] reservas = idReserva.split(",");
					String cli = reservas[0];
					for (int i = 1; i < reservas.length; i++) 
					{
						String mes = RF13CancelarReservaViajeroVuelo(cli+reservas[i]);
						if(!mes.equals("cancelacion exitosa"))
							return "fallo en la cancelacion";
					}
				}
				catch (Exception e) 
				{
					System.out.println("no se pudo cancelar la reserva");
				}
				return "Se realizo la cancelacion";
				
			}
	

}
