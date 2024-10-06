package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

@Value
public class PrenotazioneRequestDto implements Serializable {
    Set<Integer> postiIds;
    Integer userId;
    Integer spettacoloId;
}
