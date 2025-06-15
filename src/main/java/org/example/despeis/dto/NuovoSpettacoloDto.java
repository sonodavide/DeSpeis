package org.example.despeis.dto;

import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Value
public class NuovoSpettacoloDto {
    Integer id;
    LocalDate data;
    LocalTime ora;
    BigDecimal prezzo;
    Integer sala;
    Integer film;
    Boolean acquistabile;
}
