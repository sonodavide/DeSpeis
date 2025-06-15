package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.example.despeis.model.Film;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link Film}
 */
@Value
public class FilmDto implements Serializable {
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
    Set<AttoreDto> attores;
    @NotNull
    Set<GenereDto> generes;
    @NotNull
    Set<RegistaDto> registas;
}