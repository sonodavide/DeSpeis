package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link org.example.despeis.model.Sala}
 */
@Value
public class SalaConPostiDto implements Serializable {
    Integer id;
    Set<PostiDto> postis;
}