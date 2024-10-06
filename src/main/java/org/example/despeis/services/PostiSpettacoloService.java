package org.example.despeis.services;

import org.example.despeis.dto.PostiSpettacoloResponseDto;
import org.example.despeis.dto.PrenotazioneRequestDto;
import org.example.despeis.mapper.PostispettacoloMapper;
import org.example.despeis.model.Biglietto;
import org.example.despeis.model.Ordine;
import org.example.despeis.model.Postispettacolo;
import org.example.despeis.model.Utente;
import org.example.despeis.repository.BigliettoRepository;
import org.example.despeis.repository.OrdineRepository;
import org.example.despeis.repository.PostispettacoloRepository;
import org.example.despeis.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostiSpettacoloService {
    private final PostispettacoloRepository postiSpettacoloRepository;
    private final PostispettacoloMapper postiSpettacoloMapper;
    private final UtenteRepository utenteRepository;
    private final OrdineRepository ordineRepository;
    private final BigliettoRepository bigliettoRepository;

    @Autowired
    public PostiSpettacoloService(PostispettacoloRepository postiSpettacoloRepository, PostispettacoloMapper postiSpettacoloMapper, UtenteRepository utenteRepository, OrdineRepository ordineRepository, BigliettoRepository bigliettoRepository) {
        this.postiSpettacoloRepository = postiSpettacoloRepository;
        this.postiSpettacoloMapper = postiSpettacoloMapper;
        this.utenteRepository = utenteRepository;
        this.ordineRepository = ordineRepository;
        this.bigliettoRepository = bigliettoRepository;
    }

    @Transactional(readOnly = true)
    public PostiSpettacoloResponseDto getBySpettacoloId(int spettacoloId){
       List<Postispettacolo> postiSpettacolo = postiSpettacoloRepository.findAllBySpettacoloIdOrderByPostoFilaAscPostoSedileAsc(spettacoloId);
        HashMap<String, List<PostiSpettacoloResponseDto.PostoResponse>> tempMap = new HashMap<>();
       for(Postispettacolo posto : postiSpettacolo){
           String filaCorrente = posto.getPosto().getFila();
           if(!tempMap.containsKey(filaCorrente)){
                tempMap.put(filaCorrente, new ArrayList<>());
           }
           tempMap.get(filaCorrente).add(
                   new PostiSpettacoloResponseDto.PostoResponse(
                           posto.getId(), posto.getPosto().getId(), posto.getStato()
           ));
       }
       return new PostiSpettacoloResponseDto(spettacoloId, tempMap);
    }

    @Transactional
    public boolean blocca(Set<Integer> postoId) throws Exception{
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostiIdsAndLiberi(postoId);
        if(p.size()!=postoId.size()) throw new Exception("posto/i già prenotati");
        for(Postispettacolo posto : p){
            posto.setStato("bloccato");
        }
        postiSpettacoloRepository.saveAll(p);
        return true;
    }
    @Transactional
    public boolean prenota(PrenotazioneRequestDto prenotazione) throws Exception{
        try{

        List<Postispettacolo> p = postiSpettacoloRepository.findByPostiIdsAndLiberi(prenotazione.getPostiIds());
        if(p.size()!=prenotazione.getPostiIds().size()) {
            throw new Exception("posto/i già prenotati");
        }
            ArrayList<Biglietto> biglietti = new ArrayList<>();
            Ordine ordine = new Ordine();
            ordine.setData(LocalDate.now());
            ordine.setStato("confermato");
            Utente utente = utenteRepository.findById(prenotazione.getUserId()).orElseThrow();
            ordine.setUtente(utente);
            ordine = ordineRepository.save(ordine);
            for(Postispettacolo posto : p){
                posto.setStato("occupato");
                Biglietto biglietto = new Biglietto();
                biglietto.setPostospettacolo(posto);
                biglietto.setUtente(utente);
                biglietto.setOrdine(ordine);
                biglietti.add(biglietto);
            }

            bigliettoRepository.saveAll(biglietti);
            postiSpettacoloRepository.saveAll(p);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
}
