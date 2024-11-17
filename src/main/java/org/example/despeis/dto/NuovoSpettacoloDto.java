package org.example.despeis.dto;

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
    LocalDate data;
    LocalTime ora;
    BigDecimal prezzo;
    SalaDto sala;
    FilmDto film;
    Boolean acquistabile;
}