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
    @Query("SELECT s FROM Spettacolo s, Postispettacolo p WHERE s.id = :spettacoloId and s.acquistabile=true and s.data>=current_date and s.acquistabile = true and p.spettacolo.id = s.id ORDER BY p.fila ASC, p.sedile ASC ")
    public List<Postispettacolo> findAllBySpettacoloIdOrderByFilaAscSedileAscAcquistabile(@Param("spettacoloId")int spettacolo);

    public List<Postispettacolo> findAllBySpettacoloIdOrderByFilaAscSedileAsc(int spettacolo);
    @Lock(LockModeType.OPTIMISTIC)
    public List<Postispettacolo> findBySpettacoloId(int spettacoloId);
    public List<Postispettacolo> deleteBySpettacoloId(int spettacoloId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Postispettacolo p WHERE p.id IN :postiIds AND p.stato ='libero' ")
    public List<Postispettacolo> findByPostiIdsAndLiberi(@Param("postiIds") Set<Integer> postiIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Postispettacolo p WHERE p.id IN :postiIds AND p.stato !='prenotato' ")
    public List<Postispettacolo> findByPostiIdsAndNotPrenotati(@Param("postiIds") Set<Integer> postiIds);




}