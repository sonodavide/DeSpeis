package org.example.despeis.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "utente")
public class Utente {
    @Id
    @Column(name = "id", nullable = false, length = Integer.MAX_VALUE)
    private String id;

    @Column(name = "username", nullable = false, length = Integer.MAX_VALUE)
    private String username;

    @Column(name = "firstname", nullable = false, length = Integer.MAX_VALUE)
    private String firstName;

    @Column(name = "lastname", nullable = false, length = Integer.MAX_VALUE)
    private String lastName;

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

    @ColumnDefault("0")
    @Column(name = "version")
    @Version
    private Integer version;

}