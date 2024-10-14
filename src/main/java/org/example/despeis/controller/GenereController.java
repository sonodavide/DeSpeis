package org.example.despeis.controller;

import org.example.despeis.dto.GenereDto;
import org.example.despeis.services.GenereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genere")
public class GenereController {

    private final GenereService genereService;
    @Autowired
    public GenereController(GenereService genereService) {
        this.genereService = genereService;
    }

    @PostMapping("nuovo")
    public ResponseEntity<?> nuovo(@RequestBody GenereDto genereDto){
        try{
            return ResponseEntity.ok(genereService.nuovo(genereDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody GenereDto genereDto){
        try{
            genereService.delete(genereDto);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/cerca")
    public ResponseEntity<?> suggest(@RequestParam String query, Integer pageNumber, Integer pageSize) {
        try{
            return ResponseEntity.ok(genereService.ricerca(query, pageNumber, pageSize));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam Integer pageNumber, Integer pageSize){

        try{
            return ResponseEntity.ok(genereService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
