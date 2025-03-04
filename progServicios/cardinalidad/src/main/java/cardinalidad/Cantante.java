package cardinalidad;

import jakarta.persistence.*;

@Entity
@Table(name = "cantantes")
public class Cantante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "genero_id", nullable = false)
    private GeneroMusical genero;

    public Cantante() {}

    public Cantante(String nombre, GeneroMusical genero) {
        this.nombre = nombre;
        this.genero = genero;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public GeneroMusical getGenero() { return genero; }
}
