package org.example.despeis.services;

import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.dto.UtenteDto;
import org.example.despeis.dto.UtenteDto;
import org.example.despeis.mapper.UtenteMapper;
import org.example.despeis.model.Utente;
import org.example.despeis.model.Utente;
import org.example.despeis.repository.UtenteRepository;
import org.example.despeis.security.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
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
    public UtenteDto getById(String id) throws NoSuchElementException {
        UtenteDto utenteDto = utenteMapper.toDto(utenteRepository.findById(id).orElseThrow(() -> new NoSuchElementException()));
        return utenteDto;
    }

    @Transactional(readOnly = true)
    public UtenteDto getById(JwtAuthenticationToken authenticationToken){
        return getById(Utils.getUserId(authenticationToken));
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
    public void delete(String utenteId){
        utenteRepository.deleteById(utenteId);
    }
    @Transactional
    public UtenteDto nuovo(UtenteDto utenteDto){
        return utenteMapper.toDto(utenteRepository.save(utenteMapper.toEntity(utenteDto)));
    }
    @Transactional(readOnly = true)
    public Long count(){
        return utenteRepository.count();
    }

    @Transactional
    public void update(JwtAuthenticationToken authenticationToken){
        Utente utente = new Utente();
        utente.setId(Utils.getUserId(authenticationToken));
        utente.setUsername(Utils.getUsername(authenticationToken));
        utente.setFirstname(Utils.getFirstName(authenticationToken));
        utente.setEmail(Utils.getEmail(authenticationToken));
        utente.setLastname(Utils.getLastName(authenticationToken));
        utenteRepository.save(utente);
    }
}
