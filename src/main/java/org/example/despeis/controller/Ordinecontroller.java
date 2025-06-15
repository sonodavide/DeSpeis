package org.example.despeis.controller;

import org.example.despeis.dto.OrdineDto;
import org.example.despeis.services.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ordine")
public class Ordinecontroller {

    private final OrdineService ordineService;
    @Autowired
    public Ordinecontroller(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    @GetMapping
    public ResponseEntity<?> getOrdini(){
        try{
            return ResponseEntity.ok(ordineService.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getOrdine(@RequestParam("user") int userId){
        try{
            return ResponseEntity.ok(ordineService.getById(userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
