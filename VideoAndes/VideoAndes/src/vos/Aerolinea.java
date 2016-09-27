package vos;

import sun.applet.Main;

/**
 * Clase que crea las aerolineas asociadas a los aeropuertos de vuelandes
 * @author anaca
 *
 */
public class Aerolinea
{
	//Identificador de la aerolinea
	private int id;
	
	//Nombre de a aerolinea
	private String nombre;
	
	/**
	 * Clase constructora de la aerolinea
	 * @param id
	 * @param nombre
	 */
	public Aerolinea(int id, String nombre)
	{
		this.nombre = nombre;
		this.id = id;
	}
	
	//GET y SET
	
	/**
	 * Gives the id of this Aerolinea
	 * @return
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Returns the name of this Aerolinea
	 * @return
	 */
	public String getNombre()
	{
		return this.nombre;
	}
	/**
	 * Changes the id of a Aerolinea
	 * @param id
	 * @return
	 */
	public int setId(int id)
	{
		this.id = id;
		return this.id;
	}
	
	/**
	 * Updates the name of an Aerolinea
	 * @param nombre
	 * @return
	 */
	public int setNombre(String nombre)
	{
		this.nombre = nombre;
		return this.id;
	}
	
	/**
	 * Main method for test purposes
	 * @param args
	 */
	public static void main(String[] args)
	{
		Aerolinea aero = new Aerolinea(7, "Panamerican");
		System.out.println(aero.getId()+"----"+aero.getNombre());
	}
	
}
