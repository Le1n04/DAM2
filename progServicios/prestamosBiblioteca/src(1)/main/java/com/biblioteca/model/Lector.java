package com.biblioteca.model;

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

	public Object getNombre()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public Object getCurso()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public void setNombre(Object nombre2)
	{
		// TODO Auto-generated method stub
		
	}

	public void setCurso(Object curso2)
	{
		// TODO Auto-generated method stub
		
	}
}
