package org.example.despeis.repository;

import org.example.despeis.model.Posto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostoRepository extends JpaRepository<Posto, Integer> {
    public List<Posto> findBySalaId(int salaId);
}