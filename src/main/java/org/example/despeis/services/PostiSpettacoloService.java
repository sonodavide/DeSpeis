package org.example.despeis.services;

import org.example.despeis.dto.PostispettacoloDto;
import org.example.despeis.mapper.PostispettacoloMapper;
import org.example.despeis.repository.PostispettacoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostiSpettacoloService {
    private final PostispettacoloRepository postiSpettacoloRepository;
    private final PostispettacoloMapper postiSpettacoloMapper;
    @Autowired
    public PostiSpettacoloService(PostispettacoloRepository postiSpettacoloRepository, PostispettacoloMapper postiSpettacoloMapper) {
        this.postiSpettacoloRepository = postiSpettacoloRepository;
        this.postiSpettacoloMapper = postiSpettacoloMapper;
    }
    @Transactional(readOnly = true)
    public List<PostispettacoloDto> getBySpettacoloId(int spettacoloId){
        return postiSpettacoloRepository.findAllBySpettacoloIdOrderByPostoFilaAscPostoSedileAsc(spettacoloId)
                .stream().map(postiSpettacoloMapper::toDto)
                .collect(Collectors.toList());

    }
}
