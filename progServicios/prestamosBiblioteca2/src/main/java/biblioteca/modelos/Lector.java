package biblioteca.modelos;

import jakarta.persistence.*;

@Entity
@Table(name = "lectores")
public class Lector {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	private String nombrelogin;
	private String contraseña;
	private int codCurso; 

	public Lector() {
	}

	public Lector(String nombre, String nombrelogin, String contraseña, int codCurso) {
		this.nombre = nombre;
		this.nombrelogin = nombrelogin;
		this.contraseña = contraseña;
		this.codCurso = codCurso;
	}

	// getters y setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombrelogin() {
		return nombrelogin;
	}

	public void setNombrelogin(String nombrelogin) {
		this.nombrelogin = nombrelogin;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public int getCodCurso() {
		return codCurso;
	}

	public void setCodCurso(int codCurso) {
		this.codCurso = codCurso;
	}
}
