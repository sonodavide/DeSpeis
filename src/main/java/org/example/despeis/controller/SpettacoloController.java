package org.example.despeis.controller;

import org.example.despeis.dto.NuovoSpettacoloDto;
import org.example.despeis.services.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
@CrossOrigin
@RestController
@RequestMapping("/spettacolo")
public class SpettacoloController {

    private final SpettacoloService spettacoloService;
    @Autowired
    public SpettacoloController(SpettacoloService spettacoloService) {
        this.spettacoloService = spettacoloService;
    }

    @PostMapping("/byDate")
    public ResponseEntity<?> getByDate(@RequestParam("date") String date){
        LocalDate d = LocalDate.parse(date);
        try{
            return ResponseEntity.ok(spettacoloService.getByDate(d));
        }catch(Exception e){
            return new ResponseEntity<>("no", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody NuovoSpettacoloDto nuovo){
        System.out.println(nuovo.toString());
        try{
            return ResponseEntity.ok(spettacoloService.aggiungiSpettacolo(nuovo));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
