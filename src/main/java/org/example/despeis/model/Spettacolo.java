package org.example.despeis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "spettacolo")
public class Spettacolo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "spettacolo_id_gen")
    @SequenceGenerator(name = "spettacolo_id_gen", sequenceName = "spettacolo_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "ora", nullable = false)
    private LocalTime ora;

    @Column(name = "prezzo", nullable = false)
    private BigDecimal prezzo;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala", nullable = false)
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "film", nullable = false)
    private Film film;

    @Column(name = "acquistabile", nullable = false)
    private Boolean acquistabile;

    @Column(name = "\"data fine\"")
    private LocalDate dataFine;

    @Column(name = "\"ora fine\"")
    private LocalTime oraFine;

    @ColumnDefault("0")
    @Column(name = "version")
    @Version
    private Integer version;

    @JsonIgnore
    @OneToMany(mappedBy = "spettacolo", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Postispettacolo> postispettacolos = new LinkedHashSet<>();

}