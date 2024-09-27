package org.example.despeis.services;

import org.example.despeis.dto.FilmDto;
import org.example.despeis.mapper.FilmMapper;
import org.example.despeis.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;
    @Autowired
    public FilmService(FilmRepository filmRepository, FilmMapper filmMapper) {
        this.filmRepository = filmRepository;
        this.filmMapper = filmMapper;
    }
    @Transactional(readOnly = true)
    public FilmDto getById(int id) {
        return filmMapper.toDto(filmRepository.findById(id));
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
}
