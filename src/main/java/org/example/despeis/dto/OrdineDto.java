package org.example.despeis.dto;

import lombok.Value;
import org.example.despeis.model.Ordine;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Ordine}
 */
@Value
public class OrdineDto implements Serializable {
    Integer id;
    LocalDate data;
    String stato;
    UtenteDto utente;
}