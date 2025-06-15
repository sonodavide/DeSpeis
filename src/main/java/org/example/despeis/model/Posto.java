package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posto")
public class Posto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posto_id_gen")
    @SequenceGenerator(name = "posto_id_gen", sequenceName = "posto_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fila", nullable = false, length = Integer.MAX_VALUE)
    private String fila;

    @Column(name = "sedile", nullable = false)
    private Integer sedile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala", nullable = false)
    private Sala sala;

    @Column(name = "occupato", nullable = false)
    private Boolean occupato = false;

}