package org.example.despeis.repository;

import org.example.despeis.model.Biglietto;
import org.example.despeis.model.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BigliettoRepository extends JpaRepository<Biglietto, Integer> {
    public Page<Biglietto> findByUtenteId(String id, Pageable pageable);


    public Page<Biglietto> findByPostospettacoloSpettacoloData(LocalDate data, Pageable pageable);
    public Page<Biglietto> findByUtenteIdAndPostospettacoloSpettacoloData(String utente, LocalDate data, Pageable pageable);
    Page<Biglietto> findAllByUtenteId(String userId, Pageable pageable);

}
