package biblioteca.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "prestamos")
@Data
@Entity
public class Prestamo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "id_libro")
	private Long idLibro;

	@Column(name = "id_lector")
	private Long idLector;

	private String fechaalta;
	private String fechabaja;

	public Prestamo() {
	}

	public Prestamo(Long idLibro, Long idLector, String fechaalta, String fechabaja) {
		this.idLibro = idLibro;
		this.idLector = idLector;
		this.fechaalta = fechaalta;
		this.fechabaja = fechabaja;
	}

	// getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(Long idLibro) {
		this.idLibro = idLibro;
	}

	public Long getIdLector() {
		return idLector;
	}

	public void setIdLector(Long idLector) {
		this.idLector = idLector;
	}

	public String getFechaalta() {
		return fechaalta;
	}

	public void setFechaalta(String fechaalta) {
		this.fechaalta = fechaalta;
	}

	public String getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(String fechabaja) {
		this.fechabaja = fechabaja;
	}
}
