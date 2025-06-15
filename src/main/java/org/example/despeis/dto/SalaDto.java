package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    Integer id;
}