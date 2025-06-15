package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Film;
import org.example.despeis.model.Sala;
import org.example.despeis.model.Spettacolo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface SpettacoloRepository extends JpaRepository<Spettacolo, Integer> {

    public Page<Spettacolo> findAllByDataOrderByFilmTitoloAscOraAsc(LocalDate data, Pageable pageable);

    public List<Spettacolo> findAllByDataAndAcquistabileOrderByFilmTitoloAscOraAsc(LocalDate data, Boolean acquistabile);
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s.id " +
            "FROM Spettacolo s " +
            "WHERE s.sala.id = :sala AND (" +
            "(:dataInizio >= s.data AND :oraInizio >= s.ora AND :dataFine <= s.dataFine AND :oraFine <= s.oraFine) OR " +
            "(:dataInizio <= s.data AND :oraInizio <= s.ora AND :dataFine >= s.data AND :oraFine >= s.ora) OR " +
            "(:dataInizio <= s.dataFine AND :oraInizio <= s.oraFine AND :oraFine >= s.oraFine AND :dataFine >= s.dataFine) OR " +
            "(:dataInizio <= s.data AND :oraInizio <= s.ora AND :dataFine >= s.dataFine AND :oraFine >= s.oraFine)" +
            ")")
    List<Integer> findConflictingSpettacoli(@Param("sala") Integer sala,
                                               @Param("dataInizio") LocalDate data,
                                               @Param("oraInizio") LocalTime oraInizio,
                                               @Param("dataFine") LocalDate dataFine,
                                               @Param("oraFine") LocalTime oraFine);
    @Query("SELECT s FROM Spettacolo s WHERE s.data >= CURRENT_DATE and s.acquistabile=true")
    List<Spettacolo> findSpettacoliAcquistabili();
    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s FROM Spettacolo s WHERE s.data >= CURRENT_DATE and s.acquistabile=true and s.id = :id")
    Spettacolo findSpettacoloAcquistabileById(int id);
    Spettacolo findFirstBySalaAndAcquistabileTrue(Sala sala);

    @Query("SELECT s FROM Spettacolo s WHERE s.data >= CURRENT_DATE")
    List<Spettacolo> findProssimiSpettacoli();

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s FROM Spettacolo s WHERE s.data >= CURRENT_DATE and s.film = :film ")
    List<Spettacolo> findProssimiSpettacoliByFilm(Film film);



}
