package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Postispettacolo}
 */
@Value
public class PostispettacoloDto implements Serializable {
    @NotNull
    Integer id;
    @NotNull
    Integer sedile;
    @NotNull
    String stato;
    @NotNull
    String fila;


}