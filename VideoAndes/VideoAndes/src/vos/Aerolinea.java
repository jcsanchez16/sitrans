package vos;

import java.util.ArrayList;

/**
 * Clase que crea las aerolineas asociadas a los aeropuertos de vuelandes
 * @author anaca
 *
 */
public class Aerolinea
{
	//Identificador de la aerolinea
	private String paizRadicacion;
	
	//Nombre de a aerolinea
	private String nombre;
	
	private String OACI;
	
	private String codigo;
	
	private ArrayList<Vuelo> vuelos;
	
	private ArrayList<Avion> aviones;
	
	/**
	 * Clase constructora de la aerolinea
	 * @param id
	 * @param nombre
	 */	
	public Aerolinea(String paizRadicacion, String nombre, String OACI, String codigo, ArrayList<Avion> avi,ArrayList<Vuelo> vuel) 
	{
		this.nombre = nombre;
		this.paizRadicacion = paizRadicacion;
		this.OACI = OACI;		
		this.codigo = codigo;
		vuelos = vuel==null? new ArrayList<Vuelo>():vuel;
		aviones = avi==null? new ArrayList<Avion>():avi;
	}


	public void agregarVuelo(Vuelo v)
	{
		vuelos.add(v);
	}
	
	public void agregarAvion(Avion a)
	{
		aviones.add(a);
	}


	public ArrayList<Vuelo> getVuelos() {
		return vuelos;
	}





	public ArrayList<Avion> getAviones() {
		return aviones;
	}





	public String getPaizRadicacion() {
		return paizRadicacion;
	}





	public void setPaizRadicacion(String paizRadicacion) {
		this.paizRadicacion = paizRadicacion;
	}





	public String getNombre() {
		return nombre;
	}





	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public String getOACI() {
		return OACI;
	}





	public void setOACI(String oACI) {
		OACI = oACI;
	}





	public String getCodigo() {
		return codigo;
	}





	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	

	public static void main(String[] args)
	{
		Aerolinea aero = new Aerolinea("Colombia", "Panamerican", "BOG", "PA",null,null);
		System.out.println(aero.getOACI()+"----"+aero.getNombre()+"----"+aero.getPaizRadicacion());
	}
	
}
