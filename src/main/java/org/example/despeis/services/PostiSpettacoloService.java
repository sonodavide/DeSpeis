package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.PostiSpettacoloResponseDto;
import org.example.despeis.dto.PostispettacoloDto;
import org.example.despeis.dto.PrenotazioneRequestDto;
import org.example.despeis.mapper.PostispettacoloMapper;
import org.example.despeis.model.*;
import org.example.despeis.repository.*;
import org.example.despeis.security.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

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
    public PostiSpettacoloResponseDto blocca(PrenotazioneRequestDto prenotazioneRequestDto) throws BadRequestException{
        if(prenotazioneRequestDto.getPosti().size()==0){
            throw new BadRequestException("dati non validi");
        }
        Set<Integer> postiIds = new HashSet<>();
        for(PostispettacoloDto p : prenotazioneRequestDto.getPosti()){
            postiIds.add(p.getId());
        }
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostiIdsAndNotPrenotati(postiIds);

        if(p.size()!=prenotazioneRequestDto.getPosti().size()) throw new BadRequestException("dati non validi");
        for(Postispettacolo posto : p){
            if(posto.getStato().equals("bloccato")) posto.setStato("libero");
            else if(posto.getStato().equals("libero")) posto.setStato("bloccato");


        }
        postiSpettacoloRepository.saveAll(p);
        return getBySpettacoloId(p.get(0).getSpettacolo().getId());
    }
    @Transactional
    public boolean prenota(JwtAuthenticationToken authenticationToken, PrenotazioneRequestDto prenotazione) throws Exception{

        String userId = Utils.getUserId(authenticationToken);
        Spettacolo spettacolo = spettacoloRepository.findSpettacoloAcquistabileById(prenotazione.getSpettacoloId());
        if(spettacolo == null || !spettacolo.getAcquistabile()) throw new BadRequestException();
        if(!(spettacolo.getPrezzo().multiply(new BigDecimal(prenotazione.getPosti().size()))).equals(prenotazione.getPrezzo())) throw new BadRequestException();

        Set<Integer> postiIds = new HashSet<>();
        for(PostispettacoloDto p : prenotazione.getPosti()){
            postiIds.add(p.getId());
        }
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostiIdsAndLiberi(postiIds);
        if(p.size()!=prenotazione.getPosti().size()) {
            throw new IllegalStateException("posto/i già prenotati");
        }
        for(Postispettacolo posto : p) if(!posto.getSpettacolo().equals(spettacolo)) throw new BadRequestException();
        ArrayList<Biglietto> biglietti = new ArrayList<>();
        Ordine ordine = new Ordine();
        ordine.setData(LocalDate.now());
        ordine.setStato("confermato");
        Utente utente = utenteRepository.findById(userId).orElseThrow(() -> new BadRequestException("utente non valido"));
        ordine.setUtente(utente);

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

    }
}
