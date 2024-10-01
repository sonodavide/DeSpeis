package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sala")
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sala_id_gen")
    @SequenceGenerator(name = "sala_id_gen", sequenceName = "sala_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "sala")
    private Set<Posto> postos = new LinkedHashSet<>();


}