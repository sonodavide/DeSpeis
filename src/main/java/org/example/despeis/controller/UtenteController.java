package org.example.despeis.controller;

import org.example.despeis.dto.UtenteDto;
import org.example.despeis.services.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/utente")
public class UtenteController {
    private final UtenteService utenteService;

    @Autowired
    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getUtente(JwtAuthenticationToken authenticationToken){

        try{
            return ResponseEntity.ok(utenteService.getById(authenticationToken));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.ok(utenteService.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/userId")
    public ResponseEntity<?> getByUserId(String userId){
        try{
            return ResponseEntity.ok(utenteService.getById(userId));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){

        try{
            return ResponseEntity.ok(utenteService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/cerca")
    public ResponseEntity<?> ricerca(@RequestParam String query, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize) {
        try{
            return ResponseEntity.ok(utenteService.ricerca(query, pageNumber, pageSize));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(utenteService.count());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
