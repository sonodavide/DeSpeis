package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Attore;
import org.example.despeis.model.Genere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AttoreRepository extends JpaRepository<Attore, Integer> {
    @Query(value = "SELECT a FROM Attore a WHERE " +
            "CONCAT(a.nome, ' ', a.cognome) ILIKE :searchTerm OR " +
            "CONCAT(a.cognome, ' ', a.nome) ILIKE :searchTerm"
            )
    Page<Attore> cerca(
            @Param("searchTerm") String searchTerm,
            Pageable pageable
    );
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT a.id FROM Attore a WHERE a.id = :attoreIds")
    public Set<Attore> findByIds(@Param("attoreIds") Set<Integer> attoreIds);
}