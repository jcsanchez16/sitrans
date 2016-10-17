package vos;

import java.util.ArrayList;

import sun.applet.Main;

/**
 * Clase que modela los aviones que maneja VuelAndes
 * @author anaca
 *
 */
public class Avion
{
	public final static boolean PASAJEROS = true;
	public final static boolean CARGA = false;
	//Identificador del avion
	private int nSerie;
	
	//Booleano que indica si es o no un avion de carga
	private int modelo;
	
	//Int que modela la capacidad sea de un avion de carga(capacidad de peso) o sea de un avion normal(capacidad de pasajeros)
	private int anhoFabricacion;
	
	private String marca;
	
	private String aerolinea;
	
	private ArrayList<Vuelo> vuelos;
	
	private boolean tipo;
	
	/**
	 * Metodo constructor del avion
	 * @param id
	 * @param carga
	 * @param capacidad
	 */
	public Avion(int nSerie, int modelo, int anhoFabricacion, String marca, String aero, int tipo){
		this.anhoFabricacion = anhoFabricacion;
		this.marca = marca;
		this.nSerie = nSerie;
		this.modelo = modelo;
		this.aerolinea = aero;
		vuelos= new ArrayList<Vuelo>();
		this.tipo =tipo==0? true:false;
	}
	
	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public void agregarVuelo(Vuelo v)
	{
		vuelos.add(v);
	}
	
	public String getAerolinea() {
		return aerolinea;
	}



	public void setAerolinea(String aerolinea) {
		this.aerolinea = aerolinea;
	}



	public ArrayList<Vuelo> getVuelos() {
		return vuelos;
	}



	public int getnSerie() {
		return nSerie;
	}



	public void setnSerie(int nSerie) {
		this.nSerie = nSerie;
	}



	public int getModelo() {
		return modelo;
	}



	public void setModelo(int modelo) {
		this.modelo = modelo;
	}



	public int getAnhoFabricacion() {
		return anhoFabricacion;
	}



	public void setAnhoFabricacion(int anhoFabricacion) {
		this.anhoFabricacion = anhoFabricacion;
	}



	public String getMarca() {
		return marca;
	}



	public void setMarca(String marca) {
		this.marca = marca;
	}

//	public boolean buscarVueloFechaEspecifica(Date fecha){
//		
//	}


}
