package org.example.despeis.dto;

import lombok.Value;

import java.util.List;

@Value
public class PostiSpettacoloResponseDto {
    SpettacoloSenzaFilmDto spettacoloSenzaFilmDto;
    List<PostiPerFila> postiPerFila;
    @Value
    public static class PostiPerFila{
        String fila;
        List<PostiResponse> posti;
    }
}

