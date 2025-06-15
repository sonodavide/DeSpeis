package org.example.despeis.repository;

import org.example.despeis.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
}