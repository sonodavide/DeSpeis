package org.example.despeis.repository;

import org.example.despeis.model.Attore;
import org.example.despeis.model.Film;
import org.example.despeis.model.Genere;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    public Film findByTitoloOrderByTitolo(String titolo);

    public List<Film> findAllByOrderByTitoloDescDatauscitaDesc();
    public List<Film> findAllByTitoloOrderByTitoloDescDatauscitaDesc(String titolo);
    @Query("SELECT f FROM Film f WHERE " +
            "f.titolo ILIKE :searchTerm ")
    Page<Film> cerca(@Param("searchTerm") String searchTerm, Pageable pageable
    );
    Page<Film> findAllByGeneresId(Integer genereId, Pageable pageable);
    Page<Film> findAllByRegistasId(Integer registaId, Pageable pageable);
    Page<Film> findAllByAttoresId(Integer attoreId, Pageable pageable);
    @Query("SELECT f FROM Film f ORDER BY f.datauscita DESC LIMIT 8")
    List<Film> ultimeUscite();



}
