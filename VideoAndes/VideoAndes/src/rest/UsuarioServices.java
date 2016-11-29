package rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import vos.Cliente;
import vos.Remitente;
import vos.Vuelo;
import vos.VueloCarga;
import vos.VueloPasajeros;
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
	public Response GetClientesT() 
	{
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
	public String Get() 
	{
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		
		try {
			return master.darAerolineas().get(0).toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	@POST
	@Path("RF9")
	@Produces({ MediaType.APPLICATION_JSON })
	public String RF9AsignarReseva(@QueryParam("idRemitente") int idRemitente, @QueryParam("idVuelo") int idVuelo, @QueryParam("aerolinea") String aero,@QueryParam("peso") Float peso,@QueryParam("tipo") String tipoIdentificacion) 
	{
		String reserva = "algo fallo :(";
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
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
	@Path("RF8")
	@Produces({ MediaType.APPLICATION_JSON })
	public String R8Registrar (@QueryParam("idViajero") int idViajero, @QueryParam("idVuelo") int vuel, @QueryParam("aerolinea") String aerolinea,@QueryParam("silla") int silla,@QueryParam("tipo") String tipoIdentificacion) 
	{
		String reserva = "algo fallo :(";
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		boolean economica =silla==0? true:false;
		try 
		{
			reserva = fachada.registrar(vuel, aerolinea, tipoIdentificacion, idViajero,Float.parseFloat(0+""), economica);
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
	@Path("RF12")
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

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date fecha2 = formatter.parse(fecha);
			String res = fachada.registrarReserva2(idTipo,idViajero,idAeroLlegada,idAeroSalida,economica,fecha2,0,null,1,1); 
			String[] r = res.split(",");
			reserva =R8Registrar(idViajero, Integer.parseInt(r[0].split(";")[1]),r[0].split(";")[0], idTipoSilla, idTipo);
			general +=","+r[0];

			for (int i = 1; i < r.length; i++) 
			{
				general += ","+ r[i];
				reserva += "/"+R8Registrar(idViajero, Integer.parseInt(r[i].split(";")[1]),r[i].split(";")[0], idTipoSilla, idTipo);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return general+reserva;
		}
		return general+"*"+reserva;
	}

	//RF13
	@DELETE
	@Path("RF13")
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
	@Path("RF14")
	public String RF14CancelarReservaViajero( @QueryParam("idReserva") String idReserva)
	{
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());

		try
		{
			String[] reservas = idReserva.split(",");
			String cli = reservas[0];
			for (int i = 1; i < reservas.length; i++) 
			{
				String mes = RF13CancelarReservaViajeroVuelo(reservas[i]+","+cli);
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

	//RF16
	@POST
	@Path("RF16")
	public String RF16RegistrarGrupo( @QueryParam("idPersonas") String personas,@QueryParam("idAeroLlegada") String idAeroLlegada,@QueryParam("idAeroSalida") String idAeroSalida ,@QueryParam("economica") int econ,@QueryParam("fecha") String fecha)
	{
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		String respuesta = "no se pudo hacer la reserva";

		try
		{
			boolean economica =econ==0? true:false;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date fecha1 = formatter.parse(fecha);
			String[] persona = personas.split(",");
			String res =fachada.registrarReserva2(persona[0].split(";")[0], Integer.parseInt(persona[0].split(";")[1]), idAeroLlegada, idAeroSalida, economica, fecha1, 8, null, persona.length, persona.length);
			System.out.println(res);
			String[] re = res.split(",");
			respuesta = personas+"$"+res;
			String resp = "";
			for (int i = 0; i < persona.length; i++) 
			{
				for (int j = 0; j < re.length; j++) 
				{
					resp+="-"+R8Registrar(Integer.parseInt(persona[i].split(";")[1]), Integer.parseInt(re[j].split(";")[1]),re[j].split(";")[0], econ, persona[i].split(";")[0]);
				}
			}
			return respuesta+"*"+resp.substring(1);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("no se pudo hacer la reserva");
			return respuesta;
		}

	}
	@GET 
	@Path("RFC7-8")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RFC7(@QueryParam("aeropuerto") String aeropuerto,@QueryParam("aerolinea") String aerolinea, @QueryParam("fechaInicial") String fechaI,  @QueryParam("fechaFinal") String fechaF, @QueryParam("tipoVuelo") int tipoVuelo, @QueryParam("hLlegada")String fechaL, @QueryParam("hSalida")String fechaS,@QueryParam("order") String order,@QueryParam("orderT") String tipoOrder,@QueryParam("group") String group,@QueryParam("paraGroup")String pGroup,@QueryParam("siAero")int aero)
	{
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Vuelo> vuelos = new ArrayList<>();
		boolean aeros =aero==0? true:false;
		try
		{
			vuelos = fachada.buscarVuelos7(aeropuerto,aerolinea,fechaI,fechaF,fechaL,fechaS,tipoVuelo,order,tipoOrder,group,pGroup,aeros); 
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(vuelos).build();
	}
	@GET 
	@Path("RFC9")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response RFC9( @QueryParam("fechaInicial") String fechaI,  @QueryParam("fechaFinal") String fechaF, @QueryParam("tipoVuelo") int tipoVuelo,@QueryParam("order") String order,@QueryParam("orderT") String tipoOrder,@QueryParam("minMillas") int limite)
	{
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Cliente> usuarios = new ArrayList<>();
		try
		{
			usuarios = fachada.clientesViajeros(fechaI,fechaF,tipoVuelo,order,tipoOrder, limite); 
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return Response.status(500).entity(temp).build();
		}
		return Response.status(200).entity(usuarios).build();
	}
	
	@GET 
	@Path("RFC10")
	@Produces({ MediaType.APPLICATION_JSON })
	public String RFC10( @QueryParam("fechaInicial") String fechaI,  @QueryParam("fechaFinal") String fechaF, @QueryParam("ciudad1") String ciudad1,@QueryParam("ciudad2") String ciudad2)
	{
		VuelAndesMaster fachada = VuelAndesMaster.darInstancia(getPath());
		ArrayList<Vuelo> v = new ArrayList<>();
		String resp = null;
		try
		{
			v = fachada.viajesCiudad(fechaI,fechaF,ciudad1,ciudad2); 
			resp="[";
			for (int i = 0; i < v.size(); i++) 
			{
				Vuelo este = v.get(i);
				resp+= este.toString();
				if(este.isTipo())
				{
					resp+= ",\"cantPasajeros\":"+((VueloPasajeros)este).getClientes().size();
				}
				else
				{
					ArrayList<Remitente> h = ((VueloCarga)este).getClientes();
					float ton = 0;
					for (int j = 0; j < h.size(); j++) 
					{
						ton+= h.get(i).getDensidadCarga();
					}
					resp+= ",\"cantCarga\":"+ton;
				}
				resp+="}";
			}
			resp+="]";
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			ArrayList<String> temp = new ArrayList<String>();
			temp.add(e.getMessage());
			return resp;
		}
		return resp;
	}
	
	
	@PUT 
	@Path("cargar")
	@Produces({ MediaType.APPLICATION_JSON })
	public void cargar(@QueryParam("aerolinea") String aerolinea)
	{
		VuelAndesMaster fachada =VuelAndesMaster.darInstancia(getPath());
		 fachada.cargar(null);
	}

}
