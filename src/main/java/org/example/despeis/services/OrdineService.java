package org.example.despeis.services;

import org.example.despeis.dto.OrdineDto;
import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.mapper.OrdineMapper;
import org.example.despeis.model.Ordine;
import org.example.despeis.repository.OrdineRepository;
import org.example.despeis.security.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public PaginatedResponse<OrdineDto> getAllPaginated(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("data").descending());
        Page<Ordine> result = ordineRepository.findAll(pageable);
        return new PaginatedResponse<OrdineDto>(result.getContent().stream()
                .map(ordineMapper::toDto)
                .collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<OrdineDto> getAllByUserPaginated(String userId, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("data").descending());
        Page<Ordine> result = ordineRepository.findAllByUtenteId(userId, pageable);
        return new PaginatedResponse<OrdineDto>(result.getContent().stream()
                .map(ordineMapper::toDto)
                .collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<OrdineDto> getAllByUserPaginated(JwtAuthenticationToken authenticationToken, int pageNumber, int pageSize){
        return getAllByUserPaginated(Utils.getUserId(authenticationToken), pageNumber, pageSize);
    }

    @Transactional(readOnly = true)
    public Long count(){
        return ordineRepository.count();
    }

    @Transactional(readOnly = true)
    public Double incassiTotali(){
        return ordineRepository.sumAllTotale();
    }
}
