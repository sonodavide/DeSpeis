package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for {@link org.example.despeis.model.Biglietto}
 */
@Value
public class BigliettoDto implements Serializable {
    Integer id;
    Integer utenteId;
    Integer ordineId;
    Integer postospettacoloSedile;
    String postospettacoloFila;
    LocalDate postospettacoloSpettacoloData;
    LocalTime postospettacoloSpettacoloOra;
    Integer postospettacoloSpettacoloSalaId;
    String postospettacoloSpettacoloFilmTitolo;
    BigDecimal prezzo;
}