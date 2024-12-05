package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class FilmSpettacoliDto implements Serializable {
    @NotNull
    FilmDto film;
    @NotNull
    List<SpettacoloSenzaFilmTagsDto> spettacoli;
}
