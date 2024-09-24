package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ordine")
public class Ordine {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "stato", nullable = false, length = Integer.MAX_VALUE)
    private String stato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utente", nullable = false)
    private Utente utente;

    @OneToMany(mappedBy = "ordine")
    private Set<Biglietto> bigliettos = new LinkedHashSet<>();

}