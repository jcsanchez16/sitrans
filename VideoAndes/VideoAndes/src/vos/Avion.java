package vos;

import sun.applet.Main;

/**
 * Clase que modela los aviones que maneja VuelAndes
 * @author anaca
 *
 */
public class Avion
{
	//Identificador del avion
	private int id;
	
	//Booleano que indica si es o no un avion de carga
	private boolean carga;
	
	//Int que modela la capacidad sea de un avion de carga(capacidad de peso) o sea de un avion normal(capacidad de pasajeros)
	private int capacidad;
	
	/**
	 * Metodo constructor del avion
	 * @param id
	 * @param carga
	 * @param capacidad
	 */
	public Avion(int id,boolean carga, int capacidad){
		this.id = id;
		this.carga = carga;
		this.capacidad = capacidad;
	}
	
	//SET y GET
	
	/**
	 * Cambia o actualiza el id del avion actual
	 * @param id
	 * @return
	 */
	public int setID(int id){
		this.id = id;
		return this.id;
	}
	
	/**
	 * Actualiza el tipo de avion que es este de carga o no.
	 * Retorna el id del avion
	 * @param carga
	 * @return
	 */
	public int setCarga(boolean carga){
		this.carga = carga;
		return this.id;
	}
	
	/**
	 * Actualiza la capacidad de un avion
	 * @param capacidad
	 * @return
	 */
	public int setCapacidad(int capacidad)
	{
		this.capacidad = capacidad;
		return this.id;
	}
	
	/**
	 * Gives the id of this Avion
	 * @return
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Gives the boolean that tells if this avion is of carga or not
	 * @return
	 */
	public boolean getCarga(){
		return this.carga;
	}
	
	/**
	 * Gives the capacity of this Avion
	 * @return
	 */
	public int getCapacidad(){
		return this.capacidad;
	}
	
	/**
	 * Main method for testing purposes
	 * @param args
	 */
	public static void main(String[] args) {
		
		Avion avion = new Avion(8, true, 80);
		System.out.println(avion.getCapacidad()+"----"+avion.getId()+"----"+avion.getCarga());
	}
}
