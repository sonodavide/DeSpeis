package org.example.despeis.repository;

import org.example.despeis.model.Regiafilm;
import org.example.despeis.model.RegiafilmId;
import org.example.despeis.model.Regista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegiafilmRepository extends JpaRepository<Regiafilm, RegiafilmId> {

}