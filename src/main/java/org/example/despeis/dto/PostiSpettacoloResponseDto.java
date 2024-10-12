package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Value
public class PostiSpettacoloResponseDto implements Serializable {
    Integer spettacoloId;
    Map<String, List<PostoResponse>> posti;

    @Value
    public static class PostoResponse implements Serializable{
        Integer postoId;
        Integer postoSedileId;
        String stato;
    }
}
