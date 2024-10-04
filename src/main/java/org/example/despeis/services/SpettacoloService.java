package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.despeis.dto.*;
import org.example.despeis.mapper.FilmMapper;
import org.example.despeis.mapper.PostispettacoloMapper;
import org.example.despeis.mapper.SpettacoloMapper;
import org.example.despeis.mapper.SpettacoloSenzaFilmMapper;
import org.example.despeis.model.*;
import org.example.despeis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SpettacoloService {
    @PersistenceContext
    private EntityManager entityManager;
    private final SpettacoloRepository spettacoloRepository;
    private final SpettacoloMapper spettacoloMapper;
    private final FilmRepository filmRepository;
    private final SalaRepository salaRepository;
    private final PostispettacoloRepository postispettacoloRepository;
    private final PostoRepository postoRepository;
    private final PostispettacoloMapper postispettacoloMapper;
    private final SpettacoloSenzaFilmMapper spettacoloSenzaFilmMapper;
    private final FilmMapper filmMapper;
    @Autowired
    public SpettacoloService(SpettacoloRepository spettacoloRepository, SpettacoloMapper spettacoloMapper, FilmRepository filmRepository, SalaRepository salaRepository, PostispettacoloRepository postispettacoloRepository, PostoRepository postoRepository, PostispettacoloMapper postispettacoloMapper, SpettacoloSenzaFilmMapper spettacoloSenzaFilmMapper, FilmMapper filmMapper) {
        this.spettacoloRepository = spettacoloRepository;
        this.spettacoloMapper = spettacoloMapper;
        this.filmRepository = filmRepository;
        this.salaRepository = salaRepository;
        this.postispettacoloRepository = postispettacoloRepository;
        this.postoRepository = postoRepository;
        this.postispettacoloMapper = postispettacoloMapper;
        this.spettacoloSenzaFilmMapper = spettacoloSenzaFilmMapper;
        this.filmMapper = filmMapper;
    }

    @Transactional(readOnly = true)
    public List<SpettacoloDto> getAll(){
        ArrayList<SpettacoloDto> r = new ArrayList<>();
        for(Spettacolo s :this.spettacoloRepository.findAll()){
            r.add(spettacoloMapper.toDto(s));
        }
        return r;
    }
    @Transactional(readOnly = true)
    public List<SpettacoloDto> getAllAcquistabili(){
        ArrayList<SpettacoloDto> r = new ArrayList<>();
        for(Spettacolo s :this.spettacoloRepository.findByAcquistabile(true)){
            r.add(spettacoloMapper.toDto(s));
        }
        return r;
    }
    @Transactional(readOnly = true)
    public List<FilmSpettacoliDto> getByDate(LocalDate date) throws Exception {
        try{
            List<Spettacolo> spettacoli = spettacoloRepository.findAllByDataOrderByFilmTitoloAscOraAsc(date);


            List<SpettacoloSenzaFilmDto> spettacoliSenzaFilmDto = spettacoli.stream()
                    .map(spettacoloSenzaFilmMapper::toDto)
                    .collect(Collectors.toList());

            HashMap<FilmDto, ArrayList<SpettacoloSenzaFilmDto>> tempMap = new HashMap<>();


            for(Spettacolo s : spettacoli){
                FilmDto curretFilm = filmMapper.toDto(s.getFilm());
                if(!tempMap.containsKey(curretFilm)) {
                    tempMap.put(filmMapper.toDto(s.getFilm()), new ArrayList<SpettacoloSenzaFilmDto>());
                }
                tempMap.get(curretFilm).add(spettacoloSenzaFilmMapper.toDto(s));
            }
            return tempMap.entrySet().stream()
                    .map(entry -> new FilmSpettacoliDto(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }


    }

    @Transactional
    public Spettacolo aggiungiSpettacolo(NuovoSpettacoloDto spettacolo) throws Exception {
            Spettacolo s;
            if (spettacolo.getId() == null) {
                s = new Spettacolo();

            } else {
                s = spettacoloRepository.findById(spettacolo.getId()).orElseThrow();

                if (!postispettacoloRepository.findBySpettacoloIdAndStato(s.getId(), "p").isEmpty())
                    throw new Exception("impossibile effettuare modifiche. Ci sta gente prenotata");
            }
            s.setAcquistabile(spettacolo.getAcquistabile());
            s.setPrezzo(spettacolo.getPrezzo());

            Film film = filmRepository.findById(spettacolo.getFilm()).orElseThrow();
            Duration durata = Duration.ofMinutes(film.getDurata());
            LocalDateTime inizio = LocalDateTime.of(spettacolo.getData(), spettacolo.getOra());
            LocalDateTime fine = inizio.plus(durata);
            List<Spettacolo> spettacoliProblematici = spettacoloRepository.findConflictingSpettacoli(spettacolo.getSala(), spettacolo.getData(), spettacolo.getOra(), fine.toLocalTime());
            if(!spettacoliProblematici.isEmpty()){
                throw new Exception("La sala è occupata in quell'orario.");
            }
            s.setFilm(film);
            ArrayList<Postispettacolo> psList = new ArrayList<>();
            if ((s.getSala() == null && spettacolo.getSala() != null) ||
                    (spettacolo.getSala() != null && (s.getSala() == null || !spettacolo.getSala().equals(s.getSala().getId())))) {
                Sala sala = salaRepository.findById(spettacolo.getSala()).orElseThrow();
                if (s.getSala() != null){
                    postispettacoloRepository.deleteBySpettacoloId(s.getId());
                }
                s.setSala(sala);
                List<Posto> postiSala = postoRepository.findBySalaId(spettacolo.getSala());

                for (Posto p : postiSala) {
                    Postispettacolo ps = new Postispettacolo();
                    ps.setStato("l");
                    ps.setPosto(p);
                    ps.setSpettacolo(s);
                    psList.add(ps);
                }
            }
            s.setData(LocalDate.now());
            s.setOra(LocalTime.parse("00:00"));


            Spettacolo ret = spettacoloRepository.save(s);
            if (psList.size() > 0)
                postispettacoloRepository.saveAll(psList);
            return ret;


    }





}
