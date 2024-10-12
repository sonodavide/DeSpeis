package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Attore;
import org.example.despeis.model.Film;
import org.example.despeis.model.Genere;
import org.example.despeis.model.Postispettacolo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GenereRepository extends JpaRepository<Genere, Integer> {
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT g.id FROM Genere g WHERE g.id = :genereIds")
    public Set<Genere> findByIds(@Param("genereIds") Set<Integer> genereIds);

    @Query("SELECT g FROM Genere g WHERE " +
            "g.genere ILIKE :searchTerm ")
    Page<Genere> cerca(@Param("searchTerm") String searchTerm, Pageable pageable
    );
}