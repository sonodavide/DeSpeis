package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Attore}
 */
@Value
public class AttoreDto implements Serializable {
    Integer id;
    String nome;
    String cognome;
}