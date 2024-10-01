package org.example.despeis.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.example.despeis.model.Biglietto}
 */
@Value
public class BigliettoDto implements Serializable {
    Integer id;
    UtenteDto utente;
    OrdineDto ordine;
    PostispettacoloDto postospettacolo;
}