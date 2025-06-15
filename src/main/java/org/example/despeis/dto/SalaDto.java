package org.example.despeis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Sala}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalaDto implements Serializable {
    Integer id;
}