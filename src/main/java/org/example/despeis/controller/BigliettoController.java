package org.example.despeis.controller;

import org.example.despeis.services.BigliettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/biglietto")
public class BigliettoController {
    private final BigliettoService bigliettoService;

    @Autowired
    public BigliettoController(BigliettoService bigliettoService) {
        this.bigliettoService = bigliettoService;
    }

    @GetMapping()
    public ResponseEntity<?> getBiglietto(@RequestParam(name="user", required = false) Integer userId, @RequestParam(name="date", required = false) String date,  @RequestParam(name="page", defaultValue = "0") int page){

        try{
            ResponseEntity<?> r;
            if(userId == null && date == null){
                r = ResponseEntity.ok(bigliettoService.getAll(page));
            } else if(date == null){
                r = ResponseEntity.ok(bigliettoService.getByUser(userId, page));
            } else if (userId == null) {
                LocalDate d = LocalDate.parse(date);
                r = ResponseEntity.ok(bigliettoService.getByDate(d));
            } else {
                LocalDate d = LocalDate.parse(date);
                r = ResponseEntity.ok(bigliettoService.getByUserAndDate(userId, d));
            }
            return r;
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }


    }



}
