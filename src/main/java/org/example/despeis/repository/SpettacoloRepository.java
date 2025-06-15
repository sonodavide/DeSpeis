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

    @Query("SELECT s FROM Spettacolo s WHERE s.acquistabile=:acquistabile  and :data=s.data and ((s.oraFine > current_time and s.dataFine = current_date ) or s.data!=current_date ) ")
    public List<Spettacolo> findAllByDataAndAcquistabileOrderByFilmTitoloAscOraAsc(@Param("data") LocalDate data, @Param("acquistabile") Boolean acquistabile);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s.id FROM Spettacolo s " +
            "WHERE s.sala.id = :sala AND s.id != :spettacoloId AND (" +
            "   (:dataInizio > s.data OR (:dataInizio = s.data AND :oraInizio >= s.ora)) " +
            "   AND (:dataInizio < s.dataFine OR (:dataInizio = s.dataFine AND :oraInizio < s.oraFine)) " +
            "   OR " +
            "   (:dataFine > s.data OR (:dataFine = s.data AND :oraFine > s.ora)) " +
            "   AND (:dataFine < s.dataFine OR (:dataFine = s.dataFine AND :oraFine <= s.oraFine)) " +
            "   OR " +
            "   (:dataInizio <= s.data AND :dataFine >= s.dataFine AND " +
            "    :oraInizio <= s.ora AND :oraFine >= s.oraFine) " +
            ")")
    List<Integer> findConflictingSpettacoli(@Param("sala") Integer sala,
                                            @Param("dataInizio") LocalDate data,
                                            @Param("oraInizio") LocalTime oraInizio,
                                            @Param("dataFine") LocalDate dataFine,
                                            @Param("oraFine") LocalTime oraFine,
                                            @Param("spettacoloId") Integer spettacoloId);

    @Query("SELECT s FROM Spettacolo s WHERE (s.dataFine > CURRENT_DATE or (s.dataFine = CURRENT_DATE and s.oraFine >= CURRENT_TIME))  and s.acquistabile=true")
    List<Spettacolo> findSpettacoliAcquistabili();

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s FROM Spettacolo s WHERE  (s.dataFine > CURRENT_DATE or (s.dataFine = CURRENT_DATE and s.oraFine >= CURRENT_TIME))  and s.acquistabile=true and s.id = :id")
    Spettacolo findSpettacoloAcquistabileById(@Param("id") int id);

    @Query("SELECT s FROM Spettacolo s " +
            "WHERE s.sala = :sala " +
            "AND (s.dataFine > CURRENT_DATE OR (s.dataFine = CURRENT_DATE AND s.oraFine > CURRENT_TIME))")
    List<Spettacolo> findBySalaAndNonFiniti(@Param("sala") Sala sala);

    @Query("SELECT s FROM Spettacolo s WHERE s.dataFine >= CURRENT_DATE and s.oraFine>= CURRENT_TIME ")
    List<Spettacolo> findProssimiSpettacoli();

    @Lock(LockModeType.OPTIMISTIC)
    @Query("SELECT s FROM Spettacolo s WHERE  ((s.dataFine = CURRENT_DATE and s.oraFine>CURRENT_TIME) or s.dataFine > current_date ) and s.film = :film ")
    List<Spettacolo> findProssimiSpettacoliByFilm(@Param("film") Film film);

    boolean existsSpettacoloByFilmId(int filmId);

    boolean existsSpettacoloBySalaId(int salaId);


}

