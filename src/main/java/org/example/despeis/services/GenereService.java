package org.example.despeis.services;

import org.example.despeis.dto.AttoreDto;
import org.example.despeis.dto.GenereDto;
import org.example.despeis.mapper.GenereMapper;
import org.example.despeis.model.Attore;
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
import java.util.stream.Collectors;

@Service
public class GenereService {

    private final GenereRepository genereRepository;
    private final GenereMapper genereMapper;
    @Autowired
    public GenereService(GenereRepository genereRepository, GenereMapper genereMapper) {
        this.genereMapper=genereMapper;
        this.genereRepository=genereRepository;
    }
    @Transactional
    public void delete(GenereDto genereDto){
        genereRepository.delete(genereMapper.toEntity(genereDto));
    }
    @Transactional
    public void delete(Integer genereId){
        genereRepository.deleteById(genereId);
    }
    @Transactional
    public GenereDto nuovo(GenereDto genereDto){
        return genereMapper.toDto(genereRepository.save(genereMapper.toEntity(genereDto)));
    }

    @Transactional(readOnly = true)
    public List<GenereDto> ricerca(String query){
        try{
        Pageable pageable = PageRequest.of(0, 10, Sort.by("genere"));
        String searchTerm = "%" + query.toLowerCase() + "%";
        Page<Genere> result = genereRepository.cerca(searchTerm, pageable);

        return result.getContent().stream().map(genereMapper::toDto).collect(Collectors.toList());

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
