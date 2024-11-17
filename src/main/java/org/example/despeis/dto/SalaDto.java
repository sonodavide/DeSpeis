package org.example.despeis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Sala}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaDto implements Serializable {
    Integer id;
}