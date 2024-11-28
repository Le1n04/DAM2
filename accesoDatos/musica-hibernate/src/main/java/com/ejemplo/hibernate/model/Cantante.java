package com.ejemplo.hibernate.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cantantes")
public class Cantante
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String nombre;

	@ManyToOne
	@JoinColumn(name = "genero_id", nullable = false)
	private GeneroMusical genero;

	// Getters y Setters
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public GeneroMusical getGenero()
	{
		return genero;
	}

	public void setGenero(GeneroMusical genero)
	{
		this.genero = genero;
	}
}
