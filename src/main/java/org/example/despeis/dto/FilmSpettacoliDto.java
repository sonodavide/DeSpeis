package org.example.despeis.dto;

import lombok.Value;
import org.example.despeis.model.Film;

import java.io.Serializable;
import java.util.List;

@Value
public class FilmSpettacoliDto implements Serializable {
    FilmDto film;
    List<SpettacoloSenzaFilmDto> spettacoli;
}
