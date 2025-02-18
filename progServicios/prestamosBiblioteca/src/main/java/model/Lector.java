package model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lectores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "cod_curso")
    private Curso curso;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
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

	public Curso getCurso()
	{
		return curso;
	}

	public void setCurso(Curso curso)
	{
		this.curso = curso;
	}


}
