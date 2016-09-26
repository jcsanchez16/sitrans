package vos;

import java.sql.Date;



public class Vuelo {

	private int id;

	private int precio;

	private Date fechaSalida;
	
	private Date fechaLlegada;
	
	private int aeropuertoSalida;
	
	private int aeropuertoLlegada;

	public Vuelo(int id, int precio, Date fechaSalida, Date fechaLlegada, int aeropuertoSalida, int aeropuertoLlegada) {
		super();
		this.id = id;
		this.precio = precio;
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
		this.aeropuertoLlegada = aeropuertoLlegada;
		this.aeropuertoSalida = aeropuertoSalida;
	}

	public int getAeropuertoSalida() {
		return aeropuertoSalida;
	}

	public void setAeropuertoSalida(int aeropuertoSalida) {
		this.aeropuertoSalida = aeropuertoSalida;
	}

	public int getAeropuertoLlegada() {
		return aeropuertoLlegada;
	}

	public void setAeropuertoLlegada(int aeropuertoLlegada) {
		this.aeropuertoLlegada = aeropuertoLlegada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Date getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Date getFechaLlegada() {
		return fechaLlegada;
	}

	public void setFechaLlegada(Date fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
	}

	

}
