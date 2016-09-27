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
	private int id;
	
	//String que modela el nombre del cliente
	private String nombre;
	
	//Direccion del cliente
	private String direccion;
	
	//Telefono del cliente
	private String telefono;
	
	//Cargas que tiene el cliente. Pueden ser varias asi que van en lista
	private ArrayList<Carga> cargas;
	
	/**
	 * Clase constructora del cliente
	 * @param id
	 * @param nombre
	 * @param direccion
	 * @param telefono
	 */
	public Cliente(int id, String nombre, String direccion, String telefono)
	{
		this.id = id;
		this.nombre = nombre;
		this.direccion = direccion;
		this.telefono = telefono;
		cargas = new ArrayList<Carga>();
	}
	
	/**
	 * Metodo que se encarga de adicionar cargas al cliente y retorna el id asociado a este cliente
	 * @param carga
	 * @return id del cliente
	 */
	public int agregarCarga(Carga carga)
	{
		cargas.add(carga);
		return id;
	}
	
	
	//SET and GET
	
	/**
	 * Metodo que actualiza el id de un cliente y retorna el id asociado
	 * @param id
	 * @return id del cliente
	 */
	public int setId(int id)
	{
		this.id = id;
		return this.id;
	}
	
	/**
	 * Metodo que actualiza el nombre de un cliente y retorna su id asociado
	 * @param nombre
	 * @return id del cliente
	 */
	public int setNombre(String nombre)
	{
		this.nombre = nombre;
		return this.id;
	}
	
	/**
	 * Metodo que actualiza la direccion de un cliente y retorna su id asociado
	 * @param direccion
	 * @return id del cliente
	 */
	public int setDireccion(String direccion)
	{
		this.direccion = direccion;
		return this.id;
	}
	
	/**
	 * Metodo que actualiza el telefono de un cliente y retorna el id asociado a este
	 * @param telefono
	 * @return id del cliente
	 */
	public int setTelefono(String telefono)
	{
		this.telefono = telefono;
		return this.id;
	}
	
	//GET
	
	/**
	 * Retorna el id del clietne actual
	 * @return
	 */
	public int getId()
	{
		return this.id;
	}
	
	/**
	 * Retorna el nombre dle cliente actual
	 * @return
	 */
	public String getNombre()
	{
		return this.nombre;
	}
	
	/**
	 * Metodo que retorna la direccion del cliente
	 * @return
	 */
	public String getDireccion()
	{
		return this.direccion;
	}
	
	/**
	 * Metodo que retorna el telefono del cliente
	 * @return
	 */
	public String getTelefono()
	{
		return this.telefono;
	}
}
