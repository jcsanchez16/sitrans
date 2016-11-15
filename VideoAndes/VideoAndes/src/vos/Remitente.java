package vos;

import java.util.ArrayList;


public class Remitente extends Cliente

{
	private float densidadCarga;
	
	
	public Remitente(int id, String nombre, String nacionalidad, String correo, String tipoIdentificacion, float densidadCarga , ArrayList<String> vuelos,int tipo,int millas,double tiempo, ArrayList<Vuelo> mostrar) 
	{
		super(id, nombre, nacionalidad, correo, tipoIdentificacion,vuelos,tipo, millas, tiempo, mostrar);
		this.densidadCarga =densidadCarga;
	}
	
	
	
	public float getDensidadCarga() {
		return densidadCarga;
	}

	
	public void setDensidadCarga(float densidadCarga) {
		this.densidadCarga = densidadCarga;
	}
	
}
