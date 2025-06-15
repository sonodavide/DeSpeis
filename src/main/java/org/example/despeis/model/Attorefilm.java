package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "attorefilm")
public class Attorefilm {
    @EmbeddedId
    private AttorefilmId id;

    @MapsId("film")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "film", nullable = false)
    private Film film;



}