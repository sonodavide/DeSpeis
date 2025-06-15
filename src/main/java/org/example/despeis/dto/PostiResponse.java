package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

@Value
public  class PostiResponse implements Serializable {
    @NotNull
    Integer postoSpettacoloId;
    @NotNull
    Integer postoSedile;
    @NotNull
    String stato;
}
