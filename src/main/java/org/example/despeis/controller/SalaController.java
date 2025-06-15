package org.example.despeis.controller;

import org.example.despeis.dto.PostoDto;
import org.example.despeis.dto.SalaDto;
import org.example.despeis.services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sala")
public class SalaController {

    private final SalaService salaService;

    @Autowired
    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody  SalaDto sala){
        try{
            return ResponseEntity.ok(salaService.nuovo(sala));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
