package vos;

public class AvionCarga extends Avion
{
	private float capacidadDensidad;
	public AvionCarga(int nSerie, int modelo, int anhoFabricacion, String marca, float capacidad, String aerolinea) 
	{
		super(nSerie, modelo, anhoFabricacion, marca, aerolinea,1);
		capacidadDensidad = capacidad;
	}
	public float getCapacidadDensidad() {
		return capacidadDensidad;
	}
	public void setCapacidadDensidad(float capacidadDensidad) {
		this.capacidadDensidad = capacidadDensidad;
	}

}
