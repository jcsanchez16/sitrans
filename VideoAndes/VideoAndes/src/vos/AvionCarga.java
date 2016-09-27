package vos;

public class AvionCarga extends Avion
{
	private float capacidadDensidad;
	public AvionCarga(int nSerie, int modelo, int anhoFabricacion, String marca, float capacidad, Aerolinea aerolinea) 
	{
		super(nSerie, modelo, anhoFabricacion, marca, aerolinea);
		capacidadDensidad = capacidad;
	}
	public float getCapacidadDensidad() {
		return capacidadDensidad;
	}
	public void setCapacidadDensidad(float capacidadDensidad) {
		this.capacidadDensidad = capacidadDensidad;
	}

}
