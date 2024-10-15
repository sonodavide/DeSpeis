package org.example.despeis.services;

import org.example.despeis.dto.BigliettoDto;
import org.example.despeis.dto.OrdineDto;
import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.mapper.BigliettoMapper;
import org.example.despeis.model.Biglietto;
import org.example.despeis.model.Ordine;
import org.example.despeis.repository.BigliettoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BigliettoService {
    private final BigliettoRepository bigliettoRepository;
    private final BigliettoMapper bigliettoMapper;
    private final int pageSize=10;
    @Autowired
    public BigliettoService(BigliettoRepository bigliettoRepository, BigliettoMapper bigliettoMapper) {
        this.bigliettoRepository = bigliettoRepository;
        this.bigliettoMapper = bigliettoMapper;
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getAll(int page){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Biglietto> result = bigliettoRepository.findAll(pageable);
        return new PaginatedResponse<BigliettoDto>(result.getContent().stream()
                .map(bigliettoMapper::toDto).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getByUser(Integer userId, int page){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        Page<Biglietto> result = bigliettoRepository.findByUtenteId(userId, pageable);
        return new PaginatedResponse<BigliettoDto>(result.getContent().stream()
                .map(bigliettoMapper::toDto).collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<BigliettoDto> getByUserAndDate(Integer userId, LocalDate date){
        return bigliettoRepository.findByUtenteIdAndPostospettacoloSpettacoloData(userId, date).stream()
                .map(bigliettoMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BigliettoDto> getByDate(LocalDate date){
        return bigliettoRepository.findByPostospettacoloSpettacoloData(date).stream()
                .map(bigliettoMapper::toDto).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public PaginatedResponse<BigliettoDto> getAllByUserPaginated(int userId, int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("ordine.data").descending());
        Page<Biglietto> result = bigliettoRepository.findAllByUtenteId(userId, pageable);
        return new PaginatedResponse<BigliettoDto>(result.getContent().stream()
                .map(bigliettoMapper::toDto)
                .collect(Collectors.toList()), result.getTotalPages(), result.getTotalElements());
    }
}
