package org.example.despeis.repository;

import org.example.despeis.model.Posti;
import org.example.despeis.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostiRepository extends JpaRepository<Posti, Integer> {
    List<Posti> findAllBySala(Sala sala);
}