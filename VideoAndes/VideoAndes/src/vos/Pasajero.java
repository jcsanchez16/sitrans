package vos;

import java.util.ArrayList;


public class Pasajero extends Cliente

{
	
	public final static boolean ECONOMICO = true;
	public final static boolean EJECUTIVO = false;
	private boolean economica;
	
	private  ArrayList<VueloPasajeros> vuelosP;
	
	public Pasajero(int id, String nombre, String nacionalidad, String correo, String tipoIdentificacion, int tipo) 
	{
		super(id, nombre, nacionalidad, correo, tipoIdentificacion);
		this.economica =tipo==0? true:false;
		vuelosP = new ArrayList<>();
	}
	
	public void agregarVuelo(VueloPasajeros v)
	{
		vuelosP.add(v);
	}

	public boolean isEconomica() {
		return economica;
	}

	public void setEconomica(boolean economica) {
		this.economica = economica;
	}

	public ArrayList<VueloPasajeros> getVuelosC() {
		return vuelosP;
	}

	
	
}
