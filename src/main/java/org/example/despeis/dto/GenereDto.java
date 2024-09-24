package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Genere}
 */
@Value
public class GenereDto implements Serializable {
    Integer id;
    String genere;
}