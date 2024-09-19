/**
 * Nombre: Daniel Josue Janssen Gil
 * Fecha: 23 may 2024
 * Ejercicio: parking
 * Hora: 19:31:36
 */
package Persistencia;

/**
 * {@summary} Clase de Vehiculo
 */
public final class Vehiculo
{
	/*
	 * Matricula del vehiculo
	 */
	protected String matricula;
	/**
	 * Marca del vehiculo
	 */
	protected String marca;
	/**
	 * Modelo del vehiculo
	 */
	protected String modelo;
	/**
	 * Tipo del vehiculo
	 */
	protected String tipo;
	
	/**
	 * {@summary} Constructor por defecto de vehiculo
	 * @param matricula - matricula del vehiculo
	 * @param marca - marca del vehiculo
	 * @param modelo - modelo del vehiculo
	 * @param tipo - tipo del vehiculo
	 */
	public Vehiculo(String matricula, String marca, String modelo, String tipo) throws IllegalArgumentException
	{
		if (matricula.length() == 0 || marca.length() == 0 || modelo.length() == 0 || tipo.length() == 0)
			throw new IllegalArgumentException("Error: Has introducido un campo vacio.");
		this.matricula = matricula;
		this.marca = marca;
		this.modelo = modelo;
		this.tipo = tipo;
	}

	/**
	 * {@summary} Devuelve la String de la matricula
	 * @return String matricula
	 */
	public String getMatricula()
	{
		return matricula;
	}

	/**
	 * {@summary} Devuelve el DNI del propietario
	 * @return String DNI
	 */
	public String getMarca()
	{
		return marca;
	}

	/**
	 * {@summary} Devuelve el telefono del propietario
	 * @return String telefono
	 */
	public String getModelo()
	{
		return modelo;
	}
	
	/**
	 * {@summary} Devuelve el telefono del propietario
	 * @return String telefono
	 */
	public String getTipo()
	{
		return tipo;
	}

	@Override
	/**
	 * {@summary} Devuelve una string con la informacion del vehiculo
	 * @return String informacion
	 */
	public String toString()
	{
		return "Vehiculo [matricula=" + matricula + ", marca=" + marca + ", modelo=" + modelo + ", tipo=" + tipo +"]";
	}
	
	
}