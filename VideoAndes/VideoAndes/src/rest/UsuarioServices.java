package rest;

import java.sql.Date;
import java.text.SimpleDateFormat;
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
	public java.util.Date Get() 
	{
		VuelAndesMaster master = VuelAndesMaster.darInstancia(getPath());
		java.util.Date g = new java.util.Date();
		return g;
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


}
