package org.example.despeis.services;

import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.dto.UtenteDto;
import org.example.despeis.dto.UtenteDto;
import org.example.despeis.mapper.UtenteMapper;
import org.example.despeis.model.Utente;
import org.example.despeis.model.Utente;
import org.example.despeis.repository.UtenteRepository;
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

    @Transactional(readOnly = true)
    public PaginatedResponse<UtenteDto> ricerca(String query, Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("cognome"));
        String searchTerm = "%" + query.toLowerCase() + "%";
        Page<Utente> result = utenteRepository.cerca(searchTerm, pageable);

        return new PaginatedResponse<>(
                result.getContent().stream().map(utenteMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements()
        );
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<UtenteDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("cognome"));
        Page<Utente> result = utenteRepository.findAll(pageable);

        return new PaginatedResponse<>(
                result.getContent()
                        .stream().
                        map(utenteMapper::toDto)
                        .collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements()
        );
    }
    @Transactional
    public void delete(UtenteDto utenteDto){
        utenteRepository.delete(utenteMapper.toEntity(utenteDto));
    }
    @Transactional
    public void delete(Integer utenteId){
        utenteRepository.deleteById(utenteId);
    }
    @Transactional
    public UtenteDto nuovo(UtenteDto utenteDto){
        return utenteMapper.toDto(utenteRepository.save(utenteMapper.toEntity(utenteDto)));
    }
}
