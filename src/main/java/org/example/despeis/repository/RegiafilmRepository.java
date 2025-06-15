package org.example.despeis.repository;

import org.example.despeis.model.Regiafilm;
import org.example.despeis.model.RegiafilmId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegiafilmRepository extends JpaRepository<Regiafilm, RegiafilmId> {
}