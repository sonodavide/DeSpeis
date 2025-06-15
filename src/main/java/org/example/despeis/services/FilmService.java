package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.FilmDto;
import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.mapper.AttoreMapper;
import org.example.despeis.mapper.FilmMapper;
import org.example.despeis.mapper.GenereMapper;
import org.example.despeis.mapper.RegistaMapper;
import org.example.despeis.model.*;
import org.example.despeis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @PersistenceContext
    private EntityManager entityManager;
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;
    private final AttoreMapper attoreMapper;
    private final RegistaMapper registaMapper;
    private final GenereMapper genereMapper;
    private final GenerefilmRepository generefilmRepository;
    private final RegiafilmRepository regiafilmRepository;
    private final AttorefilmRepository attorefilmRepository;
    private final SpettacoloRepository spettacoloRepository;

    @Autowired
    public FilmService(FilmRepository filmRepository, FilmMapper filmMapper, AttoreMapper attoreMapper, RegistaMapper registaMapper, GenereMapper genereMapper, GenerefilmRepository generefilmRepository, RegiafilmRepository regiafilmRepository, AttorefilmRepository attorefilmRepository, SpettacoloRepository spettacoloRepository) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
        this.attoreMapper = attoreMapper;
        this.registaMapper = registaMapper;
        this.genereMapper = genereMapper;
        this.generefilmRepository = generefilmRepository;
        this.regiafilmRepository = regiafilmRepository;
        this.attorefilmRepository = attorefilmRepository;
        this.spettacoloRepository = spettacoloRepository;
    }

    @Transactional(readOnly = true)
    public FilmDto getById(int id) throws NoSuchElementException {
        return filmMapper.toDto(filmRepository.findById(id).orElseThrow(() -> new NoSuchElementException()));
    }
    @Transactional(readOnly = true)
    public List<FilmDto> getAll() {
        return filmRepository.findAllByOrderByTitoloDescDatauscitaDesc()
                .stream()
                .map(filmMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public List<FilmDto> getAllByTitolo(String titolo) {
        return filmRepository.findAllByTitoloOrderByTitoloDescDatauscitaDesc(titolo)
                .stream()
                .map(filmMapper::toDto)
                .collect(Collectors.toList());
    }
    @Transactional
    public FilmDto nuovoFilm(FilmDto nuovoFilm) throws Exception {
        List<Spettacolo> spettacoliConStoFilm = new ArrayList<>();
        List<Integer> spettacoliProblematici;
        //creo/modifico il film
        Film film;
        if(nuovoFilm.getId()==null){
            film = new Film();
        } else{
            film = entityManager.find(Film.class, nuovoFilm.getId(), LockModeType.OPTIMISTIC);
            if(film==null) throw new BadRequestException();
            spettacoliConStoFilm = spettacoloRepository.findProssimiSpettacoliByFilm(film);

        }

        film.setDatauscita(nuovoFilm.getDatauscita());
        film.setTitolo(nuovoFilm.getTitolo());
        boolean durataCambiata=false;
        if(!nuovoFilm.getDurata().equals(film.getDurata())){
            if(nuovoFilm.getDurata()<=0) throw new BadRequestException();
            film.setDurata(nuovoFilm.getDurata());
            durataCambiata=true;
        }
        film.setTrama(nuovoFilm.getTrama());
        film.setImg(nuovoFilm.getImg());
        Film f = filmRepository.save(film);
        if(durataCambiata){
            //controllo se si creano accavallamenti di spettacoli
            if(!spettacoliConStoFilm.isEmpty()){
                for(Spettacolo spettacolo : spettacoliConStoFilm){
                    Duration durata = Duration.ofMinutes(nuovoFilm.getDurata());
                    LocalDateTime inizio = LocalDateTime.of(spettacolo.getData(), spettacolo.getOra());
                    LocalDateTime fine = inizio.plus(durata);

                    spettacoliProblematici = spettacoloRepository.findConflictingSpettacoli(spettacolo.getSala().getId(),
                            spettacolo.getData(),
                            spettacolo.getOra(),
                            fine.toLocalDate(),
                            fine.toLocalTime());
                    if(!spettacoliProblematici.isEmpty()){
                        throw new IllegalStateException("Accavallamento");
                    }
                    spettacolo.setDataFine(fine.toLocalDate());
                    spettacolo.setOraFine(fine.toLocalTime().truncatedTo(ChronoUnit.MINUTES));
                }
            }
        }


        //aggiorno i tag
        Set<Genere> generi = nuovoFilm.getGeneres().stream().map(genereMapper::toEntity).collect(Collectors.toSet());
        if(!generi.isEmpty() && !generi.equals(f.getGeneres())){
            generefilmRepository.deleteByFilm(f);
            for(Genere genere : generi){
                Generefilm genereFilm = new Generefilm();
                GenerefilmId genereFilmId = new GenerefilmId();
                genereFilmId.setFilm(f.getId());
                genereFilmId.setGenere(genere.getId());
                genereFilm.setId(genereFilmId);
                genereFilm.setFilm(film);
                generefilmRepository.save(genereFilm);
            }
        }
        Set<Attore> attori = nuovoFilm.getAttores().stream().map(attoreMapper::toEntity).collect(Collectors.toSet());
        if(!attori.isEmpty() && !attori.equals(f.getAttores())){
            attorefilmRepository.deleteByFilm(film);
            for(Attore attore : attori){
                Attorefilm attorefilm = new Attorefilm();
                AttorefilmId attorefilmId = new AttorefilmId();
                attorefilmId.setFilm(film.getId());
                attorefilmId.setAttore(attore.getId());
                attorefilm.setId(attorefilmId);
                attorefilm.setFilm(film);
                attorefilmRepository.save(attorefilm);
            }
        }
        Set<Regista> registi = nuovoFilm.getRegistas().stream().map(registaMapper::toEntity).collect(Collectors.toSet());
        if(!registi.isEmpty() && !registi.equals(f.getRegistas())){
            regiafilmRepository.deleteByFilm(f);
            for(Regista regista : registi){
                Regiafilm regiafilm = new Regiafilm();
                RegiafilmId regiafilmId = new RegiafilmId();
                regiafilmId.setFilm(film.getId());
                regiafilmId.setRegista(regista.getId());
                regiafilm.setId(regiafilmId);
                regiafilm.setFilm(film);
                regiafilmRepository.save(regiafilm);
            }
        }
        return filmMapper.toDto(f);




    }
    @Transactional
    public FilmDto elimina(FilmDto film){
        Film f = entityManager.find(Film.class, film.getId());
        if(!spettacoloRepository.findProssimiSpettacoliByFilm(f).isEmpty()) throw new IllegalStateException();
        entityManager.createQuery("UPDATE Spettacolo s SET s.film = null WHERE s.film = :film").setParameter("film", film).executeUpdate();
        filmRepository.deleteById(film.getId());
        return film;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<FilmDto> ricerca(String query,Integer pageNumber, Integer pageSize ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("titolo"));
        String searchTerm = query.toLowerCase() + "%";
        Page<Film> result = filmRepository.cerca(searchTerm, pageable);

        return new PaginatedResponse<>(result.getContent().stream().map(filmMapper::toDto).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<FilmDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("titolo"));
        Page<Film> result = filmRepository.findAll(pageable);

        return new PaginatedResponse<>(result.getContent().stream().map(filmMapper::toDto).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Long count(){
        return filmRepository.count();
    }
    @Transactional(readOnly = true)
    public PaginatedResponse<FilmDto> cercaTag(String tag, Integer id, Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("titolo"));
        Page<Film> result;
        switch (tag){
            case "genere":
                result = filmRepository.findAllByGeneresId(id, pageable);
                break;
            case "attore":
                result = filmRepository.findAllByAttoresId(id, pageable);
                break;
            case "regista":
                result = filmRepository.findAllByRegistasId(id, pageable);
                break;
            default:
                throw new NoSuchElementException();
        }

        return new PaginatedResponse<>(result.getContent().stream().map(filmMapper::toDto).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }



    @Transactional(readOnly = true)
    public List<FilmDto> ultimeUscite() {
        return filmRepository.ultimeUscite().stream().map(filmMapper::toDto).collect(Collectors.toList());
    }
}
