package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Regista}
 */
@Value
public class RegistaDto implements Serializable {
    Integer id;
    @NotNull
    String nome;
    @NotNull
    String cognome;
}