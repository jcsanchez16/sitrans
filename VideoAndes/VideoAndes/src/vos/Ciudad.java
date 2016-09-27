package vos;

import java.util.ArrayList;

/**
 * Clase Ciudad que tiene las ciudades en donde estan los aeropuertos para relacionarla con los vuelos de vuelAndes
 * @author anaca
 *
 */
public class Ciudad {
	 
	//Identificador de la ciudad
	private int id;
	
	//Nombre de la Ciudad
	private String nombre;
	
	//Latitud de ubicación de la Ciudad
	private int latitud;
	
	//Su longitud
	private int longitud;
	
	//Lista de aeropuertos asociados
	private ArrayList<Aeropuerto> aeropuertos;
	
	/**
	 * Método constructor de la Ciudad
	 * @param id
	 * @param nombre
	 * @param lat
	 * @param longi
	 */
	public Ciudad(int id, String nombre, int lat, int longi)
	{
		this.id = id;
		this.nombre = nombre;
		this.latitud = lat;
		this.longitud = longi;
		aeropuertos = new ArrayList<Aeropuerto>();
	}
	
	/**
	 * Agrega un aeropuerto a la ciudad y devuelve el id de la ciudad donde se hicieron estos cambios
	 * @param aeropuerto
	 * @return id ciudad
	 */
	public int agregarAeropuerto(Aeropuerto aeropuerto)
	{
		aeropuertos.add(aeropuerto);
		return id;
	}
	
	//Ahora comenzamos un get y set de los datos
	
	//GET
	
	/**
	 * Método get de la ciudad para retornar el id de la ciudad
	 * @return id de la ciudad
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Devuelve el nombre de esta ciudad
	 * @return nombre de ciudad
	 */
	public String getNombre()
	{
		return this.nombre;
	}
	
	/**
	 * Retorna la latitud en la cual esta ubicada esta ciudad
	 * @return int latitud
	 */
	public int getLatitud()
	{
		return this.latitud;
	}
	
	/**
	 * Retorna la longitud de la ubicacion de esta ciudad
	 * @return int longitud
	 */
	public int getLongitud()
	{
		return this.longitud;
	}
	
	//SET
	
	/**
	 * Cambia el id de una ciudad por otro determinado y devuelve el id actual
	 * @param id a poner
	 * @return id de la ciudad
	 */
	public int setId(int id)
	{
		this.id = id;
		return id;
	}
	
	/**
	 * Cambia el nombre de una ciudad y devuelve el id asociado a esta.
	 * @param nombre
	 * @return id de la ciudad
	 */
	public int setNombre(String nombre)
	{
		this.nombre = nombre;
		return id;
	}
	
	/**
	 * Cambia la latitud de una ciudad y devuelve el id asociado a esta ciudad
	 * @param latitud
	 * @return id de ciudad
	 */
	public int setLatitud(int latitud)
	{
		this.latitud = latitud;
		return id;
	}
	
	/**
	 * Cambia la longitud de una ciudad y devulve el id asociado a esta ciudad
	 * @param longitud
	 * @return id de ciudad
	 */
	public int setLongitud(int longitud)
	{
		this.longitud = longitud;
		return longitud;
	}
	
	/**
	 * Metodo main para asegurarnos que todo este bien con esta clase
	 * @param args
	 */
	public static void main(String[] args) {
		Ciudad ciudad = new Ciudad(7, "Paris", 15, 98);
		System.out.println(ciudad.getId()+"----"+ciudad.getNombre()+"----"+ciudad.getLatitud()+"----"+ciudad.getLongitud());
	}
	
}
