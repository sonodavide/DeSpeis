package org.example.despeis.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.example.despeis.model.Utente;

import java.io.Serializable;

/**
 * DTO for {@link Utente}
 */
@Value
public class UtenteDto implements Serializable {
    String id;
    @NotNull
    String username;
    @NotNull
    String firstName;
    @NotNull
    String lastName;
    @NotNull
    String email;
}