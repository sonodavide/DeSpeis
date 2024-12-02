package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import org.example.despeis.model.Film;
import org.example.despeis.model.Genere;
import org.example.despeis.model.Spettacolo;
import org.example.despeis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class TestService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    FilmRepository filmRepository;
    @Autowired
    SpettacoloRepository spettacoloRepository;
    @Autowired
    GenereRepository genereRepository;
    @Autowired
    GenerefilmRepository generefilmRepository;
    @Autowired
    RegiafilmRepository regiafilmRepository;
    @Autowired
    AttorefilmRepository attorefilmRepository;

    @Transactional
    public void testOptLock(float prezzo){
        try{
            Film f = entityManager.find(Film.class, 9998, LockModeType.OPTIMISTIC);
            filmRepository.delete(f);
            System.out.println("ciao");
            System.out.println("ciao");

        }catch (ObjectOptimisticLockingFailureException e){
            System.out.println("ottimismo");
        }
    }

    @Transactional
    public void casca() {



        System.out.println(spettacoloRepository.findConflictingSpettacoli(4, LocalDate.of(2024, 10, 6), LocalTime.of(19, 30), LocalDate.of(2024, 10, 20), LocalTime.of(20, 9)).isEmpty());
        System.out.println(spettacoloRepository.findConflictingSpettacoli(4, LocalDate.of(2024, 10, 6), LocalTime.of(19, 31), LocalDate.of(2024, 10, 20), LocalTime.of(20, 9)).isEmpty());
        System.out.println(spettacoloRepository.findConflictingSpettacoli(4, LocalDate.of(2024, 10, 6), LocalTime.of(19, 29), LocalDate.of(2024, 10, 20), LocalTime.of(20, 7)).isEmpty());
        System.out.println(spettacoloRepository.findConflictingSpettacoli(4, LocalDate.of(2024, 10, 6), LocalTime.of(19, 29), LocalDate.of(2024, 10, 20), LocalTime.of(20, 10)).isEmpty());
        System.out.println(spettacoloRepository.findConflictingSpettacoli(4, LocalDate.of(2024, 10, 5), LocalTime.of(19, 30), LocalDate.of(2024, 10, 5), LocalTime.of(20, 9)).isEmpty());


    }

}