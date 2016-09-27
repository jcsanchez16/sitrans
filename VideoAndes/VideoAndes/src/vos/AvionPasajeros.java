package vos;

import com.sun.org.apache.bcel.internal.generic.CPInstruction;

public class AvionPasajeros extends Avion
{
	private float asientosEconomica;
	
	private float asientosEjecutivo;
	
	public AvionPasajeros(int nSerie, int modelo, int anhoFabricacion, String marca, float capacidad, Aerolinea aerolinea) 
	{
		super(nSerie, modelo, anhoFabricacion, marca, aerolinea);
		this.asientosEconomica = asientosEconomica;
		this.asientosEjecutivo = asientosEjecutivo;
	}

	public float getAsientosEconomica() {
		return asientosEconomica;
	}

	public void setAsientosEconomica(float asientosEconomica) {
		this.asientosEconomica = asientosEconomica;
	}

	public float getAsientosEjecutivo() {
		return asientosEjecutivo;
	}

	public void setAsientosEjecutivo(float asientosEjecutivo) {
		this.asientosEjecutivo = asientosEjecutivo;
	}
	
}
