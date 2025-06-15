package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
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
    private final SpettacoloSenzaFilmTagsMapper spettacoloSenzaFilmTagsMapper;
    private final FilmMapper filmMapper;
    private final NuovoSpettacoloMapper nuovoSpettacoloMapper;
    private final PostiMapper postiMapper;
    private final SalaMapper salaMapper;


    @Autowired
    public SpettacoloService(SpettacoloRepository spettacoloRepository, SpettacoloMapper spettacoloMapper, FilmRepository filmRepository, SalaRepository salaRepository, PostispettacoloRepository postispettacoloRepository, PostispettacoloMapper postispettacoloMapper, SpettacoloSenzaFilmTagsMapper spettacoloSenzaFilmTagsMapper, FilmMapper filmMapper, NuovoSpettacoloMapper nuovoSpettacoloMapper, PostiMapper postiMapper, SalaMapper salaMapper, PostiRepository postiRepository) {
        this.spettacoloRepository = spettacoloRepository;
        this.spettacoloMapper = spettacoloMapper;
        this.filmRepository = filmRepository;
        this.salaRepository = salaRepository;
        this.postispettacoloRepository = postispettacoloRepository;
        this.postiRepository = postiRepository;
        this.spettacoloSenzaFilmTagsMapper = spettacoloSenzaFilmTagsMapper;
        this.filmMapper = filmMapper;
        this.nuovoSpettacoloMapper =  nuovoSpettacoloMapper;
        this.postiMapper = postiMapper;
        this.salaMapper = salaMapper;
    }




    @Transactional(readOnly = true)
    public List<FilmSpettacoliDto> getFilmSpettacoloAcquistabileByDate(LocalDate date) throws Exception {
        try{
            List<Spettacolo> spettacoli = spettacoloRepository.findAllByDataAndAcquistabileOrderByFilmTitoloAscOraAsc(date, true);


            List<SpettacoloSenzaFilmTagsDto> spettacoliSenzaFilmDto = spettacoli.stream()
                    .map(spettacoloSenzaFilmTagsMapper::toDto)
                    .collect(Collectors.toList());

            HashMap<FilmDto, ArrayList<SpettacoloSenzaFilmTagsDto>> tempMap = new HashMap<>();


            for(Spettacolo s : spettacoli){
                FilmDto curretFilm = filmMapper.toDto(s.getFilm());
                if(!tempMap.containsKey(curretFilm)) {
                    tempMap.put(filmMapper.toDto(s.getFilm()), new ArrayList<SpettacoloSenzaFilmTagsDto>());
                }
                tempMap.get(curretFilm).add(spettacoloSenzaFilmTagsMapper.toDto(s));
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
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("data", "ora").descending());
        Page<Spettacolo> result = spettacoloRepository.findAllByDataOrderByFilmTitoloAscOraAsc(date, pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(nuovoSpettacoloMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());

    }
    @Transactional(readOnly = true)
    public PaginatedResponse<NuovoSpettacoloDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("data", "ora").descending());
        Page<Spettacolo> result = spettacoloRepository.findAll(pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(nuovoSpettacoloMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());

    }


    @Transactional
    public SpettacoloDto aggiungiSpettacolo(NuovoSpettacoloDto nuovoSpettacolo) throws Exception {
        List<Postispettacolo> postiEsistenti;
        List<Integer> spettacoliProblematici;
        List<Posti> posti;
        boolean iniziato = false;
        nuovoSpettacolo.setOra(nuovoSpettacolo.getOra().truncatedTo(ChronoUnit.MINUTES));

        if(nuovoSpettacolo.getPrezzo().compareTo(BigDecimal.ZERO) < 0) throw new BadRequestException();
        Spettacolo s;


            if (nuovoSpettacolo.getId() == null) {
                s = new Spettacolo();
                if(LocalDateTime.of(nuovoSpettacolo.getData(), nuovoSpettacolo.getOra()).isBefore(LocalDateTime.now())) throw new BadRequestException();
            } else {
                s = entityManager.find(Spettacolo.class, nuovoSpettacolo.getId(), LockModeType.OPTIMISTIC);
                if(s==null) throw new BadRequestException();
                //se è finito, impedisco la modifica
                if(LocalDateTime.of(s.getDataFine(), s.getOraFine()).isBefore(LocalDateTime.now())) throw new BadRequestException();
                iniziato = LocalDateTime.now().isAfter(LocalDateTime.of(s.getData(), s.getOra())) && LocalDateTime.now().isBefore(LocalDateTime.of(s.getDataFine(), s.getOraFine()));



            }
            s.setAcquistabile(nuovoSpettacolo.getAcquistabile());
            s.setPrezzo(nuovoSpettacolo.getPrezzo());


                
            Film nuovoFilm = entityManager.find(Film.class, nuovoSpettacolo.getFilm().getId(), LockModeType.OPTIMISTIC);
            if(nuovoFilm==null) throw new BadRequestException();
            if(s.getFilm()==null || !s.getFilm().equals(nuovoFilm)){
                //se è in corso, impedisco la modifica del film.
                if(iniziato) throw new BadRequestException();


                Duration durata = Duration.ofMinutes(nuovoFilm.getDurata());
                LocalDateTime inizio = LocalDateTime.of(nuovoSpettacolo.getData(), nuovoSpettacolo.getOra());
                LocalDateTime fine = inizio.plus(durata);

                spettacoliProblematici = spettacoloRepository.findConflictingSpettacoli(
                        nuovoSpettacolo.getSala().getId(),
                        nuovoSpettacolo.getData(),
                        nuovoSpettacolo.getOra(),
                        fine.toLocalDate(),
                        fine.toLocalTime(),
                        s.getId());
                if(!spettacoliProblematici.isEmpty()){
                    System.out.println("sala occupata a quell'ora");
                    throw new IllegalStateException("La sala è occupata in quell'orario.");
                }
                s.setData(nuovoSpettacolo.getData());
                s.setOra(nuovoSpettacolo.getOra().truncatedTo(ChronoUnit.MINUTES));
                s.setDataFine(fine.toLocalDate());
                s.setOraFine(fine.toLocalTime());
                s.setFilm(nuovoFilm);
            }


        List<Postispettacolo> psList = new ArrayList<>();

        Sala nuovaSala = entityManager.find(Sala.class, nuovoSpettacolo.getSala().getId(), LockModeType.OPTIMISTIC);
        if(nuovaSala==null) throw new BadRequestException();
        if( s.getSala() == null || !s.getSala().equals(nuovaSala)) {
            if(s.getId()!=null){
                //se è in corso, impedisco la modifica del film.
                if(iniziato) throw new BadRequestException();
                //impedisco la modifica della sala nel caso in cui ci sono già posti prenotati
                postiEsistenti = postispettacoloRepository.findBySpettacoloId(s.getId());
                for(Postispettacolo p : postiEsistenti)
                    if(p.getStato().equals("prenotato")) throw new IllegalStateException("impossibile effettuare modifiche. Ci sta gente prenotata");
                postispettacoloRepository.deleteBySpettacoloId(s.getId());
            }
            s.setSala(nuovaSala);
            posti = postiRepository.findAllBySalaOrderByFilaAsc(nuovaSala);
            if(posti.isEmpty()) throw new BadRequestException();
            for (Posti p : posti) {
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






            Spettacolo ret = spettacoloRepository.save(s);
            if (!psList.isEmpty())
                postispettacoloRepository.saveAll(psList);
            return spettacoloMapper.toDto(ret);


    }

    @Transactional
    public SpettacoloDto elimina(SpettacoloDto spettacolo) throws Exception {
        spettacoloRepository.deleteById(spettacolo.getId());
        if(entityManager.find(Spettacolo.class, spettacolo.getId())!=null){
            throw new Exception();
        }
        return spettacolo;
    }




    @Transactional(readOnly = true)
    public Long count(){
        return spettacoloRepository.count();
    }
    @Transactional(readOnly = true)
    public SpettacoloDto getById(Integer id){
        return spettacoloMapper.toDto(spettacoloRepository.findById(id).orElseThrow(() -> new NoSuchElementException()));
    }

    @Transactional(readOnly = true)
    public SpettacoloDto getByIdAcquistabile(Integer id){
        Spettacolo spettacolo = spettacoloRepository.findSpettacoloAcquistabileById(id);
        if(spettacolo==null) throw new NoSuchElementException();
        return spettacoloMapper.toDto(spettacolo);

    }
    @Transactional(readOnly = true)
    public SpettacoloSenzaFilmTagsDto getSenzaFilmAcquistabileById(int id) throws Exception {
        Spettacolo spettacolo = spettacoloRepository.findSpettacoloAcquistabileById(id);
        if(spettacolo==null) throw new Exception();
        SpettacoloSenzaFilmTagsDto a = spettacoloSenzaFilmTagsMapper.toDto(spettacolo);
        return a;
    }

    @Transactional(readOnly = true)
    public SpettacoloSenzaFilmTagsDto getSenzaFilmById(int id) throws Exception {
        Spettacolo spettacolo = spettacoloRepository.findById(id).orElseThrow(() -> new Exception());
        if(spettacolo==null) throw new Exception();
        return spettacoloSenzaFilmTagsMapper.toDto(spettacolo);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SpettacoloSenzaFilmTagsDto> getAllSenzaFilmTags(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("data", "ora").descending());
        Page<Spettacolo> result = spettacoloRepository.findAll(pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(spettacoloSenzaFilmTagsMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SpettacoloSenzaFilmTagsDto> cercaSenzaFilmTags(LocalDate date, Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("data", "ora").descending());
        Page<Spettacolo> result = spettacoloRepository.findAllByDataOrderByFilmTitoloAscOraAsc(date, pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(spettacoloSenzaFilmTagsMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());

    }

    @Transactional(readOnly = true)
    public NuovoSpettacoloDto getNuovoSpettacoloById(int id) throws Exception {
        return nuovoSpettacoloMapper.toDto(spettacoloRepository.findById(id).orElseThrow(() -> new Exception()));
    }

    @Transactional(readOnly = true)
    public String getStato(int id) throws Exception {
        Spettacolo s = spettacoloRepository.findById(id).orElseThrow(() -> new Exception());
        LocalDateTime fine = LocalDateTime.of(s.getDataFine(), s.getOraFine());
        LocalDateTime inizio = LocalDateTime.of(s.getData(), s.getOra());
    if(LocalDateTime.now().isAfter(fine)) return "finito";
        if(LocalDateTime.now().isAfter(inizio)) return  "in corso";
        return "non iniziato";

    }
}
