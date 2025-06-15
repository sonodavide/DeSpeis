package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Spettacolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SpettacoloRepository extends JpaRepository<Spettacolo, Integer> {

    public List<Spettacolo> findAllByDataOrderByFilmTitoloAscOraAsc(LocalDate data);
    @Lock(LockModeType.PESSIMISTIC_READ) // Aggiunge un lock pessimistico in lettura
    @Query("SELECT s.id FROM Spettacolo s WHERE s.sala.id = :sala AND s.data = :data AND " +
            "((:oraInizio BETWEEN s.ora AND s.oraFine) OR " +
            "(:oraFine BETWEEN s.ora AND s.oraFine) OR " +
            "(s.ora BETWEEN :oraInizio AND :oraFine))")
    List<Spettacolo> findConflictingSpettacoli(@Param("sala") Integer sala,
                                               @Param("data") LocalDate data,
                                               @Param("oraInizio") LocalTime oraInizio,
                                               @Param("oraFine") LocalTime oraFine);

    List<Spettacolo> findByAcquistabile(boolean b);
}
