package org.example.despeis.repository;

import org.example.despeis.model.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdineRepository extends JpaRepository<Ordine, Integer> {

    Page<Ordine> findAll(Pageable pageable);
    Page<Ordine> findAllByUtenteId(String userId, Pageable pageable);
    @Query("SELECT SUM(o.totale) FROM Ordine o")
    Double sumAllTotale();
}