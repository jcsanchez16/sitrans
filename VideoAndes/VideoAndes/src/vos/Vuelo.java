package vos;

import java.util.ArrayList;
import java.util.Date;

import sun.applet.Main;

/**
 * Clase que modela los vuelos que tienen los aviones
 * @author anaca
 *
 */
public class Vuelo
{
	//Identificador del vuelo
	private int id;
	
	//Costo del vuelo
	private int costo;
	
	//Fecha de salida del vuelo
	private Date fechaSalida;
	
	//Fecha de llegada de un vuelo
	private Date fechaLlegada;
	
	//Aeropuerto de salida
	private Aeropuerto salida;
	
	//Aeropuerto de llegada
	private Aeropuerto llegada;
	
	//Avion asociado al vuelo
	private Avion avion;
	
	//Clientes asociados a cierto vuelo
	private ArrayList<Cliente> clientes;
	
	/**
	 * Metodo constructor de un vuelo
	 * @param id
	 * @param costo
	 * @param llegada
	 * @param salida
	 * @param avion
	 * @param asalida
	 * @param allegada
	 */
	public Vuelo(int id, int costo, Date llegada, Date salida, Avion avion, Aeropuerto asalida, Aeropuerto allegada){
		this.id = id;
		this.costo = costo;
		this.fechaLlegada = llegada;
		this.fechaSalida = salida;
		this.avion = avion;
		this.salida = asalida;
		this.llegada = allegada;
		clientes = new ArrayList<Cliente>();
	}
	
	//SET y GET
	
	/**
	 * Returns the id associated with this Vuelo
	 * @param id
	 * @return id of this Vuelo
	 */
	public int setId(int id){
		this.id = id;
		return this.id;
	}
	
	/**
	 * Changes the cost of this Vuelo and returns the is associated with it
	 * @param costo
	 * @return id of this Vuelo
	 */
	public int setCosto(int costo){
		this.costo = costo;
		return this.id;
	}
	
	/**
	 * Updates the info bout the arriving date and returns id associated with the Vuelo
	 * @param fecha
	 * @return id of this Vuelo
	 */
	public int setFechaLlegada(Date fecha){
		this.fechaLlegada = fecha;
		return this.id;
	}
	
	/**
	 * Updates the info bout the date of departure and returns id associated with the Vuelo
	 * @param fecha
	 * @return id of this Vuelo
	 */
	public int setFechaSalida(Date fecha){
		this.fechaSalida = fecha;
		return this.id;
	}
	
	/**
	 * Updates the info about the Avion
	 * Returns the id of this Vuelo
	 * @param avion
	 * @return id of this Vuelo
	 */
	public int setAvion(Avion avion){
		this.avion = avion;
		return this.id;
	}
	/**
	 * Updates the info about the departure Aeropuerto
	 * @param salida
	 * @return id of this Vuelo
	 */
	public int setAeropuertoSalida(Aeropuerto salida){
		this.salida = salida;
		return this.id;
	}
	
	/**
	 * Updates info about the arriving Aeropuerto
	 * @param llegada
	 * @return id of this Vuelo
	 */
	public int setAeropuertoLlegada(Aeropuerto llegada){
		this.llegada = llegada;
		return this.id;
	}
	/**
	 * Adds a Cliente to the list of clientes of this Vuelo
	 * @param cliente
	 * @return id of this Vuelo
	 */
	public int addCliente(Cliente cliente){
		clientes.add(cliente);
		return this.id;
	}
	
	//GET
	
	/**
	 * Returns the id of this Vuelo
	 * @return
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Gives the cost of this Vuelo
	 * @return
	 */
	public int getCosto(){
		return this.costo;
	}
	
	/**
	 * Gives the date of departure
	 * @return
	 */
	public Date getFechaSalida(){
		return this.fechaSalida;
	}
	
	/**
	 * Gives the date of arriving
	 * @return
	 */
	public Date getFechaLlegada(){
		return this.fechaLlegada;
	}
	
	/**
	 * Gves the Avion of this Vuelo
	 * @return
	 */
	public Avion getAvion(){
		return this.avion;
	}
	/**
	 * Gives the Clientes of this vuelo
	 * @return
	 */
	public ArrayList<Cliente> getClientes(){
		return this.clientes;
	}
	
	/**
	 * Main for testing purposes
	 * @param args
	 */
	public static void main(String[] args) {
		Vuelo vuelo = new Vuelo(8, 7, null, null, null, null, null);
		System.out.println(vuelo.getCosto()+"-----"+vuelo.getId());
	}
}
