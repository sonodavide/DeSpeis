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
    @Lock(LockModeType.PESSIMISTIC_READ) // Aggiunge un lock pessimistico in lettura
    @Query("SELECT s.id FROM Spettacolo s WHERE s.sala.id = :sala AND (\n" +
            "                function('DATE_TIME', s.data, s.ora) <= function('DATE_TIME', :dataFine, :oraFine) AND\n" +
            "                function('DATE_TIME', s.dataFine, s.oraFine) >= function('DATE_TIME', :dataInizio, :oraInizio)\n" +
            "            )")
    List<Integer> findConflictingSpettacoli(@Param("sala") Integer sala,
                                               @Param("data") LocalDate data,
                                               @Param("oraInizio") LocalTime oraInizio,
                                               @Param("dataFine") LocalDate dataFine,
                                               @Param("oraFine") LocalTime oraFine);

    List<Spettacolo> findByAcquistabile(boolean b);

    Spettacolo findFirstBySalaAndAcquistabileTrue(Sala sala);

    @Query("SELECT s FROM Spettacolo s WHERE s.data >= CURRENT_DATE")
    List<Spettacolo> findProssimiSpettacoli();

    @Query("SELECT s FROM Spettacolo s WHERE s.data >= CURRENT_DATE and s.film = :film ")
    List<Spettacolo> findProssimiSpettacoliByFilm(Film film);



}
