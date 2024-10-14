package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.example.despeis.dto.FilmDto;
import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.mapper.AttoreMapper;
import org.example.despeis.mapper.FilmMapper;
import org.example.despeis.mapper.GenereMapper;
import org.example.despeis.mapper.RegistaMapper;
import org.example.despeis.model.*;
import org.example.despeis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    @Autowired
    public FilmService(FilmRepository filmRepository, FilmMapper filmMapper, AttoreMapper attoreMapper, RegistaMapper registaMapper, GenereMapper genereMapper, GenerefilmRepository generefilmRepository, RegiafilmRepository regiafilmRepository, AttorefilmRepository attorefilmRepository) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
        this.attoreMapper = attoreMapper;
        this.registaMapper = registaMapper;
        this.genereMapper = genereMapper;
        this.generefilmRepository = generefilmRepository;
        this.regiafilmRepository = regiafilmRepository;
        this.attorefilmRepository = attorefilmRepository;
    }

    @Transactional(readOnly = true)
    public FilmDto getById(int id) {
        return filmMapper.toDto(filmRepository.findById(id).orElseThrow());
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
    public FilmDto nuovoFilm(FilmDto nuovoFilm){
        try{
            Film film;
            if(nuovoFilm.getId()==null){
                film = new Film();
            } else{
                film = entityManager.find(Film.class, nuovoFilm.getId(), LockModeType.PESSIMISTIC_WRITE);

            }
            film.setDatauscita(nuovoFilm.getDatauscita());
            film.setTitolo(nuovoFilm.getTitolo());
            film.setDurata(nuovoFilm.getDurata());
            film.setTrama(nuovoFilm.getTrama());
            film.setImg(nuovoFilm.getImg());
            Film f = filmRepository.save(film);

            Set<Genere> generi = nuovoFilm.getGeneres().stream().map(genereMapper::toEntity).collect(Collectors.toSet());
            for(Genere genere : generi){
                Generefilm genereFilm = new Generefilm();
                GenerefilmId genereFilmId = new GenerefilmId();
                genereFilmId.setFilm(f.getId());
                genereFilmId.setGenere(genere.getId());
                genereFilm.setId(genereFilmId);
                genereFilm.setFilm(film);
                generefilmRepository.save(genereFilm);
            }
            Set<Attore> attori = nuovoFilm.getAttores().stream().map(attoreMapper::toEntity).collect(Collectors.toSet());
            for(Attore attore : attori){
                Attorefilm attorefilm = new Attorefilm();
                AttorefilmId attorefilmId = new AttorefilmId();
                attorefilmId.setFilm(film.getId());
                attorefilmId.setAttore(attore.getId());
                attorefilm.setId(attorefilmId);
                attorefilm.setFilm(film);
                attorefilmRepository.save(attorefilm);
            }
            Set<Regista> registi = nuovoFilm.getRegistas().stream().map(registaMapper::toEntity).collect(Collectors.toSet());
            for(Regista regista : registi){
                Regiafilm regiafilm = new Regiafilm();
                RegiafilmId regiafilmId = new RegiafilmId();
                regiafilmId.setFilm(film.getId());
                regiafilmId.setRegista(regista.getId());
                regiafilm.setId(regiafilmId);
                regiafilm.setFilm(film);
                regiafilmRepository.save(regiafilm);
            }
            return filmMapper.toDto(f);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }
    @Transactional
    public FilmDto elimina(FilmDto film){
        filmRepository.delete(filmMapper.toEntity(film));
        return film;
    }

    @Transactional(readOnly = true)
    public List<FilmDto> ricerca(String query,Integer pageNumber, Integer pageSize ){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("titolo"));
        String searchTerm = query.toLowerCase() + "%";
        Page<Film> result = filmRepository.cerca(searchTerm, pageable);

        return result.getContent().stream().map(filmMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<FilmDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("titolo"));
        Page<Film> result = filmRepository.findAll(pageable);

        return new PaginatedResponse<>(result.getContent().stream().map(filmMapper::toDto).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

}
