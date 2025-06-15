package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    @PersistenceContext
    private EntityManager entityManager;
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
    public boolean delete(SalaDto salaDto) throws Exception {
        return delete(salaDto.getId());

    }
    @Transactional
    public boolean delete(Integer salaId) throws Exception {
        Sala s = entityManager.find(Sala.class, salaId);
        if(s == null) throw new BadRequestException();
        if(spettacoloRepository.existsSpettacoloBySalaId(s.getId())) throw new IllegalStateException();
        salaRepository.deleteById(salaId);
        if(entityManager.find(Sala.class, salaId)!=null){
            throw new Exception();
        }
        return true;
    }
    @Transactional
    public SalaDto nuovo(SalaConPostiDto salaDto) throws BadRequestException{
            if(entityManager.find(Sala.class, salaDto.getId()) != null) throw new BadRequestException();
            Sala nuovaSala = new Sala();
            nuovaSala.setId(salaDto.getId());
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




    @Transactional
    public SalaDto modifica(SalaConPostiDto salaDto) throws BadRequestException {

        Sala nuovaSala;
        if (salaDto.getId() == null) throw new BadRequestException();

        nuovaSala = salaRepository.findById(salaDto.getId()).orElseThrow(() -> new BadRequestException());


        if (!spettacoloRepository.findBySalaAndNonFiniti(nuovaSala).isEmpty()) throw new IllegalStateException();

        postiRepository.deleteBySalaId(nuovaSala.getId());
        postiRepository.flush();


        List<Posti> postiLista = new ArrayList<>();
        nuovaSala = salaRepository.save(nuovaSala);
        for (PostiDto posti : salaDto.getPostis()) {
            Posti p = new Posti();
            p.setFila(posti.getFila());
            p.setSedili(posti.getSedili());
            p.setSala(nuovaSala);
            postiLista.add(p);

        }
        postiRepository.saveAll(postiLista);
        return salaMapper.toDto(nuovaSala);
    }
}
