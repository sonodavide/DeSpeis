package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.AttoreDto;
import org.example.despeis.dto.GenereDto;
import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.mapper.GenereMapper;
import org.example.despeis.model.Attore;
import org.example.despeis.model.Film;
import org.example.despeis.model.Genere;
import org.example.despeis.repository.GenereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class GenereService {
    @PersistenceContext
    EntityManager entityManager;
    private final GenereRepository genereRepository;
    private final GenereMapper genereMapper;
    @Autowired
    public GenereService(GenereRepository genereRepository, GenereMapper genereMapper) {
        this.genereMapper=genereMapper;
        this.genereRepository=genereRepository;
    }
    @Transactional
    public void delete(GenereDto genereDto){
        genereRepository.deleteById(genereDto.getId());


    }
    @Transactional
    public void delete(Integer genereId) throws Exception {

        genereRepository.deleteById(genereId);
        if(entityManager.find(Genere.class, genereId)!=null){
            throw new Exception();
        }
    }
    @Transactional
    public GenereDto nuovo(GenereDto genereDto) throws BadRequestException {
        if(genereDto.getGenere().trim().equals("")) throw new BadRequestException();
        return genereMapper.toDto(genereRepository.save(genereMapper.toEntity(genereDto)));
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<GenereDto> ricerca(String query, Integer pageNumber, Integer pageSize){
        try{
        Pageable pageable = PageRequest.of(0, 10, Sort.by("genere"));
        String searchTerm = "%" + query.toLowerCase() + "%";
        Page<Genere> result = genereRepository.cerca(searchTerm, pageable);

        return new PaginatedResponse<>(
                result.getContent().stream().map(genereMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(),
                result.getTotalElements()

        );

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<GenereDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("genere"));
        Page<Genere> result = genereRepository.findAll(pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(genereMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());

    }

    @Transactional(readOnly = true)
    public Long count(){
        return genereRepository.count();
    }

    @Transactional(readOnly = true)
    public String getNomeById(Integer id){
        Genere genere = genereRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return genere.getGenere();
    }
}
