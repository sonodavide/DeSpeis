package org.example.despeis.repository;

import org.example.despeis.model.Generefilm;
import org.example.despeis.model.GenerefilmId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenerefilmRepository extends JpaRepository<Generefilm, GenerefilmId> {
}