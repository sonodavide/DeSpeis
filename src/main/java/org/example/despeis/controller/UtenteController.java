package org.example.despeis.controller;

import org.example.despeis.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    public ResponseEntity<?> getUtente(@RequestParam(name = "userId", required = false) Integer userId){

        try{
            return userId == null ? ResponseEntity.ok(utenteService.getAll()) : ResponseEntity.ok(utenteService.getById(userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }
}
