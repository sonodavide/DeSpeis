package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "regista")
public class Regista {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regista_id_gen")
    @SequenceGenerator(name = "regista_id_gen", sequenceName = "regista_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = Integer.MAX_VALUE)
    private String nome;

    @Column(name = "cognome", nullable = false, length = Integer.MAX_VALUE)
    private String cognome;

    @ManyToMany
    @JoinTable(name = "regiafilm",
            joinColumns = @JoinColumn(name = "regista"),
            inverseJoinColumns = @JoinColumn(name = "film"))
    private Set<Film> films = new LinkedHashSet<>();

}