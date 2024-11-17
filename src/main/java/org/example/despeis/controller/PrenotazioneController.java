package org.example.despeis.controller;

import org.example.despeis.dto.PrenotazioneRequestDto;
import org.example.despeis.services.PostiSpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController {

    private final PostiSpettacoloService postiSpettacoloService;
    @Autowired
    public PrenotazioneController(PostiSpettacoloService postiSpettacoloService) {
        this.postiSpettacoloService = postiSpettacoloService;
    }

    @PostMapping("/prenota")
    public ResponseEntity<?> prenota(@RequestBody PrenotazioneRequestDto prenotazioneRequestDto){
        try{
            return ResponseEntity.ok(postiSpettacoloService.prenota(prenotazioneRequestDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/blocca")
    public ResponseEntity<?> blocca(@RequestBody PrenotazioneRequestDto prenotazioneRequestDto){
        try{
            return ResponseEntity.ok(postiSpettacoloService.blocca(prenotazioneRequestDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
