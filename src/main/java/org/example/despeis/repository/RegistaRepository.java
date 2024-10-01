package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Genere;
import org.example.despeis.model.Regista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RegistaRepository extends JpaRepository<Regista, Integer> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT r.id FROM Regista r WHERE r.id = :registaIds")
    public Set<Regista> findByIds(@Param("registaIds") Set<Integer> registaIds);
}