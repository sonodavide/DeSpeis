package org.example.despeis.controller;

import org.example.despeis.dto.NuovoSpettacoloDto;
import org.example.despeis.dto.SpettacoloDto;
import org.example.despeis.services.PostiSpettacoloService;
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
    private final PostiSpettacoloService postiSpettacoloService;
    @Autowired
    public SpettacoloController(SpettacoloService spettacoloService, PostiSpettacoloService postiSpettacoloService) {
        this.postiSpettacoloService = postiSpettacoloService;
        this.spettacoloService = spettacoloService;
    }

    @PostMapping("/byDate")
    public ResponseEntity<?> getFilmSpettacoloByDate(@RequestParam("date") String date){
        LocalDate d = LocalDate.parse(date);
        try{
            return ResponseEntity.ok(spettacoloService.getFilmSpettacoloByDate(d));
        }catch(Exception e){
            return new ResponseEntity<>(e.toString()
                    , HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody NuovoSpettacoloDto nuovo){

        try{
            return ResponseEntity.ok(spettacoloService.aggiungiSpettacolo(nuovo));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("elimina")
    public ResponseEntity<?> elimina(@RequestBody SpettacoloDto spettacolo){
        try{
            return ResponseEntity.ok(spettacoloService.elimina(spettacolo));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/sala")
    public ResponseEntity<?> getPostiSpettacolo(@RequestParam("spettacoloId") Integer spettacoloId){
        try{
            return ResponseEntity.ok(postiSpettacoloService.getBySpettacoloId(spettacoloId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/ricerca")
    public ResponseEntity<?> cercaByDate(@RequestParam("date") String date){
        LocalDate d = LocalDate.parse(date);
        try{
            return ResponseEntity.ok(spettacoloService.getAllByDateRicerca(d));
        }catch(Exception e){
            return new ResponseEntity<>(e.toString()
                    , HttpStatus.BAD_REQUEST);
        }
    }

}
