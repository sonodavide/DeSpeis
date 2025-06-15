package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "posti")
public class Posti {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posti_id_gen")
    @SequenceGenerator(name = "posti_id_gen", sequenceName = "posto_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "fila", nullable = false, length = Integer.MAX_VALUE)
    private String fila;

    @Column(name = "sedili", nullable = false)
    private Integer sedili;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala", nullable = false)
    private Sala sala;

}