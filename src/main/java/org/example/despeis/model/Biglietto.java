package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

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
    @JoinColumn(name = "utente", nullable = false)
    private Utente utente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ordine", nullable = false)
    private Ordine ordine;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "postospettacolo", nullable = false)
    private Postispettacolo postospettacolo;

    @Column(name = "prezzo")
    private BigDecimal prezzo;

    @ColumnDefault("0")
    @Column(name = "version")
    @Version
    private Integer version;

}