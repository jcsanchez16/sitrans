package vos;

import java.util.ArrayList;

/**
 * Clase asociada a los cliente que tiene VuelAndes
 * @author anaca
 *
 */
public class Cliente
{
	//Identificador del cliente
	private int identificacion;
	
	//String que modela el nombre del cliente
	private String nombre;
	
	//Direccion del cliente
	private String nacionalidad;
	
	//Telefono del cliente
	private String correo;
	
	private String tipoIdentificacion;
	

	private  ArrayList<String> vuelos;
	
	private boolean tipo;
	
	public boolean isTipo() {
		return tipo;
	}

	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}

	public void setVuelos(ArrayList<String> vuelos) {
		this.vuelos = vuelos;
	}

	/**
	 * Clase constructora del cliente
	 * @param id
	 * @param nombre
	 * @param direccion
	 * @param telefono
	 */
	public Cliente(int id, String nombre, String nacionalidad, String correo, String tipoIdentificacion, ArrayList<String> vuel, int tipo)
	{
		this.identificacion = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.correo = correo;
		this.tipoIdentificacion=tipoIdentificacion;
		this.vuelos = vuel;
		this.tipo = tipo == 0? true:false;
	}
	
	public void agregarVuelo(String v)
	{
		vuelos.add(v);
	}
	public ArrayList<String> getVuelos() {
		return vuelos;
	}
	
	public int getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}
	
}