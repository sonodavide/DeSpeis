package org.example.despeis.repository;

import org.example.despeis.model.Biglietto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BigliettoRepository extends JpaRepository<Biglietto, Integer> {
}