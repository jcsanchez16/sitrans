package vos;

import java.util.ArrayList;
import java.util.Date;

public class VueloCarga extends Vuelo{

	private float precioPorDensidad;

	private ArrayList<Remitente> clientes;
	
	public VueloCarga(String codigo, int frecuencia, Date llegada,
			Date salida, Avion avion, Aeropuerto asalida, Aeropuerto allegada,
			Aerolinea aero, float precio) 
	{
		super(codigo, frecuencia, llegada, salida, avion, asalida, allegada, aero);
		precioPorDensidad = precio;
		clientes = new ArrayList<Remitente>();
	}
	
	public void agregarCliente (Remitente c)
	{
		clientes.add(c);
	}

	

	public float getPrecioPorDensidad() {
		return precioPorDensidad;
	}

	public void setPrecioPorDensidad(float precioPorDensidad) {
		this.precioPorDensidad = precioPorDensidad;
	}

	public ArrayList<Remitente> getClientes() {
		return clientes;
	}

}
