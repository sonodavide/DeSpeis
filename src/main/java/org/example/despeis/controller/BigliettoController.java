package org.example.despeis.controller;

import org.example.despeis.services.BigliettoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/biglietto")
public class BigliettoController {
    private final BigliettoService bigliettoService;

    @Autowired
    public BigliettoController(BigliettoService bigliettoService) {
        this.bigliettoService = bigliettoService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<?> getBiglietti(JwtAuthenticationToken authenticationToken, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(bigliettoService.getByUser(authenticationToken,pageNumber, pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllBiglietti(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(bigliettoService.getAll(pageNumber, pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/allByDate")
    public ResponseEntity<?> getAllByDate(LocalDate date, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(bigliettoService.getByDate(date, pageNumber, pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/date")
    public ResponseEntity<?> getByDate(JwtAuthenticationToken authenticationToken, LocalDate date, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(bigliettoService.getByUserAndDate(authenticationToken, date, pageNumber, pageSize));
        }catch (Exception e ){
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/userIdAndDate")
    public ResponseEntity<?> getByUserIdAndDate(String uesrId, LocalDate date, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(bigliettoService.getByUserAndDate(uesrId, date, pageNumber, pageSize));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/userId")
    public ResponseEntity<?> getByUserId(String userId, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){
        try{
            return ResponseEntity.ok(bigliettoService.getByUser(userId,pageNumber,pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }





    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(bigliettoService.count());
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
