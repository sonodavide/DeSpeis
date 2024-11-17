package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.despeis.dto.*;
import org.example.despeis.mapper.*;
import org.example.despeis.model.*;
import org.example.despeis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final PostiRepository postiRepository;
    private final SpettacoloSenzaFilmMapper spettacoloSenzaFilmMapper;
    private final FilmMapper filmMapper;
    private final NuovoSpettacoloMapper nuovoSpettacoloMapper;
    private final PostiMapper postiMapper;
    private final SalaMapper salaMapper;


    @Autowired
    public SpettacoloService(SpettacoloRepository spettacoloRepository, SpettacoloMapper spettacoloMapper, FilmRepository filmRepository, SalaRepository salaRepository, PostispettacoloRepository postispettacoloRepository, PostispettacoloMapper postispettacoloMapper, SpettacoloSenzaFilmMapper spettacoloSenzaFilmMapper, FilmMapper filmMapper, NuovoSpettacoloMapper nuovoSpettacoloMapper, PostiMapper postiMapper, SalaMapper salaMapper, PostiRepository postiRepository) {
        this.spettacoloRepository = spettacoloRepository;
        this.spettacoloMapper = spettacoloMapper;
        this.filmRepository = filmRepository;
        this.salaRepository = salaRepository;
        this.postispettacoloRepository = postispettacoloRepository;
        this.postiRepository = postiRepository;
        this.spettacoloSenzaFilmMapper = spettacoloSenzaFilmMapper;
        this.filmMapper = filmMapper;
        this.nuovoSpettacoloMapper =  nuovoSpettacoloMapper;
        this.postiMapper = postiMapper;
        this.salaMapper = salaMapper;
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
    public List<FilmSpettacoliDto> getFilmSpettacoloByDate(LocalDate date) throws Exception {
        try{
            List<Spettacolo> spettacoli = spettacoloRepository.findAllByDataAndAcquistabileOrderByFilmTitoloAscOraAsc(date, true);


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

    @Transactional(readOnly = true)
    public PaginatedResponse<NuovoSpettacoloDto> cerca(LocalDate date, Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("data", "ora"));
        Page<Spettacolo> result = spettacoloRepository.findAllByDataOrderByFilmTitoloAscOraAsc(date, pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(nuovoSpettacoloMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());

    }
    @Transactional(readOnly = true)
    public PaginatedResponse<NuovoSpettacoloDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("data", "ora"));
        Page<Spettacolo> result = spettacoloRepository.findAll(pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(nuovoSpettacoloMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());

    }


    @Transactional
    public Spettacolo aggiungiSpettacolo(NuovoSpettacoloDto nuovoSpettacolo) throws Exception {
            Spettacolo s;
            if (nuovoSpettacolo.getId() == null) {
                s = new Spettacolo();

            } else {
                s = spettacoloRepository.findById(nuovoSpettacolo.getId()).orElseThrow();

                if (!postispettacoloRepository.findBySpettacoloIdAndStato(s.getId(), "prenotato").isEmpty())
                    throw new Exception("impossibile effettuare modifiche. Ci sta gente prenotata");
            }
            s.setAcquistabile(nuovoSpettacolo.getAcquistabile());
            s.setPrezzo(nuovoSpettacolo.getPrezzo());

            if(nuovoSpettacolo.getFilm()!=null){
                Film nuovoFilm = filmMapper.toEntity(nuovoSpettacolo.getFilm());
                if(s.getFilm()==null || !s.getFilm().equals(nuovoFilm)){
                    Duration durata = Duration.ofMinutes(nuovoFilm.getDurata());
                    LocalDateTime inizio = LocalDateTime.of(nuovoSpettacolo.getData(), nuovoSpettacolo.getOra());
                    LocalDateTime fine = inizio.plus(durata);
                    List<Integer> spettacoliProblematici = spettacoloRepository.findConflictingSpettacoli(nuovoSpettacolo.getSala().getId(),
                            nuovoSpettacolo.getData(),
                            nuovoSpettacolo.getOra(),
                            fine.toLocalDate(),
                            fine.toLocalTime());
                    if(!spettacoliProblematici.isEmpty()){
                        System.out.println("sala occupata a quell'ora");
                        throw new Exception("La sala è occupata in quell'orario.");
                    }
                    s.setFilm(nuovoFilm);
                }
            }

        List<Postispettacolo> psList = new ArrayList<>();
            if(nuovoSpettacolo.getSala()!=null){
                Sala nuovaSala = salaMapper.toEntity(nuovoSpettacolo.getSala()); //devo fare un check se riesce a castare ad entity o mi da null

                if( s.getSala() == null || !s.getSala().equals(nuovaSala)) {
                    if(s.getId()!=null){
                        postispettacoloRepository.deleteBySpettacoloId(s.getId());
                    }
                    s.setSala(nuovaSala);

                    for (Posti p : postiRepository.findAllBySala(nuovaSala)) {
                        for(int i=1;i<=p.getSedili();i++){
                            Postispettacolo ps = new Postispettacolo();
                            ps.setStato("libero");
                            ps.setSedile(i);
                            ps.setFila(p.getFila());
                            ps.setSpettacolo(s);
                            psList.add(ps);

                        }
                    }

                }
            }



            s.setData(nuovoSpettacolo.getData());
            s.setOra(nuovoSpettacolo.getOra());


            Spettacolo ret = spettacoloRepository.save(s);
            if (!psList.isEmpty())
                postispettacoloRepository.saveAll(psList);
            return ret;


    }

    @Transactional
    public SpettacoloDto elimina(SpettacoloDto spettacolo){
        spettacoloRepository.delete(spettacoloMapper.toEntity(spettacolo));
        return spettacolo;
    }


    @Transactional(readOnly = true)
    public PostiSpettacoloResponseDto getPostiSpettacoloBySpettacoloId(int spettacoloId){
        List<Postispettacolo> postiSpettacolo = postispettacoloRepository.findAllBySpettacoloIdOrderByFilaAscSedileAsc(spettacoloId);
        HashMap<String, List<PostiSpettacoloResponseDto.PostoResponse>> tempMap = new HashMap<>();
        for(Postispettacolo posto : postiSpettacolo){
            String filaCorrente = posto.getFila();
            if(!tempMap.containsKey(filaCorrente)){
                tempMap.put(filaCorrente, new ArrayList<>());
            }
            tempMap.get(filaCorrente).add(
                    new PostiSpettacoloResponseDto.PostoResponse(
                            posto.getId(), posto.getSedile(), posto.getStato()
                    ));
        }
        return new PostiSpettacoloResponseDto(spettacoloId, tempMap);
    }

    @Transactional(readOnly = true)
    public Long count(){
        return spettacoloRepository.count();
    }
}
