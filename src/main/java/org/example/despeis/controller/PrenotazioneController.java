package org.example.despeis.controller;

import org.example.despeis.services.PostiSpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/prenotazione")
public class PrenotazioneController {

    private final PostiSpettacoloService postiSpettacoloService;
    @Autowired
    public PrenotazioneController(PostiSpettacoloService postiSpettacoloService) {
        this.postiSpettacoloService = postiSpettacoloService;
    }

    @GetMapping("/prenota")
    public ResponseEntity<?> prenota(@RequestParam Set<Integer> postoId, @RequestParam int spettacoloId, @RequestParam int userId){
        try{
            return ResponseEntity.ok(postiSpettacoloService.prenota(postoId, spettacoloId, userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/blocca")
    public ResponseEntity<?> blocca(@RequestParam Set<Integer> postoId, @RequestParam int spettacoloId){
        try{
            return ResponseEntity.ok(postiSpettacoloService.blocca(postoId, spettacoloId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
