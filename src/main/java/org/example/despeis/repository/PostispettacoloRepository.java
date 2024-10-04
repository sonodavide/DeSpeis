package org.example.despeis.repository;

import jakarta.persistence.LockModeType;
import org.example.despeis.model.Postispettacolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostispettacoloRepository extends JpaRepository<Postispettacolo, Integer> {
    public List<Postispettacolo> findAllBySpettacoloIdOrderByPostoFilaAscPostoSedileAsc(int spettacolo);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Postispettacolo findByPostoIdAndSpettacoloId(int postoId, int spettacoloId);
    @Lock(LockModeType.PESSIMISTIC_READ)
    public List<Postispettacolo> findBySpettacoloIdAndStato(int spettacoloId, String stato);
    public List<Postispettacolo> deleteBySpettacoloId(int spettacoloId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Postispettacolo p WHERE p.id IN :postiIds AND p.stato ='libero' ")
    public List<Postispettacolo> findByPostiIdsAndLiberi(@Param("postiIds") Set<Integer> postiIds);

}