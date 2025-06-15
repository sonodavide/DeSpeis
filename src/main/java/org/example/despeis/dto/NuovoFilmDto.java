package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Value
public class NuovoFilmDto implements Serializable {
    Integer id;
    String titolo;
    Integer durata;
    String trama;
    String img;
    LocalDate datauscita;
    Set<Integer> genere;
    Set<Integer> regista;
    Set<Integer> attore;
}
