package vos;

import java.util.ArrayList;


public class Remitente extends Cliente

{
	private float densidadCarga;
	
	private  ArrayList<VueloCarga> vuelosC;
	
	public Remitente(int id, String nombre, String nacionalidad, String correo, String tipoIdentificacion, float densidadCarga) 
	{
		super(id, nombre, nacionalidad, correo, tipoIdentificacion);
		this.densidadCarga =densidadCarga;
		vuelosC = new ArrayList<>();
	}
	
	public void agregarVuelo(VueloCarga v)
	{
		vuelosC.add(v);
	}
	
	public float getDensidadCarga() {
		return densidadCarga;
	}

	public ArrayList<VueloCarga> getVuelosC() {
		return vuelosC;
	}

	public void setDensidadCarga(float densidadCarga) {
		this.densidadCarga = densidadCarga;
	}
	
}
