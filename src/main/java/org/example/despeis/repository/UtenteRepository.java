package org.example.despeis.repository;

import org.example.despeis.model.Attore;
import org.example.despeis.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UtenteRepository extends JpaRepository<Utente, String> {
    @Query(value = "SELECT u FROM Utente u WHERE " +
            "CONCAT(u.nome, ' ', u.cognome) ILIKE :searchTerm OR " +
            "CONCAT(u.cognome, ' ', u.nome) ILIKE :searchTerm"
    )
    Page<Utente> cerca(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );
}