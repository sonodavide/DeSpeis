package org.example.despeis.services;

import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.AttoreDto;
import org.example.despeis.dto.PaginatedResponse;
import org.example.despeis.mapper.AttoreMapper;
import org.example.despeis.model.Attore;
import org.example.despeis.repository.AttoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AttoreService {

    private final AttoreRepository attoreRepository;
    private final AttoreMapper attoreMapper;

    @Autowired
    public AttoreService(AttoreRepository attoreRepository, AttoreMapper attoreMapper) {
        this.attoreMapper=attoreMapper;
        this.attoreRepository=attoreRepository;
    }

    @Transactional
    public List<AttoreDto> getAll(){
        return attoreRepository.findAll().stream().map(attoreMapper::toDto).collect(Collectors.toList());
    }
    @Transactional(readOnly = true)
    public PaginatedResponse<AttoreDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("cognome"));
        Page<Attore> result = attoreRepository.findAll(pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(attoreMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());

    }
    @Transactional
    public void delete(AttoreDto attoreDto){
        attoreRepository.delete(attoreMapper.toEntity(attoreDto));
    }
    @Transactional
    public void delete(Integer attoreId){
        attoreRepository.deleteById(attoreId);
    }
    @Transactional
    public AttoreDto nuovo(AttoreDto attoreDto) throws BadRequestException {
        if(attoreDto.getCognome().trim().equals("") || attoreDto.getNome().trim().equals("")) throw new BadRequestException();
        return attoreMapper.toDto(attoreRepository.save(attoreMapper.toEntity(attoreDto)));
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<AttoreDto> ricerca(String query, Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("cognome"));
        String searchTerm = "%" + query.toLowerCase() + "%";
        Page<Attore> result = attoreRepository.cerca(searchTerm, pageable);

        return new PaginatedResponse<>(
                result.getContent().stream().map(attoreMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements()
        );
    }

    @Transactional(readOnly = true)
    public Long count(){
        return attoreRepository.count();
    }

    @Transactional(readOnly = true)
    public String getNomeById(Integer id){
        Attore attore = attoreRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return attore.getNome() + " " + attore.getCognome();
    }
}
