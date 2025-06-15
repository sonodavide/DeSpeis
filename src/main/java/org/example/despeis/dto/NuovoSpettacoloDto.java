package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link org.example.despeis.model.Spettacolo}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NuovoSpettacoloDto implements Serializable {
    Integer id;
    @NotNull
    LocalDate data;
    @NotNull
    LocalTime ora;
    @NotNull
    BigDecimal prezzo;
    @NotNull
    SalaDto sala;
    @NotNull
    FilmDto film;
    @NotNull
    Boolean acquistabile;
}