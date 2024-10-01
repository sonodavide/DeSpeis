package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.example.despeis.dto.FilmDto;
import org.example.despeis.dto.NuovoFilmDto;
import org.example.despeis.mapper.FilmMapper;
import org.example.despeis.model.Attore;
import org.example.despeis.model.Film;
import org.example.despeis.model.Genere;
import org.example.despeis.model.Regista;
import org.example.despeis.repository.AttoreRepository;
import org.example.despeis.repository.FilmRepository;
import org.example.despeis.repository.GenereRepository;
import org.example.despeis.repository.RegistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private EntityManager entityManager;
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;
    private final AttoreRepository attoreRepository;
    private final RegistaRepository registaRepository;
    private final GenereRepository genereRepository;
    @Autowired
    public FilmService(FilmRepository filmRepository, FilmMapper filmMapper, AttoreRepository attoreRepository, RegistaRepository registaRepository, GenereRepository genereRepository) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
        this.attoreRepository = attoreRepository;
        this.registaRepository = registaRepository;
        this.genereRepository = genereRepository;
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
    public FilmDto nuovoFilm(NuovoFilmDto nuovoFilm){
        Film film;
        if(nuovoFilm.getId()==null){
            film = new Film();
        } else{
            film = entityManager.find(Film.class, nuovoFilm.getId(), LockModeType.OPTIMISTIC);

        }
        film.setDatauscita(nuovoFilm.getDatauscita());
        film.setTitolo(nuovoFilm.getTitolo());
        film.setDurata(nuovoFilm.getDurata());
        film.setGeneres(genereRepository.findByIds(nuovoFilm.getGenere()));
        film.setAttores(attoreRepository.findByIds(nuovoFilm.getAttore()));
        film.setRegistas(registaRepository.findByIds(nuovoFilm.getRegista()));
        Film f = filmRepository.save(film);
        return filmMapper.toDto(f);


    }
}
