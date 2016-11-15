package vos;

import java.util.ArrayList;


public class Pasajero extends Cliente

{
	
	public final static boolean ECONOMICO = true;
	public final static boolean EJECUTIVO = false;
	private boolean economica;
	
	public Pasajero(int id, String nombre, String nacionalidad, String correo, String tipoIdentificacion, int tipo,ArrayList<String> vuelos,int tip,int millas,double tiempo, ArrayList<Vuelo> mostrar) 
	{
		super(id, nombre, nacionalidad, correo, tipoIdentificacion,vuelos,tip,millas,tiempo, mostrar);
		this.economica =tipo==0? true:false;
	}
	

	public boolean isEconomica() {
		return economica;
	}

	public void setEconomica(boolean economica) {
		this.economica = economica;
	}

	

	
	
}
