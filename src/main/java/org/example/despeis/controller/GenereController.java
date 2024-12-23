package org.example.despeis.controller;

import org.apache.coyote.BadRequestException;
import org.example.despeis.dto.GenereDto;
import org.example.despeis.services.GenereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genere")
public class GenereController {

    private final GenereService genereService;
    @Autowired
    public GenereController(GenereService genereService) {
        this.genereService = genereService;
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("nuovo")
    public ResponseEntity<?> nuovo(@Validated @RequestBody GenereDto genereDto){
        try{
            return ResponseEntity.ok(genereService.nuovo(genereDto));
        }catch (BadRequestException e){
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/elimina")
    public ResponseEntity<?> elimina(@Validated @RequestBody GenereDto genereDto){
        try{
            genereService.delete(genereDto);
            return ResponseEntity.ok("ok");
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/cerca")
    public ResponseEntity<?> suggest(@RequestParam String query, @RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize) {
        try{
            return ResponseEntity.ok(genereService.ricerca(query, pageNumber, pageSize));
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<?> getAllPaged(@RequestParam(name="pageNumber", defaultValue = "0") int pageNumber, @RequestParam(name="pageSize", defaultValue = "5") int pageSize){

        try{
            return ResponseEntity.ok(genereService.getAllPaginated(pageNumber, pageSize));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/count")
    public ResponseEntity<?> count(){
        try{
            return ResponseEntity.ok(genereService.count());
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/getNomeById")
    public ResponseEntity<?> getNomeById(@RequestParam Integer id){
        try{
            return ResponseEntity.ok(genereService.getNomeById(id));
        }catch (Exception e){
e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
