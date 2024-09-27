package org.example.despeis.controller;

import org.example.despeis.dto.SpettacoloDto;
import org.example.despeis.model.Spettacolo;
import org.example.despeis.services.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/spettacolo")
public class SpettacoloController {
    @Autowired
    private SpettacoloService spettacoloService;

    @PostMapping("/byDate")
    public ResponseEntity<?> getByDate(@RequestBody String date){
        LocalDate d = LocalDate.parse(date);
        try{
            return ResponseEntity.ok(spettacoloService.getSpettacoliByDate(d));
        }catch(Exception e){
            return new ResponseEntity<>("no", HttpStatus.BAD_REQUEST);
        }
    }
}
