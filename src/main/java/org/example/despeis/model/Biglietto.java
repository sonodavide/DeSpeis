package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "biglietto")
public class Biglietto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "biglietto_id_gen")
    @SequenceGenerator(name = "biglietto_id_gen", sequenceName = "biglietto_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ordine", nullable = false)
    private Ordine ordine;

    //TODO [Reverse Engineering] generate columns from DB
}