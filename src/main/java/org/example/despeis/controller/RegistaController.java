package org.example.despeis.controller;

import org.example.despeis.dto.RegistaDto;
import org.example.despeis.services.RegistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/regista")
public class RegistaController {

    private final RegistaService registaService;
    @Autowired
    public RegistaController(RegistaService registaService) {
        this.registaService = registaService;
    }

    @PostMapping("nuovo")
    public ResponseEntity<?> nuovo(@RequestBody RegistaDto registaDto){
        try{
            return ResponseEntity.ok(registaService.nuovo(registaDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody RegistaDto registaDto){
        try{
            registaService.delete(registaDto);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam String query) {
        try{
            return ResponseEntity.ok(registaService.ricerca(query));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
