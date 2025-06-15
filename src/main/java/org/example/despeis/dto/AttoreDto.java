package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Attore}
 */
@Value
public class AttoreDto implements Serializable {
    Integer id;
    @NotNull
    String nome;
    @NotNull
    String cognome;
}