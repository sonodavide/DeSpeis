package org.example.despeis.controller;

import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.PrenotazioneRequestDto;
import org.example.despeis.services.PostiSpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<?> prenota(JwtAuthenticationToken authenticationToken,@Validated @RequestBody PrenotazioneRequestDto prenotazioneRequestDto){
        try{
            return ResponseEntity.ok(postiSpettacoloService.prenota(authenticationToken, prenotazioneRequestDto));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/blocca")
    public ResponseEntity<?> blocca(@Validated @RequestBody PrenotazioneRequestDto prenotazioneRequestDto){
        try{
            return ResponseEntity.ok(postiSpettacoloService.blocca(prenotazioneRequestDto));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
