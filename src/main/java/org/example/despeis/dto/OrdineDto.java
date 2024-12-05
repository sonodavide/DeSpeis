package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    LocalDate data;
    @NotNull
    String stato;
    @NotNull
    BigDecimal totale;
    @NotNull
    Set<BigliettoDto> bigliettos;
}