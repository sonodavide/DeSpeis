package org.example.despeis.services;

import org.example.despeis.dto.OrdineDto;
import org.example.despeis.mapper.OrdineMapper;
import org.example.despeis.repository.OrdineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdineService {
    private final OrdineRepository ordineRepository;
    private final OrdineMapper ordineMapper;
    @Autowired
    public OrdineService(OrdineRepository ordineRepository, OrdineMapper ordineMapper) {
        this.ordineRepository = ordineRepository;
        this.ordineMapper = ordineMapper;
    }



    @Transactional(readOnly = true)
    public List<OrdineDto> getAll(){
        return ordineRepository.findAllByOrderByDataDesc()
                .stream().map(ordineMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrdineDto> getById(int utenteId){
        return ordineRepository.findAllByUtenteIdOrderByDataDesc(utenteId)
                .stream()
                .map(ordineMapper::toDto)
                .collect(Collectors.toList());
    }
}
