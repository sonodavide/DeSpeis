package org.example.despeis.services;

import org.example.despeis.dto.*;
import org.example.despeis.dto.SalaDto;
import org.example.despeis.mapper.PostoMapper;
import org.example.despeis.mapper.SalaMapper;
import org.example.despeis.model.Posto;
import org.example.despeis.model.Sala;
import org.example.despeis.repository.PostoRepository;
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
    private final PostoRepository postoRepository;
    private final PostoMapper postoMapper;
    @Autowired
    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper, PostoRepository postoRepository, PostoMapper postoMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
        this.postoRepository = postoRepository;
        this.postoMapper = postoMapper;
    }

    @Transactional
    public void delete(SalaDto salaDto){
        salaRepository.delete(salaMapper.toEntity(salaDto));
    }
    @Transactional
    public void delete(Integer salaId){
        salaRepository.deleteById(salaId);
    }
    @Transactional
    public SalaDto nuovo(SalaDto salaDto){
        try{
            Sala sala = salaRepository.save(new Sala());

            for(PostoDto postoDto : salaDto.getPostos()){
                Posto posto = postoMapper.toEntity(postoDto);
                posto.setSala(sala);
                postoRepository.save(posto);
            }
            return salaMapper.toDto(sala);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
