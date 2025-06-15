package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Genere}
 */
@Value
public class GenereDto implements Serializable {
    Integer id;
    @NotNull
    String genere;
}