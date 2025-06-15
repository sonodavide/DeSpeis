package org.example.despeis.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
    private String firstname;

    @Column(name = "lastname", nullable = false, length = Integer.MAX_VALUE)
    private String lastname;

    @Column(name = "email", nullable = false, length = Integer.MAX_VALUE)
    private String email;

}