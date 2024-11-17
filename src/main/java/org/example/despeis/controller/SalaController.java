package org.example.despeis.controller;

import org.example.despeis.dto.SalaConPostiDto;
import org.example.despeis.dto.SalaDto;
import org.example.despeis.services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sala")
public class SalaController {

    private final SalaService salaService;

    @Autowired
    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody SalaConPostiDto sala){
        try{
            return ResponseEntity.ok(salaService.nuovo(sala));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam Integer pageNumber, Integer pageSize){

        try{
            return ResponseEntity.ok(salaService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/postiPerFila")
    public ResponseEntity<?> getPostiPerFila(@RequestParam Integer salaId){
        return ResponseEntity.ok(salaService.getSalaConPostiPerFila(salaId));
    }

    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody SalaDto sala){
        System.out.println(sala.getId());
        return ResponseEntity.ok(salaService.delete(sala));
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(salaService.count());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
