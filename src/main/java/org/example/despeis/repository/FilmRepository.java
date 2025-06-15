package org.example.despeis.repository;

import org.example.despeis.model.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Integer> {
    public Film findByTitoloOrderByTitolo(String titolo);
    public Film findById(int id);
    public List<Film> findAllByOrderByTitoloDescDatauscitaDesc();
    public List<Film> findAllByTitoloOrderByTitoloDescDatauscitaDesc(String titolo);
}