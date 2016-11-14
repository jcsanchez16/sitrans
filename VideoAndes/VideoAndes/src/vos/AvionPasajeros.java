package vos;

import java.util.ArrayList;

public class AvionPasajeros extends Avion
{
	private int asientosEconomica;
	
	private int asientosEjecutivo;
	
	public AvionPasajeros(int nSerie, int modelo, int anhoFabricacion, String marca, int asientosEconomica,int asientosEjecutivo, String aerolinea,ArrayList<Vuelo> vuel) 
	{
		super(nSerie, modelo, anhoFabricacion, marca, aerolinea,0, vuel);
		this.asientosEconomica = asientosEconomica;
		this.asientosEjecutivo = asientosEjecutivo;
	}

	public int getAsientosEconomica() {
		return asientosEconomica;
	}

	public void setAsientosEconomica(int asientosEconomica) {
		this.asientosEconomica = asientosEconomica;
	}

	public int getAsientosEjecutivo() {
		return asientosEjecutivo;
	}

	public void setAsientosEjecutivo(int asientosEjecutivo) {
		this.asientosEjecutivo = asientosEjecutivo;
	}
	
}
