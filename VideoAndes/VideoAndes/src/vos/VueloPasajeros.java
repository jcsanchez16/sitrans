package vos;

import java.util.ArrayList;
import java.util.Date;

public class VueloPasajeros extends Vuelo{

	private float precioEjecutivo;
	
	private float precioEconomico;

	private ArrayList<Cliente> clientes;
	
	public VueloPasajeros(String codigo, int frecuencia, Date llegada,
			Date salida, Avion avion, Aeropuerto asalida, Aeropuerto allegada,
			Aerolinea aero, float precioE, float precioEc) 
	{
		super(codigo, frecuencia, llegada, salida, avion, asalida, allegada, aero);
		precioEconomico = precioEc;
		precioEjecutivo = precioE;
		clientes = new ArrayList<Cliente>();
	}
	
	public void agregarCliente (Cliente c)
	{
		clientes.add(c);
	}

	public float getPrecioEjecutivo() {
		return precioEjecutivo;
	}

	public void setPrecioEjecutivo(float precioEjecutivo) {
		this.precioEjecutivo = precioEjecutivo;
	}

	public float getPrecioEconomico() {
		return precioEconomico;
	}

	public void setPrecioEconomico(float precioEconomico) {
		this.precioEconomico = precioEconomico;
	}

	public ArrayList<Cliente> getClientes() {
		return clientes;
	}

}
