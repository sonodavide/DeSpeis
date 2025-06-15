package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Posti}
 */
@Value
public class PostiDto implements Serializable {
    Integer id;
    @NotNull
    String fila;
    @NotNull
    Integer sedili;
}