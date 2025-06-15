package org.example.despeis.repository;

import org.example.despeis.model.Biglietto;
import org.example.despeis.model.Ordine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BigliettoRepository extends JpaRepository<Biglietto, Integer> {
    public Page<Biglietto> findByUtenteId(Integer id, Pageable pageable);


    public List<Biglietto> findByPostospettacoloSpettacoloData(LocalDate data);
    public List<Biglietto> findByUtenteIdAndPostospettacoloSpettacoloData(Integer utente, LocalDate data);
    Page<Biglietto> findAllByUtenteId(Integer userId, Pageable pageable);

}
