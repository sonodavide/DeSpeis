package org.example.despeis.controller;

import org.example.despeis.services.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordine")
public class Ordinecontroller {

    private final OrdineService ordineService;
    @Autowired
    public Ordinecontroller(OrdineService ordineService) {
        this.ordineService = ordineService;
    }





    @GetMapping("/paged/user")
    public ResponseEntity<?> getAllByUserPaged(@RequestParam Integer userId, Integer pageNumber, Integer pageSize){
        try{
            return ResponseEntity.ok(ordineService.getAllByUserPaginated(userId, pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(ordineService.count());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/incassiTotali")
    public ResponseEntity<?> incassiTotali(){
        try{
            return ResponseEntity.ok(ordineService.incassiTotali());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
