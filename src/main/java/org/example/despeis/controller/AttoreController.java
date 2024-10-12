package org.example.despeis.controller;

import org.apache.coyote.Response;
import org.example.despeis.dto.AttoreDto;
import org.example.despeis.services.AttoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/attore")
public class AttoreController {

    private final AttoreService attoreService;
    @Autowired
    public AttoreController(AttoreService attoreService) {
        this.attoreService = attoreService;
    }

    @GetMapping
    public ResponseEntity<?> ricerca(@RequestParam String query) {
        try{
            return ResponseEntity.ok(attoreService.ricerca(query));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody AttoreDto attoreDto){
        try{
            return ResponseEntity.ok(attoreService.nuovo(attoreDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody AttoreDto attoreDto){
        try{
            attoreService.delete(attoreDto);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
