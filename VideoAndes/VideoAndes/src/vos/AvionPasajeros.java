package vos;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

public class AvionPasajeros extends Avion
{
	private int asientosEconomica;
	
	private int asientosEjecutivo;
	
	public AvionPasajeros(int nSerie, int modelo, int anhoFabricacion, String marca, int asientosEconomica,int asientosEjecutivo, Aerolinea aerolinea) 
	{
		super(nSerie, modelo, anhoFabricacion, marca, aerolinea);
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
