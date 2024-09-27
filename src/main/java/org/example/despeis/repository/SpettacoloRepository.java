package org.example.despeis.repository;

import org.example.despeis.model.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpettacoloRepository extends JpaRepository<Spettacolo, Integer> {
    public List<Spettacolo> findAllByDataOrderByFilmTitoloAscOraAsc(LocalDate data);
}