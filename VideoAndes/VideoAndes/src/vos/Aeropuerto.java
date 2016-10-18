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
	private String IATA;
	
	//Nombre del aeropuerto
	private String nombre;
	
	//Impuestos asociados al aeropuerto
	private String ciudad;
	
	//Lista de vuelos de salida
	private ArrayList<Vuelo> vuelosSalida;
	
	//Lista de vuelos de llegada
	private ArrayList<Vuelo> vuelosLlegada;
	
	
	/**
	 * Metodo constructor de la clase Aeropuerto
	 * @param id
	 * @param nombre
	 * @param impuestos
	 */
	
	
	public Aeropuerto(String ciudad, String nombre, String IATA,ArrayList<Vuelo> vuele, ArrayList<Vuelo> vuels) {
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.IATA = IATA;
		vuelosSalida = vuels==null? new ArrayList<Vuelo>():vuels;
		vuelosLlegada = vuele==null? new ArrayList<Vuelo>():vuele;
	}


	public void agregarVueloSalida(Vuelo v)
	{
		vuelosSalida.add(v);
	}
	
	public void agregarVueloLLegada(Vuelo v)
	{
		vuelosLlegada.add(v);
	}
	
	public String getIATA() {
		return IATA;
	}



	public void setIATA(String iATA) {
		IATA = iATA;
	}



	public String getNombre() {
		return nombre;
	}



	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	public String getCiudad() {
		return ciudad;
	}



	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}



	public ArrayList<Vuelo> getVuelosSalida() {
		return vuelosSalida;
	}



	



	public ArrayList<Vuelo> getVuelosLlegada() {
		return vuelosLlegada;
	}



	



	/**
	 * Main created for testing purposes
	 * @param args
	 */
	public static void main(String[] args) {
		Aeropuerto aeropuerto = new Aeropuerto("bogota", "el dorado", "BOG",null,null);
		System.out.println(aeropuerto.getIATA()+"------"+aeropuerto.getCiudad());
		System.out.println(aeropuerto.getNombre());
	}
}
