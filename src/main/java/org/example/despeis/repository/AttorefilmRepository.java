package org.example.despeis.repository;

import org.example.despeis.model.Attorefilm;
import org.example.despeis.model.AttorefilmId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttorefilmRepository extends JpaRepository<Attorefilm, AttorefilmId> {
}