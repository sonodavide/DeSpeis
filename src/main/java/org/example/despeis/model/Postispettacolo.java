package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "sedile", nullable = false)
    private Integer sedile;

    @Column(name = "stato", nullable = false, length = Integer.MAX_VALUE)
    private String stato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "spettacolo", nullable = false)
    private Spettacolo spettacolo;

    @Column(name = "fila", nullable = false, length = Integer.MAX_VALUE)
    private String fila;

    @ColumnDefault("0")
    @Column(name = "version")
    @Version
    private Integer version;

}