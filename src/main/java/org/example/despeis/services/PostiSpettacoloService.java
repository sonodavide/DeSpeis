package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.example.despeis.dto.PostiSpettacoloResponseDto;
import org.example.despeis.dto.PrenotazioneRequestDto;
import org.example.despeis.mapper.PostispettacoloMapper;
import org.example.despeis.model.*;
import org.example.despeis.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class PostiSpettacoloService {
    @PersistenceContext
    private EntityManager entityManager;
    private final PostispettacoloRepository postiSpettacoloRepository;
    private final PostispettacoloMapper postiSpettacoloMapper;
    private final UtenteRepository utenteRepository;
    private final OrdineRepository ordineRepository;
    private final BigliettoRepository bigliettoRepository;

    private final SpettacoloRepository spettacoloRepository;

    @Autowired
    public PostiSpettacoloService(PostispettacoloRepository postiSpettacoloRepository, PostispettacoloMapper postiSpettacoloMapper, UtenteRepository utenteRepository, OrdineRepository ordineRepository, BigliettoRepository bigliettoRepository, SpettacoloService spettacoloService, SpettacoloRepository spettacoloRepository) {
        this.postiSpettacoloRepository = postiSpettacoloRepository;
        this.postiSpettacoloMapper = postiSpettacoloMapper;
        this.utenteRepository = utenteRepository;
        this.ordineRepository = ordineRepository;
        this.bigliettoRepository = bigliettoRepository;

        this.spettacoloRepository = spettacoloRepository;
    }

    @Transactional(readOnly = true)
    public PostiSpettacoloResponseDto getBySpettacoloId(int spettacoloId){
        List<Postispettacolo> postiSpettacolo = postiSpettacoloRepository.findAllBySpettacoloIdOrderByFilaAscSedileAsc(spettacoloId);
        HashMap<String, List<PostiSpettacoloResponseDto.PostoResponse>> tempMap = new HashMap<>();
        for(Postispettacolo posto : postiSpettacolo){
            String filaCorrente = posto.getFila();
            if(!tempMap.containsKey(filaCorrente)){
                tempMap.put(filaCorrente, new ArrayList<>());
            }
            tempMap.get(filaCorrente).add(
                    new PostiSpettacoloResponseDto.PostoResponse(
                            posto.getId(), posto.getSedile(), posto.getStato()
                    ));
       }
       return new PostiSpettacoloResponseDto(spettacoloId, tempMap);
    }

    @Transactional
    public PostiSpettacoloResponseDto blocca(PrenotazioneRequestDto prenotazioneRequestDto) throws Exception{
        if(prenotazioneRequestDto.getPostiIds().size()==0){
            throw new Exception("Errore lista vuota");
        }
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostiIdsAndNotPrenotati(prenotazioneRequestDto.getPostiIds());

        if(p.size()!=prenotazioneRequestDto.getPostiIds().size()) throw new Exception("posto/i già prenotati");
        for(Postispettacolo posto : p){
            if(posto.getStato().equals("bloccato")){
                posto.setStato("libero");
            } else {
                posto.setStato("bloccato");
            }
        }
        postiSpettacoloRepository.saveAll(p);
        return getBySpettacoloId(p.get(0).getSpettacolo().getId());
    }
    @Transactional
    public boolean prenota(PrenotazioneRequestDto prenotazione) throws Exception{
        try{

        Spettacolo spettacolo =entityManager.find(Spettacolo.class, prenotazione.getSpettacoloId(), LockModeType.PESSIMISTIC_READ);
        if(!spettacolo.getAcquistabile()) throw new Exception("non è acquistabile");
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostiIdsAndLiberi(prenotazione.getPostiIds());
        if(p.size()!=prenotazione.getPostiIds().size()) {
            throw new Exception("posto/i già prenotati");
        }
            ArrayList<Biglietto> biglietti = new ArrayList<>();
            Ordine ordine = new Ordine();
            ordine.setData(LocalDate.now());
            ordine.setStato("confermato");
            Utente utente = utenteRepository.findById(prenotazione.getUserId()).orElseThrow();

            ordine = ordineRepository.save(ordine);
            for(Postispettacolo posto : p){
                posto.setStato("prenotato");
                Biglietto biglietto = new Biglietto();
                biglietto.setPostospettacolo(posto);
                biglietto.setUtente(utente);
                biglietto.setOrdine(ordine);
                biglietto.setPrezzo(spettacolo.getPrezzo());
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
