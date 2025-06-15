package org.example.despeis.repository;

import org.example.despeis.model.Posto;
import org.example.despeis.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PostoRepository extends JpaRepository<Posto, Integer> {
    public List<Posto> findBySalaId(int salaId);

    @Query("SELECT p.fila AS fila, COUNT(p) AS numeroSedili " +
            "FROM Posto p WHERE p.sala.id = :salaId " +
            "GROUP BY p.fila")
    List<FilaSediliCount> countSediliBySalaGroupByFila(@Param("salaId") Integer salaId);



    public interface FilaSediliCount {
        String getFila();
        Long getNumeroSedili();
    }
}