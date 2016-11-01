package rest;

import java.util.ArrayList;
import java.util.Date;

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
import vos.Cliente;
import vos.Pasajero;
import vos.Vuelo;
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
	public String RF15CancelarVuelo( @QueryParam("idVuelo") String idVuelo)
	{
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		String resp = "se a�adieron los viajes a :\n";
		try
		{
			int vuel= Integer.parseInt(idVuelo.split(";")[1]);
			String aerolinea = idVuelo.split(";")[0];
			ArrayList<Cliente> clientes = fachada.darCLientesPorVuelo(idVuelo); 
			for (int i = 0; i < clientes.size(); i++) 
			{
				Cliente s = clientes.get(i);
				String[] vuelos = fachada.cancelado( idVuelo, s,new Date()).split("/");
				for (int j = 0; j < vuelos.length; j++) 
				{
					resp+= s.getTipoIdentificacion()+";"+ s.getIdentificacion()+ "        " +fachada.registrar(vuel, aerolinea, s.getTipoIdentificacion(), s.getIdentificacion(), 0, ((Pasajero)s).isEconomica())+"\n";
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "No hay rutas posibles";
		}
		return resp;
	}
}
