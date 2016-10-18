package vos;

import java.util.ArrayList;


public class Remitente extends Cliente

{
	private float densidadCarga;
	
	private  ArrayList<String> vuelosC;
	
	public Remitente(int id, String nombre, String nacionalidad, String correo, String tipoIdentificacion, float densidadCarga , ArrayList<String> vuelos) 
	{
		super(id, nombre, nacionalidad, correo, tipoIdentificacion);
		this.densidadCarga =densidadCarga;
		vuelosC =vuelos==null? new ArrayList<String>():vuelos;
	}
	
	public void agregarVuelo(String v)
	{
		vuelosC.add(v);
	}
	
	public float getDensidadCarga() {
		return densidadCarga;
	}

	public ArrayList<String> getVuelosC() {
		return vuelosC;
	}

	public void setDensidadCarga(float densidadCarga) {
		this.densidadCarga = densidadCarga;
	}
	
}
