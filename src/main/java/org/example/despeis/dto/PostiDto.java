package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Posti}
 */
@Value
public class PostiDto implements Serializable {
    Integer id;
    String fila;
    Integer sedili;
}