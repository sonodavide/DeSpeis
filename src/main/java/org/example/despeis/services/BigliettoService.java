package org.example.despeis.services;

import org.example.despeis.dto.BigliettoDto;
import org.example.despeis.dto.OrdineDto;
import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.mapper.BigliettoMapper;
import org.example.despeis.model.Biglietto;
import org.example.despeis.model.Ordine;
import org.example.despeis.repository.BigliettoRepository;
import org.example.despeis.security.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BigliettoService {
    private final BigliettoRepository bigliettoRepository;
    private final BigliettoMapper bigliettoMapper;

    @Autowired
    public BigliettoService(BigliettoRepository bigliettoRepository, BigliettoMapper bigliettoMapper) {
        this.bigliettoRepository = bigliettoRepository;
        this.bigliettoMapper = bigliettoMapper;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getAll(int pageNumber, int pageSize){

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        Page<Biglietto> result = bigliettoRepository.findAll(pageable);
        return new PaginatedResponse<BigliettoDto>(result.getContent().stream()
                .map(bigliettoMapper::toDto).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getByUser(JwtAuthenticationToken authenticationToken, int pageNumber, int pageSize){
        String userId = Utils.getUserId(authenticationToken);
        return getByUser(userId, pageNumber, pageSize);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getByUser(String userId, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        Page<Biglietto> result = bigliettoRepository.findByUtenteId(userId, pageable);
        return new PaginatedResponse<BigliettoDto>(result.getContent().stream()
                .map(bigliettoMapper::toDto).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getByUserAndDate(JwtAuthenticationToken authenticationToken, LocalDate date, int pageNumber, int pageSize){
        String userId = Utils.getUserId(authenticationToken);
        return getByUserAndDate(userId, date, pageNumber, pageSize);
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getByDate(LocalDate date, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("ordine.data").descending());
        Page<Biglietto> result = bigliettoRepository.findByPostospettacoloSpettacoloData(date, pageable);
        return new PaginatedResponse<BigliettoDto>(result.getContent().stream()
                .map(bigliettoMapper::toDto)
                .collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }


    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getAllByUserId(String userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("ordine.data").descending());
        Page<Biglietto> result = bigliettoRepository.findAllByUtenteId(userId, pageable);
        return new PaginatedResponse<BigliettoDto>(result.getContent().stream()
                .map(bigliettoMapper::toDto)
                .collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getByUserAndDate(String userId, LocalDate date, int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("ordine.data").descending());
        Page<Biglietto> result = bigliettoRepository.findByUtenteIdAndPostospettacoloSpettacoloData(userId, date, pageable);
        return new PaginatedResponse<BigliettoDto>(result.getContent().stream()
                .map(bigliettoMapper::toDto)
                .collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Long count(){
        return bigliettoRepository.count();
    }
}
