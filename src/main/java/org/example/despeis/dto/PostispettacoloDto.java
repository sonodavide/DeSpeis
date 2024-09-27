package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Postispettacolo}
 */
@Value
public class PostispettacoloDto implements Serializable {
    Integer id;
    PostoDto posto;
    String stato;
    Integer spettacoloId;
}