package org.example.despeis.repository;

import org.example.despeis.model.Attorefilm;
import org.example.despeis.model.Genere;
import org.example.despeis.model.Generefilm;
import org.example.despeis.model.GenerefilmId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerefilmRepository extends JpaRepository<Generefilm, GenerefilmId> {


}