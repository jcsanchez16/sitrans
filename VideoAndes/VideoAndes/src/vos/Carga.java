package vos;

/**
 * Clase que describe la carga que se piensa enviar por los aviones de VuelAndes
 * @author anaca
 *
 */
public class Carga 
{
	//Identificador unico de la carga
	private int id;
	
	//Peso asociado a la carga
	private int peso;
	
	//Usuario asociado a esta carga
	private Cliente usuario;
	
	/**
	 * Constructor de una carga
	 * @param id
	 * @param peso
	 */
	public Carga(int id, int peso, Cliente usuario)
	{
		this.id = id;
		this.peso = peso;
		this.usuario = usuario;
	}
	
	//SET y GET
	
	/**
	 * Cambia el id de la carga y retorna el id actual de la carga
	 * @param id
	 * @return
	 */
	public int setId(int id)
	{
		this.id = id;
		return this.id;
	}
	
	/**
	 * Actualiza el peso de una carga y devuelve el id asociado a esta carga
	 * @param peso
	 * @return
	 */
	public int setPeso(int peso)
	{
		this.peso = peso;
		return id;
	}
	
	/**
	 * Actualiza el usuario de esta carga y devuelve el id asociado a la carga
	 * @param usuario
	 * @return
	 */
	public int setUsuario(Cliente usuario)
	{
		this.usuario = usuario;
		return id;
	}
	
	/**
	 * Devuelve el id asociado a la carga
	 * @return
	 */
	public int getID()
	{
		return this.id;
	}
	
	/**
	 * Devuelve el peso asocuado a la carga
	 * @return
	 */
	public int getPeso(){
		return this.peso;
	}
	
	/**
	 * Devuelve el usuario dueño y asociado a la carga
	 * @return
	 */
	public Cliente getUsuario(){
		return this.usuario;
	}
	
	/**
	 * Metodo main para asegurarnos que todo esta bien con la clase
	 * @param args
	 */
	public static void main(String[] args)
	{
		Carga carga = new Carga(3,26,null);
		System.out.println(carga.getID()+"----"+carga.getPeso());
	}
	
}
