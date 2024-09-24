package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regiafilm")
public class Regiafilm {
    @SequenceGenerator(name = "regiafilm_id_gen", sequenceName = "posto_id_seq", allocationSize = 1)
    @EmbeddedId
    private RegiafilmId id;

    @MapsId("film")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "film", nullable = false)
    private Film film;

}