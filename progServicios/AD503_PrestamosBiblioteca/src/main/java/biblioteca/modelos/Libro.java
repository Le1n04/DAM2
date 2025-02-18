package biblioteca.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String autor;
	private int paginas;
	private String genero;
	private boolean disponible;

	public Libro() {
	}

	public Libro(String titulo, String autor, int paginas, String genero, boolean disponible) {
		this.titulo = titulo;
		this.autor = autor;
		this.paginas = paginas;
		this.genero = genero;
		this.disponible = disponible;
	}

	// getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}
}
