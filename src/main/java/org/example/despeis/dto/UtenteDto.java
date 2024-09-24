package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Utente}
 */
@Value
public class UtenteDto implements Serializable {
    Integer id;
    String username;
    String nome;
    String cognome;
}