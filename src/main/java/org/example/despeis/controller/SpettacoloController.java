package org.example.despeis.controller;

import org.example.despeis.dto.NuovoSpettacoloDto;
import org.example.despeis.dto.SpettacoloDto;
import org.example.despeis.services.PostiSpettacoloService;
import org.example.despeis.services.SpettacoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    //QUESTO E' QUELLO DEL SITO PER GLI UTENTI NORMALI.
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
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody NuovoSpettacoloDto nuovo){

        try{
            return ResponseEntity.ok(spettacoloService.aggiungiSpettacolo(nuovo));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("elimina")
    public ResponseEntity<?> elimina(@RequestBody SpettacoloDto spettacolo){
        try{
            return ResponseEntity.ok(spettacoloService.elimina(spettacolo));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/postiSpettacolo")
    public ResponseEntity<?> getPostiSpettacolo(@RequestParam("spettacoloId") Integer spettacoloId){
        try{
            return ResponseEntity.ok(postiSpettacoloService.getBySpettacoloId(spettacoloId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/cerca")
    public ResponseEntity<?> cercaByDate(@RequestParam("date") String date, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        LocalDate d = LocalDate.parse(date);
        try{
            return ResponseEntity.ok(spettacoloService.cerca(d, pageNumber, pageSize));
        }catch(Exception e){
            return new ResponseEntity<>(e.toString()
                    , HttpStatus.BAD_REQUEST);
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){

        try{
            return ResponseEntity.ok(spettacoloService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(spettacoloService.count());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

}
