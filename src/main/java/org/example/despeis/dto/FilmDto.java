package org.example.despeis.dto;

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
    String titolo;
    Integer durata;
    String trama;
    String img;
    LocalDate datauscita;
    Set<AttoreDto> attores;
    Set<GenereDto> generes;
    Set<RegistaDto> registas;
}