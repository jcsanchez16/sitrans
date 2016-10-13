package vos;

import java.util.ArrayList;
import java.util.Date;

import sun.applet.Main;

/**
 * Clase que modela los vuelos que tienen los aviones
 * @author anaca
 *
 */
public class Vuelo
{
	//Identificador del vuelo
	private int codigo;
	
	//Costo del vuelo
	private int frecuencia;
	
	//Fecha de salida del vuelo
	private Date fechaSalida;
	
	//Fecha de llegada de un vuelo
	private Date fechaLlegada;
	
	//Aeropuerto de salida
	private Aeropuerto salida;
	
	//Aeropuerto de llegada
	private Aeropuerto llegada;
	
	//Avion asociado al vuelo
	private Avion avion;
	
	private Aerolinea aerolinea;
	
	private boolean realizado;
	
	private int distancia;
	
	private String duracion;
	
	
	/**
	 * Metodo constructor de un vuelo
	 * @param codigo
	 * @param frecuencia
	 * @param llegada
	 * @param salida
	 * @param avion
	 * @param asalida
	 * @param allegada
	 */
	public Vuelo(int codigo, int frecuencia, Date llegada, Date salida, Avion avion, Aeropuerto asalida, Aeropuerto allegada, Aerolinea aero, int f, int distancias, String duracion){
		this.codigo = codigo;
		this.frecuencia = frecuencia;
		this.fechaLlegada = llegada;
		this.fechaSalida = salida;
		this.avion = avion;
		this.salida = asalida;
		this.llegada = allegada;
		this.aerolinea = aero;
		realizado = f==1? true : false;
		this.distancia = distancias;
		this.duracion = duracion;
	}
	


	public boolean isRealizado() {
		return realizado;
	}


	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}


	public int getDistancia() {
		return distancia;
	}


	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}


	public String getDuracion() {
		return duracion;
	}


	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public int getFrecuencia() {
		return frecuencia;
	}


	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
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


	public Aeropuerto getSalida() {
		return salida;
	}


	public void setSalida(Aeropuerto salida) {
		this.salida = salida;
	}


	public Aeropuerto getLlegada() {
		return llegada;
	}


	public void setLlegada(Aeropuerto llegada) {
		this.llegada = llegada;
	}


	public Avion getAvion() {
		return avion;
	}


	public void setAvion(Avion avion) {
		this.avion = avion;
	}


	public Aerolinea getAerolinea() {
		return aerolinea;
	}


	public void setAerolinea(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	
}
