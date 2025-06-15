package org.example.despeis.services;

import org.example.despeis.dto.AttoreDto;
import org.example.despeis.dto.RegistaDto;
import org.example.despeis.mapper.RegistaMapper;
import org.example.despeis.model.Attore;
import org.example.despeis.model.Regista;
import org.example.despeis.repository.RegistaRepository;
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
public class RegistaService {

    private final RegistaRepository registaRepository;
    private final RegistaMapper registaMapper;
    @Autowired
    public RegistaService(RegistaRepository registaRepository, RegistaMapper registaMapper) {
        this.registaMapper=registaMapper;
        this.registaRepository=registaRepository;
    }
    @Transactional
    public void delete(RegistaDto registaDto){
        registaRepository.delete(registaMapper.toEntity(registaDto));
    }
    @Transactional
    public void delete(Integer registaId){
        registaRepository.deleteById(registaId);
    }
    @Transactional
    public RegistaDto nuovo(RegistaDto registaDto){
        return registaMapper.toDto(registaRepository.save(registaMapper.toEntity(registaDto)));
    }
    @Transactional(readOnly = true)
    public List<RegistaDto> ricerca(String query){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cognome"));
        String searchTerm = "%" + query.toLowerCase() + "%";
        Page<Regista> result = registaRepository.cerca(searchTerm, pageable);

        return result.getContent().stream().map(registaMapper::toDto).collect(Collectors.toList());
    }
}
