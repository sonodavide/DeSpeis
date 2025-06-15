package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link org.example.despeis.model.Spettacolo}
 */
@Value
public class SpettacoloDto implements Serializable {
    Integer id;
    @NotNull
    LocalDate data;
    @NotNull
    LocalTime ora;
    @NotNull
    BigDecimal prezzo;
    @NotNull
    Integer salaId;
    @NotNull
    FilmDto film;
    @NotNull
    LocalDate dataFine;
    @NotNull
    LocalTime oraFine;
}