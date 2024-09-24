package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "genere")
public class Genere {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genere_id_gen")
    @SequenceGenerator(name = "genere_id_gen", sequenceName = "genere_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "genere", nullable = false, length = Integer.MAX_VALUE)
    private String genere;

    @ManyToMany
    @JoinTable(name = "generefilm",
            joinColumns = @JoinColumn(name = "genere"),
            inverseJoinColumns = @JoinColumn(name = "film"))
    private Set<Film> films = new LinkedHashSet<>();

}