package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "films_id_gen")
    @SequenceGenerator(name = "films_id_gen", sequenceName = "films_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titolo", nullable = false, length = Integer.MAX_VALUE)
    private String titolo;

    @Column(name = "durata", nullable = false)
    private Integer durata;

    @Column(name = "trama", nullable = false, length = Integer.MAX_VALUE)
    private String trama;

    @ManyToMany(mappedBy = "films")
    private Set<Attore> attores = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "films")
    private Set<Genere> generes = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "films")
    private Set<Regista> registas = new LinkedHashSet<>();


    @Column(name = "img", length = Integer.MAX_VALUE)
    private String img;


    @Column(name = "datauscita")
    private LocalDate datauscita;

}