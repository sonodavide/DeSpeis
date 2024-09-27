package org.example.despeis.services;

import org.example.despeis.dto.SpettacoloDto;
import org.example.despeis.mapper.SpettacoloMapper;
import org.example.despeis.model.Spettacolo;
import org.example.despeis.repository.SpettacoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpettacoloService {

    private final SpettacoloRepository spettacoloRepository;
    private final SpettacoloMapper spettacoloMapper;
    @Autowired
    public SpettacoloService(SpettacoloRepository spettacoloRepository, SpettacoloMapper spettacoloMapper) {
        this.spettacoloRepository = spettacoloRepository;
        this.spettacoloMapper = spettacoloMapper;
    }



    @Transactional(readOnly = true)
    public List<SpettacoloDto> getAllSpettacoli(){
        ArrayList<SpettacoloDto> r = new ArrayList<>();
        for(Spettacolo s :this.spettacoloRepository.findAll()){
            r.add(spettacoloMapper.toDto(s));
        }
        return r;
    }
    @Transactional(readOnly = true)
    public List<SpettacoloDto> getSpettacoliByDate(LocalDate date){
        List<Spettacolo> spettacoli = spettacoloRepository.findAllByDataOrderByFilmTitoloAscOraAsc(date);
        return spettacoli.stream()
                .map(spettacoloMapper::toDto)
                .collect(Collectors.toList());
    }


}
