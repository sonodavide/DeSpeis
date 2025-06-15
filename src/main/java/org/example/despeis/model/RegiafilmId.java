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
public class RegiafilmId implements Serializable {
    private static final long serialVersionUID = 201074106251136006L;
    @Column(name = "regista", nullable = false)
    private Integer regista;

    @Column(name = "film", nullable = false)
    private Integer film;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RegiafilmId entity = (RegiafilmId) o;
        return Objects.equals(this.regista, entity.regista) &&
                Objects.equals(this.film, entity.film);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regista, film);
    }

}