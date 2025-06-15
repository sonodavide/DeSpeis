package org.example.despeis.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "spettacolo")
public class Spettacolo {
    @Id
    @ColumnDefault("nextval('spettacolo_id_seq'::regclass)")
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

}