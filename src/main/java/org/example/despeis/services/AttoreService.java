package org.example.despeis.services;

import org.example.despeis.dto.AttoreDto;
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
    public void delete(AttoreDto attoreDto){
        attoreRepository.delete(attoreMapper.toEntity(attoreDto));
    }
    @Transactional
    public void delete(Integer attoreId){
        attoreRepository.deleteById(attoreId);
    }
    @Transactional
    public AttoreDto nuovo(AttoreDto attoreDto){
        return attoreMapper.toDto(attoreRepository.save(attoreMapper.toEntity(attoreDto)));
    }

    @Transactional(readOnly = true)
    public List<AttoreDto> ricerca(String query){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("cognome"));
        String searchTerm = "%" + query.toLowerCase() + "%";
        Page<Attore> result = attoreRepository.cerca(searchTerm, pageable);

        return result.getContent().stream().map(attoreMapper::toDto).collect(Collectors.toList());
    }
}
