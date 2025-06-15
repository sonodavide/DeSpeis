package org.example.despeis.services;

import org.example.despeis.dto.SalaDto;
import org.example.despeis.mapper.SalaMapper;
import org.example.despeis.model.Sala;
import org.example.despeis.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalaService {
    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;

    @Autowired
    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
    }

}
