package model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_libro")
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "id_lector")
    private Lector lector;

    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
}
