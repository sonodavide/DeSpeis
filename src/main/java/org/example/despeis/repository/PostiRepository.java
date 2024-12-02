package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Posti;
import org.example.despeis.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface PostiRepository extends JpaRepository<Posti, Integer> {
    @Lock(LockModeType.OPTIMISTIC)
    List<Posti> findAllBySala(Sala sala);
}