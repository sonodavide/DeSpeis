package org.example.despeis.dto;

import lombok.Value;
import org.example.despeis.model.Posto;

import java.io.Serializable;

/**
 * DTO for {@link Posto}
 */
@Value
public class PostoDto implements Serializable {
    Integer id;
    String fila;
    Integer sedile;
    Boolean occupato;
}