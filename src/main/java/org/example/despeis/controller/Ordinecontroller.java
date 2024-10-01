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
    public ResponseEntity<?> getOrdine(@RequestParam(name="user", required = false) Integer userId){
        ResponseEntity<?> r;
        try{
            r = userId==null ? ResponseEntity.ok(ordineService.getAll()) : ResponseEntity.ok(ordineService.getById(userId));
        }catch (Exception e){
            r= ResponseEntity.badRequest().body(e.getMessage());
        }
        return r;
    }
}
