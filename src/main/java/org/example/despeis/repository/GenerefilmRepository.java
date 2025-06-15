package org.example.despeis.repository;

import org.example.despeis.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerefilmRepository extends JpaRepository<Generefilm, GenerefilmId> {
    void deleteByFilm(Film film);
}