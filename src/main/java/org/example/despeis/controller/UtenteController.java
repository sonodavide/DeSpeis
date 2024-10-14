package org.example.despeis.controller;

import org.example.despeis.dto.UtenteDto;
import org.example.despeis.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    public ResponseEntity<?> getUtente(@RequestParam(name = "userId", required = false) Integer userId){

        try{
            return userId == null ? ResponseEntity.ok(utenteService.getAll()) : ResponseEntity.ok(utenteService.getById(userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam Integer pageNumber, Integer pageSize){

        try{
            return ResponseEntity.ok(utenteService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/cerca")
    public ResponseEntity<?> ricerca(@RequestParam String query, Integer pageNumber, Integer pageSize) {
        try{
            return ResponseEntity.ok(utenteService.ricerca(query, pageNumber, pageSize));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody UtenteDto utenteDto){
        try{
            return ResponseEntity.ok(utenteService.nuovo(utenteDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody UtenteDto utenteDto){
        try{
            utenteService.delete(utenteDto);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
