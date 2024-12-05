package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
public class PrenotazioneRequestDto implements Serializable {
    @NotNull
    PostiSpettacoloResponseDto postiSpettacoloResponseDto;
    BigDecimal prezzo;
}
