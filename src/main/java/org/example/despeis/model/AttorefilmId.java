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
public class AttorefilmId implements Serializable {
    private static final long serialVersionUID = -4708167349850232973L;
    @Column(name = "film", nullable = false)
    private Integer film;

    @Column(name = "attore", nullable = false)
    private Integer attore;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AttorefilmId entity = (AttorefilmId) o;
        return Objects.equals(this.film, entity.film) &&
                Objects.equals(this.attore, entity.attore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(film, attore);
    }

}