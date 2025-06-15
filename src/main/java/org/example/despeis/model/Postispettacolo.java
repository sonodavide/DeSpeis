package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "postispettacolo")
public class Postispettacolo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "postispettacolo_id_gen")
    @SequenceGenerator(name = "postispettacolo_id_gen", sequenceName = "postispettacolo_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "posto", nullable = false)
    private Posto posto;

    @Column(name = "stato", nullable = false, length = Integer.MAX_VALUE)
    private String stato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spettacolo", nullable = false)
    private Spettacolo spettacolo;

}