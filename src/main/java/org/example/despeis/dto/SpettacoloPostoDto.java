package org.example.despeis.dto;

import lombok.Value;
import org.example.despeis.model.Spettacolo;

import java.io.Serializable;

/**
 * DTO for {@link Spettacolo}
 */
@Value
public class SpettacoloPostoDto implements Serializable {
    Integer id;
    Integer salaId;
}