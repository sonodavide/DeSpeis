package org.example.despeis.dto;

import lombok.Value;

import java.util.Map;

@Value
public class SalaConPostiPerFilaDto {
    SalaDto sala;
    Map<String, Long> postiPerFila;
}
