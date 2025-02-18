package model;

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

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getTitulo()
	{
		return titulo;
	}

	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}

	public String getAutor()
	{
		return autor;
	}

	public void setAutor(String autor)
	{
		this.autor = autor;
	}

	public int getPaginas()
	{
		return paginas;
	}

	public void setPaginas(int paginas)
	{
		this.paginas = paginas;
	}

	public String getGenero()
	{
		return genero;
	}

	public void setGenero(String genero)
	{
		this.genero = genero;
	}

	public Boolean getDisponible()
	{
		return disponible;
	}

	public void setDisponible(Boolean disponible)
	{
		this.disponible = disponible;
	}

    
}
