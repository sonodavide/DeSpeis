package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "generefilm")
public class Generefilm {
    @SequenceGenerator(name = "generefilm_id_gen", sequenceName = "genere_id_seq", allocationSize = 1)
    @EmbeddedId
    private GenerefilmId id;

    @MapsId("film")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "film", nullable = false)
    private Film film;

}