package org.example.despeis.repository;

import org.example.despeis.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
    public Sala findBySpettacolosId(Integer spettacoloId);
}