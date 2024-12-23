package org.example.despeis.controller;

import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.SalaConPostiDto;
import org.example.despeis.dto.SalaDto;
import org.example.despeis.services.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/sala")
public class SalaController {

    private final SalaService salaService;

    @Autowired
    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/nuovo")
    public ResponseEntity<?> nuovo(@Validated @RequestBody SalaConPostiDto sala){
        try{
            return ResponseEntity.ok(salaService.nuovo(sala));
        }
        catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (IllegalStateException e){
            return ResponseEntity.status(409).build();
        }
        catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/modifica")
    public ResponseEntity<?> modifica(@Validated @RequestBody SalaConPostiDto sala){
        try{
            return ResponseEntity.ok(salaService.modifica(sala));
        }
        catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }catch (IllegalStateException e){
            return ResponseEntity.status(409).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@Validated @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){

        try{
            return ResponseEntity.ok(salaService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/postiPerFila")
    public ResponseEntity<?> getPostiPerFila(@Validated @RequestParam Integer salaId){
        try {
            return ResponseEntity.ok(salaService.getSalaConPostiPerFila(salaId));
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@Validated @RequestBody SalaDto sala){
        System.out.println(sala.getId());
        try{
            return ResponseEntity.ok(salaService.delete(sala));
        }catch (IllegalStateException e){
            return ResponseEntity.status(409).build();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(salaService.count());
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


}
