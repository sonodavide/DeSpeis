package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Attore;
import org.example.despeis.model.Genere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AttoreRepository extends JpaRepository<Attore, Integer> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT a.id FROM Attore a WHERE a.id = :attoreIds")
    public Set<Attore> findByIds(@Param("attoreIds") Set<Integer> attoreIds);
}