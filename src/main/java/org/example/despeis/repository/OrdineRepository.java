package org.example.despeis.repository;

import org.example.despeis.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdineRepository extends JpaRepository<Ordine, Integer> {


    List<Ordine> findAllByUtenteIdOrderByDataDesc(Integer userId);
}