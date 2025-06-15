package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ordine")
public class Ordine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordine_id_gen")
    @SequenceGenerator(name = "ordine_id_gen", sequenceName = "ordine_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "stato", nullable = false, length = Integer.MAX_VALUE)
    private String stato;


    @Column(name = "totale", precision = 38, scale = 2)
    private BigDecimal totale;

    @OneToMany(mappedBy = "ordine")
    private Set<Biglietto> bigliettos = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utente", nullable = false)
    private Utente utente;

    @ColumnDefault("0")
    @Column(name = "version")
    @Version
    private Integer version;

}