package org.example.despeis.repository;

import org.example.despeis.model.Attore;
import org.example.despeis.model.Attorefilm;
import org.example.despeis.model.AttorefilmId;
import org.example.despeis.model.Film;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttorefilmRepository extends JpaRepository<Attorefilm, AttorefilmId> {
    void deleteByFilm(Film film);


}