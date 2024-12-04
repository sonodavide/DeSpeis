package org.example.despeis.services;

import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.*;
import org.example.despeis.dto.SalaDto;
import org.example.despeis.mapper.PostiMapper;

import org.example.despeis.mapper.SalaConPostiMapper;
import org.example.despeis.mapper.SalaMapper;
import org.example.despeis.model.Posti;

import org.example.despeis.model.Sala;
import org.example.despeis.repository.PostiRepository;

import org.example.despeis.repository.SalaRepository;
import org.example.despeis.repository.SpettacoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalaService {

    private final SalaRepository salaRepository;
    private final SalaMapper salaMapper;
    private final PostiRepository postiRepository;
    private final PostiMapper postiMapper;
    private final SalaConPostiMapper salaConPostiMapper;
    private final SpettacoloRepository spettacoloRepository;

    @Autowired
    public SalaService(SpettacoloRepository spettacoloRepository, SalaRepository salaRepository, SalaMapper salaMapper, PostiRepository postiRepository, PostiMapper postiMapper, SalaConPostiMapper salaConPostiMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
        this.postiRepository = postiRepository;
        this.postiMapper = postiMapper;
        this.salaConPostiMapper = salaConPostiMapper;
        this.spettacoloRepository = spettacoloRepository;
    }

    @Transactional
    public boolean delete(SalaDto salaDto){
        return delete(salaDto.getId());

    }
    @Transactional
    public boolean delete(Integer salaId){
        salaRepository.deleteById(salaId);
        return true;
    }
    @Transactional
    public SalaDto nuovo(SalaConPostiDto salaDto) throws BadRequestException{
        try{
            Sala nuovaSala;
            if(salaDto.getId() == null){
                nuovaSala = new Sala();
            } else {
                nuovaSala = salaRepository.findById(salaDto.getId()).orElseThrow(() -> new BadRequestException());
                /*
                controllo se ci sono spettacoli acquistabili con questa sala. In caso,
                ne impedisco la modifica.
                */
                if(spettacoloRepository.findFirstBySalaAndAcquistabileTrueAndNonPassati(nuovaSala)!=null) throw new BadRequestException();
                postiRepository.deleteBySalaId(nuovaSala.getId());
                postiRepository.flush();

            }
            List<Posti> postiLista = new ArrayList<>();
            nuovaSala=salaRepository.save(nuovaSala);
            for(PostiDto posti : salaDto.getPostis()){
                Posti p = new Posti();
                p.setFila(posti.getFila());
                p.setSedili(posti.getSedili());
                p.setSala(nuovaSala);
                postiLista.add(p);

            }
            postiRepository.saveAll(postiLista);
            return salaMapper.toDto(nuovaSala);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(readOnly = true)
    public PaginatedResponse<SalaDto> getAllPaginated(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<Sala> result = salaRepository.findAll(pageable);
        return new PaginatedResponse<>(
                result.getContent().stream().map(salaMapper::toDto).collect(Collectors.toList()),
                result.getTotalPages(), result.getTotalElements());
    }

    @Transactional(readOnly = true)
    public SalaConPostiDto getSalaConPostiPerFila(Integer salaId){
        Sala s = salaRepository.findById(salaId).orElseThrow(() -> new NoSuchElementException());
        List<Posti> postiDiS = postiRepository.findAllBySalaOrderByFilaAsc(s);

        return new SalaConPostiDto(salaId, postiDiS.stream().map(postiMapper::toDto).collect(Collectors.toList()));

    }

    @Transactional(readOnly = true)
    public Long count(){
        return salaRepository.count();
    }
}
