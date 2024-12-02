package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@Value
public class PrenotazioneRequestDto implements Serializable {
    Set<PostispettacoloDto> posti;
    Integer spettacoloId;
    BigDecimal prezzo;
}
