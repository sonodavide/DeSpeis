package org.example.despeis.dto;

import lombok.Value;
import org.example.despeis.dto.FilmDto;
import org.example.despeis.dto.SalaDto;
import org.example.despeis.model.Spettacolo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link Spettacolo}
 */
@Value
public class SpettacoloDto implements Serializable {
    Integer id;
    LocalDate data;
    LocalTime ora;
    BigDecimal prezzo;
    SalaDto sala;
    FilmDto film;
}