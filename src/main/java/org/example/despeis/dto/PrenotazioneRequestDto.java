package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;

@Value
public class PrenotazioneRequestDto implements Serializable {
    PostiSpettacoloResponseDto postiSpettacoloResponseDto;
    BigDecimal prezzo;
}
