package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Value
public class NuovoFilmDto implements Serializable {
    Integer id;
    @NotNull
    String titolo;
    @NotNull
    Integer durata;
    @NotNull
    String trama;
    @NotNull
    String img;
    @NotNull
    LocalDate datauscita;
    @NotNull
    Set<Integer> genere;
    @NotNull
    Set<Integer> regista;
    @NotNull
    Set<Integer> attore;
}
