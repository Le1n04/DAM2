package com.biblioteca.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String titulo;

    @Column(length = 100, nullable = false)
    private String autor;

    private int paginas;

    @Column(length = 100)
    private String genero;

    private Boolean disponible; // 👈 Aquí aseguramos que el campo existe


    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

	public Object getPaginas()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Object getGenero()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Object getAutor()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTitulo()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setGenero(Object genero2)
	{
		// TODO Auto-generated method stub
		
	}

	public void setPaginas(Object paginas2)
	{
		// TODO Auto-generated method stub
		
	}

	public void setAutor(Object autor2)
	{
		// TODO Auto-generated method stub
		
	}

	public void setTitulo(Object titulo2)
	{
		// TODO Auto-generated method stub
		
	}

	public Boolean getDisponible()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
