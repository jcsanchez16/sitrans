package vos;

import java.util.ArrayList;
import java.util.Date;

public class VueloCarga extends Vuelo{

	private float precioPorDensidad;

	private ArrayList<Remitente> clientes;
	
	public VueloCarga(int codigo, int frecuencia, Date llegada,
			Date salida, int avion, String asalida, String allegada,
			String aero, float precio,int f,int distancias, String duracion) 
	{
		super(codigo, frecuencia, llegada, salida, avion, asalida, allegada, aero, f,distancias,duracion,1);
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
