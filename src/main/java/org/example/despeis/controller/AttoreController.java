package org.example.despeis.controller;

import org.apache.coyote.Response;
import org.example.despeis.dto.AttoreDto;
import org.example.despeis.services.AttoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.ok(attoreService.getAll());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){

        try{
            return ResponseEntity.ok(attoreService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/cerca")
    public ResponseEntity<?> ricerca(@RequestParam String query, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize) {
        try{
            return ResponseEntity.ok(attoreService.ricerca(query, pageNumber, pageSize));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@RequestBody AttoreDto attoreDto){
        try{
            return ResponseEntity.ok(attoreService.nuovo(attoreDto));
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@RequestBody AttoreDto attoreDto){
        try{
            attoreService.delete(attoreDto);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(attoreService.count());
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
