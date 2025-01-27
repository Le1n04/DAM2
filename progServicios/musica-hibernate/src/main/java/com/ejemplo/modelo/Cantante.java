package com.ejemplo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "cantantes")
public class Cantante {

    @Id
    @Column(name = "codigo")
    private int codigo;
    
    @Column(name = "nombre_cantante")
    private String nombreCantante;
    
    @Column(name = "anio")
    private int anio;
    
    @Column(name = "album")
    private String album;
    
    public Cantante() {
    }

    public Cantante(int codigo, String nombreCantante, int anio, String album) {
        this.codigo = codigo;
        this.nombreCantante = nombreCantante;
        this.anio = anio;
        this.album = album;
    }

    // Getters y setters
    // ...
    
    @Override
    public String toString() {
        return "Cantante [codigo=" + codigo 
             + ", nombreCantante=" + nombreCantante 
             + ", anio=" + anio 
             + ", album=" + album + "]";
    }
}
