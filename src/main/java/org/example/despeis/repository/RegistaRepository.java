package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Attore;
import org.example.despeis.model.Genere;
import org.example.despeis.model.Regista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query(value = "SELECT r FROM Regista r WHERE " +
            "CONCAT(r.nome, ' ', r.cognome) ILIKE :searchTerm OR " +
            "CONCAT(r.cognome, ' ', r.nome) ILIKE :searchTerm"
    )
    Page<Regista> cerca(@Param("searchTerm") String searchTerm, Pageable pageable);
}