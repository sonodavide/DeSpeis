package org.example.despeis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "film")
public class Film {
    @Id
    @ColumnDefault("nextval('films_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "titolo", nullable = false, length = Integer.MAX_VALUE)
    private String titolo;

    @Column(name = "durata", nullable = false)
    private Integer durata;

    @Column(name = "trama", nullable = false, length = Integer.MAX_VALUE)
    private String trama;

}