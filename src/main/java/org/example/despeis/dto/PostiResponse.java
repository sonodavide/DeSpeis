package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public  class PostiResponse implements Serializable {
    Integer postoSpettacoloId;
    Integer postoSedile;
    String stato;
}
