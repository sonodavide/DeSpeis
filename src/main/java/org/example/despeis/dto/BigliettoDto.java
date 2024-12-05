package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    Integer id;
    @NotNull
    String utenteId;
    @NotNull
    Integer ordineId;
    @NotNull
    Integer postospettacoloSedile;
    @NotNull
    String postospettacoloFila;
    @NotNull
    LocalDate postospettacoloSpettacoloData;
    @NotNull
    LocalTime postospettacoloSpettacoloOra;
    @NotNull
    Integer postospettacoloSpettacoloSalaId;
    @NotNull
    String postospettacoloSpettacoloFilmTitolo;
    @NotNull
    BigDecimal prezzo;
}