package vos;

import java.util.ArrayList;
import java.util.Date;

public class VueloPasajeros extends Vuelo{

	private float precioEjecutivo;
	
	private float precioEconomico;

	private ArrayList<Pasajero> clientes;
	
	public VueloPasajeros(int codigo, int frecuencia, Date llegada,
			Date salida, int avion, String asalida, String allegada,
			String aero, float precioE, float precioEc,int f,int distancias, String duracion, ArrayList client) 
	{
		super(codigo, frecuencia, llegada, salida, avion, asalida, allegada, aero,f,distancias, duracion,0);
		precioEconomico = precioEc;
		precioEjecutivo = precioE;
		clientes = client==null?new ArrayList<Remitente>():client;
	}
	
	public void agregarCliente (Pasajero c)
	{
		clientes.add(c);
	}

	public float getPrecioEjecutivo() {
		return precioEjecutivo;
	}

	public void setPrecioEjecutivo(float precioEjecutivo) {
		this.precioEjecutivo = precioEjecutivo;
	}

	public void setClientes(ArrayList<Pasajero> clientes) {
		this.clientes = clientes;
	}

	public float getPrecioEconomico() {
		return precioEconomico;
	}

	public void setPrecioEconomico(float precioEconomico) {
		this.precioEconomico = precioEconomico;
	}

	public ArrayList<Pasajero> getClientes() {
		return clientes;
	}

}
