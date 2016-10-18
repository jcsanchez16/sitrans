package vos;

import java.util.ArrayList;


public class Pasajero extends Cliente

{
	
	public final static boolean ECONOMICO = true;
	public final static boolean EJECUTIVO = false;
	private boolean economica;
	
	private  ArrayList<String> vuelosP;
	public Pasajero(int id, String nombre, String nacionalidad, String correo, String tipoIdentificacion, int tipo,ArrayList<String> vuelos) 
	{
		super(id, nombre, nacionalidad, correo, tipoIdentificacion);
		this.economica =tipo==0? true:false;
		vuelosP =vuelos==null? new ArrayList<String>():vuelos;
	}
	
	public void agregarVuelo(String v)
	{
		vuelosP.add(v);
	}

	public boolean isEconomica() {
		return economica;
	}

	public void setEconomica(boolean economica) {
		this.economica = economica;
	}

	public ArrayList<String> getVuelosC() {
		return vuelosP;
	}

	
	
}
