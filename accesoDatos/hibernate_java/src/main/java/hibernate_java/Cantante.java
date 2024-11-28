package hibernate_java;

import jakarta.persistence.*;

@Entity
@Table(name = "cantantes")
public class Cantante
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codigo;

	@Column(name = "nombre_cantante", nullable = false)
	private String nombreCantante;

	@Column(nullable = false)
	private int anio;

	@Column(nullable = false)
	private String album;

	// Getters y Setters
	public int getCodigo()
	{
		return codigo;
	}

	public void setCodigo(int codigo)
	{
		this.codigo = codigo;
	}

	public String getNombreCantante()
	{
		return nombreCantante;
	}

	public void setNombreCantante(String nombreCantante)
	{
		this.nombreCantante = nombreCantante;
	}

	public int getAnio()
	{
		return anio;
	}

	public void setAnio(int anio)
	{
		this.anio = anio;
	}

	public String getAlbum()
	{
		return album;
	}

	public void setAlbum(String album)
	{
		this.album = album;
	}

	@Override
	public String toString()
	{
		return "Cantante{" + "codigo=" + codigo + ", nombreCantante='" + nombreCantante + '\'' + ", anio=" + anio
				+ ", album='" + album + '\'' + '}';
	}
}
