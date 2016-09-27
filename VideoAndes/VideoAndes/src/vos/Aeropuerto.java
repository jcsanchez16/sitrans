package vos;

import java.util.ArrayList;

/**
 * Clase que modela un aeropuerto de VuelAndes
 * @author anaca
 *
 */
public class Aeropuerto
{
	//Identificador asociado al aeropuerto
	private int id;
	
	//Nombre del aeropuerto
	private String nombre;
	
	//Impuestos asociados al aeropuerto
	private int impuestos;
	
	//Lista de vuelos de salida
	private ArrayList<Vuelo> vuelosSalida;
	
	//Lista de vuelos de llegada
	private ArrayList<Vuelo> vuelosLlegada;
	
	//Ciudad donde se encuentra ubicado
	private Ciudad ciudad;
	
	
	/**
	 * Metodo constructor de la clase Aeropuerto
	 * @param id
	 * @param nombre
	 * @param impuestos
	 */
	public Aeropuerto(int id, String nombre, int impuestos, Ciudad ciudad)
	{
		this.id = id;
		this.nombre = nombre;
		this.impuestos = impuestos;
		vuelosSalida = new ArrayList<Vuelo>();
		vuelosLlegada = new ArrayList<Vuelo>();
		this.ciudad = ciudad;
	}
	
	//SET y GET
	
	/**
	 * Updates the id of a Aeropuerto
	 * @param id
	 * @return
	 */
	public int setId(int id)
	{
		this.id = id;
		return this.id;
	}
	
	/**
	 * Updates the name of an Aeropuerto
	 * @param nombre
	 * @return
	 */
	public int setNombre(String nombre)
	{
		this.nombre = nombre;
		return this.id;
	}
	
	/**
	 * Sets the impuestos of this Aeropuerto
	 * @param impuesto
	 * @return
	 */
	public int setImpuestos(int impuesto)
	{
		this.impuestos = impuesto;
		return this.id;
	}
	
	/**
	 * sets the vuelos of departure of this Aeropuerto
	 * @param vuelosSalida
	 * @return
	 */
	public int setVuelosSalida(ArrayList<Vuelo> vuelosSalida)
	{
		this.vuelosSalida = vuelosSalida;
		return this.id;
	}
	
	/**
	 * Updates the info and adds a Vuelo to the vuelosSalida list
	 * @param vuelosSalida
	 * @return
	 */
	public int addVueloSalida(Vuelo vuelosSalida)
	{
		this.vuelosSalida.add(vuelosSalida);
		return this.id;
	}
	
	/**
	 * Updates information related with the list of arriving Vuelos
	 * @param vuelosLlegada
	 * @return id associated with this Aeropuerto
	 */
	public int setVuelosLlegada(ArrayList<Vuelo> vuelosLlegada)
	{
		this.vuelosLlegada = vuelosLlegada;
		return this.id;
	}
	
	/**
	 * Updates info about the arriving flights
	 * @param vuelosLlegada
	 * @return id associated with this Aeropuerto
	 */
	public int addVueloLlegada(Vuelo vuelosLlegada)
	{
		this.vuelosLlegada.add(vuelosLlegada);
		return this.id;
	}
	
	//GET
	
	/**
	 * Returns the id of this Aeropuerto
	 * @return
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Gets the name of this Aeropuerto
	 * @return
	 */
	public String getNombre()
	{
		return this.nombre;
	}
	
	/**
	 * Returns the fee of this Aeropuerto
	 * @return
	 */
	public int getImpuesto()
	{
		return this.impuestos;
	}
	
	/**
	 * Returns the vuelosSalida
	 * @return
	 */
	public ArrayList<Vuelo> getVuelosSalida()
	{
		return this.vuelosSalida;
	}
	
	/**
	 * Gives the list of vuelosLlegada
	 * @return
	 */
	public ArrayList<Vuelo> getVuelosLlegada()
	{
		return this.vuelosLlegada;
	}
	
	/**
	 * Returns the city where it is located.
	 * @return
	 */
	public Ciudad getCiudad()
	{
		return ciudad;
	}
	
	/**
	 * Main created for testing purposes
	 * @param args
	 */
	public static void main(String[] args) {
		Aeropuerto aeropuerto = new Aeropuerto(6, "AeroPostale", 56, null);
		System.out.println(aeropuerto.getId()+"------"+aeropuerto.getImpuesto());
		System.out.println(aeropuerto.getNombre());
	}
}
