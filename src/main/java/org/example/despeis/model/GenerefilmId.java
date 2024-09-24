package org.example.despeis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class GenerefilmId implements Serializable {
    private static final long serialVersionUID = 8384206181638579345L;
    @Column(name = "film", nullable = false)
    private Integer film;

    @Column(name = "genere", nullable = false)
    private Integer genere;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GenerefilmId entity = (GenerefilmId) o;
        return Objects.equals(this.film, entity.film) &&
                Objects.equals(this.genere, entity.genere);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, genere);
    }

}