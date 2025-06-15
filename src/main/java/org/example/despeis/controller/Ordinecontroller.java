package org.example.despeis.controller;

import org.example.despeis.services.OrdineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ordine")
public class Ordinecontroller {

    private final OrdineService ordineService;
    @Autowired
    public Ordinecontroller(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getOrdini(JwtAuthenticationToken authenticationToken, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(ordineService.getAllByUserPaginated(authenticationToken, pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }


    @PreAuthorize("hasRole('admin')")
    @GetMapping("/paged/user")
    public ResponseEntity<?> getAllByUserPaged(@RequestParam String userId, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(ordineService.getAllByUserPaginated(userId, pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(ordineService.count());
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/incassiTotali")
    public ResponseEntity<?> incassiTotali(){
        try{
            return ResponseEntity.ok(ordineService.incassiTotali());
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
