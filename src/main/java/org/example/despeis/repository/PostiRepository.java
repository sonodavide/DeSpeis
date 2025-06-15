package org.example.despeis.repository;

import org.example.despeis.model.Posti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostiRepository extends JpaRepository<Posti, Integer> {
}