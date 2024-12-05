package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;

@Value
public class PostiSpettacoloResponseDto {
    @NotNull
    SpettacoloSenzaFilmTagsDto spettacoloSenzaFilmTagsDto;
    @NotNull
    List<PostiPerFila> postiPerFila;
    @Value
    public static class PostiPerFila{
        @NotNull
        String fila;
        @NotNull
        List<PostiResponse> posti;
    }
}

