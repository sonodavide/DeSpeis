package org.example.despeis.repository;

import org.example.despeis.model.Postispettacolo;
import org.example.despeis.model.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostispettacoloRepository extends JpaRepository<Postispettacolo, Integer> {
    public List<Postispettacolo> findAllBySpettacoloIdOrderByPostoFilaAscPostoSedileAsc(int spettacolo);

}