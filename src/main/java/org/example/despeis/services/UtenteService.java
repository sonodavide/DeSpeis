package org.example.despeis.services;

import org.example.despeis.dto.UtenteDto;
import org.example.despeis.mapper.UtenteMapper;
import org.example.despeis.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteService {
    private final UtenteRepository utenteRepository;
    private final UtenteMapper utenteMapper;

    @Autowired
    public UtenteService(UtenteRepository utenteRepository, UtenteMapper utenteMapper) {
        this.utenteRepository = utenteRepository;
        this.utenteMapper = utenteMapper;
    }

    @Transactional(readOnly = true)
    public UtenteDto getById(Integer id) {
        return utenteMapper.toDto(utenteRepository.findById(id).orElseThrow());
    }

    @Transactional(readOnly = true)
    public List<UtenteDto> getAll(){
        return utenteRepository.findAll().stream()
                .map(utenteMapper::toDto).collect(Collectors.toList());
    }
}
