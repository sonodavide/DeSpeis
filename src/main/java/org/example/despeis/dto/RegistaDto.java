package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Regista}
 */
@Value
public class RegistaDto implements Serializable {
    Integer id;
    String nome;
    String cognome;
}