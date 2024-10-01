package org.example.despeis.services;

import org.example.despeis.dto.PostispettacoloDto;
import org.example.despeis.mapper.PostispettacoloMapper;
import org.example.despeis.model.Biglietto;
import org.example.despeis.model.Ordine;
import org.example.despeis.model.Postispettacolo;
import org.example.despeis.model.Utente;
import org.example.despeis.repository.OrdineRepository;
import org.example.despeis.repository.PostispettacoloRepository;
import org.example.despeis.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostiSpettacoloService {
    private final PostispettacoloRepository postiSpettacoloRepository;
    private final PostispettacoloMapper postiSpettacoloMapper;
    private final UtenteRepository utenteRepository;
    private final OrdineRepository ordineRepository;
    @Autowired
    public PostiSpettacoloService(PostispettacoloRepository postiSpettacoloRepository, PostispettacoloMapper postiSpettacoloMapper, UtenteRepository utenteRepository, OrdineRepository ordineRepository) {
        this.postiSpettacoloRepository = postiSpettacoloRepository;
        this.postiSpettacoloMapper = postiSpettacoloMapper;
        this.utenteRepository = utenteRepository;
        this.ordineRepository = ordineRepository;
    }

    @Transactional(readOnly = true)
    public List<PostispettacoloDto> getBySpettacoloId(int spettacoloId){
        return postiSpettacoloRepository.findAllBySpettacoloIdOrderByPostoFilaAscPostoSedileAsc(spettacoloId)
                .stream().map(postiSpettacoloMapper::toDto)
                .collect(Collectors.toList());

    }

    @Transactional
    public boolean blocca(Set<Integer> postoId, int spettacoloId) throws Exception{
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostoIdListAndSpettacoloId(postoId, spettacoloId);
        if(p.size()!=postoId.size()) throw new Exception("non posso prenotare tutti i posto");
        for(Postispettacolo posto : p){
            posto.setStato("bloccato");
        }
        postiSpettacoloRepository.saveAll(p);
        return true;
    }
    @Transactional
    public boolean prenota(Set<Integer> postoId, int spettacoloId, int userId) throws Exception{
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostoIdListAndSpettacoloId(postoId, spettacoloId);
        if(p.size()!=postoId.size()) throw new Exception("non posso prenotare tutti i posto");
        ArrayList<Biglietto> biglietti;
        Ordine ordine = new Ordine();
        ordine.setData(LocalDate.now());
        ordine.setStato("confermato");
        Utente utente = utenteRepository.findById(userId).orElseThrow();
        ordine.setUtente(utente);
        for(Postispettacolo posto : p){
            posto.setStato("occupato");
            Biglietto biglietto = new Biglietto();
            biglietto.setPostospettacolo(posto);
            biglietto.setUtente(utente);
        }
        ordineRepository.save(ordine);
        postiSpettacoloRepository.saveAll(p);
        return true;
    }
}
