package org.example.despeis.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.PostiResponse;
import org.example.despeis.dto.PostiSpettacoloResponseDto;
import org.example.despeis.dto.PostispettacoloDto;
import org.example.despeis.dto.PrenotazioneRequestDto;
import org.example.despeis.mapper.PostispettacoloMapper;
import org.example.despeis.mapper.SpettacoloSenzaFilmTagsMapper;
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
    private final SpettacoloSenzaFilmTagsMapper spettacoloSenzaFilmTagsMapper;
    private final SpettacoloRepository spettacoloRepository;

    @Autowired
    public PostiSpettacoloService(PostispettacoloRepository postiSpettacoloRepository, PostispettacoloMapper postiSpettacoloMapper, UtenteRepository utenteRepository, OrdineRepository ordineRepository, BigliettoRepository bigliettoRepository, SpettacoloRepository spettacoloRepository, SpettacoloSenzaFilmTagsMapper spettacoloSenzaFilmTagsMapper) {
        this.postiSpettacoloRepository = postiSpettacoloRepository;
        this.postiSpettacoloMapper = postiSpettacoloMapper;
        this.utenteRepository = utenteRepository;
        this.ordineRepository = ordineRepository;
        this.bigliettoRepository = bigliettoRepository;
        this.spettacoloSenzaFilmTagsMapper = spettacoloSenzaFilmTagsMapper;
        this.spettacoloRepository = spettacoloRepository;
    }

    @Transactional(readOnly = true)
    public PostiSpettacoloResponseDto getAcquistabileBySpettacoloId(int spettacoloId) throws BadRequestException {
        Spettacolo spettacolo = spettacoloRepository.findById(spettacoloId).orElseThrow(() -> new BadRequestException());
        List<Postispettacolo> postiSpettacolo = postiSpettacoloRepository.findAllBySpettacoloIdOrderByFilaAscSedileAscAcquistabile(spettacolo.getId());
        List<PostiSpettacoloResponseDto.PostiPerFila> postiPerFila = new ArrayList<>();
        for(Postispettacolo ps : postiSpettacolo){
            boolean trovato = false;
            for(PostiSpettacoloResponseDto.PostiPerFila ppf :postiPerFila){
                if(ppf.getFila().equals(ps.getFila())){
                    ppf.getPosti().add(new PostiResponse(ps.getId(), ps.getSedile(), ps.getStato()));
                    trovato = true;
                    break;
                }
            }
            if(!trovato){
                List<PostiResponse> qualcosa = new ArrayList<>();
                qualcosa.add(new PostiResponse(ps.getId(), ps.getSedile(), ps.getStato()));
                postiPerFila.add(new PostiSpettacoloResponseDto.PostiPerFila(ps.getFila(), qualcosa));

            }
        }

       return new PostiSpettacoloResponseDto(spettacoloSenzaFilmTagsMapper.toDto(spettacolo), postiPerFila);
    }

    public PostiSpettacoloResponseDto getBySpettacoloId(int spettacoloId) throws BadRequestException {
        Spettacolo spettacolo = spettacoloRepository.findById(spettacoloId).orElseThrow(() -> new BadRequestException());
        List<Postispettacolo> postiSpettacolo = postiSpettacoloRepository.findAllBySpettacoloIdOrderByFilaAscSedileAsc(spettacolo.getId());
        List<PostiSpettacoloResponseDto.PostiPerFila> postiPerFila = new ArrayList<>();
        for(Postispettacolo ps : postiSpettacolo){
            boolean trovato = false;
            for(PostiSpettacoloResponseDto.PostiPerFila ppf :postiPerFila){
                if(ppf.getFila().equals(ps.getFila())){
                    ppf.getPosti().add(new PostiResponse(ps.getId(), ps.getSedile(), ps.getStato()));
                    trovato = true;
                    break;
                }
            }
            if(!trovato){
                List<PostiResponse> qualcosa = new ArrayList<>();
                qualcosa.add(new PostiResponse(ps.getId(), ps.getSedile(), ps.getStato()));
                postiPerFila.add(new PostiSpettacoloResponseDto.PostiPerFila(ps.getFila(), qualcosa));

            }
        }

        return new PostiSpettacoloResponseDto(spettacoloSenzaFilmTagsMapper.toDto(spettacolo), postiPerFila);
    }



    @Transactional
    public PostiSpettacoloResponseDto blocca(PrenotazioneRequestDto prenotazioneRequestDto) throws BadRequestException{
        int numeroPostiPrenotati=calcolaPostiPrenotati(prenotazioneRequestDto.getPostiSpettacoloResponseDto());
        if(numeroPostiPrenotati==0){
            throw new BadRequestException("dati non validi");
        }
        Set<Integer> postiIds = new HashSet<>();
        for(PostispettacoloDto p : postiSpettacoloExtractor(prenotazioneRequestDto.getPostiSpettacoloResponseDto())){
            postiIds.add(p.getId());
        }
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostiIdsAndNotPrenotati(postiIds);

        if(p.size()!=numeroPostiPrenotati) throw new BadRequestException("dati non validi");
        for(Postispettacolo posto : p){
            if(posto.getStato().equals("bloccato")) posto.setStato("libero");
            else if(posto.getStato().equals("libero")) posto.setStato("bloccato");


        }
        postiSpettacoloRepository.saveAll(p);
        return getAcquistabileBySpettacoloId(p.get(0).getSpettacolo().getId());
    }
    @Transactional
    public boolean prenota(JwtAuthenticationToken authenticationToken, PrenotazioneRequestDto prenotazione) throws Exception{
        int numeroPostiPrenotati=calcolaPostiPrenotati(prenotazione.getPostiSpettacoloResponseDto());
        String userId = Utils.getUserId(authenticationToken);
        Spettacolo spettacolo = spettacoloRepository.findSpettacoloAcquistabileById(prenotazione.getPostiSpettacoloResponseDto().getSpettacoloSenzaFilmTagsDto().getId());
        if(spettacolo == null || !spettacolo.getAcquistabile() || spettacolo.getData().isBefore(LocalDate.now())) throw new BadRequestException();

        if( spettacolo.getPrezzo().multiply(new BigDecimal(numeroPostiPrenotati)).compareTo(prenotazione.getPrezzo()) != 0) throw new BadRequestException();

        Set<Integer> postiIds = new HashSet<>();
        for(PostispettacoloDto p : postiSpettacoloExtractor(prenotazione.getPostiSpettacoloResponseDto())){
            postiIds.add(p.getId());
        }
        List<Postispettacolo> p = postiSpettacoloRepository.findByPostiIdsAndLiberi(postiIds);
        if(p.size()!=numeroPostiPrenotati) {
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

    private int calcolaPostiPrenotati(PostiSpettacoloResponseDto postiSpettacoloResponseDto){
        int contatore = 0;
        for(PostiSpettacoloResponseDto.PostiPerFila p : postiSpettacoloResponseDto.getPostiPerFila()){
            contatore+=p.getPosti().size();
        }
        return contatore;
    }

    private List<PostispettacoloDto> postiSpettacoloExtractor(PostiSpettacoloResponseDto postiSpettacoloResponseDto){
        List<PostispettacoloDto> res = new ArrayList<>();
        for(PostiSpettacoloResponseDto.PostiPerFila p : postiSpettacoloResponseDto.getPostiPerFila()){
            for(PostiResponse postiresponse : p.getPosti()){
                PostispettacoloDto postispettacoloDto = new PostispettacoloDto(postiresponse.getPostoSpettacoloId(), postiresponse.getPostoSedile(), postiresponse.getStato(), p.getFila());
                res.add(postispettacoloDto);
            }
        }
        return res;
    }
}
