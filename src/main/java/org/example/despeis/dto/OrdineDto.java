package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for {@link org.example.despeis.model.Ordine}
 */
@Value
public class OrdineDto implements Serializable {
    Integer id;
    LocalDate data;
    String stato;
    BigDecimal totale;
    Set<BigliettoDto> bigliettos;
}