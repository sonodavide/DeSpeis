package org.example.despeis.services;

import org.example.despeis.dto.AttoreDto;
import org.example.despeis.dto.RegistaDto;
import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.dto.RegistaDto;
import org.example.despeis.mapper.RegistaMapper;
import org.example.despeis.model.Attore;
import org.example.despeis.model.Regista;
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
    public PaginatedResponse<RegistaDto> ricerca(String query, Integer pageNumber, Integer pageSize){
        try{
            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("cognome"));
            String searchTerm = "%" + query.toLowerCase() + "%";
            Page<Regista> result = registaRepository.cerca(searchTerm, pageable);

            return new PaginatedResponse<>(
                    result.getContent().stream().map(registaMapper::toDto).collect(Collectors.toList()),
                    result.getTotalPages(), result.getTotalElements()
            );

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<RegistaDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("cognome"));
        Page<Regista> result = registaRepository.findAll(pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(registaMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Long count(){
        return registaRepository.count();
    }
}
