package org.example.despeis.controller;

import org.example.despeis.dto.PrenotazioneRequestDto;
import org.example.despeis.services.PostiSpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/prenota")
    public ResponseEntity<?> prenota(JwtAuthenticationToken authenticationToken, @RequestBody PrenotazioneRequestDto prenotazioneRequestDto){
        try{
            return ResponseEntity.ok(postiSpettacoloService.prenota(authenticationToken, prenotazioneRequestDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/blocca")
    public ResponseEntity<?> blocca(@RequestBody PrenotazioneRequestDto prenotazioneRequestDto){
        try{
            return ResponseEntity.ok(postiSpettacoloService.blocca(prenotazioneRequestDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
